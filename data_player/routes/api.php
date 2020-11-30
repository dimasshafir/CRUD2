<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get('player','apicontroller@get_all_player');
Route::post('player/tambah_player','apicontroller@insert_data_player');
Route::put('player/update/{kode_player}', 'apicontroller@update_data_player');
Route::delete('player/update/{kode_player}', 'apicontroller@delete_data_player');