package com.example.contactos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Personas(val id:Int, val nombre:String, val apellido_paterno:String, val apellido_materno:String, val correo:String, val direccion:String, val fecha_nacimiento:String, val Numerotel:String, val Numerocas:String, val NumeroTrabajo:String)

class BaseDatos(context: Context): SQLiteOpenHelper(context, "Contactos.db", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("create table Personas(id integer primary key autoincrement, nombre text, apellido_paterno text, apellido_materno text, correo text, direccion text, fecha_nacimiento text, Numerotel text, Numerocas text, NumeroTrabajo text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("drop table Personas")

    }

    fun insertarDatos(nombre:String, apellido_paterno:String, apellido_materno:String, correo:String, direccion:String, fecha_nacimiento:String, Numerotel:String, Numerocas:String, NumeroTrabajo:String){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nombre", nombre)
        contentValues.put("apellido_paterno", apellido_paterno)
        contentValues.put("apellido_materno", apellido_materno)
        contentValues.put("correo", correo)
        contentValues.put("direccion", direccion)
        contentValues.put("fecha_nacimiento", fecha_nacimiento)
        contentValues.put("Numerotel", Numerotel)
        contentValues.put("Numerocas", Numerocas)
        contentValues.put("NumeroTrabajo", NumeroTrabajo)
        db.insert("Personas", null, contentValues)
    }

    fun mostrarContactos(): ArrayList<Personas> {
        var list : ArrayList<Personas> = arrayListOf()
        val db: SQLiteDatabase = readableDatabase
        var c = db.rawQuery("select * from Personas", null)
        while (c.moveToNext()){
            var nom : String = c.getString(c.getColumnIndex("nombre"))
            var tel : String = c.getString(c.getColumnIndex("Numerotel"))
            var id : String = c.getString(c.getColumnIndex("id"))
            var persona = Personas(id.toInt(), nom, "","", "", "", "",tel, "", "")
            list.add(persona)
            //list.add(tel)
        }
        return list
    }

    fun seleccionartodo(): List<Personas>{
        var listaContactos: MutableList<Personas> = ArrayList<Personas>()
        val db = this.readableDatabase
        val c = db.rawQuery("select * from Personas", null)
        if (c.moveToFirst()){
            do {
                val todo = Personas(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getString(9))
                listaContactos.add(todo)
            }while (c.moveToNext())
        }
        return listaContactos
    }

    fun actualizarData(id: String, nombre:String, apellido_paterno:String, apellido_materno:String, correo:String, direccion:String, fecha_nacimiento:String, Numerotel:String, Numerocas:String, NumeroTrabajo:String ): Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", id)
        contentValues.put("nombre", nombre)
        contentValues.put("apellido_paterno", apellido_paterno)
        contentValues.put("apellido_materno", apellido_materno)
        contentValues.put("correo", correo)
        contentValues.put("direccion", direccion)
        contentValues.put("fecha_nacimiento", fecha_nacimiento)
        contentValues.put("Numerotel", Numerotel)
        contentValues.put("Numerocas", Numerocas)
        contentValues.put("NumeroTrabajo", NumeroTrabajo)
        db.update("Personas", contentValues, "id=?", arrayOf(id))
        return true
    }

    fun borrarData(id:String): Int{
        val db = this.writableDatabase
        return db.delete("Personas", "id=?", arrayOf(id))
    }
}