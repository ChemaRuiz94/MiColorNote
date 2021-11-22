package Auxiliar

import java.text.SimpleDateFormat

object Constantes {

    //nombre de la base de datos
    val nombreBD : String = "administracion3.db3"

    //constantes de la tabla notas
    val TAB_NOTAS : String = "tabla_notas"
    val ID_NOTA : String = "id_nota"
    val TITULO_NOTA : String = "titulo_nota"
    val FECHA_NOTA : String = "fecha_nota"
    val HORA_NOTA : String = "hora_nota"
    val TIPO_NOTA : String = "tipo_nota"

    //constantes de la tabla notasDeTexto
    val TAB_NOTAS_TEXT : String = "tabla_notas_texto"
    val ID_NOTA_TEXT : String = "id_nota_texto"
    val TITULO_NOTA_TEXT : String = "titulo_nota_texto"
    val FECHA_NOTA_TEXT : String = "fecha_nota_texto"
    val HORA_NOTA_TEXT : String = "hora_nota_texto"
    val CONTENIDO_NOTA_TEXT : String = "contenido_nota_texto"

    //constantes de la tabla tareas
    val TAB_TAREAS : String = "tabla_tareas"
    val ID_TAREA : String = "id_tarea"
    val ID_TAREA_CRUCE : String = "id_tarea_nota"
    val TEXTO_TAREA : String = "texto_tarea"
    val TAREA_REALIZADA : String = "tarea_realizada"
    val FOTO_TAREA : String = "foto_tarea"


    //constantes DateFormat
    val completeDateFormat = SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
    val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    val hourDateFormat = SimpleDateFormat("HH:mm")



    /*

    //constantes de la tabla notasDeTexto
    val TAB_NOTAS_LISTA_TAREAS : String = "tabla_notas_lista_tareas"
    val ID_NOTA_LISTA_TAREAS : String = "id_nota_lista_tareas"
    val TITULO_NOTA_LISTA_TAREAS : String = "titulo_nota_lista_tareas"
    val FECHA_NOTA_LISTA_TAREAS : String = "fecha_nota_lista_tareas"
    val HORA_NOTA_LISTA_TAREAS : String = "hora_nota_lista_tareas"




    //constantes de la tabla cruzada
    val TAB_TAREAS_LISTA : String = "tabla_tareas_lista"
    val ID_TAREA_LISTA : String = "id_tarea_lista"


     */
}