package adapter

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
import com.example.micolornote.MainActivity
import com.example.micolornote.R
import modelo.Nota
import modelo.NotaDeTexto


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
                    Conexion.delNotaText(context as AppCompatActivity, nota)
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
                    checkModificar(nota,context)

                    view.dismiss()
                }.setNegativeButton("Cancelar") { view, _ ->
                    //cancela
                    view.dismiss()
                }.create().show()
        })
    }

    fun checkModificar(nota: Nota, context: AppCompatActivity){
        if (nota.tipo == 1) {

            //si el tipo es 1 es nota de Texto

            var notaText: NotaDeTexto? = getNotaTexto(nota,context)
            var intentTextNote: Intent = Intent(context, AddNoteActivity::class.java)
            intentTextNote.putExtra("notaText", notaText)

            context.startActivity(intentTextNote)

        } else {
            //si el tipo es 2 es nota de Tareas
            Toast.makeText(context, "MODIFICAR LISTA DE TAREAS", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun getNotaTexto(nota: Nota, context: AppCompatActivity): NotaDeTexto? {
        var nota: Nota? = Conexion.obtenerNota(context,nota.id_nota.toString())
        var notaDeTexto : NotaDeTexto?  = Conexion.obtenerNotaTexto(context, nota?.id_nota.toString())

        return notaDeTexto
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val titulo = view.findViewById<TextView>(R.id.txtTituloNota)
        val fecha = view.findViewById<TextView>(R.id.txtFechaNota)
        val hora = view.findViewById<TextView>(R.id.txt_hora_nota)

    }
}