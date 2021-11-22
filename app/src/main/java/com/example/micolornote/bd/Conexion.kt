package com.example.micolornote.bd

import com.example.micolornote.auxiliar.Constantes
import android.content.ContentValues
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.micolornote.modelo.Nota
import com.example.micolornote.modelo.NotaDeTexto

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

    fun modNotaTexto(contexto:AppCompatActivity, id:String, n: NotaDeTexto):Int {
        val admin = AdminSQLiteConexion(contexto, Constantes.nombreBD, null, 1)
        val bd = admin.writableDatabase

        //registro nota
        var regNota = ContentValues()
        regNota.put("${Constantes.ID_NOTA}", n.id_nota)
        regNota.put("${Constantes.TITULO_NOTA}", n.titulo)
        regNota.put("${Constantes.FECHA_NOTA}", n.fecha)
        regNota.put("${Constantes.HORA_NOTA}", n.hora_nota)
        regNota.put("${Constantes.TIPO_NOTA}", n.tipo)
        val cant = bd.update("${Constantes.TAB_NOTAS}", regNota, "${Constantes.ID_NOTA}='${id}'", null)

        //registro notaText
        val registroNotaText = ContentValues()
        registroNotaText.put("${Constantes.ID_NOTA_TEXT}", n.id_nota)
        registroNotaText.put("${Constantes.CONTENIDO_NOTA_TEXT}", n.contenido)
        val cant2 = bd.update("${Constantes.TAB_NOTAS_TEXT}", registroNotaText, "${Constantes.ID_NOTA_TEXT}='${id}'", null)

        bd.close()
        if (cant == cant2) {
            return cant2
        } else {
            return cant
        }
    }

    fun obtenerNota(contexto: AppCompatActivity, id: String): Nota? {
        var n:Nota? = null
        val admin = AdminSQLiteConexion(contexto, Constantes.nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select ${Constantes.ID_NOTA},${Constantes.TITULO_NOTA},${Constantes.FECHA_NOTA},${Constantes.HORA_NOTA},${Constantes.TIPO_NOTA} from ${Constantes.TAB_NOTAS} where ${Constantes.ID_NOTA}='${id}'",
            null
        )
        Log.e("OBTENER_NOTA",fila.toString())
        if (fila.moveToFirst()) {
            n = Nota(fila.getString(0),fila.getString(1),fila.getString(2),fila.getString(3),fila.getInt(4))
            Log.d("OBTENER_NOTA",n.toString())
            Log.e("OBTENER_NOTA",n.toString())
        }
        bd.close()
        return n
    }


    fun obtenerNotaTexto(contexto: AppCompatActivity, id: String): NotaDeTexto? {
        var n:NotaDeTexto? = null
        var nota: Nota? = Conexion.obtenerNota(contexto, id) //se que esto no es lo mas eficiente
        val admin = AdminSQLiteConexion(contexto, Constantes.nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select ${Constantes.ID_NOTA_TEXT},${Constantes.CONTENIDO_NOTA_TEXT} from ${Constantes.TAB_NOTAS_TEXT} where ${Constantes.ID_NOTA_TEXT}='${id}'",
            null
        )
        Log.e("OBTENER_NOTA_TEXT",fila.toString())
        if (fila.moveToFirst()) {
            if (nota != null) {
                n = NotaDeTexto(nota.id_nota,nota.titulo,nota.fecha,nota.hora_nota,nota.tipo ,fila.getString(1))
                Log.d("NOTA_TEXT",n.toString())
                Log.e("OBTENER_NOTA_TEXT",n.toString())
            }
        }
        bd.close()
        return n
    }
}