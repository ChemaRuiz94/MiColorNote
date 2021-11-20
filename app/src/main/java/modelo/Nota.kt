package modelo

import java.io.Serializable
import java.util.*

open class Nota(id : String, tit: String, fech: String, hora: String) : Serializable {

    val id_nota: String = id
    var titulo: String = tit
    var fecha: String = fech
    var hora_nota: String = hora

    override fun toString(): String {
        var msg = "${this.titulo} ${this.fecha} ${this.hora_nota}"
        return msg
    }
}