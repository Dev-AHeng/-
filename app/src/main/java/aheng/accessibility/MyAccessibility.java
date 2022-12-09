package aheng.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * @author Dev_Heng
 */
public class MyAccessibility extends AccessibilityService {
    private static final String TAG = "日志";
    
    private static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";
    private final VolumeChangeBroadcast volumeChangeBroadcast = new VolumeChangeBroadcast();
    
    private IntentFilter intentFilter;
    
    @Override
    protected void onServiceConnected() {
        Notification("标题", "内容");
    
        // 服务启动时
        intentFilter = new IntentFilter();
        intentFilter.addAction(VOLUME_CHANGED_ACTION);
        this.registerReceiver(volumeChangeBroadcast, intentFilter);
        
        Log.i(TAG, "打开无障碍开关 onServiceConnected");
    }
    
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 辅助功能事件监听 及 运行
        // Log.i(TAG, "辅助功能事件监听 及 运行 onAccessibilityEvent");
    }
    
    // @Override
    // protected boolean onKeyEvent(KeyEvent event) {
    //     // if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {
    //     //     if (volumeUtil.getCurrentMusicVolume(this) > volumeValue) {
    //     //         volumeUtil.setVol(this, volumeValue);
    //     //         Log.i(TAG, "音量变化");
    //     //         Log.i(TAG, "event.getKeyCode() = " + event.getKeyCode());
    //     //         Log.i(TAG, event.getAction() + ".......///");
    //     //     }
    //     // }
    //     // if (event.getKeyCode() == KeyEvent.KEYCODE_POWER || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {
    //     //     if (event.getAction() == 1) {
    //     //         if ()
    //     //     }
    //     // }
    //
    //     Log.i(TAG, "event.getKeyCode() = " + event.getKeyCode());
    //     Log.i(TAG, event.getAction() + " 按下0 弹起1");
    //
    //     // this.unregisterReceiver(volumeChangeBroadcast);
    //
    //     return super.onKeyEvent(event);
    // }
    
    @Override
    public void onInterrupt() {
        // 辅助功能中断时
        Log.i(TAG, "辅助功能中断时 onInterrupt");
    }

    @Override
    public void onDestroy() {
        // 在服务被销毁时，关闭前台服务
        stopForeground(true);
        this.unregisterReceiver(volumeChangeBroadcast);
        
        super.onDestroy();
        Log.i(TAG, "在服务被销毁时,关闭前台服务 onDestroy");
    }
    
    /**
     * 通知
     * <p>
     * android8.0不加会报并闪退
     * Context.startForegroundService() did not then call Service.startForeground(): ServiceRecord{f35639 u0 aheng.floatwin/.FloatWin}
     */
    private void Notification(String title, String content) {
        Intent nfIntent = new Intent(this, MainActivity.class);
        String CHANNEL_ONE_ID = "MyAccessibility";
        String CHANNEL_ONE_NAME = "无障碍前台服务";
        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            builder = new Notification.Builder(this.getApplicationContext())
                    // 设置PendingIntent
                    .setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, PendingIntent.FLAG_IMMUTABLE))
                    // 设置状态栏内的小图标
                    .setSmallIcon(R.mipmap.ic_launcher)
                    // 设置标题
                    .setContentTitle(title)
                    // 设置上下文内容
                    .setContentText(content)
                    // 设置该通知发生的时间
                    .setWhen(System.currentTimeMillis());
        }
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // 修改安卓8.1以上系统报错
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_MIN);
            // 如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
            notificationChannel.enableLights(false);
            // 是否显示角标
            notificationChannel.setShowBadge(false);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
            builder.setChannelId(CHANNEL_ONE_ID);
        }
        
        // 获取构建好的Notification
        assert builder != null;
        Notification notification = builder.build();
        // 设置为默认的声音
        // notification.defaults = Notification.DEFAULT_SOUND;
        startForeground(1, notification);
    }
    
}