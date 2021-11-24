package com.example.micolornote.modelo

import com.example.micolornote.R
import com.example.micolornote.auxiliar.Constantes
import java.sql.Timestamp

object FactoriaNota {

    fun gen_Unique_ID(): String {
        var id: String = ""
        var time = Timestamp(System.currentTimeMillis())
        val format = Constantes.completeDateFormat.format(time).toString()
        val rnds = (0..100).random()
        id += "id${time}${rnds} "
        return id
    }

    fun gen_Nota(tit: String, tipo: Int): Nota {

        var id = gen_Unique_ID()
        var time = Timestamp(System.currentTimeMillis())
        val fecha = Constantes.simpleDateFormat.format(time).toString()
        val hora = Constantes.hourDateFormat.format(time).toString()

        val nota: Nota = Nota(id, tit, fecha, hora, tipo)
        return nota
    }

    fun gen_Nota_with_Id(id_nota: String,tit: String, tipo: Int): Nota {

        var id = id_nota
        var time = Timestamp(System.currentTimeMillis())
        val fecha = Constantes.simpleDateFormat.format(time).toString()
        val hora = Constantes.hourDateFormat.format(time).toString()

        val nota: Nota = Nota(id, tit, fecha, hora, tipo)
        return nota
    }

    fun gen_NotaText(not: Nota, conten: String): NotaDeTexto {
        var notaDeTexto: NotaDeTexto =
            NotaDeTexto(not.id_nota, not.titulo, not.fecha, not.hora_nota, not.tipo, conten)
        return notaDeTexto
    }

    fun gen_Tarea(id_nota: String, text: String): Tarea {
        var id = gen_Unique_ID()
        var img = R.drawable.ejemplo.toString()
        return Tarea(id, id_nota, text,0, img)
    }
}