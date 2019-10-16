package com.vunhiem.drinkwater.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.screen.dailywatermain.DrinkwaterMainActivity
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.activity_splash2.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Glide.with(this).load(R.drawable.text_kolor_fox).into(tv)
        splashScreen()
    }

    private fun splashScreen(){
        val handler = Handler()
        handler.postDelayed({ screenRouter() }, 2000)

    }

    private fun screenRouter() {
       var check = AppConfig.getHistoryLogin(this)
        if(check==false){
            val intent = Intent(this, SplashActivity2::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, DrinkwaterMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
