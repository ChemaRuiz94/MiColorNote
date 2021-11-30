package com.example.micolornote

import com.example.micolornote.adapter.AdaptadorRecyclerV
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.bd.Conexion
import com.example.micolornote.modelo.Nota

class MainActivity : AppCompatActivity() {

    lateinit var miRecyclerView : RecyclerView
    var notas : ArrayList<Nota> = ArrayList<Nota>()
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

    fun refrescarRV(){
        notas = Conexion.obtenerNotas(this)
        miAdapter = AdaptadorRecyclerV(this, notas)
        miRecyclerView.adapter = miAdapter
        miAdapter.notifyDataSetChanged()
    }


    fun btn_add_Note(view: View){

        AlertDialog.Builder(this).setTitle(getString(R.string.tipo))
            .setNegativeButton(getString(R.string.nota_texto)){ view,_ ->

            //NOTAS DE TEXTO
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
            view.dismiss()}

            .setPositiveButton(getString(R.string.nota_tareas)){ view,_ ->
            //LISTA DE TAREAS
                val intent = Intent(this, AddListaActivity::class.java)
                startActivity(intent)
            view.dismiss()}.create().show()

    }

    fun btn_check_buscar(view: View){
        if(txt_buscar.text.toString().trim() == ""){
            Toast.makeText(applicationContext, "Introduce un titulo para buscar", Toast.LENGTH_LONG).show()
            refrescarRV()
        }else{
            var listaNotas = Conexion.obtenerNotasPorNombre(this,txt_buscar.text.toString().trim())
            miAdapter = AdaptadorRecyclerV(this, listaNotas)
            miRecyclerView.adapter = miAdapter
            miAdapter.notifyDataSetChanged()
        }
    }


}