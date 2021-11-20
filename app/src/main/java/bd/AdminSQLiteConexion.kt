package bd

import Auxiliar.Constantes
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteConexion(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {

        //db.execSQL("create table ${Constantes.TAB_NOTAS}(${Constantes.ID_NOTA} integer primary key autoincrement, ${Constantes.TITULO_NOTA} text, ${Constantes.FECHA_NOTA} text, ${Constantes.HORA_NOTA} text)")
        //db.execSQL("create table ${Constantes.TAB_NOTAS_TEXT} (${Constantes.ID_NOTA_TEXT} int primary key, ${Constantes.CONTENIDO_NOTA_TEXT} text) ")
        //db.execSQL("create table ${Constantes.TAB_TAREAS} (${Constantes.ID_TAREA} integer primary key autoincrement,${Constantes.ID_TAREA_CRUCE} int, ${Constantes.TEXTO_TAREA} text,${Constantes.TEXTO_TAREA} text, ${Constantes.TAREA_REALIZADA} boolean, ${Constantes.FOTO_TAREA} text)")

        db.execSQL("create table ${Constantes.TAB_NOTAS} (${Constantes.ID_NOTA} int primary key, ${Constantes.TITULO_NOTA} text, ${Constantes.FECHA_NOTA} text, ${Constantes.HORA_NOTA} text)")
        db.execSQL("create table ${Constantes.TAB_NOTAS_TEXT} (${Constantes.ID_NOTA_TEXT} int primary key, ${Constantes.CONTENIDO_NOTA_TEXT} text)")
        db.execSQL("create table ${Constantes.TAB_TAREAS} (${Constantes.ID_TAREA} int primary key, ${Constantes.ID_TAREA_CRUCE} int, ${Constantes.TEXTO_TAREA} text, ${Constantes.TAREA_REALIZADA} boolean, ${Constantes.FOTO_TAREA} text)")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}