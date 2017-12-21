package ro.ubbcluj.mobileapp.dogdate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

import ro.ubbcluj.mobileapp.dogdate.domain.Dog;

public class DogStatsActivity extends AppCompatActivity {

    GraphView graph;
    ArrayList<Dog> allDogs;
    BarGraphSeries<DataPoint> series;
    String[] allRaces;
    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_stats);

        Intent data = getIntent();
        allDogs = (ArrayList<Dog>)data.getSerializableExtra(getString(R.string.key_all_dogs));
        allRaces = getResources().getStringArray(R.array.dog_races);
        n = allRaces.length;

        int[] entries = countEntries();
        DataPoint[] bars = entriesToDataPoints(entries);

        graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(bars);
        graph.addSeries(series);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(n);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                switch ((int)data.getX()){
                    case 0:
                        return Color.BLUE;
                    case 1:
                        return Color.GREEN;
                    case 2:
                        return Color.RED;
                    case 3:
                        return Color.CYAN;
                    default:
                        return Color.BLACK;
                }
            }
        });


    }

    public DataPoint[] entriesToDataPoints(int[] entries){
        DataPoint[] points = new DataPoint[n];
        for(int i=0;i<n;i++){
            points[i] = new DataPoint(i,entries[i]);
        }
        return points;
    }

    public int[] countEntries(){
        int[] raceK = new int[n];
        for(int i=0;i<n;i++){
            raceK[i] = 0;
        }

        for(int i=0;i<allDogs.size();i++){
            for(int j=0;j<n;j++){
                if(allDogs.get(i).getRace().equals(allRaces[j])){
                    raceK[j]++;
                }
            }
        }

        return raceK;
    }
}
