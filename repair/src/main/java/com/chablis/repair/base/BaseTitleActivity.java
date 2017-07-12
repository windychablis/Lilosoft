package com.chablis.repair.base;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chablis.repair.R;

/**
 * Created by chablis on 2017/7/12.
 */

public class BaseTitleActivity extends BaseActivity {
    protected TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvBack= (TextView) findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }
}
