package adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.R
import modelo.Nota

class AdaptadorRecyclerV : RecyclerView.Adapter<AdaptadorRecyclerV.ViewHolder>{
    private var context: Activity
    private var notas: ArrayList<Nota>? = null

    constructor(context: Activity, notes: ArrayList<Nota>)  {
        this.context = context
        this.notas = notes
    }

    override fun getItemCount(): Int {
        return this.notas?.size!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder?.item.text = this.valores!![position].toString()
        val nota = this.notas!![position]
        holder.bind(nota, context)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        //Usando el card definido en item_card.xml
        val titulo = view.findViewById<TextView>(R.id.txtTituloNota)
        val fecha = view.findViewById<TextView>(R.id.txtFechaNota)
        val hora = view.findViewById<TextView>(R.id.txt_hora_nota)
        //Usando el layout definido en item_lo.xml
        //val item = view.findViewById<TextView>(R.id.txtItem)


        fun bind(valorSeleccionado: Nota, context: Context){
            titulo.text = valorSeleccionado.titulo
            fecha.text = valorSeleccionado.fecha
            hora.text = valorSeleccionado.hora_nota

            itemView.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, valorSeleccionado.toString(), Toast.LENGTH_SHORT).show()
            })
        }
    }
}