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
import com.khatch.projektarbete_vg.apiGoogleBooks.GoogleBooks
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
            " The path of my-google-books-database.db is:  " + requireContext().getDatabasePath(/* name = */
                "my-google-books-database.db"
            )
        )

        // retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        println(" retrofit = $retrofit")

        // INSERT
        fun insertTheBook(sW: String, sT: String, pL: String, mT: String, mA: String) {
            bookRepository.performDatabaseOperation(Dispatchers.IO) {
                bookRepository.addBook(
                    Book(sW, sT, pL, mT, mA)
                )
            }
        }

        // FETCH
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


        // ViewModel
        val counterSearchesViewModel: CounterSearchesViewModel by viewModels<CounterSearchesViewModel>()
        //val counterViewModel by viewModels <CounterViewModel>()
        println(" counterSearchesViewModel = $counterSearchesViewModel ")

        // ID:s
        val tvCounterSearchesValueResultatFragment: TextView =
            bindingResultatFragment.tvCounterSearchesValueResultatFragment
        val ivFirstResult: ImageView = bindingResultatFragment.ivFirstResult
        val tvFirstResultDesc: TextView = bindingResultatFragment.tvFirstResultDesc
        val ivSecondResult: ImageView = bindingResultatFragment.ivSecondResult
        val tvSecondResultDesc: TextView = bindingResultatFragment.tvSecondResultDesc
        val edEnterDesiredBookResultatFragment: EditText =
            bindingResultatFragment.edEnterDesiredBookResultatFragment
        val btnBookSearchResultatFragment: Button =
            bindingResultatFragment.btnBookSearchResultatFragment
        val ivGoogleBooksLillaFruntimmer: ImageView =
            bindingResultatFragment.ivGoogleBooksLillaFruntimmer
        val tvGoogleBooksApiDescResultatFragment: TextView =
            bindingResultatFragment.tvGoogleBooksApiDescResultatFragment

        // Logic goes here
        if (edEnterDesiredBookResultatFragment.text.toString() == "") {
            querySentence = "fruntimmer"
        }
        ivFirstResult.setOnClickListener() {}
        tvFirstResultDesc.setOnClickListener() {}
        ivSecondResult.setOnClickListener() {}
        tvSecondResultDesc.setOnClickListener() {}
        edEnterDesiredBookResultatFragment.setOnClickListener() {}
        btnBookSearchResultatFragment.setOnClickListener() {
            counterSearchesViewModel.push(element = edEnterDesiredBookResultatFragment.text.toString())
            counterSearchesViewModel.add()
            println("size of counterSearchesViewModel = ${counterSearchesViewModel.uiState.value.searchQueries.size}") // Then to change this line to see the last pushed string on Array
            for (myString in counterSearchesViewModel.uiState.value.searchQueries) {
                println(" myString = $myString")
            }

            if (counterSearchesViewModel.uiState.value.searchQueries.isNotEmpty()) {
                // Update UI Elements
                tvCounterSearchesValueResultatFragment.text =
                    counterSearchesViewModel.uiState.value.searchQueries.get(
                        counterSearchesViewModel.uiState.value.searchQueries.size - 1
                    ).toString()
                //println("searchQueries are: "+counterSearchesViewModel.uiState.value.searchQueries.toString())
                print("searchQueries are: ")
                for (i in counterSearchesViewModel.uiState.value.searchQueries) {
                    print("$i , ")
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
                println("RoomDB size = ${myFetchedBooks.size}")  // TODO - Fix this after Lunch
                for (j_book: Book in myFetchedBooks) { // TODO: it must enter inside ??
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

