package adapter

import android.app.Activity
import android.content.Context
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
import bd.Conexion
import com.example.micolornote.AddNoteActivity
import com.example.micolornote.R
import modelo.Nota





class AdaptadorRecyclerV : RecyclerView.Adapter<AdaptadorRecyclerV.ViewHolder> {
    private var context: Activity
    private var notas: ArrayList<Nota>? = null

    constructor(context: Activity, notes: ArrayList<Nota>) {
        this.context = context
        this.notas = notes
    }

    override fun getItemCount(): Int {
        return this.notas?.size!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder?.item.text = this.valores!![position].toString()
        val nota = this.notas!![position]
        holder.bind(nota, context)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val titulo = view.findViewById<TextView>(R.id.txtTituloNota)
        val fecha = view.findViewById<TextView>(R.id.txtFechaNota)
        val hora = view.findViewById<TextView>(R.id.txt_hora_nota)


        fun bind(valorSeleccionado: Nota, context: Context) {
            titulo.text = valorSeleccionado.titulo
            fecha.text = valorSeleccionado.fecha
            hora.text = valorSeleccionado.hora_nota

            itemView.setOnLongClickListener(OnLongClickListener {
                val al = AlertDialog.Builder(context).setTitle("¿Desea eliminar esta nota?").setPositiveButton("Eliminar"){ view, _ ->
                //elimina nota
                Conexion.delNotaText(context as AppCompatActivity,valorSeleccionado)
                view.dismiss()}.setNegativeButton("Cancelar"){ view,_ ->//cancela
                view.dismiss()}.create().show()
                false
            })

            itemView.setOnClickListener(View.OnClickListener {
                AlertDialog.Builder(context).setTitle("¿Desea Modificar esta nota?").setPositiveButton("Modificar"){ view, _ ->
                    // nota
                    //Intent
                    view.dismiss()}.setNegativeButton("Cancelar"){ view,_ ->//cancela
                    view.dismiss()}.create().show()
            })

        }
    }
}