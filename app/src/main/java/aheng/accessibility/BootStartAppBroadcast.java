package aheng.accessibility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 开机自启动
 *
 * @author AHeng
 * @date 2022/08/07 20:59
 */
public class BootStartAppBroadcast extends BroadcastReceiver {
    private static final String TAG = "日志";
    private static final String BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        if (BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(TAG, "system boot completed");
            // Intent service = new Intent(context, MyAccessibility.class);
            // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //     context.startForegroundService(service);
            // } else {
            //     context.startService(service);
            // }
    
            // Intent openIntent = new Intent();
            // openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // openIntent.setClassName("aheng.accessibility", "aheng.accessibility.MainActivity");
            // context.startActivity(openIntent);
            //
            // Intent i = new Intent(Intent.ACTION_MAIN);
            // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // i.addCategory(Intent.CATEGORY_HOME);
            // context.startActivity(i);
        }
    }
}
