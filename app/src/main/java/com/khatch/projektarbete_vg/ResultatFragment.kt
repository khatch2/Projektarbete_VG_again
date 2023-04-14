package com.khatch.projektarbete_vg

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khatch.projektarbete_vg.apiGoogleBooks.GoogleBookItem
import com.khatch.projektarbete_vg.apiGoogleBooks.GoogleBooks
import com.khatch.projektarbete_vg.apiGoogleBooks.GoogleBooksResponse
import com.khatch.projektarbete_vg.apiGoogleBooks.IGoogleBooks
import com.khatch.projektarbete_vg.book.Book
import com.khatch.projektarbete_vg.book.BookRepository
import com.khatch.projektarbete_vg.counterSearches.CounterSearchesViewModel
import com.khatch.projektarbete_vg.databinding.FragmentResultatBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class ResultatFragment : Fragment() {
    private lateinit var bindingResultatFragment: FragmentResultatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    //private lateinit var bindingResultatFragment: FragmentResultatBinding

    //private var baseUrl = "https://www.googleapis.com/"
    private var querySentence = ""

    //@SuppressLint("UnsafeRepeatOnLifecycleDetector")
    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingResultatFragment = FragmentResultatBinding.inflate(layoutInflater, container, false)
        val returnedViewFrameLayout: FrameLayout = bindingResultatFragment.root

        // setting RoomDB
        val db: AppDatabase = AppDatabase.getInstance(this@ResultatFragment.requireContext())
        //val db: AppDatabase = AppDatabase.getInstance(applicationContext)
        val bookRepository = BookRepository(db, lifecycleScope)
        println(
            " The path of my-google-books-database.db is:  " + requireContext().getDatabasePath(/* p0 = */
                "my-google-books-database.db"
            )
        )

        // ViewModel
        val counterSearchesViewModel: CounterSearchesViewModel by viewModels<CounterSearchesViewModel>()
        //val counterViewModel by viewModels <CounterViewModel>()
        //println(" counterSearchesViewModel = $counterSearchesViewModel ")

        // ID:s
        val tvCounterSearchesValueResultatFragment: TextView =
            bindingResultatFragment.tvCounterSearchesValueResultatFragment
        val ivFirstResult: ImageView = bindingResultatFragment.ivFirstResult
        val tvFirstResultTitle: TextView = bindingResultatFragment.tvFirstResultTitle
        val ivSecondResult: ImageView = bindingResultatFragment.ivSecondResult
        val tvFirstResultDescription: TextView = bindingResultatFragment.tvFirstResultDescription
        val edEnterDesiredBookResultatFragment: EditText =
            bindingResultatFragment.edEnterDesiredBookResultatFragment
        val btnBookSearchResultatFragment: Button =
            bindingResultatFragment.btnBookSearchResultatFragment
        val ivGoogleBooksLillaFruntimmer: ImageView =
            bindingResultatFragment.ivGoogleBooksLillaFruntimmer
        val tvGoogleBooksApiDescResultatFragment: TextView =
            bindingResultatFragment.tvGoogleBooksApiDescResultatFragment
        var ivFox = bindingResultatFragment.ivFox



        // Logic goes here
        if (edEnterDesiredBookResultatFragment.text.toString() == "") {
            querySentence = "fruntimmer"
        } else {
            querySentence = edEnterDesiredBookResultatFragment.text.toString()
            println(edEnterDesiredBookResultatFragment.text)
            println(edEnterDesiredBookResultatFragment.text.toString())
        }
        ivFirstResult.setOnClickListener() {}
        tvFirstResultTitle.setOnClickListener() {}
        ivSecondResult.setOnClickListener() {}
        tvFirstResultDescription.setOnClickListener() {}
        edEnterDesiredBookResultatFragment.setOnClickListener() {}
        ivFox.setOnClickListener() {}
        btnBookSearchResultatFragment.setOnClickListener() {

            if (edEnterDesiredBookResultatFragment.text.toString() == "") {
                querySentence = "fruntimmer"
            } else {
                querySentence = edEnterDesiredBookResultatFragment.text.toString()
                //println(edEnterDesiredBookResultatFragment.text)
                //println(edEnterDesiredBookResultatFragment.text.toString())
            }


            // Declaration of INSERT
            fun insertTheBook(searchedWord: String, title: String, authors: String,
                              publishedDate: String, description: String, smallThumbnail: String,
                              thumbnail: String) {
                bookRepository.performDatabaseOperation(Dispatchers.IO) {
                    bookRepository.addBook(
                        Book(searchedWord, title, authors, publishedDate,
                            description, smallThumbnail, thumbnail)


                    )
                }
            }

            // Declaration of FETCH
            fun fetchTheBook(): List<Book> {
                var booksList: List<Book> = emptyList()
                bookRepository.performDatabaseOperation(Dispatchers.IO) {
                    booksList = bookRepository.getAllBooks()
                    println("[Inside IO]booksList = $booksList")
                    bookRepository.performDatabaseOperation(Dispatchers.Main) {
                        println("[Inside Main] bookList = $booksList")
                    }
                }
                return booksList
            }

            // retrofit
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            println(" retrofit = " + retrofit)

            val desiredBook: Call<GoogleBooksResponse> =
                retrofit.create<IGoogleBooks>().getDesiredBook(querySentence)
            println("desiredBook = " + desiredBook)
            println("desiredBook.isExecuted " + desiredBook.isExecuted)
            println("desiredBook.isCanceled " + desiredBook.isCanceled)
            //var lookInsideReqest = desiredBook.request()
            //println("lookInsideReqest.url() " + lookInsideReqest.url())

            desiredBook.enqueue(object : Callback<GoogleBooksResponse> {
                override fun onResponse(
                    call: Call<GoogleBooksResponse>,
                    response: Response<GoogleBooksResponse>
                ) {
                    // Status code 200 - 300
                    if(response.isSuccessful){
                        println("Successfull HTTP code is = " + response.code())
                        //println("response.message() is = " + response.message())
                        var myBook: GoogleBooksResponse? = response.body()

                        // Is myBook NOT null?
                        if (myBook != null) {
                            println(" myBook = " + myBook)
                            for(item: GoogleBookItem in myBook.items) {
                                println("(item of GoogleBookItem).volumeinfo.imageLinks = "
                                        + item.volumeInfo.imageLinks)

                            }
                            tvFirstResultTitle.text = myBook.items.first().volumeInfo.title
                            tvFirstResultDescription.text = myBook.items.first().volumeInfo.description
                            val firstImage = myBook.items.first().volumeInfo.imageLinks?.smallThumbnail
                            println("firstImage = " + firstImage)
                            var resultString = firstImage?.drop(4)
                            resultString = "https" + resultString
                            println("resultString = " + resultString)
                            //val firstImageString: String = firstImage.toString()
                            //println("firstImageString =  " + firstImageString)
                            /*
                            Glide.with(bindingResultatFragment.root)
                                /* .load("https://books.google.com/books/content?id=l-OzCkpXtA4C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api */
                                .load(resString)
                                .into(ivFox)
                            */


                            Glide.with(bindingResultatFragment.root)
                                .load(resultString)
                                 .apply(RequestOptions.overrideOf(450))
                                .into(ivFirstResult)

                            insertTheBook(
                                counterSearchesViewModel.uiState.value.searchQueries[
                                        counterSearchesViewModel.uiState.value.searchQueries.size - 1
                                ], // Here is searchedWord
                                myBook.items.first().volumeInfo.title,
                                myBook.items.first().volumeInfo.authors.toString(),
                                myBook.items.first().volumeInfo.publishedDate.toString(),
                                myBook.items.first().volumeInfo.description.toString(),
                                myBook.items.first().volumeInfo.imageLinks?.smallThumbnail.toString(),
                                myBook.items.first().volumeInfo.imageLinks?.thumbnail.toString()
                            )

                        }
                    } else {
                        println(" ERROR myBook was null !!! "+" HTTP code = " + response.code()) // DONE: FIXED http_error was = 400
                    }
                }

                override fun onFailure(call: Call<GoogleBooksResponse>, t: Throwable) {
                    // ERROR + 404 Not found
                    // ERROR + No Internet Connection
                    println(" ERROR  = ${t.message}")
                    //println(t.printStackTrace().toString())
                    println(t.localizedMessage)
                    println(t.fillInStackTrace())

                    println()
                    println(" call.request() ${call.request()}")
                    println(" call.toString() $call")
                    println(" call.isExecuted ${call.isExecuted}")
                    println("call.timeout() ${call.timeout()}")
                }
            })





            counterSearchesViewModel.push(element = edEnterDesiredBookResultatFragment.text.toString())
            counterSearchesViewModel.add()
            println("size of counterSearchesViewModel = ${counterSearchesViewModel.uiState.value.searchQueries.size}") // Then to change this line to see the last pushed string on Array
            for (myString in counterSearchesViewModel.uiState.value.searchQueries) {
                println(" myString = $myString")
            }

            if (counterSearchesViewModel.uiState.value.searchQueries.isNotEmpty()) {
                // Update UI Elements
                tvCounterSearchesValueResultatFragment.text = " Last counterSearchesValue = " +
                    counterSearchesViewModel.uiState.value.searchQueries.get(
                        counterSearchesViewModel.uiState.value.searchQueries.size - 1
                    ).toString()
                //println("searchQueries are: "+counterSearchesViewModel.uiState.value.searchQueries.toString())
                print("searchQueries are: ")
                for (i_String in counterSearchesViewModel.uiState.value.searchQueries) {
                    print("$i_String , ")
                }
                //println("==== counterSearchesViewModel.uiState.value.searchQueries ====")




                println()
                println("<===========================>")
                println("Fetching Books from RoomDB")
                val myFetchedBooks: List<Book> = fetchTheBook()
                println("RoomDB size = ${myFetchedBooks.size}")  // TODO - Fix this after Lunch
                for (j_book: Book in myFetchedBooks) { // TODO: it must enter inside ??
                    println("Item \"searchedWord\" from myFetchedBooks is: ${j_book.searchedWord}")
                    println("Item \"authors\" from myFetchedBooks is: ${j_book.authors}")
                    println("Item \"id\" from myFetchedBooks is: ${j_book.id}")
                    println("Item \"smallThumbnail\" from myFetchedBooks is: ${j_book.smallThumbnail}")
                    println("Item \"title\" from myFetchedBooks is: ${j_book.title}")
                    println("Item \"toString()\" from myFetchedBooks is: ${j_book.toString()}")
                }
                println("<===========================>")
                println("=== END Search History ===")
                Toast.makeText(
                    /* context = */ requireContext(),
                    /* text = */
                    "{ ${edEnterDesiredBookResultatFragment.text} } has been added to " +
                            "the search history successfully. ",
                    /* duration = */
                    Toast.LENGTH_LONG
                ).show()
            }
            ivGoogleBooksLillaFruntimmer.setOnClickListener() {}
            tvGoogleBooksApiDescResultatFragment.setOnClickListener() {}


            // access the ListView from xml-file


            // ViewModel Lifecycle
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    counterSearchesViewModel.uiState.collect() {
                        // TODO : MUST to rewrite this part to achieve the Updating UI Elements
                        val mySearchQueries: Array<String> =
                            counterSearchesViewModel.uiState.value.searchQueries
                        val myCounterSearchValue: Int =
                            counterSearchesViewModel.uiState.value.counterSearchesValue
                        for (i: String in mySearchQueries) {
                            println("Item from mySearchQueries = $i")
                        }
                        println("myCounterSearchValue = $myCounterSearchValue")
                    }
                }
            }
        }

        return returnedViewFrameLayout
    }






        // OnClick Snack-bar Fragment

        // OnClick ViewModel Value btnAddTomatoFragment

        // OnClick


    }

