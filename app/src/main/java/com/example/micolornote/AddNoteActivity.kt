package com.example.micolornote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import bd.Conexion
import com.google.android.material.floatingactionbutton.FloatingActionButton
import modelo.NotaDeTexto

class AddNoteActivity : AppCompatActivity() {
    lateinit var texto_nota: EditText
    lateinit var texto_titulo: EditText
    lateinit var btn_titulo: FloatingActionButton
    lateinit var btn_save: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        texto_nota = findViewById(R.id.ed_txt_multiline_notaText)
        texto_titulo = findViewById(R.id.ed_txt_titulo_notaText)
        btn_save = findViewById(R.id.floating_btn_save_notaText)
    }

    fun btn_saveNote(view: View) {

        if (!texto_nota.text.equals("")) {

        } else {
            Toast.makeText(applicationContext, "La nota esta en blanco", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveNota() {
        val titulo: String = texto_titulo.text.toString()
        var nota: NotaDeTexto
    }
}