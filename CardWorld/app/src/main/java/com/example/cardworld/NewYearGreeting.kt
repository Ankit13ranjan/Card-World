package com.example.cardworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new_year_greeting2.*

class NewYearGreeting : AppCompatActivity() {
    companion object {
        const val NAME_EXTRA = "name_extra"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_year_greeting2)

        val name =intent.getStringExtra(NAME_EXTRA)

        happyNewtext.text="Happy New Year\n$name"

    }
}