package com.chablis.lilosoft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.Marker;
import com.chablis.lilosoft.R;
import com.chablis.lilosoft.activity.MapActivity;

/**
 * Created by chablis on 2017/4/19.
 */

public class InfoWinAdapter implements AMap.InfoWindowAdapter{

    private Context mContext;
    private TextView tv_address;
    private EditText et_phone;
    private Button btn_send;
    private String agentName;
    private String url;

    public InfoWinAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public InfoWinAdapter(Context mContext,String address,String url) {
        this.mContext = mContext;
        this.agentName=address;
        this.url=url;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        initData(marker);
        View view = initView();
        return view;
    }
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void initData(Marker marker) {
//        latLng = marker.getPosition();
        agentName = marker.getTitle();
        url=marker.getSnippet();
    }

    private View initView() {
        agentName=((MapActivity)mContext).address;
        url=((MapActivity)mContext).msgUrl;
        final View view = LayoutInflater.from(mContext).inflate(R.layout.map_address_info, null);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        et_phone= (EditText) view.findViewById(R.id.et_phone);
        btn_send= (Button) view.findViewById(R.id.btn_send);
        tv_address.setText(agentName);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MapActivity) mContext).showDialog();
//                String str = et_phone.getText().toString();
//                if (CommonUtil.isMobileNO(str)) {
//                    if (agentName.equals("")||url.equals(""))
//                        return;
//                    String content = agentName + "，点击：" + url + "\n查看位置线路、团购优惠、靠谱评论、周边探索";
//                    Log.d("MapActivity", content);
//                    ((MapActivity)mContext).sendMessage(str, content);
//                    UIUtils.HideKeyboard(view);
//                } else {
////                            Toast.makeText(MapActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
//                    ToastUtils.showToast(mContext, "请输入正确的手机号码", ToastUtils.BLACK);
//                }

            }
        });

//        addrTV.setText(String.format(mContext.getString(R.string.agent_addr),snippet));
        return view;
    }



}
