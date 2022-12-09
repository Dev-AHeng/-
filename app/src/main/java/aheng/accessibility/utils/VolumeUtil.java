package aheng.accessibility.utils;

import android.content.Context;
import android.media.AudioManager;

/**
 * @author AHeng
 * @date 2022/08/06 18:34
 */
public class VolumeUtil {
    /**
     * 获取当前媒体音量
     *
     * @return 当前音量
     */
    public float getCurrentMusicVolume(Context context) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return mAudioManager != null ? ((float) mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) / (float) getMaxMusicVolume(context)) : -1.0F;
    }
    
    /**
     * 获取系统最大媒体音量
     *
     * @return 最大音量
     */
    public int getMaxMusicVolume(Context context) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return mAudioManager != null ? mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) : 15;
    }
    
    /**
     * 百分比音量
     *
     * @param context 上下文
     * @param volume 百分比音量 [0.0,1.0]
     */
    public void setVol(Context context, float volume) {
        int i = (int) ((float) getMaxMusicVolume(context) * volume);
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, i,AudioManager.FLAG_PLAY_SOUND);
    }
}
