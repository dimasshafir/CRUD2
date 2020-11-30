package com.example.crud2.response;

import com.example.crud2.model.data_model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class data_response {
    private Boolean status;
    private String message;

    @SerializedName("data")
    List<data_model> seluruh_player;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<data_model> getSeluruh_player() {
        return seluruh_player;
    }

    public void setSeluruh_player(List<data_model> seluruh_player) {
        this.seluruh_player = seluruh_player;
    }
}
