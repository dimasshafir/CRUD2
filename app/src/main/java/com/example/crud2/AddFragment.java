package com.example.crud2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crud2.response.data_response;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import rest.ApiConnection;
import rest.InterfaceConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFragment extends Fragment {
    TextInputLayout layoutNamaPlayer, layoutLevelPlayer;
    TextInputEditText inputNamaPlayer, inputLevelPlayer;
    Button btnTambahPlayer;

    InterfaceConnection interfaceConnection;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutNamaPlayer = (TextInputLayout)view.findViewById(R.id.layoutNamaPlayer);
        layoutLevelPlayer = (TextInputLayout)view.findViewById(R.id.layoutLevelPlayer);
        inputNamaPlayer = (TextInputEditText)view.findViewById(R.id.inputNamaPlayer);
        inputLevelPlayer = (TextInputEditText)view.findViewById(R.id.inputLevelPlayer);
        btnTambahPlayer = (Button)view.findViewById(R.id.btnTambahPlayer);

        interfaceConnection = ApiConnection.getClient().create(InterfaceConnection.class);
        btnTambahPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_Player();
            }
        });
    }

    private void add_Player() {
        String namaPlayer = inputNamaPlayer.getText().toString();
        String levelPlayer = inputLevelPlayer.getText().toString();
        if (namaPlayer.isEmpty() || namaPlayer.length() == 0){
            layoutNamaPlayer.setError("Nama Player Tidak Boleh Kosong");
        } else if (levelPlayer.isEmpty() || levelPlayer.length() == 0){
            layoutLevelPlayer.setError("Level Player Tidak Boleh Kosong");
        } else if (((!namaPlayer.isEmpty() && namaPlayer.length() !=0) && (!levelPlayer.isEmpty() && levelPlayer.length()!=0))){
            Call<data_response> add_barang = interfaceConnection.tambah_player(namaPlayer, levelPlayer);
            add_barang.enqueue(new Callback<data_response>() {
                @Override
                public void onResponse(Call<data_response> call, Response<data_response> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<data_response> call, Throwable t) {
                    Log.d("Error here", "Error here", t);
                    t.printStackTrace();
                    Log.d("Error here", "Error here2", t);
                    Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_main, container, false);
    }
}
