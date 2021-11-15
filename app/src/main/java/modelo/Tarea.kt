package modelo

import java.util.*
import kotlin.collections.ArrayList

class Tarea (id_tar: Int, t: String, img : String)  {

    var id_tarea = id_tar
    var texto = t
    var realizada : Boolean = false
    var foto : String = img

    override fun toString(): String {
        return texto.toString()
    }
}