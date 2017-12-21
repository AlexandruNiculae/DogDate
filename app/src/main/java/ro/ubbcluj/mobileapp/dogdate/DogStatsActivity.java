package ro.ubbcluj.mobileapp.dogdate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

import ro.ubbcluj.mobileapp.dogdate.domain.Dog;
import ro.ubbcluj.mobileapp.dogdate.repository.DogsRepository;

public class DogStatsActivity extends AppCompatActivity {

    GraphView graph;
    ArrayList<Dog> allDogs;
    BarGraphSeries<DataPoint> series;
    private DogsRepository repo;
    String[] allRaces;

    int maxY;
    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_stats);
        repo = new DogsRepository(getApplicationContext());

        Intent data = getIntent();

        allDogs = repo.getAllDogs();
        allRaces = getResources().getStringArray(R.array.dog_races);
        n = allRaces.length;

        int[] entries = countEntries();
        DataPoint[] bars = entriesToDataPoints(entries);

        graph = (GraphView) findViewById(R.id.dogStatsGraph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(bars);
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
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(allRaces);


        series.setDataWidth(1/n);

        graph.getViewport().setMinY(-0.1);
        graph.getViewport().setMaxY(maxY + 2);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(n);
        graph.getViewport().setXAxisBoundsManual(true);


        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.addSeries(series);



    }

    public void showToast(String message)
    {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public DataPoint[] entriesToDataPoints(int[] entries){
        DataPoint[] points = new DataPoint[n];
        for(int i=0;i<n;i++){
            points[i] = new DataPoint(i,entries[i] + 1);
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
                    if (raceK[j] > maxY){
                        maxY = raceK[j];
                    }
                }
            }
        }

        return raceK;
    }
}
