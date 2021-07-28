package com.example.cardworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun createBirthdayCard(view: View)
    {
        val name = nameInput.editableText.toString()

        val intent = Intent(this, Birthdaygreeting::class.java)

        intent.putExtra(Birthdaygreeting.NAME_EXTRA ,name)
        startActivity(intent)

    }

    fun createNewYearCard(view: View)
    {
        val name = nameInput.editableText.toString()
        val intent = Intent(this,NewYearGreeting::class.java)

        intent.putExtra(NewYearGreeting.NAME_EXTRA,name)
        startActivity(intent)
    }

    fun createAnniversaryCard(view: View)
    {
        val name= nameInput.editableText.toString()
        val intent=Intent(this,MarriageGreeting::class.java)

        intent.putExtra(MarriageGreeting.NAME_EXTRA,name)
        startActivity(intent)
    }


}