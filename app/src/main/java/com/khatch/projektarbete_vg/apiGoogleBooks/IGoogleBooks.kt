package com.khatch.projektarbete_vg.apiGoogleBooks

import retrofit2.Call
import retrofit2.http.GET

interface IGoogleBooks {
    @GET("/books/v1/volumes")
    fun getDesiredBook() : Call<GoogleBooks>
}
