package com.khatch.projektarbete_vg.apiGoogleBooks

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IGoogleBooks {
    @GET("/books/v1/volumes")
    fun getDesiredBook(@Query("q") query:String) : Call<GoogleBooksResponse>
}
