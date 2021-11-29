package com.example.micolornote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.micolornote.bd.Conexion
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.micolornote.modelo.FactoriaNota
import com.example.micolornote.modelo.Nota
import com.example.micolornote.modelo.NotaDeTexto
import java.lang.Exception


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
                getString(R.string.texto_obligatoiro),
                Toast.LENGTH_SHORT
            ).show()

        } else if (texto_titulo.text.toString() == "") {
            Toast.makeText(applicationContext, getString(R.string.titulo_nota_texto_obligatorio), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(applicationContext, getString(R.string.nota_guardada), Toast.LENGTH_SHORT)
                .show()
            saveNota(crearNotaTexto())
            finish()
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
                getString(R.string.texto_obligatoiro),
                Toast.LENGTH_SHORT
            ).show()

        } else if (texto_titulo.text.toString() == "") {
            Toast.makeText(applicationContext, getString(R.string.titulo_nota_texto_obligatorio), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(applicationContext, getString(R.string.nota_modificada), Toast.LENGTH_SHORT)
                .show()
            modificar(crearNotaTexto())
            finish()
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

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.salir_sin_guardar))
            .setMessage(getString(R.string.perder_cambios))
            .setPositiveButton(getString(R.string.salir)) { view, _ ->
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

}