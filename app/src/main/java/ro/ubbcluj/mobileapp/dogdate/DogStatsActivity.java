package ro.ubbcluj.mobileapp.dogdate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.ubbcluj.mobileapp.dogdate.Utils.ApiUtils;
import ro.ubbcluj.mobileapp.dogdate.domain.Dog;
import ro.ubbcluj.mobileapp.dogdate.observers.Subject;
import ro.ubbcluj.mobileapp.dogdate.observers.UserDataRepository;
import ro.ubbcluj.mobileapp.dogdate.service.UserService;

public class DogStatsActivity extends AppCompatActivity {


    UserService userService;
    private Subject userDataRepository;

    GraphView graph;
    List<Dog> allDogs;
    BarGraphSeries<DataPoint> series;

    //private DogsRepository repo;
    Intent data;

    String[] allRaces;

    int maxY;
    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_stats);
        //repo = new DogsRepository(getApplicationContext());

        data = getIntent();

        //allDogs = repo.getAllDogs();

        userService = ApiUtils.getUserService();
        userDataRepository = UserDataRepository.getInstance();

        Call<JsonArray> call = userService.getAllDogs();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                System.out.println("Ah");
                System.out.println(response.code());
                Gson gson = new Gson();
                Type type = new TypeToken<List<Dog>>(){}.getType();
                allDogs = gson.fromJson(response.body(), type);
                generateGraph();
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(this,MainActivity.class);
        back.putExtra("user",data.getStringExtra("user"));
        back.putExtra("access",data.getStringExtra("access"));
        setResult(Activity.RESULT_OK,back);
        finish();
    }

    public void generateGraph(){
        allRaces = getResources().getStringArray(R.array.dog_races);
        n = allRaces.length;

        int[] entries = countEntries();
        DataPoint[] bars = entriesToDataPoints(entries);

        graph = (GraphView) findViewById(R.id.dogStatsGraphView);
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
