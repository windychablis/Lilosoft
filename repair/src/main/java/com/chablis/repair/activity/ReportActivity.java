package com.chablis.repair.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.chablis.repair.R;
import com.chablis.repair.widget.ImageManagerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportActivity extends AppCompatActivity {

    @BindView(R.id.image_managerView)
    ImageManagerView imageManagerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        imageManagerView.setOnImageClickListener(new ImageManagerView.OnImageClickListener() {
            @Override
            public void onImageClick(int position, String filePath, ImageView iv) {

            }

            @Override
            public void onDeleteClick(int position, String filePath) {
                imageManagerView.removeImage(position);
            }

            @Override
            public void onAddClick() {
                imageManagerView.addImage(null);
            }
        });
    }
}
