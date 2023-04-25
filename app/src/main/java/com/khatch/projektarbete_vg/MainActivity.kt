package com.khatch.projektarbete_vg

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.khatch.projektarbete_vg.databinding.ActivityMainBinding
import kotlin.system.exitProcess


/* https://www.googleapis.com/books/v1/volumes?q=fruntimmer */

class MainActivity : AppCompatActivity() {
    /* Initialize ViewBinding */
    lateinit var bindingMainActivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Set ViewBinding */
        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)

        var builder = AlertDialog.Builder(this)
        builder.setTitle("WARNING!")
        builder.setMessage("This App will not receives anything from Botkyrka Library network!")
        builder.setPositiveButton("YES, continue!") { dialog, which ->
            run {
                Toast.makeText(applicationContext, "Let's begin!", Toast.LENGTH_LONG).show()
            }
        }
        builder.setNegativeButton("NO, exit!") { dialog, which ->
            run {
                exitProcess(-1)
            }
        }
        builder.setNeutralButton("I didn't try this wifi network yet, ") { dialog, which ->
            run {
                Toast.makeText(applicationContext, "Continue on your own! ", Toast.LENGTH_LONG).show()
            }
        }
        builder.show()
    }
}
