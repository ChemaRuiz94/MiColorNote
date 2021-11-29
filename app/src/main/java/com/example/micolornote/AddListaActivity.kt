package com.example.micolornote

import android.Manifest
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private var tareasAntiguas: ArrayList<Tarea> = ArrayList<Tarea>()

    private var id_not_lista: String = ""
    private var foto_tarea: String = ""
    private var modificando: Boolean = false
    private var guradado: Boolean = false
    private var notaListaAntigua: Nota? = null
    private var tarea: Tarea? = null

    lateinit var btn_add_tarea: FloatingActionButton
    lateinit var btn_save_lista: FloatingActionButton

    private lateinit var itemView: View
    private lateinit var txt_titulo_lista: EditText
    private lateinit var txt_titulo_tarea: EditText

    private val cameraRequest = 1888


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lista)

        val i = intent
        notaListaAntigua = i.getSerializableExtra("nota") as Nota?
        tarea = i.getSerializableExtra("newTarea") as Tarea?

        tareas = ArrayList<Tarea>(1)

        btn_add_tarea = findViewById(R.id.floating_btn_add_tarea)
        btn_save_lista = findViewById(R.id.floating_btn_save_lista)
        txt_titulo_lista = findViewById(R.id.ed_titulo_lista)

        //RECYCLER
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

        if (tarea != null) {
            addTareaLista(tarea!!)
        }

        //BOTONES
        if (!modificando) {
            btn_save_lista.setOnClickListener { view -> check_saveLista() }
        } else {
            btn_save_lista.setOnClickListener { view -> checkModificar() }
        }

        btn_add_tarea.setOnClickListener { view -> add_tarea_AlertDialog() }


    }


    override fun onStart() {
        super.onStart()
        Log.d("CICLO", "OnStart")

        cargarTareas()
        miAdapterTarea = AdaptadorRecyclerV_Tareas(this, tareas)
        miRecyclerView.adapter = miAdapterTarea
        miAdapterTarea.notifyDataSetChanged()
    }


    private fun completarNota() {
        txt_titulo_lista.setText(notaListaAntigua?.titulo.toString())
        cargarTareas()
        miAdapterTarea = AdaptadorRecyclerV_Tareas(this, tareas)
        miRecyclerView.adapter = miAdapterTarea
    }

    fun cargarTareas() {
        tareasAntiguas.clear()
        tareasAntiguas = Conexion.obtenerTareasIdNota(this, id_not_lista)
        tareas = tareasAntiguas
    }

    fun check_saveLista() {

        if (!modificando) {
            if (txt_titulo_lista.text.toString() == "") {
                Toast.makeText(applicationContext, R.string.titulo_lista_tareas_obligatorio, Toast.LENGTH_SHORT)
                    .show()
            } else if (tareas.size < 1) {
                Toast.makeText(applicationContext, R.string.tareas_obligatoiro, Toast.LENGTH_SHORT)
                    .show()
            } else {

                saveLista(crearNotaListaDeTareas())
                Toast.makeText(
                    applicationContext,
                    R.string.lista_guardada,
                    Toast.LENGTH_SHORT
                )
                    .show()
                finish()
            }
        }

    }

    fun checkModificar() {
        if (txt_titulo_lista.text.toString() == "") {
            Toast.makeText(applicationContext, R.string.titulo_lista_tareas_obligatorio, Toast.LENGTH_SHORT)
                .show()
        } else if (tareas.size < 1) {
            Toast.makeText(applicationContext, R.string.tareas_obligatoiro, Toast.LENGTH_SHORT)
                .show()
        } else {
            modificar()
            Toast.makeText(applicationContext, R.string.lista_modificada, Toast.LENGTH_SHORT)
                .show()
            finish()
        }
    }


    fun modificar() {

        val id_ant = notaListaAntigua?.id_nota.toString()
        val notaNueva =
            FactoriaNota.gen_Nota_with_Id(id_not_lista, txt_titulo_lista.text.toString(), 2)
        try {
            Conexion.modListaTareas(this, id_ant, notaNueva!!, tareas)
        } catch (e: Exception) {
            Log.getStackTraceString(e)
        }

    }


    fun add_tarea_newActivity(context: AppCompatActivity) {

        var intentAddTarea: Intent = Intent(context, AddTareaActivity::class.java)
        intentAddTarea.putExtra("id_nota", id_not_lista)
        intentAddTarea.putExtra("ventana", "Lista")

        //var resultado = context.startActivityForResult(intentAddTarea)
        //var t = resultLauncher.launch(intentAddTarea)

        startActivityForResult(intentAddTarea, 1)
        Log.d("CHEMA", "AQUI")
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                // Get String data from Intent
                //val returnString = data!!.getStringExtra("valorEdicionV2")
                val new_tarea: Tarea = data!!.getSerializableExtra("new_tarea") as Tarea
                addTareaLista(new_tarea)
            }
        }

    fun add_tarea_AlertDialog() {
        alertDialogTarea()
    }


    fun alertDialogTarea() {
        var itemView = layoutInflater.inflate(R.layout.add_tarea_layout, null)


        AlertDialog.Builder(this).setTitle(getString(R.string.add_tarea)).setView(itemView)
            .setPositiveButton("Añadir") { view, _ ->

                //AÑADIR TAREA
                txt_titulo_tarea = itemView.findViewById(R.id.ed_txt_texto_tarea)
                if (txt_titulo_tarea.text.trim().toString() == "") {
                    Toast.makeText(applicationContext, getString(R.string.titulo_tareas_obligatorio), Toast.LENGTH_LONG).show()

                } else {

                    val tarea: Tarea = FactoriaNota.gen_Tarea(
                        id_not_lista,
                        txt_titulo_tarea.text.toString()
                    )

                    addTareaLista(tarea)
                }
                view.dismiss()
            }
            .setNegativeButton(R.string.cancelar) { view, _ ->
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

            finish()
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

        tareas.add(tarea)
        miAdapterTarea = AdaptadorRecyclerV_Tareas(this, tareas)
        miRecyclerView.adapter = miAdapterTarea
        Conexion.addTarea(this, id_not_lista, tarea)
    }


    fun borrarTareasNoModificadas() {
        var lista_a_borrar = ArrayList<Tarea>(1)
        for (t in tareas) {
            if (!tareasAntiguas.contains(t)) {
                lista_a_borrar.add(t)
            }
        }
        for (ta in lista_a_borrar) {
            Conexion.delTarea(this, ta)
        }
    }


    /*
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.salir_sin_guardar))
            .setMessage(getString(R.string.perder_cambios))
            .setPositiveButton(getString(R.string.salir)) { view, _ ->
                if (!modificando) {
                    borrarTareasNoModificadas()
                }
                super.onBackPressed()
                view.dismiss()
            }
            .setNegativeButton(getString(R.string.cancelar)) { view, _ ->
                //super.onBackPressed()
                view.dismiss()
            }
            .setCancelable(true)
            .create()
            .show()
    }

     */

}