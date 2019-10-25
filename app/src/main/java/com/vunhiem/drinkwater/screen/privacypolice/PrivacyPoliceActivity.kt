package com.vunhiem.drinkwater.screen.privacypolice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vunhiem.drinkwater.R
import kotlinx.android.synthetic.main.activity_privacy_police.*

class PrivacyPoliceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_police)
        webView.loadUrl("https://navatech.vn/")
        img_back.setOnClickListener {
            finish()
        }
    }
}
