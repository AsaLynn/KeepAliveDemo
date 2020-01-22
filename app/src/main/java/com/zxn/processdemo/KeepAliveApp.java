package com.zxn.processdemo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zxn.process.KeepLive;
import com.zxn.process.config.ForegroundNotification;
import com.zxn.process.config.ForegroundNotificationClickListener;
import com.zxn.process.config.KeepLiveService;

/**
 * Created by zxn on 2019/12/28.
 */
public class KeepAliveApp extends Application {

    private ForegroundNotification mForegroundNotification = new ForegroundNotification("", "", R.mipmap.ic_launcher,
            //定义前台服务的通知点击事件
            new ForegroundNotificationClickListener() {

                @Override
                public void foregroundNotificationClick(Context context, Intent intent) {

                }
            });

    @Override
    public void onCreate() {
        super.onCreate();
        initKeepLive();
    }
    
    private void initKeepLive() {
        //启动保活服务
        KeepLive.startWork(this, KeepLive.RunMode.ROGUE, mForegroundNotification,
                //你需要保活的服务，如socket连接、定时任务等，建议不用匿名内部类的方式在这里写
                new KeepLiveService() {
                    /**
                     * 运行中
                     * 由于服务可能会多次自动启动，该方法可能重复调用
                     */
                    @Override
                    public void onWorking() {
                        Log.e("KeepLive", "initKeepLive--->onWorking");

                    }

                    /**
                     * 服务终止
                     * 由于服务可能会被多次终止，该方法可能重复调用，需同onWorking配套使用，如注册和注销broadcast
                     */
                    @Override
                    public void onStop() {
                        Log.e("KeepLive", "initKeepLive--->onStop");
                    }
                }
        );
    }
}
