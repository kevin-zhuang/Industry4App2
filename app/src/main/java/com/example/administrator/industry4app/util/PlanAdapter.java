package com.example.administrator.industry4app.util;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.administrator.industry4app.R;
import com.example.administrator.industry4app.entity.PlanBean;

import java.util.List;

/**
 * Created by Administrator
 * on 2016/6/21.
 */
public class PlanAdapter extends BaseAdapter {

    private Context mContext;
    private List<PlanBean> mList;
    private TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1);

    public PlanAdapter(Context context,List<PlanBean> list){
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        PlanBean planBean = mList.get(position);
        ViewHolder viewHolder;
        if(convertView==null){

            view = LayoutInflater.from(mContext).inflate(R.layout.list_view_plan_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.llTitle = (TableRow)view.findViewById(R.id.llTitle);
            viewHolder.tvOrderId = (TextView)view.findViewById(R.id.tvOrderId);
            viewHolder.tvProductModel = (TextView)view.findViewById(R.id.tvProductModel);
            viewHolder.tvOrderNumber = (TextView)view.findViewById(R.id.tvOrderNumber);
            viewHolder.tvChargeMan = (TextView)view.findViewById(R.id.tvChargeMan);
            viewHolder.tvStartDate = (TextView)view.findViewById(R.id.tvStartDate);
            viewHolder.tvFinishDate = (TextView)view.findViewById(R.id.tvFinishDate);
            viewHolder.tvPlanId = (TextView)view.findViewById(R.id.tvPlanId);
            viewHolder.tvStatus = (TextView)view.findViewById(R.id.tvStatus);

            viewHolder.tvShowOrderId = (TextView)view.findViewById(R.id.tvShowOrderId);
            viewHolder.tvShowProductModel = (TextView)view.findViewById(R.id.tvShowProductModel);
            viewHolder.tvShowOrderNumber = (TextView)view.findViewById(R.id.tvShowOrderNumber);
            viewHolder.tvShowChargeMan = (TextView)view.findViewById(R.id.tvShowChargeMan);
            viewHolder.tvShowStartDate = (TextView)view.findViewById(R.id.tvShowStartDate);
            viewHolder.tvShowFinishDate = (TextView)view.findViewById(R.id.tvShowFinishDate);
            viewHolder.tvShowPlanId = (TextView)view.findViewById(R.id.tvShowPlanId);
            viewHolder.tvShowStatus = (TextView)view.findViewById(R.id.tvShowStatus);

            view.setTag(viewHolder);

        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        Log.e("PlanAdapter","position="+position);
        if(position==0){
            viewHolder.llTitle.setVisibility(View.VISIBLE);
        }else{
            viewHolder.tvShowOrderId.setLayoutParams(layoutParams);
            viewHolder.tvShowProductModel.setLayoutParams(layoutParams);
            viewHolder.tvShowOrderNumber.setLayoutParams(layoutParams);
            viewHolder.tvShowChargeMan.setLayoutParams(layoutParams);
            viewHolder.tvShowStartDate.setLayoutParams(layoutParams);
            viewHolder.tvShowFinishDate.setLayoutParams(layoutParams);
            viewHolder.tvShowPlanId.setLayoutParams(layoutParams);
            viewHolder.tvShowStatus.setLayoutParams(layoutParams);
            viewHolder.llTitle.setVisibility(View.INVISIBLE);

        }


        viewHolder.tvOrderId.setText(planBean.getOrderId());
        viewHolder.tvProductModel.setText(planBean.getProductModel());
        viewHolder.tvOrderNumber.setText(planBean.getOrderNumber());
        viewHolder.tvChargeMan.setText(planBean.getChargeMan());
        viewHolder.tvStartDate.setText(planBean.getStartDate());
        viewHolder.tvFinishDate.setText(planBean.getFinishDate());
        viewHolder.tvPlanId.setText(planBean.getPlanId() + "");
        viewHolder.tvStatus.setText(planBean.getStatus());




        return view;
    }



    private  class  ViewHolder{
        TableRow llTitle;
        TextView tvOrderId;
        TextView tvProductModel;
        TextView tvOrderNumber;
        TextView tvChargeMan;
        TextView tvStartDate;
        TextView tvFinishDate;
        TextView tvPlanId;
        TextView tvStatus;

        TextView tvShowOrderId;
        TextView tvShowProductModel;
        TextView tvShowOrderNumber;
        TextView tvShowChargeMan;
        TextView tvShowStartDate;
        TextView tvShowFinishDate;
        TextView tvShowPlanId;
        TextView tvShowStatus;


    }

}
