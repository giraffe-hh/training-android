package com.quick.trainingapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_Lihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Lihat = (Button) findViewById(R.id.btn_Lihat);

        btn_Lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //untuk pindah keActivity lain saat buttonlihat dipencet
                Intent intent = new Intent(MainActivity.this, ReadActivity.class);
                startActivity(intent);
            }
        });
    }
}
