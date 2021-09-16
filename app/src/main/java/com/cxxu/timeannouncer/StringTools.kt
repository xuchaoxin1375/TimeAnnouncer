package com.cxxu.timeannouncer

import android.widget.Toast
import com.cxxu.timeannouncer.GetContextApplication.Companion.context


object StringTool {
    private val TAG = "StringTool"

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    @JvmStatic
    fun isEmpty(str: CharSequence?) = str == null || str.isEmpty()

    @JvmStatic
    fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
        /*try to use the global context to show the toast*/
//        Lg.d(TAG, "test toast StringTool")
        Lg.d(TAG, "try to get the context (global):${GetContextApplication.context},show toast test")
//        Toast.makeText(GetContextApplication.context, this, duration).show()
        Toast.makeText(context, this, duration).show()
    }

    @JvmStatic
    fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(GetContextApplication.context, this, duration).show()
    }
}
/*test the function*/
//fun main(){
//    println(StringTool.isEmpty(""))
//    println(StringTool.isEmpty("u"))
//}