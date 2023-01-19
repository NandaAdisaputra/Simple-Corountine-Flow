package com.nandaadisaputra.coroutineflow.network

import com.nandaadisaputra.coroutineflow.model.PlaceResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("mapbox.places/{query}.json")
    suspend fun getCountry(
        //Endpoint URL : mapbox.places/{KATA_KUNCI_TEMPAT}.json,
        // bagian yang di dalam tanda kurung kurawal merupakan Path
        // karena kita perlu mengubah isinya setiap kali melakukan pencarian.
        // Di dalam Retrofit untuk mengisinya menggunakan @Path.
        @Path("query") query: String,
        // Query 1 : access_token={ACCESS_TOKEN_ANDA}, untuk awal query selalu menggunakan tanda tanya (?).
        // Di dalam Retrofit, untuk mengisinya menggunakan @Query.
        @Query("access_token") accessToken: String,
        //Query 2 : autocomplete=true, untuk memisahkan antar query selalu menggunakan tanda dan (&).
        // Di dalam Retrofit caranya sama dengan query 1. Hanya saja karena kita selalu menginginkan
        // autoComplete bernilai True maka kita bisa memberikan default value seperti cara di atas.
        @Query("autocomplete") autoComplete: Boolean = true
        //Jika memilih False maka kita hanya mencari tempat yang awalnya sesuai dengan yang kita ketik saja.
        // Misal “coding” maka yang muncul hanya “Codingle”, CodingSans” dan “Codington”.

        // Jika memilih True maka kita juga mencari tempat yang mengandung kata-kata yang kita ketik.
        // Misal “coding, maka yang muncul bisa jadi “Dicoding”, “YukCoding,  “Coding Road”.
    ): PlaceResponse
}