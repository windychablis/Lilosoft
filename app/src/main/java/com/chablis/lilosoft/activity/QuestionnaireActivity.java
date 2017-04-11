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
import com.chablis.lilosoft.model.Answer;
import com.chablis.lilosoft.model.Question;
import com.chablis.lilosoft.utils.ToastUtils;
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
    private ArrayList<Question> questions;
    private ArrayList<Answer> answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        getQuestionData();
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
                if(mAdapter!=null){
                   mAdapter.submit();
                }
                break;
        }
    }


    public void getQuestionData() {
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
                questions = gson.fromJson(s, type);
                if (questions!=null) {
                    getAnswerData(questions);
                }else{
                    ToastUtils.showToast(mActivity,"暂无数据");
                }

            }

        }.execute();
    }

    public void getAnswerData(final ArrayList<Question> questions){
        new AsyncTask<String, Integer, ArrayList<Question>>() {

            @Override
            protected ArrayList<Question> doInBackground(String... params) {
                return WebUtil.getAnswer(questions);
            }

            @Override
            protected void onPostExecute(ArrayList<Question> questions) {
                super.onPostExecute(questions);
                mAdapter=new QuestionAdapter(mActivity,questions);
                listview.setAdapter(mAdapter);
            }
        }.execute();

    }

}
