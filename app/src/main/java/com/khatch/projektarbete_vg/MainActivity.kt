package com.khatch.projektarbete_vg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khatch.projektarbete_vg.counter.CounterViewModel
import com.khatch.projektarbete_vg.databinding.ActivityMainBinding
import androidx.activity.viewModels
import com.khatch.projektarbete_vg.apiGoogleBooks.IGoogleBooks
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

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
        

    }
}