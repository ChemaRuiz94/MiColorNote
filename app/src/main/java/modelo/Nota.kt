package modelo

import java.io.Serializable
import java.util.*

open class Nota(id: Int, tit: String, fech: String, hora: String) : Serializable {

    var id_nota: Int = id
    var titulo: String = tit
    var fecha: String = fech
    var hora_nota: String = hora

    override fun toString(): String {
        var msg = "${this.titulo} ${this.fecha} ${this.hora_nota}"
        return msg
    }
}