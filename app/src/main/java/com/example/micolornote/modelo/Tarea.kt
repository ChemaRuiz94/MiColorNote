package com.example.micolornote.modelo

class Tarea(id_tarea: String, id_nota: String, t: String, img: Int)  {

    var id_Tarea = id_tarea
    var id_Nota = id_nota
    var texto_tarea = t
    var tarea_realizada : Boolean = false
    var foto_tarea : Int = img

    override fun toString(): String {
        return texto_tarea.toString()
    }
}