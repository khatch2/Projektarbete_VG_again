package com.khatch.projektarbete_vg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khatch.projektarbete_vg.counter.CounterViewModel
import com.khatch.projektarbete_vg.databinding.ActivityMainBinding
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khatch.projektarbete_vg.apiGoogleBooks.GoogleBooks
import com.khatch.projektarbete_vg.apiGoogleBooks.IGoogleBooks
import com.khatch.projektarbete_vg.counterSearches.CounterSearchesViewModel
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

/* https://www.googleapis.com/books/v1/volumes?q=lilla+fruntimmer */

class MainActivity : AppCompatActivity() {
    /* Initialize ViewBinding */
    private lateinit var binding:ActivityMainBinding
    //private var baseUrl = "https://www.googleapis.com/"
    private var querySentence = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Set ViewBinding */
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        // ViewModel's
        val counterSearchesViewModel by viewModels<CounterSearchesViewModel>()
        //val counterViewModel by viewModels <CounterViewModel>()
        println(" counterSearchesViewModel = $counterSearchesViewModel ")


        // ID's
        val tvBookFinder = binding.tvBookFinder
        val tvGoogleBooksApi = binding.tvGoogleBooksApi // TextView
        val ivGoogleBooks = binding.ivGoogleBooks    // ImageView
        val edEnterDesiredBook = binding.edEnterDesiredBook
        val btnBookSearch = binding.btnBookSearch
        val tvCounterSearchesValue = binding.tvCounterSearchesValue

        if (edEnterDesiredBook.text.toString() == "") {
            querySentence = "lilla fruntimmer"
        }

        // OnClick's
        tvBookFinder.setOnClickListener() {}
        tvGoogleBooksApi.setOnClickListener() {}
        ivGoogleBooks.setOnClickListener() {}
        edEnterDesiredBook.setOnClickListener() {}

        btnBookSearch.setOnClickListener() {
            counterSearchesViewModel.push(edEnterDesiredBook.text.toString())

            if(counterSearchesViewModel.uiState.value.searchQueries.isNotEmpty()) {
                // Update UI Elements
                tvCounterSearchesValue.text =
                    counterSearchesViewModel.uiState.value.searchQueries.get(
                        counterSearchesViewModel.uiState.value.searchQueries.size -1
                    ).toString()
                println("serchQueries are: "+counterSearchesViewModel.uiState.value.searchQueries.toString())
        }
        tvCounterSearchesValue.setOnClickListener() {}

        // ViewModel LifeCycle
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                counterSearchesViewModel.uiState.collect() {

                    }
                }
            }
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        println("retrofit = "+retrofit.toString())
        val desiredBook = retrofit.create<IGoogleBooks>().getDesiredBook()
        println("desiredBook = "+desiredBook.toString())
        desiredBook.enqueue(object : Callback<GoogleBooks> {
            override fun onResponse(call: Call<GoogleBooks>, response: Response<GoogleBooks>) {

                // Status code 200 - 300
                if (response.isSuccessful) {
                    println("response.body() = "+response.body())
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
                println( t.toString() )
            }
        })
    }
}

