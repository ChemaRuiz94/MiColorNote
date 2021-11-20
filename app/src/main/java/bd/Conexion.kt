package bd

import Auxiliar.Constantes
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
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
}