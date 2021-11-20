package modelo

import java.util.*
import kotlin.collections.ArrayList

class Tarea ( t: String, img : String)  {

    var texto_tarea = t
    var tarea_realizada : Boolean = false
    var foto_tarea : String = img

    override fun toString(): String {
        return texto_tarea.toString()
    }
}