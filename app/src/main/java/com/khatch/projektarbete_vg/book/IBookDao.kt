package com.khatch.projektarbete_vg.book

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


// Our Queries
@Dao
interface IBookDao {
    @Insert
    fun addBook(book:Book)
    @Query("SELECT * FROM Books")
    fun getAllBooks(): List<Book>

    @Query("DELETE FROM Books WHERE id = :targetedId")
    fun deleteRow(targetedId: Int): Int





    @Query("SELECT * FROM Books WHERE id IN (:bookIds)")
    fun getAllByBookId(vararg bookIds: Int): List<Book?>?

    @Query("SELECT * FROM Books WHERE searchedWord LIKE :first AND title = :t1 LIMIT 1")
    fun getOneBySearchedWordAndTitle(first: String, t1: String): Book?

    @Insert
    fun insertAll(vararg book: Book)

    @Delete
    fun deleteTable(book: Book)

}
