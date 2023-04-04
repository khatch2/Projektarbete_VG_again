package com.khatch.projektarbete_vg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

import androidx.lifecycle.lifecycleScope
import com.khatch.projektarbete_vg.book.Book
import com.khatch.projektarbete_vg.book.BookRepository
//import com.example.lektion_11_roomdb.databinding.ActivityMainBinding
//import com.example.lektion_11_roomdb.user.User
//import com.example.lektion_11_roomdb.user.UserRepository
import kotlinx.coroutines.Dispatchers


/* https://www.googleapis.com/books/v1/volumes?q=fruntimmer */

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

        // setting RoomDB
        val db = AppDatabase.getInstance(applicationContext)
        val bookRepository = BookRepository(db, lifecycleScope)

        // INSERT
        fun insertTheBook(sT:String, pL:String, mT:String, mA:String) {
            bookRepository.performDatabaseOperation(Dispatchers.IO) {
                bookRepository.addBook(
                    Book(sT, pL, mT, mA)
                )
            }
        }

        // FETCH
        fun fetchTheBook():List<Book> {
            var booksList:List<Book> = emptyList()
            bookRepository.performDatabaseOperation(Dispatchers.IO) {
                booksList = bookRepository.getAllBooks()
                println("[Inside IO]booksList = $booksList")
                bookRepository.performDatabaseOperation(Dispatchers.Main) {
                    println("[Inside Main] bookList = $booksList")
                }
            }
            return booksList
        }



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
            querySentence = "fruntimmer"
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
                println("searchQueries are: "+counterSearchesViewModel.uiState.value.searchQueries.toString())
                println("==== counterSearchesViewModel.uiState.value.searchQueries ====")

                print("  Search history : ")
                for (item in counterSearchesViewModel.uiState.value.searchQueries) {
                    print("  $item  ")
                }
                println()
                println("=== END ===")
                Toast.makeText(this,
                    "{ ${edEnterDesiredBook.text} } has been added to " +
                            "the search history successfully. ",
                    Toast.LENGTH_LONG).show()
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
        println("retrofit = $retrofit")
        val desiredBook = retrofit.create<IGoogleBooks>().getDesiredBook(querySentence)
        println("desiredBook = ${desiredBook.toString()}")

        desiredBook.enqueue(object : Callback<GoogleBooks> {
            override fun onResponse( call: Call<GoogleBooks>, response: Response<GoogleBooks> ) {
                // Status code 200 - 300
                if (response.isSuccessful) {
                    val myBook = response.body()

                    // Is myBook NOT null?
                    if (myBook != null) {
                        //tvGoogleBooksApi.text = myBook.get(0).myTitle
                        tvGoogleBooksApi.text = myBook.myTitle
                        // Load Image
                        Glide.with(binding.root)
                            // .load(myBook.get(0).myImage)
                            .load(myBook.myImage)
                            .apply(RequestOptions.overrideOf(450))
                            .into(ivGoogleBooks)


                    }

                } else {
                    println("ERROR")
                    println(" errorBody(): "+response.errorBody())
                }
            }

            override fun onFailure(call: Call<GoogleBooks>, t: Throwable) {
                // ERROR + 404 Not found
                // ERROR + No Internet Connection
                println(" ERROR  = ${t.message}")
                println(t.localizedMessage)
                println(t.fillInStackTrace())

                println()
                println(" call.request() "+call.request())
                println(" call.toString() "+call.toString())
                println(" call.isExecuted "+call.isExecuted)
                println("call.timeout() "+call.timeout().toString())
            }

        })

    }
}

