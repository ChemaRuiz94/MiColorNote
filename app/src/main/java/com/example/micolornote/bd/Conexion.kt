package com.example.micolornote.bd

import com.example.micolornote.auxiliar.Constantes
import android.content.ContentValues
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.micolornote.modelo.Nota
import com.example.micolornote.modelo.NotaDeTareas
import com.example.micolornote.modelo.NotaDeTexto
import com.example.micolornote.modelo.Tarea

object Conexion {
    var nombreBD = Constantes.nombreBD

    fun addNotaText(contexto: AppCompatActivity, n: NotaDeTexto) {
        val admin = AdminSQLiteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        //registro nota
        var regNota = ContentValues()
        regNota.put("${Constantes.ID_NOTA}", n.id_nota)
        regNota.put("${Constantes.TITULO_NOTA}", n.titulo)
        regNota.put("${Constantes.FECHA_NOTA}", n.fecha)
        regNota.put("${Constantes.HORA_NOTA}", n.hora_nota)
        regNota.put("${Constantes.TIPO_NOTA}", n.tipo)
        bd.insert("${Constantes.TAB_NOTAS}", null, regNota)

        //registro notaText
        val registroNotaText = ContentValues()
        registroNotaText.put("${Constantes.ID_NOTA_TEXT}", n.id_nota)
        registroNotaText.put("${Constantes.CONTENIDO_NOTA_TEXT}", n.contenido)
        bd.insert("${Constantes.TAB_NOTAS_TEXT}", null, registroNotaText)
        bd.close()
    }

    fun delNotaText(contexto: AppCompatActivity, n: Nota): Int {
        val admin = AdminSQLiteConexion(contexto, Constantes.nombreBD, null, 1)
        val bd = admin.writableDatabase
        val id = n.id_nota
        var cant = bd.delete("${Constantes.TAB_NOTAS}", "${Constantes.ID_NOTA}='${id}'", null)
        val cant2 =
            bd.delete("${Constantes.TAB_NOTAS_TEXT}", "${Constantes.ID_NOTA_TEXT}='${id}'", null)

        bd.close()
        if (cant == cant2) {
            return cant2
        } else {
            return cant
        }
    }

    fun obtenerNotas(contexto: AppCompatActivity): ArrayList<Nota> {
        var notas: ArrayList<Nota> = ArrayList(1)
        val admin = AdminSQLiteConexion(contexto, Constantes.nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select ${Constantes.ID_NOTA},${Constantes.TITULO_NOTA},${Constantes.FECHA_NOTA},${Constantes.HORA_NOTA},${Constantes.TIPO_NOTA} from ${Constantes.TAB_NOTAS}",
            null
        )
        while (fila.moveToNext()) {

            var n: Nota = Nota(
                fila.getString(0),
                fila.getString(1),
                fila.getString(2),
                fila.getString(3),
                fila.getInt(4)

            )


            notas.add(n)
        }
        bd.close()
        return notas

    }

