package com.example.rohith.group_29_assignment_1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series,line;
    private double lastX = 0;
    boolean flag=false;
    float arr[]=new float[1800];
    int count=0;
    double inc=1;
    int speed=20;
    GraphView graph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        line = new LineGraphSeries<DataPoint>();
        line.setColor(Color.GREEN);
        populateArray();

        graph.addSeries(series);
        graph.addSeries(line);
        Viewport vp = graph.getViewport();
        vp.setYAxisBoundsManual(true);
        vp.setMinY(0);
        vp.setMaxY(10);
        vp.setXAxisBoundsManual(true);
        vp.setMinX(0);
        vp.setMaxX(100);
        vp.setScrollable(true);
        vp.setBackgroundColor(Color.BLACK);
        vp.setBorderColor(Color.DKGRAY);
        series.setColor(Color.GREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Using Thread for live graphs concept taken from http://www.android-graphview.org/realtime-chart/

        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 100; ) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry();
                        }
                    });

                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }

    private void addEntry() {

        if(flag==true) {
            lastX+=inc;
            series.appendData(new DataPoint(lastX,arr[count++%1800]), true, 200);
            line.appendData(new DataPoint(lastX,5),true,200);
        }
    }


    private void populateArray(){
        int x=0;
        for(float i=0.0f;i<180;i=i+0.1f){
            arr[x++]= (float)Math.sin(i)*2+5;
        }

    }

    public void runGraph(View view){
        EditText e=(EditText)findViewById(R.id.editTextPatientName);
        EditText e2=(EditText)findViewById(R.id.editTextAge);

        if(!e.getText().toString().trim().equals("") && !e2.getText().toString().trim().equals("")) {
            flag = true;
        }
        else{
            Toast.makeText(this,"Enter name and id",Toast.LENGTH_LONG).show();
            flag=false;
        }
    }

    public void stopGraph(View view){
        flag=false;
    }

    public void changeWave(View view){
        inc=inc==1?0.5:1;
        speed=speed==20?10:20;
    }

}
