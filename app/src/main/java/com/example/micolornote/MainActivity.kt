package com.example.micolornote

import adapter.AdaptadorRecyclerV
import adapter.MyAdapterRV
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bd.Conexion
import modelo.Nota

class MainActivity : AppCompatActivity() {

    lateinit var miRecyclerView : RecyclerView
    var notas : ArrayList<Nota> = ArrayList<Nota>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notas = Conexion.obtenerNotas(this)

        miRecyclerView = findViewById(R.id.rvVista) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        var miAdapter = AdaptadorRecyclerV(this, notas)
        miRecyclerView.adapter = miAdapter
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