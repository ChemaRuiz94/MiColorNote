package com.example.micolornote.adapter

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.ModTareaActivity
import com.example.micolornote.R
import com.example.micolornote.auxiliar.Utiles
import com.example.micolornote.modelo.Tarea

class AdaptadorRecyclerV_Tareas(
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

        //
        if(tarea.foto_tarea != null){
            val u = Utiles()
            val foto: Bitmap? = tarea.foto_tarea?.let { u.base64ToBitmap(it) }
            holder.foto.setImageBitmap(foto)
        }else{
            holder.foto.setImageResource(R.drawable.ejemplo)
        }


        if (tarea.tarea_realizada == 1) {
            holder.img_realizado.setImageResource(R.drawable.ic_baseline_done_24)
        } else {
            holder.img_realizado.setImageResource(R.drawable.ic_baseline_undone_24)
        }

        holder.foto.setOnClickListener {
            Toast.makeText(context, R.string.foto, Toast.LENGTH_SHORT)
                .show()
        }

        //MODIFICAR TAREA
        holder.itemView.setOnClickListener {

            var intent_tarea_mod = Intent (context, ModTareaActivity::class.java)
            intent_tarea_mod.putExtra("tarea_mod",tarea)
            ContextCompat.startActivity(context,intent_tarea_mod, Bundle())

        }
        //ELIMINAR TAREA
        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            AlertDialog.Builder(context).setTitle(R.string.eliminar_tarea)
                .setPositiveButton(R.string.eliminar) { view, _ ->
                    //elimina tarea
                    tareas.remove(tarea)
                    notifyDataSetChanged()
                    view.dismiss()
                }.setNegativeButton(R.string.cancelar) { view, _ ->//cancela
                    view.dismiss()
                }.create().show()
            false
        })

        //CAMBIAR TAREA A REALIZADO
        holder.img_realizado.setOnClickListener(View.OnClickListener {
            if (tarea.tarea_realizada == 0) {
                //tarea no realizada
                holder.img_realizado.setImageResource(R.drawable.ic_baseline_done_24)
                //la convierte a realizada
                tarea.tarea_realizada = 1
                //notifyDataSetChanged()
            } else {
                //tarea realizada realizada
                holder.img_realizado.setImageResource(R.drawable.ic_baseline_undone_24)
                //la convierte a no realizada
                tarea.tarea_realizada = 0
                //notifyDataSetChanged()
            }
        })

    }

    override fun getItemCount(): Int {
        return tareas.size
    }

    class ViewHolderTareas(view: View) : RecyclerView.ViewHolder(view) {

        val texto_tarea = view.findViewById<TextView>(R.id.txt_tarea)
        val foto = view.findViewById<ImageView>(R.id.img_tarea)
        val img_realizado = view.findViewById<ImageView>(R.id.img_tarea_realizada)

    }
}

