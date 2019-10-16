package com.vunhiem.drinkwater.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.screen.dailywatermain.DrinkwaterMainActivity
import com.vunhiem.drinkwater.screen.personaldata.PersonalDataActivity
import kotlinx.android.synthetic.main.activity_splash2.*

class SplashActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash2)
        Glide.with(this).load(R.drawable.splash).into(img_splash)
        onClick()
    }

    private fun onClick() {
        btn_splash2.setOnClickListener {
            val intent = Intent(this, PersonalDataActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
