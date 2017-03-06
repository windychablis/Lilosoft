package com.chablis.lilosoft.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.QuestionAdapter;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionnaireActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener {

    @BindView(R.id.listview)
    ListView listview;

    private QuestionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        ButterKnife.bind(this);
        initView();
    }

    public void initView(){
        mAdapter=new QuestionAdapter(mActivity,null);
        listview.setAdapter(mAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick({R.id.tv_back, R.id.btn_OK})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.btn_OK:
                break;
        }
    }
}
