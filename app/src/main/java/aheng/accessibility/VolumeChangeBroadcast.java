package aheng.accessibility;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

import aheng.accessibility.utils.Data;
import aheng.accessibility.utils.VolumeUtil;

/**
 * 音量
 *
 * @author Dev_Heng
 */
public class VolumeChangeBroadcast extends BroadcastReceiver {
    private static final String TAG = "日志";
    
    private static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";
    private static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
    
    private static final String SECRET_CODE = "android.provider.Telephony.SECRET_CODE";
    /**
     * 拨号命令 *#*#1919#*#*
     */
    private static final String COMMAND = "1919";
    private final VolumeUtil volumeUtil = new VolumeUtil();
    
    @SuppressLint("SetTextI18n")
    @Override
    public void onReceive(Context context, Intent intent) {
        // 拨号
        if (SECRET_CODE.equals(intent.getAction())) {
            if (!COMMAND.equals(intent.getData().getHost())) {
                float inputVolume = Float.parseFloat(intent.getData().getHost()) * 0.1F;
                BigDecimal bigDecimal = new BigDecimal(inputVolume);
                Data.setVolume = bigDecimal.setScale(2, RoundingMode.DOWN).floatValue();
                volumeUtil.setVol(context, Data.setVolume);
            }
            Log.i(TAG, Data.setVolume + " 代码");
        }
    
    
        // 音量
        if (VOLUME_CHANGED_ACTION.equals(intent.getAction()) && (intent.getIntExtra(EXTRA_VOLUME_STREAM_TYPE, -1) == AudioManager.STREAM_MUSIC)) {
            if (volumeUtil.getCurrentMusicVolume(context) > Data.setVolume) {
                volumeUtil.setVol(context, Data.setVolume);
                Log.i(TAG, Data.setVolume + "监听");
            }
        }
        
    }
    
    /**
     * Toast
     *
     * @param content 提示内容
     */
    public void toast(Context context, Object content) {
        Toast.makeText(context, content.toString(), Toast.LENGTH_SHORT).show();
    }
    
}