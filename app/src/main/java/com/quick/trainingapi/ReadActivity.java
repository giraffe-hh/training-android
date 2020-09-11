package com.quick.trainingapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {
    //inisialisasi variable
    private static final String TAG = "ReadActivity";
    private List<DataPekerja> dataPekerja;
    private RecyclerView recyclerView;
    FloatingActionButton fab_Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        recyclerView = findViewById(R.id.rv_Read);
        fab_Add = findViewById(R.id.fab_Add);

        recyclerView.setHasFixedSize(true); //agar recyclerView tergambar lebih cepat
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //menset layout manager sebagai LinearLayout(scroll kebawah)
        dataPekerja = new ArrayList<>(); //arraylist untuk menyimpan data mahasiswa
        AndroidNetworking.initialize(getApplicationContext()); //inisialisasi FAN
        getData(); // pemanggilan fungsi get data

        fab_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReadActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getData(){
        //koneksi ke file read.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu
        AndroidNetworking.get("http://192.168.168.136/api-fan/read.php")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse: " + response); //untuk log pada onresponse
                        // do anything with response
                        {
                            //mengambil data dari JSON array pada read_all.php
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject data = response.getJSONObject(i);
                                    dataPekerja.add(new DataPekerja(
                                            data.getInt("id"), //"name:/String" diisi sesuai dengan yang di JSON pada read.php
                                            data.getString("no_induk"), //"name:/String" diisi sesuai dengan yang di JSON pada read.php
                                            data.getString("nama"), //"name:/String" diisi sesuai dengan yang di JSON pada read.php
                                            data.getString("jenis_kelamin"), //"name:/String" diisi sesuai dengan yang di JSON pada read.php
                                            data.getString("alamat") //"name:/String" diisi sesuai dengan yang di JSON pada read.php
                                    ));
                                }
                                //men inisialisasi adapter RecyclerView yang sudah kita buat sebelumnya
                                ListPekerjaAdapter adapter = new ListPekerjaAdapter(dataPekerja);
                                recyclerView.setAdapter(adapter); //menset adapter yang akan digunakan pada recyclerView
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } @Override
                    public void onError(ANError error) {
                        Log.d(TAG, "onError: " + error); //untuk log pada onerror
                        // handle error
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}