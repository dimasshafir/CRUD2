package rest;


import com.example.crud2.response.data_response;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InterfaceConnection {

    @GET("player")
    Call<data_response> daftar_player();

    @FormUrlEncoded
    @POST("player/tambah_player")
    Call<data_response> tambah_player(
            @Field("namaPlayer") String namaPlayer,
            @Field("levelPlayer") String levelPlayer
    );

    @DELETE("player/delete/{kode_player}")
    Call<data_response> hapus_player(@Path("kode_player") String id
    );

//    @Headers({"Content-Type: application/json"})
//    @PUT("barang/update/{kode_barang}")
//    Call<Data_Response> update_barang(@Path("kode_barang") String id,
//                                   @Body Data_Model data
//    );


//    @Multipart
//    @PUT("barang/update/{kode_barang}")
//    Call<Data_Response> update_barang(@Path("kode_barang") String id,
//                                      @Part("nama_barang") String nama_barang,
//                                      @Part("jumlah_barang") String jumlah_barang
//    );

    @FormUrlEncoded
    @PUT("player/update/{kode_player}")
    Call<data_response> update_barang(@Path("kode_player") String id,
                                      @Field("nama_player") String nama_player,
                                      @Field("level_player") String level_player
    );
}