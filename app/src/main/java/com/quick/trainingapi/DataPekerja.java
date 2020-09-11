package com.quick.trainingapi;

public class DataPekerja {
    //inisialisasi variabel
    int id;
    String no_induk;
    String nama;
    String jenis_kelamin;
    String alamat;

    //construktor datapekerja
    public DataPekerja(int id, String no_induk, String nama, String jenis_kelamin, String alamat) {
        this.id = id;
        this.no_induk = no_induk;
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.alamat = alamat;
    }

    //getter dan setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo_induk() {
        return no_induk;
    }

    public void setNo_induk(String no_induk) {
        this.no_induk = no_induk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}