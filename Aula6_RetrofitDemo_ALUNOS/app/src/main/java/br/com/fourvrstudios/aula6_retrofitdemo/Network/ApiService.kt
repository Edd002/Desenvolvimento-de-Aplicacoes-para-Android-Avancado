package br.com.fourvrstudios.aula6_retrofitdemo.Network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Este método concatenará photos com a base url, resultadando em https://jsonplaceholder.typicode.com/photos
    @GET("photos")
    fun getAllCall() : Call<List<Photo>>

    @GET("photos")
    suspend fun getAll() : Response<List<Photo>>

    @GET("photos")
    suspend fun getPhotosByAlbumId(@Query("albumId") idAlbum : Int) : Response<List<Photo>>

    @GET("photos/{id}")
    suspend fun getByIdPath(@Path("id") myId : Int) : Response<Photo>

    @POST("photos")
    suspend fun salvarFoto(@Body foto: Photo) : Response<Photo>

    @FormUrlEncoded
    @POST("photos")
    suspend fun postPhotoField(
        @Field("albumId") albumId : Int,
        @Field("id") id : Int,
        @Field("thumbnailUrl") thumbnailUrl : String,
        @Field("title") title : String,
        @Field("url") url : String) : Response<Photo>

    // Sobrescreve
    @PUT("photos/{id}")
    suspend fun overwritePhoto(@Path("id") id: Int, @Body foto: Photo) : Response<Photo>

    // Atualiza
    @PATCH("photos/{id}")
    suspend fun patchPhoto(@Path("id") id : Int, @Body foto: Photo) : Response<Photo>

    @DELETE("photos/{id}")
    suspend fun deletePhoto(@Path("id") id : Int)
}