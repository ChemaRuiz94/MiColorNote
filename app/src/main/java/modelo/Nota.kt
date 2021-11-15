package modelo

import java.io.Serializable
import java.util.*

open abstract class Nota (id: Int, tit: String, fech : Date, hora: String) : Serializable{

    var id_nota = id
    var titulo: String = tit
    var fecha: Date = fech
    var hora_nota : String = hora

    override fun toString(): String {
        var msg = "${this.titulo} ${this.fecha} ${this.hora_nota}"
        return msg
    }
}