package com.amg.postsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amg.postsapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
