package com.example.micolornote.modelo

import java.io.Serializable

open class Nota(id : String, tit: String, fech: String, hora: String, tip: Int) : Serializable {

    val id_nota: String = id
    var titulo: String = tit
    var fecha: String = fech
    var hora_nota: String = hora
    var tipo: Int = tip

    override fun toString(): String {
        var msg = "${this.titulo} ${this.fecha} ${this.hora_nota}"
        return msg
    }
}