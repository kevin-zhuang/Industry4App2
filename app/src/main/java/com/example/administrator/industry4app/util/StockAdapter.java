package com.example.administrator.industry4app.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.administrator.industry4app.R;
import com.example.administrator.industry4app.entity.StockBean;

import java.util.List;

/**
 * Created by Administrator
 * on 2016/6/21.
 */
public class StockAdapter extends BaseAdapter{

    private Context mContext;
    private List<StockBean> mList;
    private StockBean stockBean;
    private TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,0);
    private int changeBackgroundId=-1;
    private boolean isChanged = false;

    public StockAdapter(Context context,List<StockBean> list){
        mContext = context;
        mList = list;
    }

    public void setChangeId(int changeId,boolean isChange){
        this.changeBackgroundId = changeId;
        this.isChanged = isChange;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        stockBean = mList.get(position);
        if(convertView==null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.list_view_stock_layout,null);
            viewHolder.llTitle = (TableRow)view.findViewById(R.id.llTitle);
            viewHolder.tbContent = (TableRow)view.findViewById(R.id.tbContent);

            viewHolder.tvStockId = (TextView)view.findViewById(R.id.tvStockId);
            viewHolder.tvGroupNum = (TextView)view.findViewById(R.id.tvGroupNum);
            viewHolder.tvRowNum = (TextView)view.findViewById(R.id.tvRowNum);
            viewHolder.tvColumNum = (TextView)view.findViewById(R.id.tvColumNum);
            viewHolder.tvName = (TextView)view.findViewById(R.id.tvName);
            viewHolder.tvType = (TextView)view.findViewById(R.id.tvType);
            viewHolder.tvNum = (TextView)view.findViewById(R.id.tvNum);

            viewHolder.tvShowStockId = (TextView)view.findViewById(R.id.tvShowStockId);
            viewHolder.tvShowGroupNum = (TextView)view.findViewById(R.id.tvShowGroupNum);
            viewHolder.tvShowRowNum = (TextView)view.findViewById(R.id.tvShowRowNum);
            viewHolder.tvShowColumNum = (TextView)view.findViewById(R.id.tvShowColumNum);
            viewHolder.tvShowName = (TextView)view.findViewById(R.id.tvShowName);
            viewHolder.tvShowType = (TextView)view.findViewById(R.id.tvShowType);
            viewHolder.tvShowNum = (TextView)view.findViewById(R.id.tvShowNum);

            view.setTag(viewHolder);

        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        if(position==0){
            viewHolder.llTitle.setVisibility(View.VISIBLE);
        }else{
            viewHolder.tvShowStockId.setLayoutParams(layoutParams);
            viewHolder.tvShowGroupNum.setLayoutParams(layoutParams);
            viewHolder.tvShowRowNum.setLayoutParams(layoutParams);
            viewHolder.tvShowColumNum.setLayoutParams(layoutParams);
            viewHolder.tvShowName.setLayoutParams(layoutParams);
            viewHolder.tvShowType.setLayoutParams(layoutParams);
            viewHolder.tvShowNum.setLayoutParams(layoutParams);
            viewHolder.llTitle.setVisibility(View.INVISIBLE);

        }

        if(changeBackgroundId == position){
            if(isChanged){
                viewHolder.tbContent.setBackgroundResource(R.color.listItemClickColor);
            }else{
                viewHolder.tbContent.setBackgroundResource(R.color.listItemNotClickColor);
            }

        }else{
            viewHolder.tbContent.setBackgroundResource(R.color.listItemNotClickColor);
        }


        viewHolder.tvStockId.setText(stockBean.getStockId()+"");
        viewHolder.tvGroupNum.setText(stockBean.getGroupNum() + "");
        viewHolder.tvRowNum.setText(stockBean.getRowNum()+"");
        viewHolder.tvColumNum.setText(stockBean.getColumNum()+"");
        viewHolder.tvName.setText(stockBean.getName()+"");
        viewHolder.tvType.setText(stockBean.getType()+"");
        viewHolder.tvNum.setText(stockBean.getNum()+"");

        return view;
    }


    private class ViewHolder{
        TextView tvStockId;
        TextView tvGroupNum;
        TextView tvRowNum;
        TextView tvColumNum;
        TextView tvName;
        TextView tvType;
        TextView tvNum;

        TextView tvShowStockId;
        TextView tvShowGroupNum;
        TextView tvShowRowNum;
        TextView tvShowColumNum;
        TextView tvShowName;
        TextView tvShowType;
        TextView tvShowNum;

        TableRow llTitle;
        TableRow tbContent;

    }

}


