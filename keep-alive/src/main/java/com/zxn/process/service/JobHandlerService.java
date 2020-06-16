package com.zxn.process.service;

import android.app.Notification;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


import androidx.annotation.RequiresApi;

import com.zxn.process.KeepLive;
import com.zxn.process.config.NotificationUtils;
import com.zxn.process.receiver.NotificationClickReceiver;
import com.zxn.process.utils.ServiceUtils;


/**
 * 定时器
 * 安卓5.0及以上
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@SuppressWarnings(value = {"unchecked", "deprecation"})
public final class JobHandlerService extends JobService {
    private JobScheduler mJobScheduler;
    private int jobId = 100;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startService(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            mJobScheduler.cancel(jobId);
            JobInfo.Builder builder = new JobInfo.Builder(jobId,
                    new ComponentName(getPackageName(), JobHandlerService.class.getName()));
            if (Build.VERSION.SDK_INT >= 24) {
                builder.setMinimumLatency(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS); //执行的最小延迟时间
                builder.setOverrideDeadline(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);  //执行的最长延时时间
                builder.setMinimumLatency(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);
                builder.setBackoffCriteria(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS, JobInfo.BACKOFF_POLICY_LINEAR);//线性重试方案
            } else {
                builder.setPeriodic(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);
                builder.setRequiresDeviceIdle(true);
            }
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            builder.setRequiresCharging(true); // 当插入充电器，执行该任务
            builder.setPersisted(true);
            mJobScheduler.schedule(builder.build());
        }
        return START_STICKY;
    }

    private void startService(Context context) {
        //启动本地服务
        Intent localIntent = new Intent(context, LocalService.class);
        //启动守护进程
        Intent guardIntent = new Intent(context, RemoteService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(localIntent);
            context.startForegroundService(guardIntent);
        } else {
            context.startService(localIntent);
            context.startService(guardIntent);
        }

        //使用 startForegroundService 启动service 后需要在5秒内 调用startoreground 否则会抛出ANR 异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (KeepLive.foregroundNotification != null) {
                Intent intent2 = new Intent(getApplicationContext(), NotificationClickReceiver.class);
                intent2.setAction(NotificationClickReceiver.CLICK_NOTIFICATION);
                Notification notification = NotificationUtils.createNotification(this, KeepLive.foregroundNotification.getTitle(), KeepLive.foregroundNotification.getDescription(), KeepLive.foregroundNotification.getIconRes(), intent2);

                startForeground(13691, notification);
            }else {
                startForeground(1, new Notification());
            }
        }
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        if (!ServiceUtils.isServiceRunning(getApplicationContext(), "com.zxn.process.service.LocalService") || !ServiceUtils.isRunningTaskExist(getApplicationContext(), getPackageName() + ":remote")) {
            startService(this);
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (!ServiceUtils.isServiceRunning(getApplicationContext(), "com.zxn.process.service.LocalService") || !ServiceUtils.isRunningTaskExist(getApplicationContext(), getPackageName() + ":remote")) {
            startService(this);
        }
        return false;
    }
}
