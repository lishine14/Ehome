package com.zzu.ehome.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzu.ehome.R;
import com.zzu.ehome.activity.ECGDetailsActivity;
import com.zzu.ehome.activity.MedicalExaminationActivity;
import com.zzu.ehome.activity.MedicalExaminationDesActivity;
import com.zzu.ehome.bean.MedicalBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/18.
 */
public class MedicalExaminationAdapter extends BaseAdapter {


    public void setmList(List<MedicalBean> mList) {
        this.mList = mList;
    }

    private List<MedicalBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    public MedicalExaminationAdapter(Context context, List<MedicalBean> objects) {

        this.mList=objects;
        this.mContext=context;
        mInflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public List<MedicalBean> getmList() {
        return mList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        MedicalBean itme=(MedicalBean)getItem(position);
        if(convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.ecg_item,null);
            holder.name=(TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        String time = itme.getCheckTime().split(" ")[0];
        String[] yeartime = time.split("/");


        final String title=yeartime[0] + "年" + yeartime[1] + "月" + yeartime[2] + "日" + "体检报告";
        holder.name.setText(title);
        final String id=itme.getID();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, MedicalExaminationDesActivity.class);
                i.putExtra("Title",title);
                i.putExtra("ID", id);
                mContext.startActivity(i);
            }
        });
        return convertView;
    }

    public static  class ViewHolder{
        private TextView name;
    }


}