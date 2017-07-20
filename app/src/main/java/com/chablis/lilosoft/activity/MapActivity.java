package com.chablis.lilosoft.activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.LatLonSharePoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.share.ShareSearch;
import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.InfoWinAdapter;
import com.chablis.lilosoft.adapter.MapAddressAdapter;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.base.BaseFragment;
import com.chablis.lilosoft.base.Global;
import com.chablis.lilosoft.model.MapAddress;
import com.chablis.lilosoft.utils.CommonUtil;
import com.chablis.lilosoft.utils.ComparatorList;
import com.chablis.lilosoft.utils.ToastUtils;
import com.chablis.lilosoft.utils.WebUtil;
import com.chablis.lilosoft.widget.BladeView;
import com.chablis.lilosoft.widget.PinnedHeaderListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 地图导航界面
 */
public class MapActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener, LocationSource,
        AMapLocationListener, AMap.OnMarkerClickListener,
        GeocodeSearch.OnGeocodeSearchListener, ShareSearch.OnShareSearchListener, AMap.OnMapLoadedListener {
    private SlidingMenu menu;

    @BindView(R.id.map)
    MapView mapView;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private MarkerOptions markerOption;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private GeocodeSearch geocoderSearch;
    private ShareSearch mShareSearch;
    private InfoWinAdapter infoWinAdapter;
    private Marker currentMarker;

    private static final String FORMAT = "^[a-z,A-Z].*$";
    private PinnedHeaderListView mListView;
    private BladeView mLetter;
    private MapAddressAdapter mAdapter;
    private String[] datas;
    ArrayList<MapAddress> mapAddresses;
    // 首字母集
    private List<String> mSections;
    // 根据首字母存放数据
    private Map<String, List<String>> mMap;
    // 首字母位置集
    private List<Integer> mPositions;
    // 首字母对应的位置
    private Map<String, Integer> mIndexer;

    private String[] currentAddressInfo = new String[2];
    public String address = "";

    public String msgUrl = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        mShareSearch = new ShareSearch(this.getApplicationContext());
        mShareSearch.setOnShareSearchListener(this);
        getData();
        initMap();
    }

    public void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
            aMap.setOnMarkerClickListener(this);
        }
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        aMap.setOnMapLoadedListener(this);
    }

    public void initMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
