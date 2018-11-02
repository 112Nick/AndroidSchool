package ru.mail.park.tfshw3;

import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView workerListView;
    private WorkerListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new WorkerListAdapter(this);

        workerListView = findViewById(R.id.workerList);
        workerListView.setLayoutManager(new LinearLayoutManager(this));
        workerListView.setAdapter(adapter);
        workerListView.setHasFixedSize(true);


        ItemTouchHelper.Callback callback =
                new MyItemTouchHelperCallBack(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(workerListView);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Worker newWorker = WorkerGenerator.generateWorker();
               workerListView.scrollToPosition(0);
               adapter.add(newWorker);
            }
        });



        List<Worker> newWorkers = WorkerGenerator.generateWorkers(10);

        for (int i = 0; i < newWorkers.size(); i++) {
            workerListView.scrollToPosition(0);
            adapter.add(newWorkers.get(i));
        }

    }
}