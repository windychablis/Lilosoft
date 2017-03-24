package com.chablis.lilosoft.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.MatterAdapter;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.model.TDDept;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MatterListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.listview)
    ExpandableListView listview;

    private MatterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matter_list);
        ButterKnife.bind(this);

        int position = getIntent().getIntExtra("position", 0);
        TDDept dept=appContext.list_dept.get(position);
        Log.d("MatterListActivity", "dept:" + dept);
        mAdapter=new MatterAdapter(mActivity,null,null);
        listview.setAdapter(mAdapter);

    }

    @OnClick(R.id.tv_back)
    public void onClick() {
        this.finish();
    }
}
