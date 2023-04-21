package com.khatch.projektarbete_vg

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

public var booksArrayList: ArrayList<Book> = arrayListOf()

class ResultatFragment : Fragment() {
    private lateinit var bindingResultatFragment: FragmentResultatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    /** private var baseUrl = "https://www.googleapis.com/" **/
    private var querySentence = ""

    //@SuppressLint("UnsafeRepeatOnLifecycleDetector")
    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingResultatFragment = FragmentResultatBinding.inflate(layoutInflater, container, false)
        val returnedViewResultatFragment: FrameLayout = bindingResultatFragment.root

        // setting RoomDB
        val db: AppDatabase = AppDatabase.getInstance(this@ResultatFragment.requireContext())
        val bookRepository = BookRepository(db, lifecycleScope)
        println(
            " The path of my-google-books-database.db is:  " + requireContext().getDatabasePath(/* name = */
                "my-google-books-database.db"
            )
        )

        // ViewModel
        val counterSearchesViewModel: CounterSearchesViewModel by viewModels<CounterSearchesViewModel>()

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
        val tvSecondResultTitle: TextView = bindingResultatFragment.tvSecondResultTitle
        val tvSecondResultDescription: TextView = bindingResultatFragment.tvSecondResultDescription
        val btnViewDatabase: Button = bindingResultatFragment.btnViewDatabase
        val btnDeleteAtRow3Testing: Button = bindingResultatFragment.btnDeleteAtRow3Testing
        val btnUpdateAtRow2Testing: Button = bindingResultatFragment.btnUpdateAtRow2Testing

        // Logic goes here
        //btnViewDatabase.isVisible = false
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
        tvSecondResultTitle.setOnClickListener() {}
        tvSecondResultDescription.setOnClickListener() {}

        // DONE: I want to test to change title att row 2 of db-file to "Sti"
        // Declaration of testingUpdate
        fun testingUpdate(intPos: Int, desiredNewString: String): Int {
            var var1: Int = 0
            bookRepository.performDatabaseOperation(Dispatchers.IO) {
                var1 = bookRepository.updateBookTitle(intPos, desiredNewString)

                bookRepository.performDatabaseOperation(Dispatchers.Main) {
                }
            }
            println(" Done, var1 = "+var1)
            return var1
        }
        btnUpdateAtRow2Testing.setOnClickListener() {
            println("btnUpdateAtRow2Testing has been clicked. " + btnUpdateAtRow2Testing)
            testingUpdate(2, "Sti")
        }


        // Declaration of testingDelete
        fun testingDelete(anItem: Int): ArrayList<Book> {
            var booksListTesting: ArrayList<Book> = arrayListOf()
            var numberOfDeletedRow: Int = 0
            bookRepository.performDatabaseOperation(Dispatchers.IO) {
                numberOfDeletedRow = bookRepository.deleteRow(anItem)
                if (booksListTesting.isNotEmpty()) {
                    println("[Dispatchers.IO] booksListTesting size = " + booksListTesting.last().id)
                    println("[Inside IO]booksListTesting = $booksListTesting")
                    println("[Dispatchers.IO] number = " + numberOfDeletedRow)
                }

                bookRepository.performDatabaseOperation(Dispatchers.Main) {
                    if (booksListTesting.isNotEmpty()) {
                        println("[Dispatchers.Main] booksListTesting size = " + booksListTesting.last().id)
                        println("[Inside Main] bookListTesting = $booksListTesting")
                        println("[Dispatchers.Main] number  = " + numberOfDeletedRow)
                    }
                }
            }
            return booksListTesting
        }

        btnDeleteAtRow3Testing.setOnClickListener() {
            testingDelete(3) // DONE : Check if it works, i.e. Here i wanted to test deleting the third row of RoomDB
        }




