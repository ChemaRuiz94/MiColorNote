package com.example.micolornote

import com.example.micolornote.adapter.AdaptadorRecyclerV
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.bd.Conexion
import com.example.micolornote.modelo.Nota

class MainActivity : AppCompatActivity() {

    lateinit var miRecyclerView : RecyclerView
    var notas : ArrayList<Nota> = ArrayList<Nota>()
    private lateinit var miAdapter: AdaptadorRecyclerV

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("CICLO", "OnCreate")
        notas = Conexion.obtenerNotas(this)

        miRecyclerView = findViewById(R.id.rvVista)
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        miAdapter = AdaptadorRecyclerV(this, notas)
        miRecyclerView.adapter = miAdapter
    }

    override fun onStart() {
        super.onStart()
        Log.d("CICLO", "OnStart")
        notas = Conexion.obtenerNotas(this)
        miAdapter = AdaptadorRecyclerV(this, notas)
        miRecyclerView.adapter = miAdapter
        miAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        Log.d("CICLO", "OnResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("CICLO", "OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("CICLO", "OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CICLO", "OnDestroy")
    }

    fun btn_add_Note(view: View){

        AlertDialog.Builder(this).setTitle("¿Que tipo de nota quieres?").setNegativeButton("Nota de texto"){ view,_ ->
            val intent = Intent(this, AddNoteActivity::class.java)

            startActivity(intent)
            view.dismiss()}.setPositiveButton("Lista de tareas"){ view,_ ->
            //LISTA DE TAREAS
            view.dismiss()}.create().show()

    }


}