package br.com.fourvrstudios.exercicio03_webserviceretrofit.Network

import com.squareup.moshi.Json

data class Endereco(
    @Json(name = "cep")
    val cep: String,
    @Json(name = "logradouro")
    val logradouro: String,
    @Json(name = "complemento")
    val complemento: String,
    @Json(name = "bairro")
    val bairro: String,
    @Json(name = "localidade")
    val localidade: String,
    @Json(name = "uf")
    val uf: String,
    @Json(name = "ibge")
    val ibge: String,
    @Json(name = "gia")
    val gia: String,
    @Json(name = "ddd")
    val ddd: String,
    @Json(name = "siafi")
    val siafi: String
)