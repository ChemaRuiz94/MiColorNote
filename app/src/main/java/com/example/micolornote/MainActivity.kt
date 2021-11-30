package com.example.micolornote

import android.Manifest
import com.example.micolornote.adapter.AdaptadorRecyclerV
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.bd.Conexion
import com.example.micolornote.modelo.Nota

class MainActivity : AppCompatActivity() {

    lateinit var miRecyclerView: RecyclerView
    var notas: ArrayList<Nota> = ArrayList<Nota>()
    private lateinit var miAdapter: AdaptadorRecyclerV
    private lateinit var txt_buscar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txt_buscar = findViewById(R.id.ed_txt_buscar_nota)

        Log.d("CICLO", "OnCreate")
        notas = Conexion.obtenerNotas(this)

        miRecyclerView = findViewById(R.id.rvVista)
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        miAdapter = AdaptadorRecyclerV(this, notas)
        miRecyclerView.adapter = miAdapter


    }

    override fun onResume() {
        super.onResume()

        Log.d("CICLO", "OnStart")
        refrescarRV()
    }

    fun refrescarRV() {
        notas = Conexion.obtenerNotas(this)
        miAdapter = AdaptadorRecyclerV(this, notas)
        miRecyclerView.adapter = miAdapter
        miAdapter.notifyDataSetChanged()
    }


    fun btn_add_Note(view: View) {

        AlertDialog.Builder(this).setTitle(getString(R.string.tipo))
            .setNegativeButton(getString(R.string.nota_texto)) { view, _ ->

                //NOTAS DE TEXTO
                val intent = Intent(this, AddNoteActivity::class.java)
                startActivity(intent)
                view.dismiss()
            }

            .setPositiveButton(getString(R.string.nota_tareas)) { view, _ ->
                //LISTA DE TAREAS
                val intent = Intent(this, AddListaActivity::class.java)
                startActivity(intent)
                view.dismiss()
            }.create().show()

    }

    fun btn_check_buscar(view: View) {
        if (txt_buscar.text.toString().trim() == "") {
            Toast.makeText(
                applicationContext,
                getString(R.string.titulo_buscar_obligatorio),
                Toast.LENGTH_LONG
            ).show()
            refrescarRV()
        } else {
            var listaNotas = Conexion.obtenerNotasPorNombre(this, txt_buscar.text.toString().trim())
            miAdapter = AdaptadorRecyclerV(this, listaNotas)
            miRecyclerView.adapter = miAdapter
            miAdapter.notifyDataSetChanged()
        }
    }

    fun btn_check_lenguaje(view: View) {
        AlertDialog.Builder(this).setTitle(getString(R.string.idioma))
            .setPositiveButton(getString(R.string.spanish)) { view, _ ->
                //ESPAÑOL
                view.dismiss()
            }.setNegativeButton(getString(R.string.englsih)) { view, _ ->
                //ENGLISH
                view.dismiss()
            }
            .setNeutralButton(getString(R.string.frances)) { view, _ ->
                //FRENCH
                view.dismiss()
            }.create().show()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.salir))
            .setMessage(getString(R.string.salir_app))
            .setPositiveButton(getString(R.string.salir)) { view, _ ->
                super.onBackPressed()
                view.dismiss()
            }
            .setNegativeButton(getString(R.string.cancelar)) { view, _ ->

                view.dismiss()
            }
            .setCancelable(true)
            .create()
            .show()
    }
}