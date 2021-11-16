package bd

import Auxiliar.Constantes
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import modelo.NotaDeTexto

object Conexion {
    var nombreBD = Constantes.nombreBD

    fun addNota(contexto: AppCompatActivity, n: NotaDeTexto) {
        val admin = AdminSQLiteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("${Constantes.ID_NOTA_TEXT}", n.id_nota)
        registro.put("${Constantes.TITULO_NOTA_TEXT}", n.titulo)
        registro.put("${Constantes.FECHA_NOTA_TEXT}", n.fecha)
        registro.put("${Constantes.HORA_NOTA_TEXT}", n.hora_nota)
        registro.put("${Constantes.CONTENIDO_NOTA_TEXT}", n.contenido)
        bd.insert("${Constantes.TAB_NOTAS_TEXT}", null, registro)
        bd.close()
    }
}