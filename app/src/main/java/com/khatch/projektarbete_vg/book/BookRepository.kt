package com.khatch.projektarbete_vg.book

import com.khatch.projektarbete_vg.AppDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BookRepository(
    private val appDatabase: AppDatabase,       // Our Database Class
    private val coroutineScope: CoroutineScope  // Allows work with Threads
) {
    /* TODO - DEFINE OUR METHODS FOR QUERIES */

    fun addBook(book: Book) {
        appDatabase.bookDao().addBook(book)
    }

    fun getAllBooks(): List<Book> {
        return appDatabase.bookDao().getAllBooks()
    }

    fun deleteRow(b: Int): Int {
        return appDatabase.bookDao().deleteRow(b)
    }

    // Allows Working with Threads
    fun performDatabaseOperation(
        dispatcher: CoroutineDispatcher,
        databaseOperation: suspend () -> Unit
    ) {
        coroutineScope.launch(dispatcher) {
            databaseOperation()
        }
    }
}
