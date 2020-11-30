<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\player_model;

class apicontroller extends Controller
{
    public function get_all_player(){
        return response()->json(player_model::all(),200);
    }

    public function insert_data_player(Request $request){
        $insert_player=new player_model;
        $insert_player->nama_player=$request->namaplayer;
        $insert_player->level_player=$request->levelplayer;
        $insert_player->save();
        return response([
            'status'=>true,
            'message'=>'Player Disimpan',
            //'data'=>$insert_player
        ],200);
    }

    public function update_data_player(Request $request,$id){
        $check_player=player_model::firstwhere('kode_player',$id);
        if($check_player){
            $data_player=player_model::find($id);
            $data_player->nama_player=$request->namaplayer;
            $data_player->level_player=$request->levelplayer;
            $data_player->save();
            return response([
                'status'=>true,
                'message'=>'Player Berhasil Diubah',
                //'update-data'=>$data_player
            ],200);
        }else{
            return response([
                'status'=>false,
                'message'=>'Player Tidak ditemukan',
            ],404);
        }
    }

    public function delete_data_player($id){
        $check_player=player_model::firstwhere('kode_player',$id);
        if($check_player){
            player_model::destroy($id);
            return response([
                'status'=>true,
                'message'=>'Player Berhasil Dihapus',
            ],200);
        } else{
            return response([
                'status'=>false,
                'message'=>'Player Tidak ditemukan',
            ],404);
        }
    }


    //
}
