package cpumonitor.ybdesire.com.androidcpumonitor;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import android.content.Intent;
import android.graphics.Color;
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
    private static int counter = 7;
    private Handler mHandler;
    private ArrayList<Double> cpu_usage_list = new ArrayList<Double>();
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                double cpuUsage = 100 * DeviceUtil.getCpuUsage();
                // text view str_cpu_usage
                String str_cpu_usage = "\n\n";
                str_cpu_usage += getResources().getString(R.string.cpu_usage) + cpuUsage + "%\n\n";
                TextView txt = (TextView) findViewById(R.id.txt_cpu_usage);//find output label by id
                txt.setText(str_cpu_usage);

                // text view txt_cpu_temp
                counter++;
                if(counter%8==0) {
                    counter=0;
                    String str_cpu_temp = "\n\n";
                    str_cpu_temp += getResources().getString(R.string.cpu_tmp) + DeviceUtil.getCpuTemp() + "\n\n";
                    TextView txt_cpu_temp = (TextView) findViewById(R.id.txt_cpu_temp);//find output label by id
                    txt_cpu_temp.setText(str_cpu_temp);
                }

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
                graph.getViewport().setMinY(-15);
                graph.getViewport().setMaxY(110);
                // set manual X bounds
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(listLen);
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
                series.setColor(Color.RED);
                series.setDrawDataPoints(true);

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
        // init counter
        counter = 7;

        //AdMob
        MobileAds.initialize(this, "ca-app-pub-8100413825150401/4031937877");
        AdView mAdView;
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


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
