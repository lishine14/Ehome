package com.zzu.ehome.activity;

import android.os.Bundle;

import com.zzu.ehome.R;
import com.zzu.ehome.view.HeadView;

/**
 * Created by Mersens on 2016/8/17.
 */
public class NearPharmacyMapActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.layout_near_pharmacy_map);
        initViews();
        initEvent();
        initDatas();
    }



    public void initViews(){
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "附近药店", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finishActivity();
            }
        });

    }

    public void initEvent(){

    }

    public void initDatas(){

    }
}
