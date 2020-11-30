package com.example.crud2.model;

import com.google.gson.annotations.SerializedName;

public class data_model {
    @SerializedName("kode_player")
    private String kode_player;
    @SerializedName("nama_player")
    private String nama_player;
    @SerializedName("level_player")
    private String level_player;

    public String getKode_player() {
        return kode_player;
    }

    public void setKode_player(String kode_player) {
        this.kode_player = kode_player;
    }

    public String getNama_player() {
        return nama_player;
    }

    public void setNama_player(String nama_player) {
        this.nama_player = nama_player;
    }

    public String getLevel_player() {
        return level_player;
    }

    public void setLevel_player(String level_player) {
        this.level_player = level_player;
    }
}
