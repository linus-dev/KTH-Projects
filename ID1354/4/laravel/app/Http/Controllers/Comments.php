<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Auth;
use Illuminate\Http\Request;
use DB;

class Comments extends Controller {
  public function Post(Request $request) {
    DB::table('comments')->insert(['user' => Auth::user()->email,
                                   'recipe' => $request->dish_id,
                                   'comment' => $request->txt]);    
    return (Auth::Check() ? '1' : '0');
  }

  public function GetAll(Request $request) {
    $result = DB::table('comments')->where('recipe', $request->dish_id)->get();
    return $result->toJson();
  }

  public function Delete(Request $request) {
    $where = [
      ['id', $request->cmt_id],
      ['user', Auth::User()->email],
    ];
    DB::table('comments')->where($where)->delete();
  }
}
