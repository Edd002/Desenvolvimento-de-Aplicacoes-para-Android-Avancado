package com.example.app.trabalhofinal_worldwidepublicholiday.Network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{year}/{countryCode}")
    suspend fun getByYearAndCountryCode(@Path("year") year : String, @Path("countryCode") countryCode : String) : Response<List<Holiday>>;
}