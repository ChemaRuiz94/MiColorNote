package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.R
import modelo.Nota

class MyAdapterRV(var lista_notas: ArrayList<Nota>, var context: Context) :
    RecyclerView.Adapter<MyAdapterRV.ViewHolder>() {

    companion object {
        var seleccionado: Int = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista_notas.get(position)

        holder.bind(item, context, position, this)
    }

    override fun getItemCount(): Int {
        return lista_notas.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tit = view.findViewById(R.id.txtTituloNota) as TextView
        val fecha = view.findViewById(R.id.txtFechaNota) as TextView
        val itemLayout = view.findViewById(R.id.item_nota_linearLayout) as LinearLayout

        fun bind(nota: Nota, context: Context, pos: Int, miAdaptadorRV: MyAdapterRV) {
            tit.text = nota.titulo
            fecha.text = nota.fecha


            if (pos == seleccionado) {
                with(tit) {

                    this.setTextColor(resources.getColor(R.color.design_default_color_on_secondary))
                }
                with(itemLayout) {
                    this.setBackgroundColor(resources.getColor(R.color.design_default_color_primary_variant))
                }
            } else {
                with(tit) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
                with(itemLayout) {
                    this.setBackgroundColor(resources.getColor(R.color.white))
                }
            }

            itemView.setOnClickListener(View.OnClickListener
            {
                if (pos == seleccionado) {
                    seleccionado = -1
                } else {
                    seleccionado = pos
                }

                miAdaptadorRV.notifyDataSetChanged()
            })

        }
    }
}

