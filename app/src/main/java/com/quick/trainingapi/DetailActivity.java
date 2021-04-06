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

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private EditText etInduk, etNama, etAlamat;
    RadioGroup rg_jk;
    RadioButton rb_l, rb_p;
    private Button btnUpdate, btnDelete;
    int id;
    String no_induk, nama, jenis_kelamin, alamat;
    SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();

        init(); //deklarasi layout
        getData(); //get data intent

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmUpdate();
            }
        });
    }

    void init(){
        etInduk = findViewById(R.id.et_induk);
        etNama = findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_alamat);
        rg_jk = findViewById(R.id.rg_jk);
        rb_l = findViewById(R.id.rb_laki);
        rb_p = findViewById(R.id.rb_perempuan);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
    }

    void getData() {
        Bundle c = getIntent().getExtras();
        if (c != null) {
            id = c.getInt("id");
            no_induk = c.getString("no_induk");
            nama = c.getString("nama");
            jenis_kelamin = c.getString("jenis_kelamin");
            alamat = c.getString("alamat");
        } else {
            id = 0;
            no_induk = "";
            nama = "";
            jenis_kelamin = "";
            alamat = "";
        }
        setText();
    }

    void setText() {
        etInduk.setText(no_induk);
        etNama.setText(nama);
        etAlamat.setText(alamat);
        if (jenis_kelamin.equalsIgnoreCase("L")) {
            rb_l.setChecked(true);
        } else {
            rb_p.setChecked(true);
        }
    }

    public void confirmDelete() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Yakin menghapus data ini ?")
                .setCancelText("Tidak")
                .setConfirmText("Ya")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        deleteData();
                        sDialog.dismissWithAnimation();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }

    public void confirmUpdate() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Yakin mengubah data ini ?")
                .setCancelText("Tidak")
                .setConfirmText("Ya")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        getText();
                        updateData();
                        sDialog.dismissWithAnimation();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }

    void getText(){
        no_induk = etInduk.getText().toString();
        nama = etNama.getText().toString();
        alamat = etAlamat.getText().toString();
        if (rb_l.isChecked()) {
            jenis_kelamin = "L";
        } else if(rb_p.isChecked()) {
            jenis_kelamin = "P";
        } else {
            jenis_kelamin=" ";
        }
    }

    public void deleteData(){
        //koneksi ke file add.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu
        AndroidNetworking.post("http://192.168.168.136/api-fan/delete.php")
                .addBodyParameter("id", String.valueOf(id))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Handle Response
                        Log.d(TAG, "onResponse: " + response); //untuk log pada onresponse
//                        memunculkan Toast saat data berhasil ditambahkan
//                        Toast.makeText(getApplicationContext(),"Data berhasil ditambahkan" , Toast.LENGTH_SHORT).show();
                        successDelete();
                    }
                    @Override
                    public void onError(ANError error) {
                        //Handle Error
                        Log.d(TAG, "onError: Failed" + error); //untuk log pada onerror
                        Toast.makeText(getApplicationContext(),"Data gagal dihapus", Toast.LENGTH_SHORT).show();
                        //memunculkan Toast saat data gagal dihapus
                    }
                });
    }

    public void successDelete(){
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Berhasil menghapus data!")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent i = new Intent(getApplicationContext(), ReadActivity.class);
                        startActivity(i);
                        sDialog.dismissWithAnimation();
                    }
                });
        sweetAlertDialog.show();
        sweetAlertDialog.setCanceledOnTouchOutside(false);
    }

    public void updateData(){
        //koneksi ke file add.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu
        AndroidNetworking.post("http://192.168.168.136/api-fan/update.php")
                .addBodyParameter("id", String.valueOf(id))
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
//                        memunculkan Toast saat data berhasil diubah
//                        Toast.makeText(getApplicationContext(),"Data berhasil diubah" , Toast.LENGTH_SHORT).show();
                        successUpdate();
                    }
                    @Override
                    public void onError(ANError error) {
                        //Handle Error
                        Log.d(TAG, "onError: Failed" + error); //untuk log pada onerror
                        Toast.makeText(getApplicationContext(),"Data gagal diubah", Toast.LENGTH_SHORT).show();
                        //memunculkan Toast saat data gagal ditambahkan
                    }
                });
    }

    public void successUpdate(){
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Berhasil mengubah data!")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent i = new Intent(getApplicationContext(), ReadActivity.class);
                        startActivity(i);
                        sDialog.dismissWithAnimation();
                    }
                });
        sweetAlertDialog.show();
        sweetAlertDialog.setCanceledOnTouchOutside(false);
    }

}
