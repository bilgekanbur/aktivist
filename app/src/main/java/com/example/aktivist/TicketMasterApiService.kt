package com.example.aktivist

import TicketmasterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketMasterApiService {
    @GET("discovery/v2/events.json")
    fun getEvents(
        @Query("apikey") apiKey: String, // API anahtarınız
        @Query("keyword") keyword: String?, // İsteğe bağlı anahtar kelime (ör: music)
        @Query("city") city: String? // İsteğe bağlı şehir bilgisi (ör: Istanbul)
    ): Call<TicketmasterResponse>
}
