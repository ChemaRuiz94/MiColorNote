package modelo

import java.util.*

class NotaDeTexto(id: Int, t: String, f: String, h: String, cont: String) :
    Nota(id = id, tit = t, fech = f, hora = h) {

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