package com.chablis.lilosoft.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.activity.QuestionnaireActivity;
import com.chablis.lilosoft.model.Answer;
import com.chablis.lilosoft.model.Question;
import com.chablis.lilosoft.utils.WebUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 完善资料的适配器
 *
 * @author chenhao
 * @date 2015-05-04
 */
public class QuestionAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater = null;
    private ArrayList<Question> data;

    private final int TYPE_ONE = 0;
    private final int TYPE_MORE = 1;
    private HashMap<String, String> ids_rb = new HashMap<String, String>();
    private HashMap<String, String> ids_cb = new HashMap<String, String>();

    public QuestionAdapter(Context context, ArrayList<Question> data) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Question getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        String str = getItem(position).getSelect_type();
        return Integer.parseInt(str);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder_one = null;
        Holder holder_more = null;
        final Question question = (Question) getItem(position);
        final ArrayList<Answer> answers = question.getAnswers();
        int type = getItemViewType(position);
        if (convertView == null) {
            holder_one = new Holder();
            if (type == TYPE_ONE) {
                //单选按钮一个问题一个id
                convertView = mInflater.inflate(R.layout.item_questionnaire_rb, parent,
                        false);
                RadioGroup radioGroup = (RadioGroup) convertView.findViewById(R.id.radio_group);
                for (int i = 0; i < answers.size(); i++) {
                    addRB(radioGroup, answers.get(i),i);
                }
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        Answer answer = answers.get(checkedId);
                        ids_rb.put(question.getVote_c_id(), answer.getVote_id());
                        //TODO 保存所有答案的id
                    }
                });

                holder_one.tv_no = (TextView) convertView.findViewById(R.id.tv_no);
                holder_one.tv_question = (TextView) convertView.findViewById(R.id.tv_question);
                convertView.setTag(holder_one);
            } else {
                holder_more = new Holder();
                convertView = mInflater.inflate(R.layout.item_questionnaire_cb, parent,
                        false);
                LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_cb);
                for (int i = 0; i < answers.size(); i++) {
                    addCB(linearLayout, answers.get(i), i);
                }


                holder_more.tv_no = (TextView) convertView.findViewById(R.id.tv_no);
                holder_more.tv_question = (TextView) convertView.findViewById(R.id.tv_question);
                convertView.setTag(holder_more);
            }

        } else {
            if (type == TYPE_ONE) {
                holder_one = (Holder) convertView.getTag();
            } else if (type == TYPE_MORE) {
                holder_more = (Holder) convertView.getTag();
            }

        }
        if (type == TYPE_ONE) {
            String no = position + 1 + "";
            holder_one.tv_no.setText(no);
            holder_one.tv_question.setText(question.getVote_title());
        } else if (type == TYPE_MORE) {
            String no = position + 1 + "";
            holder_more.tv_no.setText(no);
            holder_more.tv_question.setText(question.getVote_title());
        }


        return convertView;
    }

    static class Holder {
        private TextView tv_no;
        private TextView tv_question;
    }

    public void addRB(RadioGroup radioGroup, Answer answer,int position) {
        Resources resources = context.getResources();
        RadioButton radioButton = new RadioButton(context);
        Drawable drawable = resources.getDrawable(R.drawable.d_question_radio);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        radioButton.setButtonDrawable(null);
        radioButton.setCompoundDrawables(drawable, null, null, null);
        radioButton.setText(answer.getOpt_content());
        radioButton.setTextSize(24);
        radioButton.setTextColor(resources.getColor(R.color.color_liuliuliu));
        radioButton.setCompoundDrawablePadding(20);
        radioButton.setId(position);


        TextView textView = new TextView(context);
        textView.setBackground(resources.getDrawable(R.drawable.ants_line));
        textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);

        radioGroup.addView(radioButton);
        radioGroup.addView(textView);

    }

    public void addCB(LinearLayout linearLayout, final Answer answer, int position) {
        Resources resources = context.getResources();
        CheckBox checkBox = new CheckBox(context);
        Drawable drawable = resources.getDrawable(R.drawable.d_question_check);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        checkBox.setButtonDrawable(null);
        checkBox.setCompoundDrawables(drawable, null, null, null);
        checkBox.setText(answer.getOpt_content());
        checkBox.setTextSize(24);
        checkBox.setTextColor(resources.getColor(R.color.color_liuliuliu));
        checkBox.setCompoundDrawablePadding(20);
        checkBox.setId(position);

        TextView textView = new TextView(context);
        textView.setBackground(resources.getDrawable(R.drawable.ants_line));
        textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);

        linearLayout.addView(checkBox);
        linearLayout.addView(textView);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ids_cb.put(answer.getVote_id(), answer.getVote_id());
                } else {
                    ids_cb.remove(answer.getVote_id());
                }
            }
        });

    }


    /**
     * 提交问卷
     */
    public void submit() {
//        Log.d("QuestionAdapter", "ids:" + ids_rb.toString());
        String ids="";
        Iterator iter = ids_cb.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String val = (String) entry.getValue();
            ids+=val+",";
        }
        ids=ids.substring(0,ids.length()-1);

        Iterator iter2 = ids_rb.entrySet().iterator();
        while (iter2.hasNext()) {
            Map.Entry entry = (Map.Entry) iter2.next();
            String val = (String) entry.getValue();
            ids+=","+val;
        }
        updateQuestionnaire(ids);
    }

    public void updateQuestionnaire(final String ids) {
        new AsyncTask<String, Integer, Boolean>() {


            @Override
            protected Boolean doInBackground(String... params) {
                return WebUtil.updateQuestionnaire(ids);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                Log.d("QuestionAdapter", "aBoolean:" + aBoolean);
                if(aBoolean){
                    Toast.makeText(context, "问卷提交成功", Toast.LENGTH_SHORT).show();
                    ((QuestionnaireActivity)context).finish();
                }
            }
        }.execute();
    }

}
