package com.example.cardworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_birthdaygreeting.*

class Birthdaygreeting : AppCompatActivity() {

    companion object {
        const val NAME_EXTRA = "name_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birthdaygreeting)

        val name = intent.getStringExtra(NAME_EXTRA)
        birthdayGreeting.text="Happy Birthday\n$name!"
    }

    fun showBirthdayCard(view: View)
    {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey,Checkout this Wonderfull Gift")

    }


}