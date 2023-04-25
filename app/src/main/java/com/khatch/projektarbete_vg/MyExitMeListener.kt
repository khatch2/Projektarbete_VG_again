package com.khatch.projektarbete_vg

import android.view.View
import kotlin.system.exitProcess

class MyExitMeListener : View.OnClickListener {
    override fun onClick(v: View?) {
        println("inside MyExitMeListener")
        exitProcess(/* status = */ 3)
    }
}
