package com.example.micolornote.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.AddListaActivity
import com.example.micolornote.bd.Conexion
import com.example.micolornote.AddNoteActivity
import com.example.micolornote.MainActivity
import com.example.micolornote.R
import com.example.micolornote.modelo.Nota
import com.example.micolornote.modelo.NotaDeTareas
import com.example.micolornote.modelo.NotaDeTexto
import com.example.micolornote.modelo.Tarea


class AdaptadorRecyclerV(
    private val context: AppCompatActivity,
    private val notas: ArrayList<Nota>
) : RecyclerView.Adapter<AdaptadorRecyclerV.ViewHolder>() {


    override fun getItemCount(): Int {
        return notas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder?.item.text = this.valores!![position].toString()
        val nota = notas[position]
        holder.titulo.text = nota.titulo
        holder.fecha.text = nota.fecha
        holder.hora.text = nota.hora_nota

        holder.itemView.setOnLongClickListener(OnLongClickListener {
            AlertDialog.Builder(context).setTitle("¿Desea eliminar esta nota?")
                .setPositiveButton("Eliminar") { view, _ ->

                    //elimina nota
                    //Conexion.delNotaText(context as AppCompatActivity, nota)
                    checkEliminar(nota,context)
                    //refresca la MainActivity
                    var myIntent = Intent(context, MainActivity::class.java)
                    context.startActivity(myIntent)

                    view.dismiss()
                }.setNegativeButton("Cancelar") { view, _ ->//cancela
                    view.dismiss()
                }.create().show()
            false
        })

        holder.itemView.setOnClickListener(View.OnClickListener {
            AlertDialog.Builder(context).setTitle("¿Desea Modificar esta nota?")
                .setPositiveButton("Modificar") { view, _ ->

                    // Modificar nota
                    checkModificar(nota, context)

                    view.dismiss()
                }.setNegativeButton("Cancelar") { view, _ ->
                    //cancela
                    view.dismiss()
                }.create().show()
        })
    }

    fun checkEliminar(nota: Nota, context: AppCompatActivity) {
        if (nota.tipo == 1) {
            //si el tipo es 1 es nota de Texto
            Conexion.delNotaText(context as AppCompatActivity, nota)
            Toast.makeText(context, "Nota de texto Eliminada", Toast.LENGTH_SHORT)
                .show()
        } else {
            //si es del tipo 2 es lista
            Conexion.delTareasIdNota(context as AppCompatActivity, nota)
            Conexion.delNota(context as AppCompatActivity, nota)
            Toast.makeText(context, "Lista de Tareas Eliminada", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun checkModificar(nota: Nota, context: AppCompatActivity) {
        if (nota.tipo == 1) {

            //si el tipo es 1 es nota de Texto

            var notaText: NotaDeTexto? = getNotaTexto(nota, context)
            var intentTextNote: Intent = Intent(context, AddNoteActivity::class.java)
            intentTextNote.putExtra("notaText", notaText)

            context.startActivity(intentTextNote)

        } else {
            //si el tipo es 2 es nota de Tareas

            var nota: Nota? = Conexion.obtenerNota(context, nota.id_nota.toString())
            var intentListNote: Intent = Intent(context, AddListaActivity::class.java)
            intentListNote.putExtra("nota", nota)

            context.startActivity(intentListNote)
        }
    }

    fun getNotaTexto(nota: Nota, context: AppCompatActivity): NotaDeTexto? {
        var nota: Nota? = Conexion.obtenerNota(context, nota.id_nota.toString())
        var notaDeTexto: NotaDeTexto? = Conexion.obtenerNotaTexto(context, nota?.id_nota.toString())

        return notaDeTexto
    }

    fun getNotaLista(nota: Nota, context: AppCompatActivity): NotaDeTareas? {
        var nota: Nota? = Conexion.obtenerNota(context, nota.id_nota.toString())
        var notaDeTareas: NotaDeTareas? =
            Conexion.obtenerNotaListaTareas(context, nota?.id_nota.toString())
        return notaDeTareas
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val titulo = view.findViewById<TextView>(R.id.txtTituloNota)
        val fecha = view.findViewById<TextView>(R.id.txtFechaNota)
        val hora = view.findViewById<TextView>(R.id.txt_hora_nota)

    }
}