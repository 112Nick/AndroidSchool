package ru.mail.park.tfshw4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static Integer number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addBtn = findViewById(R.id.add_btn);
        Button deleteBtn = findViewById(R.id.delete_btn);
        Button deleteAllBtn = findViewById(R.id.delete_all_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number++;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, MyFragment.newInstance(number))
                        .addToBackStack(null)
                        .commit();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number != 0) {
                    number--;
                }
                getSupportFragmentManager().popBackStack();
            }
        });

        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < number; i++) {
                    getSupportFragmentManager().popBackStack();
                }
                number = 0;
            }
        });


    }
}
