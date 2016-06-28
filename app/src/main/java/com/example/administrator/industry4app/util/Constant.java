package com.example.administrator.industry4app.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator
 * on 2016/6/23.
 */
public class Constant {

    public static final String CMD_LOGIN = "cmd_login";
    public static final String CMD_PLAN = "cmd_plan";

    public static final String CMD_AGV = "cmd_agv";
    public static final String CMD_MAIN = "cmd_main";
    public static final String CMD_STOCK = "cmd_stock";
    public static final String CMD_ADD_STOCK = "cmd_add_stock";
    public static final String CMD_OPC = "cmd_opc";


    public static final String CMD_AGV_COMMUNICATION = "cmd_agv_communication";
    public static final String CMD_AGV_UNIT = "cmd_agv_unit";
    public static final String CMD_AGV_CURRENT_LOC = "cmd_agv_current_loc";

    public static final String KEY_PLAN = "plan";
    public static final String KEY_AGV = "agv";
    public static final String KEY_MAIN = "main";
    public static final String KEY_STOCK = "stock";
    public static final String KEY_ADD_STOCK = "add_stock";
    public static final String KEY_OPC = "opc";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_LOGIN_STATUS = "login_status";
    public static final String KEY_USER = "user";
    public static final String KEY_PWD = "pwd";

    public static final String KEY_GET_PLAN_DATA = "get_plan_data";
    public static final String KEY_GET_AGV_DATA = "get_agv_data";
    public static final String KEY_GET_MAIN_DATA = "get_main_data";
    public static final String KEY_GET_OPC_DATA = "get_opc_data";
    public static final String KEY_GET_STOCK_DATA = "get_stock_data";

    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAIL = 0;





    public static String getJsonData(String key,String value,String key2,String value2){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(key, value);
                jsonObject.put(key2, value2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        }


}
