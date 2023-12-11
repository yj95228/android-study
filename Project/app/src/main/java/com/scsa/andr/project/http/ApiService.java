package com.scsa.andr.project.http;

import com.scsa.andr.project.MemoDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // @Body : json을 http body에 전송
    // @Path : url pathVariable 에 추가
    // @Query : query string에 추가


    @GET("memo")
    Call<List<MemoDto>> getAllData();

    @GET("memo/{id}")
    Call<MemoDto> getData(@Path("id") String id);

    @POST("memo") // POST : insert (C)
    Call<String> insertPost(@Body MemoDto dto);

    @PUT("memo") // POST : update (C)
    Call<String> updatePost(@Body MemoDto dto);

    @DELETE("memo/{id}") // POST : delete (C)
    Call<String> deletePost(@Path("id") String id);

}
