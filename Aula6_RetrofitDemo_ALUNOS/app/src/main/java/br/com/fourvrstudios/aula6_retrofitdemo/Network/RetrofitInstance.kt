package br.com.fourvrstudios.aula6_retrofitdemo.Network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build();
private const val BASE_URL = "https://jsonplaceholder.typicode.com/";

val interceptor = HttpLoggingInterceptor().apply {
    // HEAD OU BODY trazem vulnerabilidade de segurança (utilizar apenas em ambiente de desenvolvimento)
    this.level = HttpLoggingInterceptor.Level.BODY
}

private val client = OkHttpClient.Builder().apply {
    this.addInterceptor(interceptor)
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(2, TimeUnit.SECONDS)
    .writeTimeout(25, TimeUnit.SECONDS)
}.build()

class RetrofitInstance {
    // Static acess
    companion object {
        val retrofit: ApiService = Retrofit.Builder()
            .baseUrl(BASE_URL) // URL base
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // Converter Factory
            .client(client)
            .build().create(ApiService:: class.java); // Interface onde estarão os métodos HTTP
    }
}