package com.example.micolornote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.adapter.AdaptadorRecyclerV
import com.example.micolornote.adapter.AdaptadorRecyclerV_Tareas
import com.example.micolornote.auxiliar.MyDialogTarea
import com.example.micolornote.bd.Conexion
import com.example.micolornote.modelo.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception

class AddListaActivity : AppCompatActivity() {
    lateinit var miRecyclerView: RecyclerView
    private lateinit var miAdapterTarea: AdaptadorRecyclerV_Tareas
    private var tareas: ArrayList<Tarea> = ArrayList<Tarea>()
    private var tareasModif: ArrayList<Tarea> = ArrayList<Tarea>()
    private var id_not_lista: String = ""
    private var foto_tarea: String = ""
    private var modificando: Boolean = false
    private var notaListaAntigua: Nota? = null

    lateinit var btn_add_tarea: FloatingActionButton
    lateinit var btn_save_lista: FloatingActionButton

    private lateinit var txt_titulo_lista: EditText
    private lateinit var txt_titulo_tarea: EditText
    private lateinit var btn_foto: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lista)

        val i = intent
        notaListaAntigua = i.getSerializableExtra("nota") as Nota?


        btn_add_tarea = findViewById(R.id.floating_btn_add_tarea)
        btn_save_lista = findViewById(R.id.floating_btn_save_lista)
        txt_titulo_lista = findViewById(R.id.ed_titulo_lista)



        miRecyclerView = findViewById(R.id.rvTareas)
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        miAdapterTarea = AdaptadorRecyclerV_Tareas(this, tareas)
        miRecyclerView.adapter = miAdapterTarea


        if (notaListaAntigua != null) {
            completarNota()
            modificando = true
            id_not_lista = notaListaAntigua!!.id_nota
        } else {
            modificando = false
            id_not_lista = FactoriaNota.gen_Unique_ID()
        }


        if (!modificando) {
            btn_save_lista.setOnClickListener { view -> check_saveLista() }
        } else {
            btn_save_lista.setOnClickListener { view -> checkModificar() }
        }


        btn_add_tarea.setOnClickListener { view -> add_tarea() }
        //btn_save_lista.setOnClickListener { view -> check_saveLista() }

    }

    private fun completarNota() {
        txt_titulo_lista.setText(notaListaAntigua?.titulo.toString())
        tareas = Conexion.obtenerTareasIdNota(this,notaListaAntigua!!.id_nota)

        miAdapterTarea = AdaptadorRecyclerV_Tareas(this, tareas)
        miRecyclerView.adapter = miAdapterTarea
    }

    fun check_saveLista() {

        if(!modificando){
            if (txt_titulo_lista.text.toString() == "") {
                Toast.makeText(applicationContext, "Ponle un titulo a la lista", Toast.LENGTH_SHORT)
                    .show()
            } else if (tareas.size <1) {
                Toast.makeText(applicationContext, "Añade al menos una tarea", Toast.LENGTH_SHORT)
                    .show()
            } else {

                saveLista(crearNotaListaDeTareas())
                Toast.makeText(applicationContext, "Lista GUARDADA correctamente", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }

    }

    fun checkModificar(){
        if (txt_titulo_lista.text.toString() == "") {
            Toast.makeText(applicationContext, "Ponle un titulo a la lista", Toast.LENGTH_SHORT)
                .show()
        }  else if (tareas.size <1) {
            Toast.makeText(applicationContext, "Añade al menos una tarea", Toast.LENGTH_SHORT)
                .show()
        } else {
            modificar()
            Toast.makeText(applicationContext, "Lista MODIFICADA correctamente", Toast.LENGTH_SHORT)
                .show()
            finish()
        }
    }


    fun modificar() {

        val id_ant = notaListaAntigua?.id_nota.toString()
        val notaNueva = FactoriaNota.gen_Nota_with_Id(id_not_lista, txt_titulo_lista.text.toString(), 2)
        try {
            Conexion.modListaTareas(this,id_ant,notaNueva!!,tareas)
        } catch (e: Exception) {
            Log.getStackTraceString(e)
        }

    }
    fun btn_foto(view: View) {
        Toast.makeText(applicationContext, "FOTO", Toast.LENGTH_LONG).show()

    }

    fun add_tarea() {
        alertDialogTarea()
    }


    fun alertDialogTarea() {
        var itemView = layoutInflater.inflate(R.layout.add_tarea_layout, null)

        AlertDialog.Builder(this).setTitle(getString(R.string.add_tarea)).setView(itemView)
            .setPositiveButton("Añadir") { view, _ ->

                //AÑADIR TAREA
                txt_titulo_tarea = itemView.findViewById(R.id.ed_txt_texto_tarea)
                btn_foto = itemView.findViewById(R.id.btn_add_imagen_tarea)

                if (txt_titulo_tarea.text.trim().toString() == "") {
                    Toast.makeText(applicationContext, "Ponle un titulo", Toast.LENGTH_LONG).show()

                } else {
                    val tarea: Tarea =
                        FactoriaNota.gen_Tarea(id_not_lista, txt_titulo_tarea.text.toString())
                    addTareaLista(tarea)
                    Toast.makeText(applicationContext, "Tarea añadida", Toast.LENGTH_LONG).show()
                }
                view.dismiss()
            }
            .setNegativeButton("Cancelar") { view, _ ->
                //Toast.makeText(applicationContext,"Mal",Toast.LENGTH_LONG).show()
                view.dismiss()
            }.create().show()
    }

    fun saveLista(nota: Nota) {
        try {
            //insertamos una nota en la tabla tareas
            Conexion.addNota(this, nota)
            //insertamos tareas a la tabla tareas, asociadas al id de la nota
            for (tarea in tareas) {
                Conexion.addTarea(this, id_not_lista, tarea)
            }

        } catch (e: Exception) {
            Log.getStackTraceString(e)
        }
    }

    fun crearNotaListaDeTareas(): Nota {
        val nota: Nota =
            FactoriaNota.gen_Nota_with_Id(id_not_lista, txt_titulo_lista.text.toString(), 2)
        return nota
    }


    fun addTareaLista(tarea: Tarea) {

        this.tareas.add(tarea)
        miAdapterTarea = AdaptadorRecyclerV_Tareas(this, tareas)
        miRecyclerView.adapter = miAdapterTarea
    }
}