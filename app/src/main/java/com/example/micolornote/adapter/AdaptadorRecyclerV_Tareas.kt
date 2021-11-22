package com.example.micolornote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.R
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
        holder.texto_tarea.text = tarea.texto_tarea
        holder.foto.setBackgroundResource(tarea.foto_tarea)
    }

    override fun getItemCount(): Int {
        return tareas.size
    }

    class ViewHolderTareas(view: View) : RecyclerView.ViewHolder(view) {

        val texto_tarea = view.findViewById<TextView>(R.id.txt_tarea)
        val foto = view.findViewById<TextView>(R.id.img_tarea)

    }
}