    fun obtenerNotasPorNombre(contexto: AppCompatActivity, nombre: String): ArrayList<Nota> {
        var notas: ArrayList<Nota> = ArrayList(1)

        val admin = AdminSQLiteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select ${Constantes.ID_NOTA},${Constantes.TITULO_NOTA},${Constantes.FECHA_NOTA},${Constantes.HORA_NOTA},${Constantes.TIPO_NOTA} from ${Constantes.TAB_NOTAS} where ${Constantes.TITULO_NOTA}='${nombre}'",
            null
        )
        while (fila.moveToNext()) {
            var nota: Nota = Nota(
                fila.getString(0),
                fila.getString(1),
                fila.getString(2),
                fila.getString(3),
                fila.getInt(4)
            )
            notas.add(nota)
        }
        bd.close()
        return notas

    }

    fun modNotaTexto(contexto: AppCompatActivity, id: String, n: NotaDeTexto): Int {
        val admin = AdminSQLiteConexion(contexto, Constantes.nombreBD, null, 1)
        val bd = admin.writableDatabase

        //registro nota
        var regNota = ContentValues()
        regNota.put("${Constantes.ID_NOTA}", n.id_nota)
        regNota.put("${Constantes.TITULO_NOTA}", n.titulo)
        regNota.put("${Constantes.FECHA_NOTA}", n.fecha)
        regNota.put("${Constantes.HORA_NOTA}", n.hora_nota)
        regNota.put("${Constantes.TIPO_NOTA}", n.tipo)
        val cant =
            bd.update("${Constantes.TAB_NOTAS}", regNota, "${Constantes.ID_NOTA}='${id}'", null)

        //registro notaText
        val registroNotaText = ContentValues()
        registroNotaText.put("${Constantes.ID_NOTA_TEXT}", n.id_nota)
        registroNotaText.put("${Constantes.CONTENIDO_NOTA_TEXT}", n.contenido)
        val cant2 = bd.update(
            "${Constantes.TAB_NOTAS_TEXT}",
            registroNotaText,
            "${Constantes.ID_NOTA_TEXT}='${id}'",
            null
        )

        bd.close()
        if (cant == cant2) {
            return cant2
        } else {
            return cant
        }
    }

    fun modListaTareas(
        contexto: AppCompatActivity,
        id: String,
        n: Nota,
        tareas: ArrayList<Tarea>
    ): Int {
        val admin = AdminSQLiteConexion(contexto, Constantes.nombreBD, null, 1)
        val bd = admin.writableDatabase

        Conexion.delTareasIdNota(contexto, n)

        //registro nota
        var regNota = ContentValues()
        regNota.put("${Constantes.ID_NOTA}", n.id_nota)
        regNota.put("${Constantes.TITULO_NOTA}", n.titulo)
        regNota.put("${Constantes.FECHA_NOTA}", n.fecha)
        regNota.put("${Constantes.HORA_NOTA}", n.hora_nota)
        regNota.put("${Constantes.TIPO_NOTA}", n.tipo)
        val cant =
            bd.update("${Constantes.TAB_NOTAS}", regNota, "${Constantes.ID_NOTA}='${id}'", null)

        //registro lista
        //Conexion.delTareasIdNota(contexto, n)
        Conexion.addTareas(contexto, n.id_nota.toString(), tareas)

        bd.close()
        return cant

    }

    fun delNota(contexto: AppCompatActivity, n: Nota): Int {
        val admin = AdminSQLiteConexion(contexto, Constantes.nombreBD, null, 1)
        val bd = admin.writableDatabase
        val id = n.id_nota
        var cant = bd.delete("${Constantes.TAB_NOTAS}", "${Constantes.ID_NOTA}='${id}'", null)

        bd.close()
        return cant
    }



    fun addNota(contexto: AppCompatActivity, n: Nota) {
        val admin = AdminSQLiteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        //registro nota
        var regNota = ContentValues()
        regNota.put("${Constantes.ID_NOTA}", n.id_nota)
        regNota.put("${Constantes.TITULO_NOTA}", n.titulo)
        regNota.put("${Constantes.FECHA_NOTA}", n.fecha)
        regNota.put("${Constantes.HORA_NOTA}", n.hora_nota)
        regNota.put("${Constantes.TIPO_NOTA}", n.tipo)
        bd.insert("${Constantes.TAB_NOTAS}", null, regNota)

        bd.close()
    }

    fun addTareas(contexto: AppCompatActivity, id_nota: String, tareas: ArrayList<Tarea>) {
        val admin = AdminSQLiteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        //registro tarea
        for (tarea in tareas) {
            var regTarea = ContentValues()
            regTarea.put("${Constantes.ID_TAREA}", tarea.id_Tarea)
            regTarea.put("${Constantes.ID_TAREA_CRUCE}", id_nota)
            regTarea.put("${Constantes.TEXTO_TAREA}", tarea.texto_tarea)
            regTarea.put("${Constantes.TAREA_REALIZADA}", tarea.tarea_realizada)
            regTarea.put("${Constantes.FOTO_TAREA}", tarea.foto_tarea)
            bd.insert("${Constantes.TAB_TAREAS}", null, regTarea)
        }

        bd.close()
    }

    fun addTarea(contexto: AppCompatActivity, id_nota: String, tarea: Tarea) {
        val admin = AdminSQLiteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        //registro tarea
        var regTarea = ContentValues()
        regTarea.put("${Constantes.ID_TAREA}", tarea.id_Tarea)
        regTarea.put("${Constantes.ID_TAREA_CRUCE}", id_nota)
        regTarea.put("${Constantes.TEXTO_TAREA}", tarea.texto_tarea)
        regTarea.put("${Constantes.TAREA_REALIZADA}", tarea.tarea_realizada)
        regTarea.put("${Constantes.FOTO_TAREA}", tarea.foto_tarea)
        bd.insert("${Constantes.TAB_TAREAS}", null, regTarea)

        bd.close()
    }

    fun modTarea(contexto: AppCompatActivity, id_tarea: String, tarea: Tarea): Int {
        val admin = AdminSQLiteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        var regTarea = ContentValues()
        //regTarea.put("${Constantes.ID_TAREA}", id_tarea)
        regTarea.put("${Constantes.ID_TAREA_CRUCE}", tarea.id_Nota)
        regTarea.put("${Constantes.TEXTO_TAREA}", tarea.texto_tarea)
        regTarea.put("${Constantes.TAREA_REALIZADA}", tarea.tarea_realizada)
        regTarea.put("${Constantes.FOTO_TAREA}", tarea.foto_tarea)
        val cant = bd.update(
            "${Constantes.TAB_TAREAS}",
            regTarea,
            "${Constantes.ID_TAREA}= '${id_tarea}'",
            null
        )
        bd.close()
        return cant
    }

    fun delTarea(contexto: AppCompatActivity, tarea: Tarea): Int {
        val admin = AdminSQLiteConexion(contexto, Constantes.nombreBD, null, 1)
        val bd = admin.writableDatabase
        val id = tarea.id_Tarea
        var cant =
            bd.delete("${Constantes.TAB_TAREAS}", "${Constantes.ID_TAREA}='${id}'", null)

        bd.close()
        return cant
    }

    fun obtenerNota(contexto: AppCompatActivity, id: String): Nota? {
        var n: Nota? = null
        val admin = AdminSQLiteConexion(contexto, Constantes.nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select ${Constantes.ID_NOTA},${Constantes.TITULO_NOTA},${Constantes.FECHA_NOTA},${Constantes.HORA_NOTA},${Constantes.TIPO_NOTA} from ${Constantes.TAB_NOTAS} where ${Constantes.ID_NOTA}='${id}'",
            null
        )
        if (fila.moveToFirst()) {
            n = Nota(
                fila.getString(0),
                fila.getString(1),
                fila.getString(2),
                fila.getString(3),
                fila.getInt(4)
            )
            Log.d("OBTENER_NOTA", n.toString())
        }
        bd.close()
        return n
    }

    fun obtenerTareasIdNota(contexto: AppCompatActivity, id: String): ArrayList<Tarea> {
        var tareas: ArrayList<Tarea> = ArrayList(1)

        val admin = AdminSQLiteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select ${Constantes.ID_TAREA}, ${Constantes.ID_TAREA_CRUCE}, ${Constantes.TEXTO_TAREA}, ${Constantes.TAREA_REALIZADA}, ${Constantes.FOTO_TAREA} from ${Constantes.TAB_TAREAS} where ${Constantes.ID_TAREA_CRUCE}='${id}'",
            null
        )
        while (fila.moveToNext()) {
            var t: Tarea = Tarea(
                fila.getString(0),
                fila.getString(1),
                fila.getString(2),
                fila.getInt(3),
                fila.getString(4)
            )
            tareas.add(t)
        }
        bd.close()
        return tareas
    }

    fun delTareasIdNota(contexto: AppCompatActivity, n: Nota): Int {
        val admin = AdminSQLiteConexion(contexto, Constantes.nombreBD, null, 1)
        val bd = admin.writableDatabase
        val id = n.id_nota
        var cant =
            bd.delete("${Constantes.TAB_TAREAS}", "${Constantes.ID_TAREA_CRUCE}='${id}'", null)

        bd.close()

        return cant

    }

    fun obtenerNotaListaTareas(contexto: AppCompatActivity, id: String): NotaDeTareas? {
        var n: NotaDeTareas? = null
        var t: Tarea? = null
        var tareas: ArrayList<Tarea>? = null
        var nota: Nota? = Conexion.obtenerNota(contexto, id) //se que esto no es lo mas eficiente
        val admin = AdminSQLiteConexion(contexto, Constantes.nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select ${Constantes.ID_TAREA},${Constantes.ID_TAREA_CRUCE},${Constantes.TEXTO_TAREA}, ${Constantes.TAREA_REALIZADA}, ${Constantes.FOTO_TAREA} from ${Constantes.TAB_TAREAS} where ${Constantes.ID_TAREA_CRUCE}='${id}'",
            null
        )

        if (fila.moveToFirst()) {
            if (nota != null) {
                t = Tarea(
                    fila.getString(0),
                    fila.getString(1),
                    fila.getString(3),
                    fila.getInt(3),
                    fila.getString(4)
                )
                tareas?.add(t)
            }
            if (nota != null) {
                n = NotaDeTareas(
                    nota.id_nota,
                    nota.titulo,
                    nota.fecha,
                    nota.hora_nota,
                    nota.tipo,
                    tareas
                )
            }
        }
        bd.close()
        return n
    }


    fun obtenerNotaTexto(contexto: AppCompatActivity, id: String): NotaDeTexto? {
        var n: NotaDeTexto? = null
        var nota: Nota? = Conexion.obtenerNota(contexto, id) //se que esto no es lo mas eficiente
        val admin = AdminSQLiteConexion(contexto, Constantes.nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select ${Constantes.ID_NOTA_TEXT},${Constantes.CONTENIDO_NOTA_TEXT} from ${Constantes.TAB_NOTAS_TEXT} where ${Constantes.ID_NOTA_TEXT}='${id}'",
            null
        )
        if (fila.moveToFirst()) {
            if (nota != null) {
                n = NotaDeTexto(
                    nota.id_nota,
                    nota.titulo,
                    nota.fecha,
                    nota.hora_nota,
                    nota.tipo,
                    fila.getString(1)
                )
                Log.d("NOTA_TEXT", n.toString())
            }
        }
        bd.close()
        return n
    }
}