package com.chablis.lilosoft.activity;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.share.LocationShareURLOption;
import com.baidu.mapapi.search.share.OnGetShareUrlResultListener;
import com.baidu.mapapi.search.share.ShareUrlResult;
import com.baidu.mapapi.search.share.ShareUrlSearch;
import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.MapAddressAdapter;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.base.BaseFragment;
import com.chablis.lilosoft.base.Global;
import com.chablis.lilosoft.model.MapAddress;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 地图导航界面
 */
public class MapActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener, OnGetShareUrlResultListener {
    private SlidingMenu menu;
    public LocationClient mLocationClient = null;
    public MyLocationListener myLocationListner =
            new MyLocationListener();
    private BaiduMap baiduMap;//百度地图
    private boolean isFirstIn = true;

    @BindView(R.id.bmapView)
    TextureMapView bmapView;

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

    private String msgUrl="";

    public void initMap() {
        baiduMap = bmapView.getMap();
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(16).build()));//设置缩放级别
        //开启定位服务
        mLocationClient = new LocationClient(appContext);
        mLocationClient.registerLocationListener(myLocationListner);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开GPS
        option.setCoorType("bd09ll");// 设置坐标类型,返回国测局经纬度坐标系：gcj02 返回百度墨卡托坐标系 ：bd09 返回百度经纬度坐标系 ：bd09ll
        option.setScanSpan(1000 * 30);//设置扫描间隔，单位是毫秒
        mLocationClient.setLocOption(option);

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
        }else{
            ToastUtils.showToast(mActivity,"暂无数据");
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
            String firstName = address.getChina_initial().substring(0, 1);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        getData();
        initMap();
        mLocationClient.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MapActivity", "des");
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        bmapView.onDestroy();
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        baiduMap.setMyLocationEnabled(false);
        bmapView = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        bmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        bmapView.onPause();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick({R.id.tv_back, R.id.iv_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.iv_menu:
                //TODO
                if (menu!=null)
                menu.toggle();
                break;
        }
    }

    @Override
    public void onGetPoiDetailShareUrlResult(ShareUrlResult shareUrlResult) {

    }

    @Override
    public void onGetLocationShareUrlResult(ShareUrlResult shareUrlResult) {
        Log.d("MapActivity", shareUrlResult.getUrl());
        msgUrl=shareUrlResult.getUrl();
    }

    @Override
    public void onGetRouteShareUrlResult(ShareUrlResult shareUrlResult) {

    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(location.getRadius())//getRadius 获取定位精度,默认值0.0f
                    .latitude(latitude)//百度纬度坐标
                    .longitude(longitude)//百度经度坐标
                    .build();
            //设置定位数据, 只有先允许定位图层后设置数据才会生效，参见 setMyLocationEnabled(boolean)
            baiduMap.setMyLocationData(data);
            if (isFirstIn) {
                //地理坐标基本数据结构
                LatLng latLng = new LatLng(latitude, longitude);
                //描述地图状态将要发生的变化,通过当前经纬度来使地图显示到该位置
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                //改变地图状态
                baiduMap.setMapStatus(msu);
                isFirstIn = false;
//                Toast.makeText(MapActivity.this, location.getAddrStr(), Toast.LENGTH_SHORT).show();
            }

            /*//获取定位结果
            StringBuffer sb = new StringBuffer(256);

            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间

            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型

            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息

            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息

            sb.append("\nradius : ");
            sb.append(location.getRadius());    //获取定位精准度

            if (location.getLocType() == BDLocation.TypeGpsLocation) {

                // GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // 单位：公里每小时

                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //获取卫星数

                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //获取海拔高度信息，单位米

                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //获取方向信息，单位度

                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {

                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息

                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");

            } else if (location.getLocType() == BDLocation.TypeServerError) {

                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

            }

            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息

            List<Poi> list = location.getPoiList();    // POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }

            Log.i("BaiduLocationApiDem", sb.toString());*/
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
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
                if (s!=null) {
                    Type type = new TypeToken<ArrayList<MapAddress>>() {
                    }.getType();
                    Gson gson = new Gson();
                    mapAddresses = gson.fromJson(s, type);
                    initMenu();
                }
            }
        }.execute();
    }

    public void serchMap(double lat, double lng) {
        baiduMap.clear();
        //定义Maker坐标点
        final LatLng point = new LatLng(lat, lng);

        //获取坐标的地理信息
        getAddressInfo(point);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.dingwei);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
