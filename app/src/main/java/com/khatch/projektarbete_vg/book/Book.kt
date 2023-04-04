package com.khatch.projektarbete_vg.book

import androidx.room.Entity
import androidx.room.PrimaryKey

/* INFO
* @Entity("TableName") <-- Specifies your own table name
* @PrimaryKey(autogenerate = true) <-- Automatically increment ID
* @ColumInfo("") <-- Specify your own column name
* */
@Entity("Books")
data class Book (
    val smallThumbnail:String,
    val previewLink: String,
    val title: String,
    val authors: String
    ) {
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null
}

