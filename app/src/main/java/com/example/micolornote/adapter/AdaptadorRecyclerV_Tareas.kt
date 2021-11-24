package com.example.micolornote.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.MainActivity
import com.example.micolornote.R
import com.example.micolornote.bd.Conexion
import com.example.micolornote.modelo.Nota
import com.example.micolornote.modelo.Tarea

class AdaptadorRecyclerV_Tareas (
    private val context: AppCompatActivity,
    private val tareas: ArrayList<Tarea>
) : RecyclerView.Adapter<AdaptadorRecyclerV_Tareas.ViewHolderTareas>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTareas {
        return AdaptadorRecyclerV_Tareas.ViewHolderTareas(
            LayoutInflater.from(context).inflate(R.layout.tarea_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderTareas, position: Int) {
        val tarea = tareas[position]
        val foto = tarea.foto_tarea.toString().toInt()
        holder.texto_tarea.text = tarea.texto_tarea
        holder.foto.setBackgroundResource(foto)

        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            AlertDialog.Builder(context).setTitle("¿Desea eliminar tarea?")
                .setPositiveButton("Eliminar") { view, _ ->

                    //elimina tarea
                    tareas.remove(tarea)
                    notifyDataSetChanged()
                    Toast.makeText(context, "Tarea eliminada", Toast.LENGTH_SHORT)
                        .show()
                    view.dismiss()
                }.setNegativeButton("Cancelar") { view, _ ->//cancela
                    view.dismiss()
                }.create().show()
            false
        })

        holder.itemView.setOnClickListener(View.OnClickListener {
            AlertDialog.Builder(context).setTitle("¿Tarea realizada?")
                .setPositiveButton("Si") { view, _ ->

                    Toast.makeText(context, "CAMBIAR REALIZADO", Toast.LENGTH_SHORT)
                        .show()

                    view.dismiss()
                }.setNegativeButton("No") { view, _ ->
                    //cancela
                    view.dismiss()
                }.create().show()
        })

    }

    override fun getItemCount(): Int {
        return tareas.size
    }

    class ViewHolderTareas(view: View) : RecyclerView.ViewHolder(view) {

        val texto_tarea = view.findViewById<TextView>(R.id.txt_tarea)
        val foto = view.findViewById<ImageView>(R.id.img_tarea)

    }
}

