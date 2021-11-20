package modelo

import Auxiliar.Constantes
import java.sql.Timestamp

object FactoriaNota {

    fun gen_Id_Nota(): String {
        var id: String = ""
        var time = Timestamp(System.currentTimeMillis())
        val format = Constantes.completeDateFormat.format(time).toString()
        val rnds = (0..100).random()
        id += "id${time}${rnds} "
        return id
    }

    fun gen_Nota(tit: String): Nota {

        var id = gen_Id_Nota()
        var time = Timestamp(System.currentTimeMillis())
        val fecha = Constantes.simpleDateFormat.format(time).toString()
        val hora = Constantes.hourDateFormat.format(time).toString()

        val nota: Nota = Nota(id, tit, fecha, hora)
        return nota
    }

    fun gen_NotaText(not: Nota, conten: String): NotaDeTexto {
        var notaDeTexto: NotaDeTexto =
            NotaDeTexto(not.id_nota, not.titulo, not.fecha, not.hora_nota, conten)
        return notaDeTexto
    }
}