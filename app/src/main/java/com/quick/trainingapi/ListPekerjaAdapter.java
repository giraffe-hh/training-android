package com.quick.trainingapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListPekerjaAdapter extends RecyclerView.Adapter<ListPekerjaAdapter.ViewHolder> {

    private List<DataPekerja> dataPekerja; //inisialisasi List dengan object DataPekerja


    //construktor ListMahasiswaAdapter
    public ListPekerjaAdapter(List<DataPekerja> dataPekerja) {
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
        DataPekerja data = dataPekerja.get(position); //inisialisasi object DataPekerja
        holder.mNama.setText(data.getNama()); //menset value view "mNama" sesuai data dari getNama();
        holder.mAlamat.setText(data.getAlamat()); //menset value view "mAlamat" sesuai data dari getAlamat();
    }

    @Override
    public int getItemCount() {
        return dataPekerja.size(); //mengambil item sesuai urutan
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mNama, mJenisKelamin, mAlamat; //inisialisasi variabel

        public ViewHolder(View itemView) {
            super(itemView);
            mNama = itemView.findViewById(R.id.tv_nama);
            mAlamat = itemView.findViewById(R.id.tv_alamat);
        }
    }
}
