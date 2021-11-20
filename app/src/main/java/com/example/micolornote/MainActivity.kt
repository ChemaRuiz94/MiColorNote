package com.example.micolornote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun btn_add_Note(view: View){

        AlertDialog.Builder(this).setTitle("¿Que tipo de nota quieres?").setNegativeButton("Nota de texto"){ view,_ ->
            val intent = Intent(this, AddNoteActivity::class.java)

            startActivity(intent)
            view.dismiss()}.setPositiveButton("Lista de tareas"){ view,_ ->
            val intent = Intent(this, AddNoteActivity::class.java)

            startActivity(intent)
            view.dismiss()}.create().show()

        //val myIntent: Intent =  Intent(this, AddNoteActivity::class.java)
        //startActivity(myIntent)
    }


}