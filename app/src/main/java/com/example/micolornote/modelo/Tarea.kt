package com.example.micolornote.modelo

class Tarea(id_tarea: String, id_nota: String, t: String, realiz: Int, img: String) {

    var id_Tarea = id_tarea
    var id_Nota = id_nota
    var texto_tarea = t
    var tarea_realizada: Int = realiz //si es 0 es no realizada, si es 1 es realizada
    var foto_tarea: String = img

    override fun toString(): String {
        return texto_tarea.toString()
    }
}