//        baiduMap.addOverlay(option);
        Marker marker = (Marker) (baiduMap.addOverlay(option));
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                View popupWindow = getLayoutInflater().inflate(R.layout.map_address_info, null);
                TextView address = (TextView) popupWindow.findViewById(R.id.tv_address);
                final EditText phone = (EditText) popupWindow.findViewById(R.id.et_phone);
                Button btn = (Button) popupWindow.findViewById(R.id.btn_send);
                address.setText(currentAddressInfo[0]);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str = phone.getText().toString();
                        if (isMobileNO(str)) {
//                            Toast.makeText(MapActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            String content=currentAddressInfo[0]+"，点击："+msgUrl+"\n查看位置线路、团购优惠、靠谱评论、周边探索";
                            sendMessage(str,content);
                        } else {
//                            Toast.makeText(MapActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                            ToastUtils.showToast(mActivity,"请输入正确的手机号码",ToastUtils.BLACK);
                        }
                    }
                });
                PopupWindow infoPopupWindow = new PopupWindow(popupWindow, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                infoPopupWindow.setFocusable(true);
                infoPopupWindow.setOutsideTouchable(true);
                infoPopupWindow.setBackgroundDrawable(new BitmapDrawable());
                infoPopupWindow.showAtLocation(bmapView, Gravity.CENTER, 0, -160);
                //让地图以备点击的覆盖物为中心
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(point);
                baiduMap.setMapStatus(status);
                return true;
            }
        });

//        //设定中心点坐标
//        //定义地图状态
//        MapStatus mMapStatus = new MapStatus.Builder()
//                //要移动的点
//                .target(point)
//                //放大地图到20倍
//                .zoom(20)
//                .build();
//        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
//
//        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//        //改变地图状态
//        baiduMap.setMapStatus(mMapStatusUpdate);

        //描述地图状态将要发生的变化,通过当前经纬度来使地图显示到该位置
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(point);
        //改变地图状态
        baiduMap.setMapStatus(msu);

        menu.toggle();


    }


    /**
     * 根据经纬度获取位置信息
     */
    public void getAddressInfo(final LatLng latLng) {
        ShareUrlSearch mShareUrlSearch = null;
        mShareUrlSearch = ShareUrlSearch.newInstance();
        mShareUrlSearch.setOnGetShareUrlResultListener(this);


        //新建编码查询对象
        GeoCoder geocode = GeoCoder.newInstance();
        //新建查询对象要查询的条件
        ReverseGeoCodeOption options = new ReverseGeoCodeOption().location(latLng);
        // 发起反地理编码请求
        geocode.reverseGeoCode(options);
        //设置查询结果监听者
        final ShareUrlSearch finalMShareUrlSearch = mShareUrlSearch;
        geocode.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {


            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            /**
             * 反地理编码查询结果回调函数
             * @param reverseGeoCodeResult  反地理编码查询结果
             */
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    return;
                }
                if (reverseGeoCodeResult != null && reverseGeoCodeResult.error == SearchResult.ERRORNO.NO_ERROR) {

                    //得到位置
                    String address = reverseGeoCodeResult.getAddress();
                    finalMShareUrlSearch.requestLocationShareUrl(new LocationShareURLOption().location(latLng).name(address).snippet(reverseGeoCodeResult.getSematicDescription()));

                    Log.d("MapActivity", "reverseGeoCodeResult:" + reverseGeoCodeResult.getAddress());
                    Log.d("MapActivity", reverseGeoCodeResult.getSematicDescription());
                    currentAddressInfo[0] = reverseGeoCodeResult.getAddress();
                    currentAddressInfo[1] = reverseGeoCodeResult.getSematicDescription();
                }
            }
        });
    }

    public void sendMessage(final String mobile, final String content){
        new AsyncTask<String, Integer, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                return WebUtil.sendMessage(mobile, content);
            }

            @Override
            protected void onPostExecute(Boolean s) {
                super.onPostExecute(s);
                if (s) {
                    ToastUtils.showToast(mActivity,"发送成功",ToastUtils.BLACK);
                }else{
                    ToastUtils.showToast(mActivity,"发送失败",ToastUtils.BLACK);
                }

            }
        }.execute();
    }


    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(14[57])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

}
