package com.chablis.lilosoft.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chablis.lilosoft.R;

/**
 * Created by chablis on 2017/1/16.
 * 圆点指示器
 */

public class RoundNavigationIndicator extends LinearLayout {

    private LinearLayout container;
    private int sum=0;
    private int selected=0;
    private Context context;

    public RoundNavigationIndicator(Context context) {
        super(context);
        this.context=context;
        init(context);
    }


    public RoundNavigationIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init(context);
    }


    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.image_navigation_indicaor_layout, this);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        container=(LinearLayout) findViewById(R.id.indicator);
    }

    //设置圆点总数
    public void setLenght(int sum){
        this.sum=sum;
    }

    //设置选中圆点的下标
    public void setSelected(int selected){
        container.removeAllViews();
        this.selected=selected;
    }


    //绘制指示器
    public void draw(){
        for(int i=0;i<sum;i++){
            ImageView imageview=new ImageView(context);
            imageview.setLayoutParams(new LayoutParams(20, 20));
//            imageview.setPadding(5, 5, 5, 5);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageview.getLayoutParams());
            lp.setMargins(20,0,20,0);
            imageview.setLayoutParams(lp);

            if(i==selected){
                imageview.setImageResource(R.drawable.shape_point_white);
//                imageview.setImageDrawable(getResources().getDrawable(R.drawable.shape_point_white));
            }else{
//                imageview.setImageDrawable(getResources().getDrawable(R.drawable.shape_point_loop));
                imageview.setImageResource(R.drawable.shape_point_loop);
            }
            container.addView(imageview);
        }
    }


}
