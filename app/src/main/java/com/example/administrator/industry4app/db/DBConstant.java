package com.example.administrator.industry4app.db;

/**
 * Created by Administrator
 * on 2016/6/22.
 */
public class DBConstant {
    public static final String DB_NAME = "industry.db";
    public static int DB_VERSION = 1;
    public static final String TABLE_NAME_PLAN = "plan";
    public static final String TABLE_NAME_STOCK = "stock";


    //=======================PLAN=====================================
    //plan字段
    public static final String PLAN_ID = "plan_id";
    public static final String PLAN_ORDER_ID = "order_id";
    public static final String PLAN_PRODUCT_MODEL = "product_model";
    public static final String PLAN_ORDER_NUMBER = "order_number";
    public static final String PLAN_CHARGE_MAN = "charge_man";
    public static final String PLAN_START_DATE = "start_date";
    public static final String PLAN_FINISH_DATE = "finish_date";
    public static final String LPAN_STATUS = "status";

    public static final String CREATEE_PLAN_DB_SQL = "create table "+TABLE_NAME_PLAN+"("
            +PLAN_ID+" varchar(20) primary key,"
            +PLAN_ORDER_ID+" varchar(20),"
            +PLAN_PRODUCT_MODEL+" varchar(20),"
            +PLAN_ORDER_NUMBER+" varchar(20),"
            +PLAN_CHARGE_MAN+" varchar(20),"
            +PLAN_START_DATE+" varchar(20),"
            +PLAN_FINISH_DATE+" varchar(20),"
            +LPAN_STATUS+" varchar(20)"
            +")";

    //insert
    public static final String INSERT_PLAN_SQL = "insert into "+ TABLE_NAME_PLAN+"("
            +PLAN_ID+","
            +PLAN_ORDER_ID+","
            +PLAN_PRODUCT_MODEL+","
            +PLAN_ORDER_NUMBER+","
            +PLAN_CHARGE_MAN+","
            +PLAN_START_DATE+","
            +PLAN_FINISH_DATE+","
            +LPAN_STATUS+
            ") values (?,?,?,?,?,?,?,?)";

    //Delete all data
    public static final String DEL_ALL_PLAN_SQL = "delete from "+TABLE_NAME_PLAN;

    //select all
    public static final String SELECT_ALL_PLAN_SQL = "select * from "+TABLE_NAME_PLAN;

    //select one
    public static final String SELECT_ONE_PLAN_BY_ID_SQL = "select * from "+TABLE_NAME_PLAN+" where "+PLAN_ID+" = ?";


    //==============================STOCK===============================

    //stock
    public static final String STOCK_ID = "stock_id";
    public static final String STOCK_GROUP_NUM = "group_num";
    public static final String STOCK_ROW_NUM = "row_num";
    public static final String STOCK_COLUM_NUM = "colum_num";
    public static final String STOCK_NAME = "name";
    public static final String STOCK_TYPE = "type";
    public static final String STOCK_NUM = "num";


    public static final String CREATEE_STOCK_DB_SQL = "create table "+TABLE_NAME_STOCK+"("
            +STOCK_ID+" varchar(20) primary key,"
            +STOCK_GROUP_NUM+" varchar(20),"
            +STOCK_ROW_NUM+" varchar(20),"
            +STOCK_COLUM_NUM+" varchar(20),"
            +STOCK_NAME+" varchar(20),"
            +STOCK_TYPE+" varchar(20),"
            +STOCK_NUM+" varchar(20)"
            +")";

    //insert
    public static final String INSERT_STOCK_SQL = "insert into "+ TABLE_NAME_STOCK+"("
            +STOCK_ID+","
            +STOCK_GROUP_NUM+","
            +STOCK_ROW_NUM+","
            +STOCK_COLUM_NUM+","
            +STOCK_NAME+","
            +STOCK_TYPE+","
            +STOCK_NUM+
            ") values (?,?,?,?,?,?,?)";

    //Delete all data
    public static final String DEL_ALL_STOCK_SQL = "delete from "+TABLE_NAME_STOCK;

    //select all
    public static final String SELECT_ALL_STOCK_SQL = "select * from "+TABLE_NAME_STOCK;

    //select one
    public static final String SELECT_ONE_STOCK_BY_ID_SQL = "select * from "+TABLE_NAME_STOCK+" where "+STOCK_ID+" = ?";




}
