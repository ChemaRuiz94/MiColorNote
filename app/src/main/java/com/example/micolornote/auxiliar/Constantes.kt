package com.example.micolornote.auxiliar

import java.text.SimpleDateFormat

object Constantes {

    //nombre de la base de datos
    const val nombreBD : String = "administracion3.db3"

    //constantes de la tabla notas
    const val TAB_NOTAS : String = "tabla_notas"
    const val ID_NOTA : String = "id_nota"
    const val TITULO_NOTA : String = "titulo_nota"
    const val FECHA_NOTA : String = "fecha_nota"
    const val HORA_NOTA : String = "hora_nota"
    const val TIPO_NOTA : String = "tipo_nota"

    //constantes de la tabla notasDeTexto
    const val TAB_NOTAS_TEXT : String = "tabla_notas_texto"
    const val ID_NOTA_TEXT : String = "id_nota_texto"
    const val TITULO_NOTA_TEXT : String = "titulo_nota_texto"
    const val FECHA_NOTA_TEXT : String = "fecha_nota_texto"
    const val HORA_NOTA_TEXT : String = "hora_nota_texto"
    const val CONTENIDO_NOTA_TEXT : String = "contenido_nota_texto"

    //constantes de la tabla tareas
    const val TAB_TAREAS : String = "tabla_tareas"
    const val ID_TAREA : String = "id_tarea"
    const val ID_TAREA_CRUCE : String = "id_tarea_nota"
    const val TEXTO_TAREA : String = "texto_tarea"
    const val TAREA_REALIZADA : String = "tarea_realizada"
    const val FOTO_TAREA : String = "foto_tarea"


    //constantes DateFormat
    val completeDateFormat = SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
    val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    val hourDateFormat = SimpleDateFormat("HH:mm")


}