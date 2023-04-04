package com.khatch.projektarbete_vg

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khatch.projektarbete_vg.book.Book
import com.khatch.projektarbete_vg.book.IBookDao

@Database(entities = [Book::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): IBookDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "my-google-books-database.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
