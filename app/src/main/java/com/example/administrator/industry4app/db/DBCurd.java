package com.example.administrator.industry4app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.industry4app.entity.PlanBean;
import com.example.administrator.industry4app.entity.StockBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator
 * on 2016/6/22.
 */
public class DBCurd implements DBCurdInterface{
    private static final String TAG = "DBcurd";
    private DBHelper dbHelper;
    private SQLiteDatabase databaseWrite,databaseRead;

    private static DBCurd instance;
    private DBCurd(Context context){
        dbHelper = new DBHelper(context, DBConstant.DB_NAME,null,DBConstant.DB_VERSION);
        //读和写分开处理
        databaseRead = dbHelper.getReadableDatabase();
        databaseWrite = dbHelper.getWritableDatabase();
    }

    public static DBCurd getInstance(Context context){
        if(instance==null){
            instance = new DBCurd(context);
        }
        return instance;
    }


    @Override
    public void addPlanData(PlanBean planBean) {
        if(databaseWrite!=null){
            databaseWrite.beginTransaction();//开启事务
            try{
                databaseWrite.execSQL(DBConstant.INSERT_PLAN_SQL, new Object[]{
                        planBean.getPlanId(),
                        planBean.getOrderId(),
                        planBean.getProductModel(),
                        planBean.getOrderNumber(),
                        planBean.getChargeMan(),
                        planBean.getStartDate(),
                        planBean.getFinishDate(),
                        planBean.getStatus()});
                databaseWrite.setTransactionSuccessful();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                databaseWrite.endTransaction();
            }
        }
    }

    @Override
    public void delAllPlanData() {
        if(databaseWrite!=null){
            databaseWrite.execSQL(DBConstant.DEL_ALL_PLAN_SQL);
        }
    }

    @Override
    public List<PlanBean> selectAllPlanData() {
        List<PlanBean> planBeanList = new ArrayList<>();
        PlanBean planBean;
        Cursor cursor;
        if(databaseRead!=null){
            cursor = databaseRead.rawQuery(DBConstant.SELECT_ALL_PLAN_SQL,null);
            if(cursor.moveToFirst()){
                do{
                    planBean = new PlanBean();
                    planBean.setPlanId(cursor.getString(cursor.getColumnIndex(DBConstant.PLAN_ID)));
                    planBean.setOrderId(cursor.getString(cursor.getColumnIndex(DBConstant.PLAN_ORDER_ID)));
                    planBean.setProductModel(cursor.getString(cursor.getColumnIndex(DBConstant.PLAN_PRODUCT_MODEL)));
                    planBean.setOrderNumber(cursor.getString(cursor.getColumnIndex(DBConstant.PLAN_ORDER_NUMBER)));
                    planBean.setChargeMan(cursor.getString(cursor.getColumnIndex(DBConstant.PLAN_CHARGE_MAN)));
                    planBean.setStartDate(cursor.getString(cursor.getColumnIndex(DBConstant.PLAN_START_DATE)));
                    planBean.setFinishDate(cursor.getString(cursor.getColumnIndex(DBConstant.PLAN_FINISH_DATE)));
                    planBean.setStatus(cursor.getString(cursor.getColumnIndex(DBConstant.LPAN_STATUS)));
                    planBeanList.add(planBean);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return planBeanList;
    }

//    @Override
//    public boolean selectPlanDataById(String id) {
//        boolean isHave = false;
//        Cursor cursor;
//        if(databaseRead!=null){
//            cursor = databaseRead.rawQuery(DBConstant.SELECT_ONE_PLAN_BY_ID_SQL,new String[]{id});
//            if(cursor.moveToFirst()){
//                do{
//                    isHave = true;
//                }while (cursor.moveToNext());
//            }
//            cursor.close();
//        }
//        return isHave;
//    }

    @Override
    public void addStockData(StockBean stockBean) {
        if(databaseWrite!=null){
            databaseWrite.beginTransaction();//开启事务
            try{
                databaseWrite.execSQL(DBConstant.INSERT_STOCK_SQL, new Object[]{
                        stockBean.getStockId(),
                        stockBean.getGroupNum(),
                        stockBean.getRowNum(),
                        stockBean.getColumNum(),
                        stockBean.getName(),
                        stockBean.getType(),
                        stockBean.getNum()});
                databaseWrite.setTransactionSuccessful();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                databaseWrite.endTransaction();
            }
        }
    }

    @Override
    public void delAllStockData() {
        if(databaseWrite!=null){
            databaseWrite.execSQL(DBConstant.DEL_ALL_STOCK_SQL);
        }
    }

    @Override
    public List<StockBean> selectAllStockData() {
        List<StockBean> stockBeanList = new ArrayList<>();
        StockBean stockBean;
        Cursor cursor;
        if(databaseRead!=null){
            cursor = databaseRead.rawQuery(DBConstant.SELECT_ALL_STOCK_SQL,null);
            if(cursor.moveToFirst()){
                do{
                    stockBean = new StockBean();
                    stockBean.setStockId(cursor.getString(cursor.getColumnIndex(DBConstant.STOCK_ID)));
                    stockBean.setGroupNum(cursor.getString(cursor.getColumnIndex(DBConstant.STOCK_GROUP_NUM)));
                    stockBean.setRowNum(cursor.getString(cursor.getColumnIndex(DBConstant.STOCK_ROW_NUM)));
                    stockBean.setColumNum(cursor.getString(cursor.getColumnIndex(DBConstant.STOCK_COLUM_NUM)));
                    stockBean.setName(cursor.getString(cursor.getColumnIndex(DBConstant.STOCK_NAME)));
                    stockBean.setType(cursor.getString(cursor.getColumnIndex(DBConstant.STOCK_TYPE)));
                    stockBean.setNum(cursor.getString(cursor.getColumnIndex(DBConstant.STOCK_NUM)));

                    stockBeanList.add(stockBean);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return stockBeanList;
    }

//    @Override
//    public PlanBean selectPlanDataById(String id) {
//        return null;
//    }
}
