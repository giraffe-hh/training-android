package com.quick.trainingapi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListPekerjaAdapter extends RecyclerView.Adapter<ListPekerjaAdapter.ViewHolder> {

    Context mContext;
    private List<DataPekerja> dataPekerja; //inisialisasi List dengan object DataPekerja


    //construktor ListMahasiswaAdapter
    public ListPekerjaAdapter(Context mContext, List<DataPekerja> dataPekerja) {
        this.mContext = mContext;
        this.dataPekerja = dataPekerja;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate view yang akan digunakan yaitu layout list_mahasiswa_row.xml
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_data, parent, false);
        ViewHolder holder = new ViewHolder(v); //inisialisasi ViewHolder
        return holder;
    } //fungsi yang dijalankan saat ViewHolder dibuat

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DataPekerja data = dataPekerja.get(position); //inisialisasi object DataPekerja
        holder.mNama.setText(data.getNama()); //menset value view "mNama" sesuai data dari getNama();
        holder.mAlamat.setText(data.getAlamat()); //menset value view "mAlamat" sesuai data dari getAlamat();
        holder.cv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DetailActivity.class);
                i.putExtra("id", data.getId());
                i.putExtra("no_induk", data.getNo_induk());
                i.putExtra("nama", data.getNama());
                i.putExtra("jenis_kelamin",data.getJenis_kelamin());
                i.putExtra("alamat",data.getAlamat());
//                ((ReadActivity)mContext).startActivity(i);
                ((ReadActivity)mContext).startActivityForResult(i, 32);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataPekerja.size(); //mengambil item sesuai urutan
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cv_item;
        TextView mNama, mJenisKelamin, mAlamat; //inisialisasi variabel

        public ViewHolder(View itemView) {
            super(itemView);
            cv_item = itemView.findViewById(R.id.cv_item);
            mNama = itemView.findViewById(R.id.tv_nama);
            mAlamat = itemView.findViewById(R.id.tv_alamat);
        }
    }
}
