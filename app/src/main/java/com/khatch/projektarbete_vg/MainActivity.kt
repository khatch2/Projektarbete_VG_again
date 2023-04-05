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
import com.khatch.projektarbete_vg.book.Book
import com.khatch.projektarbete_vg.book.BookRepository
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
        println(
            " The path of my-google-books-database.db is:  ${
                applicationContext.getDatabasePath(/* p0 = */"my-google-books-database.db")
            }"
        )

        // INSERT
        fun insertTheBook(sW: String, sT:String, pL:String, mT:String, mA:String) {
            bookRepository.performDatabaseOperation(Dispatchers.IO) {
                bookRepository.addBook(
                    Book(sW, sT, pL, mT, mA)
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
            counterSearchesViewModel.push(element = edEnterDesiredBook.text.toString())
            counterSearchesViewModel.add()
            println("size of counterSearchesViewModel = ${counterSearchesViewModel.uiState.value.searchQueries.size}") // Then to change this line to see the last pushed string on Array
            for(myString in counterSearchesViewModel.uiState.value.searchQueries) {
                println(" myString = $myString")
            }

            if(counterSearchesViewModel.uiState.value.searchQueries.isNotEmpty()) {
                // Update UI Elements
                tvCounterSearchesValue.text =
                    counterSearchesViewModel.uiState.value.searchQueries.get(
                        counterSearchesViewModel.uiState.value.searchQueries.size -1
                    ).toString()
                //println("searchQueries are: "+counterSearchesViewModel.uiState.value.searchQueries.toString())
                print("searchQueries are: ")
                for (i in counterSearchesViewModel.uiState.value.searchQueries) {
                    print(i+" , ")
                }
                //println("==== counterSearchesViewModel.uiState.value.searchQueries ====")


                    insertTheBook(
                        counterSearchesViewModel.uiState.value.searchQueries[
                                counterSearchesViewModel.uiState.value.searchQueries.size - 1
                        ], // Here is searchedWord
                        counterSearchesViewModel.uiState.value.searchQueries[0], // TODO - Change this line to smallThumbnail
                        counterSearchesViewModel.uiState.value.searchQueries[0], // TODO - Change this line to previewLink
                        counterSearchesViewModel.uiState.value.searchQueries[0], // TODO - Change this line to title
                        counterSearchesViewModel.uiState.value.searchQueries[0], // TODO - Change this line to authors
                    ) // TODO MUST to vary these params

                println()
                println("<===========================>")
                println("Fetching Books from RoomDB")
                val myFetchedBooks: List<Book> = fetchTheBook()
                println("RoomDB size = ${myFetchedBooks.size}")
                for (j_book in myFetchedBooks) { // TODO: it must enter inside ??
                    println("Item \"searchedWord\" from myFetchedBooks is: ${j_book.searchedWord}")
                    println("Item \"authors\" from myFetchedBooks is: ${j_book.authors}")
                    println("Item \"id\" from myFetchedBooks is: ${j_book.id}")
                    println("Item \"previewLink\" from myFetchedBooks is: ${j_book.previewLink}")
                    println("Item \"smallThumbnail\" from myFetchedBooks is: ${j_book.smallThumbnail}")
                    println("Item \"title\" from myFetchedBooks is: ${j_book.title}")
                    println("Item \"toString()\" from myFetchedBooks is: ${j_book.toString()}")


                }
                println("<===========================>")
                println("=== END Search History ===")
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
                    // TODO : MUST to rewrite this part to achieve the Updating UI Elements
                    var mySearchQueries = counterSearchesViewModel.uiState.value.searchQueries
                    var myCounterSearchValue = counterSearchesViewModel.uiState.value.counterSearchesValue
                    for (i in mySearchQueries) {
                        println("Item from mySearchQueries = $i")
                    }
                    println("myCounterSearchValue = $myCounterSearchValue")
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

