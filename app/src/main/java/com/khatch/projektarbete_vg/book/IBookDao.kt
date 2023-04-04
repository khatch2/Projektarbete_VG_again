package com.khatch.projektarbete_vg.book

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// Our Queries
@Dao
interface IBookDao {
    @Insert
    fun addBook(book:Book)

    @Query("SELECT * FROM Books")
    fun getAllBooks(): List<Book>
}
