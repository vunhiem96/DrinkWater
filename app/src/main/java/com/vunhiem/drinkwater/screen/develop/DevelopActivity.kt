package com.vunhiem.drinkwater.screen.develop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vunhiem.drinkwater.R
import kotlinx.android.synthetic.main.activity_privacy_police.*

class DevelopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_police)
        tv_title.text= "NavaTech"
        webView.loadUrl("https://navatech.vn/")
        img_back.setOnClickListener {
            finish()
        }
    }
}
