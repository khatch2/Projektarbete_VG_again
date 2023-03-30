package com.khatch.projektarbete_vg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khatch.projektarbete_vg.counter.CounterViewModel
import com.khatch.projektarbete_vg.databinding.ActivityMainBinding
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khatch.projektarbete_vg.apiGoogleBooks.GoogleBooks
import com.khatch.projektarbete_vg.apiGoogleBooks.IGoogleBooks
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

/* https://www.googleapis.com/books/v1/volumes?q=lilla+fruntimmer */

class MainActivity : AppCompatActivity() {
    /* Initialize ViewBinding */
    private lateinit var binding:ActivityMainBinding
    private var baseUrl = "https://www.googleapis.com/"
    private var querySentence = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Set ViewBinding */
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        // ViewModel's
        val counterViewModel by viewModels <CounterViewModel>()
        println(" counterViewModel = $counterViewModel ")

        // ID's
        val tvBookFinder = binding.tvBookFinder
        val tvGoogleBooksApi = binding.tvGoogleBooksApi // TextView
        val ivGoogleBooks = binding.ivGoogleBooks    // ImageView
        val edEnterDesiredBook = binding.edEnterDesiredBook

        if (edEnterDesiredBook.text.toString() == "") {
            querySentence = "lilla fruntimmer"
        }

        // OnClick's
        tvBookFinder.setOnClickListener() {}
        tvGoogleBooksApi.setOnClickListener() {}
        ivGoogleBooks.setOnClickListener() {}
        edEnterDesiredBook.setOnClickListener() {}

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val desiredBook = retrofit.create<IGoogleBooks>().getDesiredBook()

        desiredBook.enqueue(object : Callback<GoogleBooks> {
            override fun onResponse(call: Call<GoogleBooks>, response: Response<GoogleBooks>) {

                // Status code 200 - 300
                if (response.isSuccessful) {
                    val myDesiredGoogleBooks = response.body()

                    // Is FOX NOT null?
                    if (myDesiredGoogleBooks != null) {
                        tvGoogleBooksApi.text = myDesiredGoogleBooks.myImage

                        // Load Image
                        Glide.with(binding.root)
                            .load(myDesiredGoogleBooks.myImage)
                            .apply(RequestOptions.overrideOf(450))
                            .into(ivGoogleBooks)
                    }
                } else {
                    println("ERROR")
                }

            }

            override fun onFailure(call: Call<GoogleBooks>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}

