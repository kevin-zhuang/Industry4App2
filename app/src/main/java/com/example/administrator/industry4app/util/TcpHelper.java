package com.example.administrator.industry4app.util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Administrator
 * on 2016/6/22.
 */
public class TcpHelper{
    private static final String TAG = "TcpHelper";
    private static final String IP = "192.168.1.100";
    private static final int PORT = 9999;
    private static final int RECEIVE_DATA = 0x123;
    private static final int CONNECT_SUCCESS = 0x124;

    public boolean isConnected = false;
    private OutputStream ou;
    private InputStream in;
    private Socket socket = null;
    private MyHandler myHandler = null;
    private Thread receiverThread = null;
    private boolean isWhile = false;

    private ReceiveDataCallback receiveDataCallback;

    private static TcpHelper instance = null;
    private TcpHelper(String ip, int port) {
        myHandler = new MyHandler();
        connectThread(ip, port);
    }

    public static synchronized TcpHelper getInstance() {
        if (instance == null) {
            instance = new TcpHelper(IP, PORT);
        }
        return instance;
    }


    private void connectThread(final String ip, final int port) {

        if (!isConnected) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    connectServer(ip, port);
                }
            }).start();
        } else {
            try {
                if (socket != null) {
                    socket.close();
                    socket = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            isConnected = false;
        }
    }

    private void connectServer(String ip, int port) {
        try {
            Log.e("TcpHelper", "connectServer1");

            socket = new Socket(ip, port);
            Log.e("TcpHelper","connectServer2");
            ou = socket.getOutputStream();
            in = socket.getInputStream();
            myHandler.sendEmptyMessage(CONNECT_SUCCESS);
        } catch (IOException e) {
            Log.e("TcpHelper","con Fail");
            isConnected = false;
            myHandler.sendEmptyMessage(4);
        }

    }


    public void closeTcp() {
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (socket != null) {
                        isWhile = false;
                        ou.close();
                        isConnected = true;
                        socket.close();
                        instance = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void sendData(byte[] data) {
        try {
            ou.write(data);
            ou.flush();
        } catch (IOException e) {
            if(socket.isConnected()){
                try {
                    socket.close();
                    instance = null;
                } catch (IOException e1) {

                    e1.printStackTrace();
                }
            }
        }

    }


    public void setReceiveDataCallback(ReceiveDataCallback receiveDataCallback){
        this.receiveDataCallback = receiveDataCallback;
    }

    @SuppressLint("HandlerLeak")
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            receiverData(msg.what);
            if(msg.what == RECEIVE_DATA){
                String data = (String)msg.obj;
                if(receiveDataCallback!=null){
                    receiveDataCallback.onReceiveData(data);
                }

            }


        }
    }

    private void receiverData(int flag) {

        if (flag == CONNECT_SUCCESS) {
            isWhile = true;
            receiverThread = new Thread(new MyReceiverRunnable());
            receiverThread.start();
            isConnected = true;

            Log.e("TcpHelper","receiverData");
        }

    }

    private class MyReceiverRunnable implements Runnable {

        public void run() {

            while (isWhile) {
                if (isConnected) {
                    if (socket != null && socket.isConnected()) {

                        int count = 0;
                        byte[] inDatas;
                        String result;
                        try {
                            while (count == 0) {
                                count = in.available();
                            }
                            inDatas = new byte[count];
                            in.read(inDatas);
                            result = new String(inDatas,"GB2312");
//							result = Utils.bytes2HexString(inDatas, inDatas.length);
                            Log.e("rev", "rev = " + result);
                            Message msg = new Message();
                            msg.what = RECEIVE_DATA;
                            msg.obj = result;
                            myHandler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }
}
