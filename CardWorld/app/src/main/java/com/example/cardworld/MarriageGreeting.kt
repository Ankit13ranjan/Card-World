package com.example.cardworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_marriage_greeting.*

class MarriageGreeting : AppCompatActivity() {
    companion object {
        const val NAME_EXTRA = "name_extra"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marriage_greeting)
        val name = intent.getStringExtra(NAME_EXTRA)

        marriageText.text="Happy Marriage Aniversary\n$name"




    }
}