//        menu.setFadeDegree(0.35f);
//        menu.setShadowDrawable(R.drawable.shadow);
//        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        // 为侧滑菜单设置布局
        menu.setMenu(R.layout.menu_map);

        // TODO Auto-generated method stub
        mListView = (PinnedHeaderListView) findViewById(R.id.pinnedListView);
        mLetter = (BladeView) findViewById(R.id.friends_myletterlistview);
        mLetter.setOnItemClickListener(new BladeView.OnItemClickListener() {

            @Override
            public void onItemClick(String s) {
                if (mIndexer.get(s) != null) {
                    mListView.setSelection(mIndexer.get(s));
                }
            }
        });
        if (mapAddresses != null) {
            initData();
            mAdapter = new MapAddressAdapter(this, mapAddresses, mSections, mPositions);
            mListView.setAdapter(mAdapter);
            mListView.setOnScrollListener(mAdapter);
            mListView.setPinnedHeaderView(LayoutInflater.from(this).inflate(
                    R.layout.listview_head, mListView, false));
        } else {
            ToastUtils.showToast(mActivity, "暂无数据");
        }
    }


    private void initData() {
//        datas = getResources().getStringArray(R.array.countries);
        mSections = new ArrayList<String>();
        mMap = new HashMap<String, List<String>>();
        mPositions = new ArrayList<Integer>();
        mIndexer = new HashMap<String, Integer>();

        for (int i = 0; i < mapAddresses.size(); i++) {
            MapAddress address = mapAddresses.get(i);
            String firstName = address.getChina_initial().substring(0, 1).toUpperCase();
            if (firstName.matches(FORMAT)) {
                if (mSections.contains(firstName)) {
                    mMap.get(firstName).add(address.getArea_name());
                } else {
                    mSections.add(firstName);
                    List<String> list = new ArrayList<String>();
                    list.add(address.getArea_name());
                    mMap.put(firstName, list);
                }
            } else {
                if (mSections.contains("#")) {
                    mMap.get("#").add(address.getArea_name());
                } else {
                    mSections.add("#");
                    List<String> list = new ArrayList<String>();
                    list.add(address.getArea_name());
                    mMap.put("#", list);
                }
            }
        }

        Collections.sort(mSections);
        ComparatorList.sort(mapAddresses);
        int position = 0;
        for (int i = 0; i < mSections.size(); i++) {
            mIndexer.put(mSections.get(i), position);// 存入map中，key为首字母字符串，value为首字母在listview中位置
            mPositions.add(position);// 首字母在listview中位置，存入list中
            position += mMap.get(mSections.get(i)).size();// 计算下一个首字母在listview的位置
        }

    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick({R.id.tv_back, R.id.tv_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.tv_menu:
                //TODO
                mapToggle();
                break;
        }
    }

    public void mapToggle() {
        if (menu != null)
            menu.toggle();
    }


    public void getData() {
        new AsyncTask<String, Integer, String>() {

            @Override
            protected String doInBackground(String... params) {
                return WebUtil.getMapData(Global.areacode);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    Type type = new TypeToken<ArrayList<MapAddress>>() {
                    }.getType();
                    Gson gson = new Gson();
                    try {
                        mapAddresses = gson.fromJson(s, type);
                        Log.d("MapActivity", "mapAddresses:" + mapAddresses);
                        initMenu();

                    } catch (Exception e) {
                        CommonUtil.saveLog("error", e.getMessage());
                    }
                }
            }
        }.execute();
    }


    /**
     * 根据经纬度获取位置信息
     */
    public void getAddressInfo(LatLonPoint latLng) {
        RegeocodeQuery query = new RegeocodeQuery(latLng, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }

    public void sendMessage(final String mobile, final String content) {
        new AsyncTask<String, Integer, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                return WebUtil.sendMessage(mobile, content);
            }

            @Override
            protected void onPostExecute(Boolean s) {
                super.onPostExecute(s);
                if (s) {
                    ToastUtils.showToast(mActivity, "发送成功", ToastUtils.BLACK);
                    currentMarker.hideInfoWindow();
                } else {
                    ToastUtils.showToast(mActivity, "发送失败", ToastUtils.BLACK);
                }

            }
        }.execute();
    }


    public void serchMap(double lat, double lng) {
        aMap.clear();
        final LatLng point = new LatLng(lat, lng);
        LatLonPoint point1 = new LatLonPoint(lat, lng);
        getAddressInfo(point1);
        if (!address.equals("")) {
            LatLonSharePoint sharePoint = new LatLonSharePoint(point1.getLatitude(),
                    point1.getLongitude(), address);
            mShareSearch.searchLocationShareUrlAsyn(sharePoint);
        }

        markerOption = new MarkerOptions();
        markerOption.position(point);
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(R.mipmap.dingwei));
        markerOption.title(address);
        markerOption.snippet(msgUrl);
        aMap.addMarker(markerOption);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(point));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
//        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        setupLocationStyle();
    }

    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.mipmap.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(3);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
//         将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //只定位一次
            mLocationOption.setOnceLocation(true);
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//            mLocationOption.setInterval(1000*60);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
//        if (!address.equals("")&& !msgUrl.equals("")) {
        currentMarker = marker;
        infoWinAdapter = new InfoWinAdapter(mActivity);
        aMap.setInfoWindowAdapter(infoWinAdapter);
//        }
        return false;
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                String addressName = result.getRegeocodeAddress().getFormatAddress();
                Log.d("MapActivity", addressName);
                currentAddressInfo[0] = addressName;
                address = addressName;
            }
        } else {
            Log.d("MapActivity", "rCode:" + rCode);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


    @Override
    public void onPoiShareUrlSearched(String s, int i) {

    }

    @Override
    public void onLocationShareUrlSearched(String url, int errorCode) {
        // TODO Auto-generated method stub
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            msgUrl = url;
            Log.d("MapActivity", url);
        } else {
            Log.d("MapActivity", "errorCode:" + errorCode);
        }
    }

    @Override
    public void onNaviShareUrlSearched(String s, int i) {

    }

    @Override
    public void onBusRouteShareUrlSearched(String s, int i) {

    }

    @Override
    public void onWalkRouteShareUrlSearched(String s, int i) {

    }

    @Override
    public void onDrivingRouteShareUrlSearched(String s, int i) {

    }

    @Override
    public void onMapLoaded() {
    }
}
