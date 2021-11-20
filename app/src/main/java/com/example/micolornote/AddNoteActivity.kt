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

class AddNoteActivity : AppCompatActivity() {
    lateinit var texto_nota: EditText
    lateinit var texto_titulo: EditText
    lateinit var btn_save: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        texto_nota = findViewById(R.id.ed_txt_multiline_notaText)
        texto_titulo = findViewById(R.id.ed_txt_titulo_notaText)
        btn_save = findViewById(R.id.floating_btn_save_notaText)

        btn_save.setOnClickListener { view -> check_saveNote() }


    }

    fun check_saveNote() {

        if (texto_nota.text.toString().equals("")) {

            Toast.makeText(
                applicationContext,
                "La nota no puede estar en blanco",
                Toast.LENGTH_SHORT
            ).show()

        } else if (texto_titulo.text.toString().equals("")) {
            Toast.makeText(applicationContext, "Ponle un titulo a la nota", Toast.LENGTH_SHORT)
                .show()
        } else {

            Toast.makeText(applicationContext, "Nota guardada correctamente", Toast.LENGTH_SHORT)
                .show()
            var nota = crearNotaTexto()
            saveNota(nota)
        }


    }

    fun crearNotaTexto(): NotaDeTexto {
        val titulo: String = texto_titulo.text.toString()
        val texto: String = texto_nota.text.toString()

        val nota:Nota = FactoriaNota.gen_Nota(titulo)
        val notaDeTexto:NotaDeTexto = FactoriaNota.gen_NotaText(nota,texto)

        return notaDeTexto
    }

    fun saveNota(notaDeTexto: NotaDeTexto) {
        try {

            Conexion.addNotaText(this, notaDeTexto)

        } catch (e: Exception) {
            Log.getStackTraceString(e)
        }
    }
}