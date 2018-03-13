package cpumonitor.ybdesire.com.androidcpumonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CpuMonitorActivity extends AppCompatActivity {

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

        double cpuUsage = 100*DeviceUtil.getCpuUsage();
        // text view details
        String str = "\n\n";

        str += "CPU : " + cpuUsage + "\n\n";

        TextView txt=(TextView)findViewById(R.id.txtDetails);//find output label by id
        txt.setText(str);

    }
}
