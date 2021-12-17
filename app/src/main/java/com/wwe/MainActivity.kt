package com.wwe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import com.hk.annotation.DebugTrace

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    @DebugTrace
//    private fun testAnnotatedMethod() {
//        try {
//            Thread.sleep(100)
//        } catch (ex: InterruptedException) {
//            ex.printStackTrace()
//        }
//    }
}