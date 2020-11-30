package com.example.crud2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud2.model.data_model;
import com.example.crud2.response.data_response;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rest.ApiConnection;
import rest.InterfaceConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardFragment extends Fragment {
    ArrayList<data_model> daftarseluruhPlayer = new ArrayList<>();
    RecyclerView tabel_player;
    InterfaceConnection interfaceConnection;
    adapterdaftarplayer adapterDaftarPlayer;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabel_player = (RecyclerView)view.findViewById(R.id.recyclerView_daftarPlayer);
        interfaceConnection = ApiConnection.getClient().create(InterfaceConnection.class);
        loadDataPlayer();
    }
    private void loadDataPlayer() {
        adapterDaftarPlayer = new adapterdaftarplayer(getContext());
        Call<data_response> daftar_player = interfaceConnection.daftar_player();
        daftar_player.enqueue(new Callback<data_response>() {
            @Override
            public void onResponse(Call<data_response> call, Response<data_response> response) {
                if (response.isSuccessful()){
                    List<data_model> seluruh_player = response.body().getSeluruh_player();
                    daftarseluruhPlayer.addAll(seluruh_player);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                adapterDaftarPlayer.updatedataplayer(daftarseluruhPlayer);
            }
            @Override
            public void onFailure(Call<data_response> call, Throwable t) {
                Log.d("Error Jaringan", "disini");
                t.printStackTrace();
                Log.d("here", "here", t);
                Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
        startRecyclerView();
    }

    private void startRecyclerView() {
        tabel_player.setAdapter(adapterDaftarPlayer);
        tabel_player.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard_main, container, false);
    }
}