        btnBookSearchResultatFragment.setOnClickListener() {
            btnViewDatabase.isVisible = true

            if (edEnterDesiredBookResultatFragment.text.toString() == "") {
                querySentence = "fruntimmer"
            } else {
                querySentence = edEnterDesiredBookResultatFragment.text.toString()
            }

            // Declaration of INSERT
            fun insertTheBook(
                searchedWord: String, title: String, authors: ArrayList<String>?,
                publishedDate: String, description: String, smallThumbnail: String,
                thumbnail: String
            ) {
                if (authors != null) {
                    bookRepository.performDatabaseOperation(Dispatchers.IO) {
                        bookRepository.addBook(
                            Book(
                                searchedWord, title, authors.first(), publishedDate,
                                description, smallThumbnail, thumbnail
                            )
                        )
                    }
                } else {
                    bookRepository.performDatabaseOperation(Dispatchers.IO) {
                        bookRepository.addBook(
                            Book(
                                searchedWord, title, "isEmpty", publishedDate,
                                description, smallThumbnail, thumbnail
                            )
                        )
                    }
                }
            }

            // Declaration of FETCH
            fun fetchTheBook(): ArrayList<Book> {
                // var booksList: List<Book> = emptyList()
                bookRepository.performDatabaseOperation(Dispatchers.IO) {
                    booksArrayList = bookRepository.getAllBooks() as ArrayList<Book>

                    if (booksArrayList.isNotEmpty()) {
                        println("[Dispatchers.IO] booksList size = " + booksArrayList.last().id)
                        println("[Inside IO]booksList = $booksArrayList")
                    }

                    bookRepository.performDatabaseOperation(Dispatchers.Main) {

                        if (booksArrayList.isNotEmpty()) {
                            println("[Dispatchers.Main] booksList size = " + booksArrayList.last().id)
                            println("[Inside Main] bookList = $booksArrayList")
                        }

                    }
                }
                //booksTitlesFragmentArrayList.clear()
                //for (j: Book in booksList) { booksTitlesFragmentArrayList.add( j.title.toString() ) }
                return booksArrayList
            }

            // OnClick special variant
            btnViewDatabase.setOnClickListener() {      // DONE : Go to an another Fragment of interact with the database
                println(" btnViewDatabase was clicked. ")
                val retFetched: ArrayList<Book> = fetchTheBook() as ArrayList<Book>
                println(" retFetched = " + retFetched)  // TODO - It looks like that here retfetched is Nothing
                if (retFetched.isNotEmpty()) {
                    println("RoomDB size = " + retFetched.last().id + " rows")  // TODO - Fix this after Lunch
                }
                Navigation.findNavController(returnedViewResultatFragment).navigate(
                    R.id.action_resultatFragment_to_viewDatabaseFragment
                )
            }

            // retrofit
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val desiredBook: Call<GoogleBooksResponse> =
                retrofit.create<IGoogleBooks>().getDesiredBook(querySentence)

            desiredBook.enqueue(object : Callback<GoogleBooksResponse> {
                override fun onResponse(
                    call: Call<GoogleBooksResponse>,
                    response: Response<GoogleBooksResponse>
                ) {
                    // Status code 200 - 300
                    if (response.isSuccessful) {
                        val myBook: GoogleBooksResponse? = response.body()

                        // Is myBook NOT null?
                        if (myBook != null) {
                            println(" myBook = " + myBook)
                            booksTitlesFragmentArrayList.add(myBook.items.first().volumeInfo.title.toString())

                            tvFirstResultTitle.text = myBook.items.first().volumeInfo.title
                            tvFirstResultDescription.text =
                                myBook.items.first().volumeInfo.description

                            val firstImage =
                                myBook.items.first().volumeInfo.imageLinks?.smallThumbnail
                            var resultString1 = firstImage?.drop(4)
                            resultString1 = "https" + resultString1
                            Glide.with(bindingResultatFragment.root)
                                .load(resultString1)
                                .apply(RequestOptions.overrideOf(450))
                                .into(ivFirstResult)

                            tvSecondResultTitle.text = myBook.items[1].volumeInfo.title
                            tvSecondResultDescription.text = myBook.items[1].volumeInfo.description

                            val secondImage = myBook.items[1].volumeInfo.imageLinks?.smallThumbnail
                            var resultString2 = secondImage?.drop(4)
                            resultString2 = "https" + resultString2
                            Glide.with(bindingResultatFragment.root)
                                .load(resultString2)
                                .apply(RequestOptions.overrideOf(450))
                                .into(ivSecondResult)

                            insertTheBook(
                                querySentence, // Here is searchedWord
                                myBook.items.first().volumeInfo.title,
                                ArrayList(myBook.items.first().volumeInfo.authors),
                                myBook.items.first().volumeInfo.publishedDate.toString(),
                                myBook.items.first().volumeInfo.description.toString(),
                                myBook.items.first().volumeInfo.imageLinks?.smallThumbnail.toString(),
                                myBook.items.first().volumeInfo.imageLinks?.thumbnail.toString()
                            )
                        }
                    } else {
                        println(" ERROR myBook was null !!! " + " HTTP code = " + response.code()) // DONE: FIXED http_error was = 400
                    }
                }
                override fun onFailure(call: Call<GoogleBooksResponse>, t: Throwable) {
                    // ERROR + 404 Not found
                    // ERROR + No Internet Connection
                    println(" ERROR  = ${t.message}")
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
                println("searchQueries size = " + counterSearchesViewModel.uiState.value.searchQueries.size)
                print("searchQueries are: ")
                for (i_String: String in counterSearchesViewModel.uiState.value.searchQueries) {
                    print("$i_String , ")
                }
                println()
                println("<===========================>")
                println("Fetching Books from RoomDB")
                var myFetchedBooks: ArrayList<Book> =
                    fetchTheBook() as ArrayList<Book>  // TODO - It looks like that here myFetchedBooks.isEmpty() was true !!!
                if (myFetchedBooks.isNotEmpty()) {
                    println("RoomDB size = " + myFetchedBooks.last().id + " rows")  // TODO - Fix this after Lunch
                }

                for (j_book: Book in myFetchedBooks) { // DONE: it must enter inside ??
                    println("Notice: Usually the following lines cannot reach the last column in the database !!!")
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
                    /* text = */ "{ ${edEnterDesiredBookResultatFragment.text} } has been added to "
                            + "the search history successfully. ",
                    /* duration = */ Toast.LENGTH_LONG
                ).show()
            }
            ivGoogleBooksLillaFruntimmer.setOnClickListener() {}
            tvGoogleBooksApiDescResultatFragment.setOnClickListener() {}

            // ViewModel Lifecycle
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    counterSearchesViewModel.uiState.collect() {
                        // DONE : MUST to rewrite this part to achieve the Updating UI Elements
                        val mySearchQueries: Array<String> =
                            counterSearchesViewModel.uiState.value.searchQueries
                        val myCounterSearchValue: Int =
                            counterSearchesViewModel.uiState.value.counterSearchesValue
                        println(" myCounterSearchValue = " + myCounterSearchValue)
                        println("mySearchQueries.size =" + mySearchQueries.size)
                        for (i: String in mySearchQueries) {
                            println("Item from mySearchQueries = $i")
                        }
                    }
                }
            }
        }
        return returnedViewResultatFragment
    }
}
