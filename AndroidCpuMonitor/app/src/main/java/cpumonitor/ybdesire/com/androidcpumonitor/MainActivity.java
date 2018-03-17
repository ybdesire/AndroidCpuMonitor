package cpumonitor.ybdesire.com.androidcpumonitor;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnsys=(Button)findViewById(R.id.btn_sys);//find button by id(defined at activity_main.xml)
        btnsys.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intnt = new Intent(MainActivity.this, SysMonitorActivity.class);
                startActivity(intnt);
            }
        });

        Button btncpu=(Button)findViewById(R.id.btn_cpu);//find button by id(defined at activity_main.xml)
        btncpu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intnt = new Intent(MainActivity.this, CpuMonitorActivity.class);
                startActivity(intnt);
            }
        });

        //ConstraintLayout myLayout = (ConstraintLayout) findViewById(R.id.layout_main);
        //myLayout.setBackgroundColor(Color.BLUE);

    }
}
