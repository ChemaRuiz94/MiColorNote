package modelo

import java.util.*

class NotaDeTexto(id_notaText: String, t: String, f: String, h: String, tip: Int, cont: String) :
    Nota(id= id_notaText, tit = t, fech = f, hora = h, tip = tip) {

    var contenido: String = cont

    fun getConten(): String {
        return this.contenido
    }

    override fun toString(): String {
        var msg = super.toString()
        msg += "${this.contenido}"
        return msg
    }
}