package com.example.crud2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crud2.model.data_model;
import com.example.crud2.response.data_response;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import rest.ApiConnection;
import rest.InterfaceConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateData extends Fragment {

    TextInputEditText updateNamaPlayer, updateLevelPlayer;
    TextInputLayout layoutUpdateNamaPlayer, layoutUpdateLevelPlayer;
    Button btnUpdatePlayer;
    String kodePlayer, namaPlayer, levelPlayer;
    TextView hiddenKodePlayer;
    InterfaceConnection interfaceConnection;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        interfaceConnection = ApiConnection.getClient().create(InterfaceConnection.class);
        updateNamaPlayer = (TextInputEditText)view.findViewById(R.id.updateNamaPlayer);
        updateLevelPlayer = (TextInputEditText)view.findViewById(R.id.updateLevelPlayer);
        layoutUpdateNamaPlayer = (TextInputLayout)view.findViewById(R.id.layoutUpdateNamaPlayer);
        layoutUpdateLevelPlayer = (TextInputLayout)view.findViewById(R.id.layoutUpdateLevelPlayer);
        hiddenKodePlayer = (TextView)view.findViewById(R.id.hiddenkodePlayer);
        btnUpdatePlayer = (Button)view.findViewById(R.id.btnUpdatePlayer);

        try {
            final Bundle bundle = getArguments();
            kodePlayer = bundle.getString("kdPlayer");
            namaPlayer = bundle.getString("nmPlayer");
            levelPlayer = bundle.getString("levelPlayer");
        }
        catch(final Exception e){
            // Do nothing
        }

        updateNamaPlayer.setText(namaPlayer);
        updateLevelPlayer.setText(levelPlayer);

        btnUpdatePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePlayer();
            }
        });
    }

    private void updatePlayer() {
//        Toast.makeText(getContext(), namaBarang, Toast.LENGTH_SHORT).show();
        String getNewNamaPlayer = updateNamaPlayer.getText().toString();
        String getNewLevelPlayer = updateLevelPlayer.getText().toString();
        data_model dataModel = new data_model();
        dataModel.setNama_player(getNewLevelPlayer);
        dataModel.setLevel_player(getNewNamaPlayer);
        Call<data_response> update_data_player = interfaceConnection.update_barang(kodePlayer, getNewNamaPlayer, getNewLevelPlayer);
        Log.d("Kode Player", kodePlayer);
        Log.d("Nama Player", kodePlayer);
        Log.d("Level Player ", kodePlayer);
        update_data_player.enqueue(new Callback<data_response>() {
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
                Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.update_data, container, false);
    }


}
