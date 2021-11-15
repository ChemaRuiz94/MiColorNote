package bd

import Auxiliar.Constantes
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteConexion(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        //db.execSQL("create table ${Constantes.TAB_NOTAS}(${Constantes.ID_NOTA} integer primary key autoincrement, ${Constantes.TITULO_NOTA} text, ${Constantes.FECHA_NOTA} text, ${Constantes.HORA_NOTA} text)")

        db.execSQL("create table ${Constantes.TAB_NOTAS_TEXT}(${Constantes.ID_NOTA_TEXT} integer primary key autoincrement, ${Constantes.TITULO_NOTA_TEXT} text, ${Constantes.FECHA_NOTA_TEXT} text, ${Constantes.HORA_NOTA_TEXT} text , ${Constantes.CONTENIDO_NOTA_TEXT} text)")
        db.execSQL("create table ${Constantes.TAB_TAREAS}(${Constantes.ID_TAREA} integer primary key autoincrement, ${Constantes.TEXTO_TAREA} text, ${Constantes.TAREA_REALIZADA} boolean, ${Constantes.FOTO_TAREA} text)")

        //db.execSQL("create table ${Constantes.TAB_NOTAS_LISTA_TAREAS}(${Constantes.ID_NOTA_LISTA_TAREAS} integer primary key autoincrement, ${Constantes.TITULO_NOTA_LISTA_TAREAS} text, ${Constantes.FECHA_NOTA_LISTA_TAREAS} text, ${Constantes.HORA_NOTA_LISTA_TAREAS} text , ${Constantes.ID_TAREA_LISTA} text)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}