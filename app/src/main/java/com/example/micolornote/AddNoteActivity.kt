package com.example.micolornote

import Auxiliar.Constantes
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import bd.Conexion
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import modelo.FactoriaNota
import modelo.Nota
import modelo.NotaDeTexto
import java.lang.Exception
import java.sql.Timestamp
import java.util.*
import android.content.Intent


class AddNoteActivity : AppCompatActivity() {
    lateinit var texto_nota: EditText
    lateinit var texto_titulo: EditText
    lateinit var btn_save: FloatingActionButton
    private var modificando: Boolean = false
    private var nota: Nota? = null
    private var notaTextoAntigua: NotaDeTexto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val i = intent
        notaTextoAntigua = i.getSerializableExtra("notaText") as NotaDeTexto?

        texto_nota = findViewById(R.id.ed_txt_multiline_notaText)
        texto_titulo = findViewById(R.id.ed_txt_titulo_notaText)
        btn_save = findViewById(R.id.floating_btn_save_notaText)

        if (notaTextoAntigua != null) {
            completarNota()
            modificando = true
        }else{
            modificando = false
        }


        if (!modificando) {
            btn_save.setOnClickListener { view -> check_saveNote() }
        } else {
            btn_save.setOnClickListener { view -> checkModificar() }
        }




    }

    fun check_saveNote() {

        if (texto_nota.text.toString() == "") {

            Toast.makeText(
                applicationContext,
                "La nota no puede estar en blanco",
                Toast.LENGTH_SHORT
            ).show()

        } else if (texto_titulo.text.toString() == "") {
            Toast.makeText(applicationContext, "Ponle un titulo a la nota", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(applicationContext, "Nota guardada correctamente", Toast.LENGTH_SHORT)
                .show()
            saveNota(crearNotaTexto())
        }
    }

    fun crearNotaTexto(): NotaDeTexto {
        val titulo: String = texto_titulo.text.toString()
        val texto: String = texto_nota.text.toString()

        //tipo 1 es para las notas de Texto
        val nota: Nota = FactoriaNota.gen_Nota(titulo, 1)

        return FactoriaNota.gen_NotaText(nota, texto)
    }

    fun saveNota(notaDeTexto: NotaDeTexto) {
        try {
            Conexion.addNotaText(this, notaDeTexto)
        } catch (e: Exception) {
            Log.getStackTraceString(e)
        }
    }

    fun completarNota() {

        texto_titulo.setText(notaTextoAntigua?.titulo.toString())
        texto_nota.setText(notaTextoAntigua?.contenido.toString())

    }

    fun checkModificar(){
        if (texto_nota.text.toString() == "") {

            Toast.makeText(
                applicationContext,
                "La nota no puede estar en blanco",
                Toast.LENGTH_SHORT
            ).show()

        } else if (texto_titulo.text.toString() == "") {
            Toast.makeText(applicationContext, "Ponle un titulo a la nota", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(applicationContext, "Nota de texto modificada", Toast.LENGTH_SHORT)
                .show()
            modificar(crearNotaTexto())
        }
    }

    fun modificar(notaDeTexto: NotaDeTexto) {

        val id_ant = notaTextoAntigua?.id_nota.toString()
        try {
            Conexion.modNotaTexto(this,id_ant,notaDeTexto)
        } catch (e: Exception) {
            Log.getStackTraceString(e)
        }

    }


}