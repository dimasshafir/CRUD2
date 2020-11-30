package com.example.crud2;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud2.model.data_model;
import com.example.crud2.response.data_response;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONObject;

import java.util.ArrayList;

import rest.ApiConnection;
import rest.InterfaceConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapterdaftarplayer extends RecyclerView.Adapter<adapterdaftarplayer.ViewHolder> {

    UpdateFragment updateDataPlayerFragement;
    InterfaceConnection interfaceConnection;
    ArrayList<data_model> daftarPlayer;
    Context mContext;
    String id;

    public adapterdaftarplayer(Context context){
        daftarPlayer = new ArrayList<>();
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_player_main, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.kodePlayer.setText(daftarPlayer.get(position).getKode_player());
        holder.namaPlayer.setText(daftarPlayer.get(position).getNama_player());
        holder.levelPlayer.setText(daftarPlayer.get(position).getLevel_player());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = daftarPlayer.get(position).getKode_player();
                popupDelete();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kode = daftarPlayer.get(position).getKode_player();
                String nama = daftarPlayer.get(position).getNama_player();
                String level = daftarPlayer.get(position).getLevel_player();
                Bundle bundle = new Bundle();
//                bundle.putString("key", "data");
                bundle.putString("kdPlayer", kode);
                bundle.putString("nmPlayer", nama);
                bundle.putString("lvlPlayer", level);

                Fragment fragment = new UpdateFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((MainActivity)mContext).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return daftarPlayer.size();
    }

    public void updatedataplayer(ArrayList<data_model> updatedataplayer){
        daftarPlayer.clear();
        daftarPlayer.addAll(updatedataplayer);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout layout_daftar_player;
        TextView kodePlayer, namaPlayer, levelPlayer;
        ImageButton btnDelete, btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_daftar_player = (ConstraintLayout)itemView.findViewById(R.id.layout_daftar_player);
            kodePlayer = (TextView) itemView.findViewById(R.id.textViewKodePlayer);
            namaPlayer = (TextView)itemView.findViewById(R.id.textViewNamaPlayer);
            levelPlayer = (TextView)itemView.findViewById(R.id.textViewLevelPlayer);
            btnDelete = (ImageButton)itemView.findViewById(R.id.imgBtnDeletePlayer);
            btnEdit = (ImageButton)itemView.findViewById(R.id.imgBtnEditPlayer);
        }
    }

    private void popupDelete() {
        Context context = new ContextThemeWrapper(mContext, R.style.AppTheme);
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);
        materialAlertDialogBuilder.setTitle("Hapus Player")
                .setMessage("Apa anda yakin ingin menghapus player ini?")
                .setNegativeButton("Batalkan", null)
                .setPositiveButton("Ya!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteplayer();
                    }
                })
                .show();
    }

    private void deleteplayer(){
        interfaceConnection = ApiConnection.getClient().create(InterfaceConnection.class);
        Call<data_response> hapus_data_barang = interfaceConnection.hapus_player(id);
        hapus_data_barang.enqueue(new Callback<data_response>() {
            @Override
            public void onResponse(Call<data_response> call, Response<data_response> response) {
                if (response.isSuccessful()){
                    Toast.makeText(mContext,  response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(mContext, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<data_response> call, Throwable t) {

            }
        });
    }


}