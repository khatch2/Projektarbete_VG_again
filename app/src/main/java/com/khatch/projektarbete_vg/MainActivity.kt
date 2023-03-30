package com.khatch.projektarbete_vg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khatch.projektarbete_vg.counter.CounterViewModel
import com.khatch.projektarbete_vg.databinding.ActivityMainBinding
import androidx.activity.viewModels

/* https://www.googleapis.com/books/v1/volumes?q=lilla+fruntimmer */

class MainActivity : AppCompatActivity() {
    /* Initialize ViewBinding */
    private lateinit var binding:ActivityMainBinding
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

        // OnClick's
        tvBookFinder.setOnClickListener() {}
    }
}