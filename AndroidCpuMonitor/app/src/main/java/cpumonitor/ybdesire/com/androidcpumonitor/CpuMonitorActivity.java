package cpumonitor.ybdesire.com.androidcpumonitor;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CpuMonitorActivity extends AppCompatActivity {

    private int mInterval = 1000; // 1 seconds by default, can be changed later
    private Handler mHandler;
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                double cpuUsage = 100 * DeviceUtil.getCpuUsage();
                // text view details
                String str = "\n\n";
                str += "CPU : " + cpuUsage + "\n\n";
                TextView txt = (TextView) findViewById(R.id.txtDetails);//find output label by id
                txt.setText(str);
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);//将指定Runnable（包装成PostMessage）加入到MessageQueue中，然后Looper不断从MessageQueue中读取Message进行处理
            }
        }
    };
    void startRepeatingTask() {
        mStatusChecker.run();
    }
    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_monitor);

        // button home listener
        Button btn=(Button)findViewById(R.id.btn_home);//find button by id(defined at activity_main.xml)
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intnt = new Intent(CpuMonitorActivity.this, MainActivity.class);
                startActivity(intnt);
            }
        });

        // get cpu usage and update by 1s timer
        mHandler = new Handler();
        startRepeatingTask();
    }
}
