package com.chablis.lilosoft.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.chablis.lilosoft.adapter.GridViewAdapter;
import com.chablis.lilosoft.base.BaseFragment;
import com.chablis.lilosoft.model.IconModel;
import com.chablis.lilosoft.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FirstFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.gridView)
    GridView gridView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<IconModel> iconModels;
    private GridViewAdapter mAdapter;

    public static final int APP_PAGE_SIZE = 16;//每一页装载数据的大小

    private BaseFragment.OnFragmentInteractionListener mListener;


    public FirstFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void initData() {
        Log.d("FirstFragment", "加载数据");

        //初始化数据
        iconModels = new ArrayList<IconModel>();
        IconModel zhaoshangju = new IconModel(R.mipmap.zhaoshangju, "招商局");
        IconModel shiyaojian = new IconModel(R.mipmap.shiyaojian, "食药监");
        IconModel huanbaoju = new IconModel(R.mipmap.huanbaoju, "环保局");
        IconModel weijiwei = new IconModel(R.mipmap.weijiwei, "卫计委");
        IconModel renziju = new IconModel(R.mipmap.renziju, "人资局");
        IconModel shuiwuju = new IconModel(R.mipmap.shuiwuju, "水务局");
        IconModel jiansheju = new IconModel(R.mipmap.jiansheju, "建设局");
        IconModel yuanlinju = new IconModel(R.mipmap.yuanlinju, "园林局");
        IconModel chengguanju = new IconModel(R.mipmap.chengguanju, "城管局");
        IconModel jiaotongju = new IconModel(R.mipmap.jiaotongju, "交通局");
        IconModel dishuiju = new IconModel(R.mipmap.dishuiju, "地税局");
        IconModel jiaoyuju = new IconModel(R.mipmap.jiaoyuju, "教育局");
        IconModel zhijianju = new IconModel(R.mipmap.zhijianju, "质监局");
        IconModel anjianju = new IconModel(R.mipmap.anjianju, "安监局");
        IconModel sifaju = new IconModel(R.mipmap.sifaju, "司法局");
        IconModel fagaiwei = new IconModel(R.mipmap.fagaiwei, "发改委");
        iconModels.add(zhaoshangju);
        iconModels.add(shiyaojian);
        iconModels.add(huanbaoju);
        iconModels.add(weijiwei);
        iconModels.add(renziju);
        iconModels.add(shuiwuju);
        iconModels.add(jiansheju);
        iconModels.add(yuanlinju);
        iconModels.add(chengguanju);
        iconModels.add(jiaotongju);
        iconModels.add(dishuiju);
        iconModels.add(jiaoyuju);
        iconModels.add(zhijianju);
        iconModels.add(anjianju);
        iconModels.add(sifaju);
        iconModels.add(fagaiwei);

        final int PageCount = (int)Math.ceil(iconModels.size()/APP_PAGE_SIZE);
//        for (int i=0;i<PageCount;i++){
//            mAdapter = new GridViewAdapter(mContext, ,i);
//            gridView.setAdapter(mAdapter);
//            gridView.setNumColumns(3);
//        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
