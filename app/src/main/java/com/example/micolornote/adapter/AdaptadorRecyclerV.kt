package com.example.micolornote.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
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


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder?.item.text = this.valores!![position].toString()
        val nota = notas[position]
        holder.titulo.text = nota.titulo
        holder.fecha.text = nota.fecha
        holder.hora.text = nota.hora_nota
        //val color: Int = (R.color.my_yellow_background)


        if (nota.tipo != 1){
            holder.img.setImageResource(R.drawable.ic_baseline_format_list_numbered_rtl_24)
        }else{
            holder.img.setImageResource(R.drawable.ic_baseline_note_24)
        }


        holder.itemView.setOnLongClickListener(OnLongClickListener {
            AlertDialog.Builder(context).setTitle(R.string.eliminar_nota)
                .setPositiveButton(R.string.eliminar) { view, _ ->

                    //elimina nota
                    //Conexion.delNotaText(context as AppCompatActivity, nota)
                    checkEliminar(nota,context)

                    //refresca la MainActivity
                    var myIntent = Intent(context, MainActivity::class.java)
                    context.startActivity(myIntent)
                    context.finish()

                    view.dismiss()
                }.setNegativeButton(R.string.cancelar) { view, _ ->//cancela
                    view.dismiss()
                }.create().show()
            false
        })

        holder.itemView.setOnClickListener(View.OnClickListener {
            AlertDialog.Builder(context).setTitle(R.string.modificar_nota)
                .setPositiveButton(R.string.modificar) { view, _ ->

                    // Modificar nota
                    checkModificar(nota, context)

                    view.dismiss()
                }.setNegativeButton("COMPARTIR POR SMS") { view, _ ->

                    // Modificar nota
                    checkComparitr(nota, context)

                    view.dismiss()
                }.setNeutralButton(R.string.cancelar) { view, _ ->
                    //cancela
                    view.dismiss()
                }.create().show()
        })
    }

    fun checkEliminar(nota: Nota, context: AppCompatActivity) {
        if (nota.tipo == 1) {
            //si el tipo es 1 es nota de Texto
            Conexion.delNotaText(context as AppCompatActivity, nota)
            Toast.makeText(context, R.string.nota_eliminada, Toast.LENGTH_SHORT)
                .show()
        } else {
            //si es del tipo 2 es lista
            Conexion.delTareasIdNota(context as AppCompatActivity, nota)
            Conexion.delNota(context as AppCompatActivity, nota)
            Toast.makeText(context, R.string.lista_eliminada, Toast.LENGTH_SHORT)
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

    fun checkComparitr(nota: Nota, context: AppCompatActivity) {
        if (nota.tipo == 1) {
            Toast.makeText(context," compartir pos SMS", Toast.LENGTH_SHORT)
                .show()

        } else {
            Toast.makeText(context,"La lista de tareas no se puede compartir pos SMS", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun getNotaTexto(nota: Nota, context: AppCompatActivity): NotaDeTexto? {
        var nota: Nota? = Conexion.obtenerNota(context, nota.id_nota.toString())
        var notaDeTexto: NotaDeTexto? = Conexion.obtenerNotaTexto(context, nota?.id_nota.toString())

        return notaDeTexto
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val titulo = view.findViewById<TextView>(R.id.txtTituloNota)
        val fecha = view.findViewById<TextView>(R.id.txtFechaNota)
        val hora = view.findViewById<TextView>(R.id.txt_hora_nota)
        val img = view.findViewById<ImageView>(R.id.imagenNota)

    }
}