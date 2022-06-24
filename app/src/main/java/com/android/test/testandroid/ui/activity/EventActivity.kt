package com.android.test.testandroid.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.test.testandroid.databinding.ActivityEventBinding

class EventActivity : AppCompatActivity() {

    private val viewBinding: ActivityEventBinding by lazy {
        ActivityEventBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }
}