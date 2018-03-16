package cpumonitor.ybdesire.com.androidcpumonitor;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class CpuMonitorActivity extends AppCompatActivity {

    private int mInterval = 1000; // 1 seconds by default, can be changed later
    private Handler mHandler;
    private ArrayList<Double> cpu_usage_list = new ArrayList<Double>();
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

                // update graph
                int listLen = 100;
                if(cpu_usage_list.size()<listLen) {
                    cpu_usage_list.add(cpuUsage);
                }
                else{
                    for(int i=1;i<cpu_usage_list.size();i++)
                    {
                        cpu_usage_list.set(i-1, cpu_usage_list.get(i));
                    }
                    cpu_usage_list.set(cpu_usage_list.size()-1,cpuUsage );
                }
                DataPoint[] dps = new DataPoint[listLen];
                for(int i=0;i<listLen;i++)
                {
                    if(i<cpu_usage_list.size()) {
                        dps[i] = new DataPoint(i, cpu_usage_list.get(i));
                    }
                    else{
                        dps[i] = new DataPoint(i, 0);
                    }
                }
                GraphView graph = (GraphView) findViewById(R.id.graph_cpu);
                // set manual Y bounds
                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setMinY(-130);
                graph.getViewport().setMaxY(150);
                // remove all series
                graph.removeAllSeries();
                // activate horizontal zooming and scrolling
                graph.getViewport().setScalable(true);
                // activate horizontal scrolling
                graph.getViewport().setScrollable(true);
                // activate horizontal and vertical zooming and scrolling
                graph.getViewport().setScalableY(true);
                // activate vertical scrolling
                graph.getViewport().setScrollableY(true);

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dps);
                graph.addSeries(series);

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
