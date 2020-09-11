package com.quick.trainingapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddActivity extends AppCompatActivity {
    private static final String TAG = "AddActivity";
    private EditText etInduk, etNama, etAlamat;
    RadioGroup rg_jk;
    RadioButton rb_l, rb_p;
    private Button btnSubmit;
    String jk;
    SweetAlertDialog sweetAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etInduk = findViewById(R.id.et_induk);
        etNama = findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_alamat);
        rg_jk = findViewById(R.id.rg_jk);
        rb_l = findViewById(R.id.rb_laki);
        rb_p = findViewById(R.id.rb_perempuan);
        btnSubmit = findViewById(R.id.btn_submit);
        AndroidNetworking.initialize(getApplicationContext()); //inisialisasi library FAN

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String induk = etInduk.getText().toString();
                String nama = etNama.getText().toString();
                String alamat = etAlamat.getText().toString();
                if (rb_l.isChecked()) {
                    jk = "L";
                } else if(rb_p.isChecked()) {
                    jk = "P";
                } else {
                    jk="";
                }
                if (induk.equals("") || nama.equals("") || jk.equals("") || alamat.equals("")){
                    Toast.makeText(getApplicationContext(),"Semua data harus diisi" , Toast.LENGTH_SHORT).show();
                    //memunculkan toast saat form masih ada yang kosong
                } else {
                    tambahData(induk,nama,jk,alamat); //memanggil fungsi tambahData()

                    //mengosongkan form setelah data berhasil ditambahkan
                    etInduk.setText("");
                    etNama.setText("");
                    etAlamat.setText("");
                    rb_l.setChecked(false);
                    rb_p.setChecked(false);
                }
            }
        });
    }

    public void tambahData(String no_induk, String nama, String jenis_kelamin, String alamat){
        //koneksi ke file add.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu
        AndroidNetworking.post("http://192.168.168.136/api-fan/add.php")
                .addBodyParameter("no_induk", no_induk)
                .addBodyParameter("nama", nama)
                .addBodyParameter("jenis_kelamin", jenis_kelamin)
                .addBodyParameter("alamat", alamat)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Handle Response
                        Log.d(TAG, "onResponse: " + response); //untuk log pada onresponse
//                        memunculkan Toast saat data berhasil ditambahkan
//                        Toast.makeText(getApplicationContext(),"Data berhasil ditambahkan" , Toast.LENGTH_SHORT).show();
                        success();
                    }
                    @Override
                    public void onError(ANError error) {
                        //Handle Error
                        Log.d(TAG, "onError: Failed" + error); //untuk log pada onerror
                        Toast.makeText(getApplicationContext(),"Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                        //memunculkan Toast saat data gagal ditambahkan
                    }
                });
    }

    public void success(){
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success")
                .setContentText("Berhasil menambah data!")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent i = new Intent(getApplicationContext(), ReadActivity.class);
                        startActivity(i);
                        sDialog.dismissWithAnimation();
                    }
                })
                .setCancelText("Cancel")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                });
        sweetAlertDialog.show();
        sweetAlertDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddActivity.this, ReadActivity.class);
        startActivity(intent);
    }
}
