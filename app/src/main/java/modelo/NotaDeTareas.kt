package modelo

import java.util.*
import kotlin.collections.ArrayList

class NotaDeTareas(id_notaTarea: String, t: String, f: String, h: String, lista: ArrayList<Tarea>) :
    Nota(id = id_notaTarea, tit = t, fech = f, hora = h) {

    var listaTareas = lista

    fun addTarea(tarea: Tarea){
        this.listaTareas.add(tarea)
    }

    fun deleteTarea(tarea: Tarea){
        this.listaTareas.remove(tarea)
    }

    override fun toString(): String {
        var msg = ""
        for (t in listaTareas) {
            msg += "${t.toString()} \n"
        }

        return msg
    }
}