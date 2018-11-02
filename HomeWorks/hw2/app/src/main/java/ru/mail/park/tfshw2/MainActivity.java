package ru.mail.park.tfshw2;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<Pair<String, Integer>> stations = new ArrayList<>();

    private void init() {
        stations.add(new Pair<>("Перово", R.drawable.rounded_station_yellow));
        stations.add(new Pair<>("Новогиреево", R.drawable.rounded_station_yellow));
        stations.add(new Pair<>("Новокосино", R.drawable.rounded_station_yellow));
        stations.add(new Pair<>("Авиамоторная", R.drawable.rounded_station_yellow));
        stations.add(new Pair<>("Бауманская", R.drawable.rounded_station_blue));
        stations.add(new Pair<>("Щелковская", R.drawable.rounded_station_blue));
        stations.add(new Pair<>("Измайловская", R.drawable.rounded_station_blue));
        stations.add(new Pair<>("Арбатская", R.drawable.rounded_station_blue));
        stations.add(new Pair<>("Сокол", R.drawable.rounded_station_green));
        stations.add(new Pair<>("Аэропорт", R.drawable.rounded_station_green));
        stations.add(new Pair<>("Водный стадион", R.drawable.rounded_station_green));
        stations.add(new Pair<>("Динамо", R.drawable.rounded_station_green));
        stations.add(new Pair<>("Беларусская", R.drawable.rounded_station_brown));
        stations.add(new Pair<>("Таганская", R.drawable.rounded_station_brown));
        stations.add(new Pair<>("Павелецкая", R.drawable.rounded_station_brown));
        stations.add(new Pair<>("Октябрьская", R.drawable.rounded_station_brown));

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout top = findViewById(R.id.topView);
        final LinearLayout bottom = findViewById(R.id.bottomView);
        top.setOrientation(LinearLayout.HORIZONTAL);
        bottom.setOrientation(LinearLayout.HORIZONTAL);
        top.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        bottom.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        init();
        for (int i = 0; i < stations.size(); i++) {
            TextView itemChip;
            itemChip = (TextView) getLayoutInflater().inflate(R.layout.item_chip, top, false);
            itemChip.setText(stations.get(i).first);
            itemChip.setBackground(ContextCompat.getDrawable(this, stations.get(i).second));
            itemChip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (top == v.getParent()) { //TODO
                            top.removeView(v);
                            bottom.addView(v);
                        } else {
                            bottom.removeView(v);
                            top.addView(v);
                        }

                    }
            });
            if (i % 2 == 0) {
                top.addView(itemChip);
            } else {
                bottom.addView(itemChip);
            }
        }
    }
}
