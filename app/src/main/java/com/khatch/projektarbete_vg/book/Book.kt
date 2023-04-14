package com.khatch.projektarbete_vg.book

import androidx.room.Entity
import androidx.room.PrimaryKey

/* INFO
* @Entity("TableName") <-- Specifies your own table name
* @PrimaryKey(autogenerate = true) <-- Automatically increment ID
* @ColumInfo("") <-- Specify your own column name
* */
@Entity(tableName = "Books")
data class Book (
    var searchedWord: String,
    var title: String? = null,
    var authors: String? = null,
    var publishedDate: String? = null,
    var description: String? = null,
    var smallThumbnail: String? = null,
    var thumbnail: String? = null
    ) {
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null
}

