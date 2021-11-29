package com.example.micolornote

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.micolornote.bd.Conexion
import com.example.micolornote.modelo.FactoriaNota
import com.example.micolornote.modelo.Nota
import com.example.micolornote.modelo.Tarea
import java.lang.Exception

class AddTareaActivity : AppCompatActivity() {
    private val cameraRequest = 1888
    lateinit var imagenSacada: ImageView
    lateinit var txt_titulo_tarea: EditText


    private var tareaAntigua: Tarea? = null
    private var id_nota_lista: String? = null
    private var ventana: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tarea)

        val i = intent
        tareaAntigua = i.getSerializableExtra("tarea_mod") as Tarea?


        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                cameraRequest
            )

        txt_titulo_tarea = findViewById(R.id.ed_txt_titulo_tarea)
        imagenSacada = findViewById(R.id.img_add_tarea)
        val btnFoto: Button = findViewById(R.id.btn_add_foto_tarea)
        val btn_add_tarea: Button = findViewById(R.id.btn_add_tarea)

        cargar_tarea()

        btnFoto.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }

        btn_add_tarea.setOnClickListener {
            checkModTarea()
        }

    }

    fun cargar_tarea() {
        if (tareaAntigua != null) {
            txt_titulo_tarea.setText(tareaAntigua?.texto_tarea.toString())
            imagenSacada.setImageResource(tareaAntigua?.foto_tarea.toString().toInt())
        }
    }

    fun checkModTarea() {
        if (txt_titulo_tarea.text.toString() == "") {
            Toast.makeText(
                applicationContext,
                R.string.titulo_tareas_obligatorio,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            modificar()

            finish()
        }
    }


    fun modificar() {

        val id_ant = tareaAntigua?.id_Tarea.toString()
        val imagen = imagenSacada.imageTintMode
        //val imagen  = (imagenSacada.getDrawable()) as Bitmap

        val tareaNueva = FactoriaNota._gen_tarea_with_Id(
            id_ant,
            tareaAntigua?.id_Nota.toString(),
            txt_titulo_tarea.text.toString(),
            tareaAntigua!!.tarea_realizada,
            imagen.toString()
        )

        try {
            var cant =  Conexion.modTarea(this, id_ant, tareaNueva)
            if (cant == 1)
                Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No existe esa tarea", Toast.LENGTH_SHORT).show()

            //val intent_new = Intent(this, AddListaActivity::class.java)
            //intent_new.putExtra("newTarea", tareaNueva)
            //startActivity(intent_new)

            finish()
        } catch (e: Exception) {
            Log.getStackTraceString(e)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == cameraRequest) {
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            imagenSacada.setImageBitmap(photo)
        }

    }
}