package com.chablis.lilosoft.activity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.QuestionAdapter;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.base.BaseFragment;
import com.chablis.lilosoft.model.Question;
import com.chablis.lilosoft.utils.WebUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 问卷调查界面
 */
public class QuestionnaireActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener {

    @BindView(R.id.listview)
    ListView listview;

    private QuestionAdapter mAdapter;
    private ArrayList<Question> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        getData();
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

    public void getData() {
        final String id = getIntent().getStringExtra("id");
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return WebUtil.getQuestion(id);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Type type = new TypeToken<ArrayList<Question>>() {
                }.getType();
                Gson gson = new Gson();
                data = gson.fromJson(s, type);
                mAdapter = new QuestionAdapter(mActivity, data);
                listview.setAdapter(mAdapter);
            }

        }.execute();
    }
}
