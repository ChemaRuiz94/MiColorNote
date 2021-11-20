package bd

import Auxiliar.Constantes
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import modelo.Nota
import modelo.NotaDeTexto

object Conexion {
    var nombreBD = Constantes.nombreBD

    fun addNotaText(contexto: AppCompatActivity, n: NotaDeTexto) {
        val admin = AdminSQLiteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        //registro nota
        var regNota = ContentValues()
        regNota.put("${Constantes.ID_NOTA}",n.id_nota)
        regNota.put("${Constantes.TITULO_NOTA}", n.titulo)
        regNota.put("${Constantes.FECHA_NOTA}", n.fecha)
        regNota.put("${Constantes.HORA_NOTA}", n.hora_nota)
        bd.insert("${Constantes.TAB_NOTAS}", null, regNota)

        //registro notaText
        val registroNotaText = ContentValues()
        registroNotaText.put("${Constantes.ID_NOTA_TEXT}", n.id_nota)
        registroNotaText.put("${Constantes.CONTENIDO_NOTA_TEXT}", n.contenido)
        bd.insert("${Constantes.TAB_NOTAS_TEXT}", null, registroNotaText)
        bd.close()
    }

    fun obtenerNotas(contexto: AppCompatActivity):ArrayList<Nota>{
        var notas: ArrayList<Nota> = ArrayList(1)
        val admin = AdminSQLiteConexion(contexto, Constantes.nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select ${Constantes.ID_NOTA},${Constantes.TITULO_NOTA},${Constantes.FECHA_NOTA},${Constantes.HORA_NOTA} from ${Constantes.TAB_NOTAS}",
            null
        )
        while (fila.moveToNext()) {

            var n: Nota = Nota(
                fila.getString(0),
                fila.getString(1),
                fila.getString(2),
                fila.getString(3),
            )


            notas.add(n)
        }
        bd.close()
        return notas

    }
}