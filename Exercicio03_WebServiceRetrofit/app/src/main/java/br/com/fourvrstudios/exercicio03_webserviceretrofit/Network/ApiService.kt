package br.com.fourvrstudios.exercicio03_webserviceretrofit.Network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{cep}/json")
    suspend fun getByCepPath(@Path("cep") cep : String) : Response<Endereco>
}