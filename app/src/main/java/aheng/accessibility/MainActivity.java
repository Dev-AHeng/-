package aheng.accessibility;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import aheng.accessibility.utils.AccessibilityUtil;
import aheng.accessibility.utils.Data;

/**
 * 打开无障碍
 * 关闭前台通知显示
 * 开启自启动
 * 打开允许后台弹出权限
 * 省电策略设为无限制
 *
 * @author Dev_Heng
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "日志";
    private Button btn_Open;
    
    private Intent intent, intent2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        
        intent2 = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        
        btn_Open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
        
        intent = new Intent(MainActivity.this, MyAccessibility.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(intent);
        } else {
            this.startService(intent);
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        isOpen();
    }
    
    @Override
    public void onBackPressed() {
        //实现Home键效果
        //super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (intent != null) {
            this.stopService(intent);
        }
    }
    
    private void initView() {
        btn_Open = (Button) findViewById(R.id.open);
    }
    
    /**
     * 是否开启无障碍
     */
    @SuppressLint("SetTextI18n")
    private void isOpen() {
        if (AccessibilityUtil.isAccessibilitySettingsOn(this)) {
            btn_Open.setText("开启 data:" + Data.setVolume);
        } else {
            btn_Open.setText("未开启");
        }
    }
    
    /**
     * Toast
     *
     * @param content 提示内容
     */
    public void toast(Object content) {
        Toast.makeText(this, content.toString(), Toast.LENGTH_SHORT).show();
    }
}
