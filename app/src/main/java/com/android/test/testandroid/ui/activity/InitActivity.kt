package com.android.test.testandroid.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.test.testandroid.databinding.ActivityInitBinding

class InitActivity : AppCompatActivity() {

    private val viewBinding: ActivityInitBinding by lazy {
        ActivityInitBinding.inflate(layoutInflater)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        animateLogo()
    }

    private fun animateLogo(){
        viewBinding.logo.animate().apply {
            duration = ANIM_DURATION
            scaleXBy(ANIM_POSITIVE_SCALE)
            scaleYBy(ANIM_POSITIVE_SCALE)
        }.withEndAction {
            viewBinding.logo.animate().apply {
                duration = ANIM_DURATION
                scaleXBy(ANIM_NEGATIVE_SCALE)
                scaleYBy(ANIM_NEGATIVE_SCALE)
            }.withEndAction {
                startActivity(Intent(this@InitActivity, EventActivity::class.java))
            }
        }.start()
    }


    companion object{
        const val ANIM_DURATION = 1000.toLong()
        const val ANIM_POSITIVE_SCALE = .200f
        const val ANIM_NEGATIVE_SCALE = -.200f
    }
}