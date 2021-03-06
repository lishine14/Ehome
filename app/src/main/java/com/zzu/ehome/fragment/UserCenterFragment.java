package com.zzu.ehome.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzu.ehome.R;
import com.zzu.ehome.activity.AboutEhomeActivity;
import com.zzu.ehome.activity.AdviceActivity;
import com.zzu.ehome.activity.AppointmentActivity1;
import com.zzu.ehome.activity.LoginActivity1;
import com.zzu.ehome.activity.MyHome;
import com.zzu.ehome.activity.MyRemindActivity1;
import com.zzu.ehome.activity.MyReportActivity;
import com.zzu.ehome.activity.SettingActivity;
import com.zzu.ehome.bean.RefreshEvent;
import com.zzu.ehome.bean.ShareModel;
import com.zzu.ehome.bean.User;
import com.zzu.ehome.db.EHomeDao;
import com.zzu.ehome.db.EHomeDaoImpl;
import com.zzu.ehome.utils.CommonUtils;
import com.zzu.ehome.utils.ImageUtil;
import com.zzu.ehome.utils.SharePreferenceUtil;
import com.zzu.ehome.view.SharePopupWindow;

import de.greenrobot.event.EventBus;

/**
 * Created by Mersens on 2016/8/5.
 */
public class UserCenterFragment extends BaseFragment implements View.OnClickListener {
    private View mView;
    private TextView tvGoMyFaimily;
    private String userid;
    private ImageView icon_user;
    private TextView tv_name;
    private RelativeLayout layout_msg, layout_wdda, layout_wdbg,
            layout_wdyy, layout_wdtx, layout_wdgz, layout_yqhy,
            layout_yjfk, layout_about, layout_setting;
    private EHomeDao dao;
    private TextView tv_msg;
    private View vTop;
    private SharePopupWindow share;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_user_center, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userid = SharePreferenceUtil.getInstance(getActivity()).getUserId();
        EventBus.getDefault().register(this);
        dao = new EHomeDaoImpl(getActivity());
        mView = view;
        initViews();
        initEvent();
        initDatas();
    }

    public void onEventMainThread(RefreshEvent event) {
        if (getResources().getInteger(R.integer.refresh_info) == event
                .getRefreshWhere()) {
            userid = SharePreferenceUtil.getInstance(getActivity()).getUserId();
            initDatas();
        }
    }


    public void initViews() {
        layout_wdda = (RelativeLayout) mView.findViewById(R.id.layout_wdda);
        layout_wdbg = (RelativeLayout) mView.findViewById(R.id.layout_wdbg);
        layout_wdyy = (RelativeLayout) mView.findViewById(R.id.layout_wdyy);
        layout_wdtx = (RelativeLayout) mView.findViewById(R.id.layout_wdtx);
        layout_wdgz = (RelativeLayout) mView.findViewById(R.id.layout_wdgz);
        layout_yqhy = (RelativeLayout) mView.findViewById(R.id.layout_yqhy);
        layout_yjfk = (RelativeLayout) mView.findViewById(R.id.layout_yjfk);
        layout_about = (RelativeLayout) mView.findViewById(R.id.layout_about);
        layout_setting = (RelativeLayout) mView.findViewById(R.id.layout_setting);
        tv_name = (TextView) mView.findViewById(R.id.tv_name);
        layout_msg = (RelativeLayout) mView.findViewById(R.id.layout_msg);
        tvGoMyFaimily = (TextView) mView.findViewById(R.id.tvGoMyFaimily);
        icon_user = (ImageView) mView.findViewById(R.id.icon_user);
        tv_msg = (TextView) mView.findViewById(R.id.tv_msg);
        vTop = mView.findViewById(R.id.v_top);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int h = CommonUtils.getStatusHeight(getActivity());
            ViewGroup.LayoutParams params = vTop.getLayoutParams();
            params.height = h;
            params.width = ViewGroup.LayoutParams.FILL_PARENT;
            vTop.setLayoutParams(params);
            vTop.setBackgroundResource(R.color.actionbar_color);
        } else {
            vTop.setVisibility(View.GONE);
        }

    }


    public void initEvent() {
        layout_wdda.setOnClickListener(this);
        layout_wdbg.setOnClickListener(this);
        layout_wdyy.setOnClickListener(this);
        layout_wdtx.setOnClickListener(this);
        layout_wdgz.setOnClickListener(this);
        layout_yqhy.setOnClickListener(this);
        layout_yjfk.setOnClickListener(this);
        layout_about.setOnClickListener(this);
        layout_setting.setOnClickListener(this);
        tvGoMyFaimily.setOnClickListener(this);
    }


    public void initDatas() {
        if (TextUtils.isEmpty(userid)) {
            tv_name.setVisibility(View.GONE);
            layout_msg.setVisibility(View.GONE);
            icon_user.setImageResource(R.drawable.icon_uesr_tx2);
        } else {
            User user = dao.findUserInfoById(userid);
            if (user != null) {
                icon_user.setImageResource(R.drawable.icon_uesr_tx);
                ImageLoader.getInstance().displayImage(user.getImgHead(), icon_user);
                tv_name.setText(user.getUsername());
                StringBuffer sbf = new StringBuffer();
                String sex = user.getSex();
                String age = user.getAge();
                String height = user.getUserHeight();
                if (!TextUtils.isEmpty(sex)) {
                    if ("01".equals(sex)) {
                        sbf.append("男");
                    } else if ("02".equals(sex)) {
                        sbf.append("女");
                    }
                }
                if (!TextUtils.isEmpty(age)) {
                    sbf.append(" | " + age + "岁");

                }
                if (TextUtils.isEmpty(height)) {
                    sbf.append(" | " + height + "cm");
                }
                String str = sbf.toString();
                if (!TextUtils.isEmpty(str)) {
                    layout_msg.setVisibility(View.VISIBLE);
                    tv_msg.setText(str);
                }
            }

        }
    }


    public static Fragment getInstance() {
        return new UserCenterFragment();
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvGoMyFaimily:
                doGoFamily();
                break;
            case R.id.layout_wdda:
                //我的档案

                break;
            case R.id.layout_wdbg:
                //我的报告
                startActivity(new Intent(getActivity(), MyReportActivity.class));
                break;
            case R.id.layout_wdyy:
                //我的预约
                startActivity(new Intent(getActivity(), AppointmentActivity1.class));
                break;
            case R.id.layout_wdtx:
                //我的提醒
                startActivity(new Intent(getActivity(), MyRemindActivity1.class));

                break;
            case R.id.layout_wdgz:
                //我的关注

                break;
            case R.id.layout_yqhy:
                //邀请好友
                doInvite();
                break;
            case R.id.layout_yjfk:
                //意见反馈
                startActivity(new Intent(getActivity(), AdviceActivity.class));
                break;
            case R.id.layout_about:
                //关于App
                startActivity(new Intent(getActivity(), AboutEhomeActivity.class));
                break;
            case R.id.layout_setting:
                //设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }

    }

    public void doInvite(){
        share = new SharePopupWindow(getActivity());
        ShareModel model = new ShareModel();
        model.setImgPath(ImageUtil.saveResTolocal(getActivity().getResources(),R.drawable.share,"home_logo"));
        model.setText("跟我一起关注个人和家人健康吧，还可以预约网络视频问诊哦!");
        model.setTitle("个人健康数据管理专家");
        model.setUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.zzu.ehome.main.ehome");
        share.initShareParams(model);
        share.showShareWindow();
        share.showAtLocation(getActivity().findViewById(R.id.set),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void doGoFamily() {

        userid = SharePreferenceUtil.getInstance(getActivity()).getUserId();

        if (!TextUtils.isEmpty(userid)) {
            startActivity(new Intent(getActivity(), MyHome.class));
        } else {
            startActivity(new Intent(getActivity(), LoginActivity1.class));
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
