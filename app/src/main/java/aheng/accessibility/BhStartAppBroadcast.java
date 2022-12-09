package aheng.accessibility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 拨号命令启动App
 *
 * @author AHeng
 * @date 2022/08/06 15:36
 */
public class BhStartAppBroadcast extends BroadcastReceiver {
    private static final String SECRET_CODE = "android.provider.Telephony.SECRET_CODE";
    /**
     * 拨号命令 *#*#1919#*#*
     */
    private static final String COMMAND = "1919";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        if (SECRET_CODE.equals(intent.getAction())) {
            if (COMMAND.equals(intent.getData().getHost())) {
                Intent openIntent = new Intent();
                openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                openIntent.setClassName("aheng.accessibility", "aheng.accessibility.MainActivity");
                context.startActivity(openIntent);
                
                System.out.println(intent.getData().getHost());
            }
        }
    }
}
