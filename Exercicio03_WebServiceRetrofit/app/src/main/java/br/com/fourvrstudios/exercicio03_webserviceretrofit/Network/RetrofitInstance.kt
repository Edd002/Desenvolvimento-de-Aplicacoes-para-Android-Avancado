package br.com.fourvrstudios.exercicio03_webserviceretrofit.Network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build();
private const val BASE_URL = "https://viacep.com.br/ws/";

class RetrofitInstance {
    companion object {
        val retrofit: ApiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService:: class.java);
    }
}