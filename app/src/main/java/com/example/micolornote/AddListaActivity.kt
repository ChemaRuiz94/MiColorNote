package com.example.micolornote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.adapter.AdaptadorRecyclerV
import com.example.micolornote.adapter.AdaptadorRecyclerV_Tareas
import com.example.micolornote.modelo.Nota
import com.example.micolornote.modelo.Tarea
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddListaActivity : AppCompatActivity() {
    lateinit var miRecyclerView : RecyclerView
    var tareas : ArrayList<Tarea> = ArrayList<Tarea>()
    private lateinit var miAdapter: AdaptadorRecyclerV_Tareas
    lateinit var btn_add_tarea : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lista)

        btn_add_tarea = findViewById(R.id.floating_btn_add_tarea)

        //aqui deberia traer todas las tareas de una lista en caso de modificar una existente

        miRecyclerView = findViewById(R.id.rvTareas)
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        miAdapter = AdaptadorRecyclerV_Tareas(this, tareas)
        miRecyclerView.adapter = miAdapter


        btn_add_tarea.setOnClickListener { view -> add_tarea() }
    }

    fun add_tarea(){
        Toast.makeText(applicationContext,"Añadir Tarea",Toast.LENGTH_LONG).show()
    }
}