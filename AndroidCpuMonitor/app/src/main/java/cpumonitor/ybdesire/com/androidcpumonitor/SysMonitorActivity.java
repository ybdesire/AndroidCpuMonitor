package cpumonitor.ybdesire.com.androidcpumonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SysMonitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_monitor);


        Button btn=(Button)findViewById(R.id.btn_home);//find button by id(defined at activity_main.xml)
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intnt = new Intent(SysMonitorActivity.this, MainActivity.class);
                startActivity(intnt);
            }
        });


    }

}
