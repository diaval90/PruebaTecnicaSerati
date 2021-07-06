package com.example.contactos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView

class ListaContactos : AppCompatActivity() {

    internal val conexion = BaseDatos(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_contactos)

        var listview : ListView = findViewById(R.id.listview)
        var btnAdd : ImageView = findViewById(R.id.btnAdd)
        var btnatras : ImageView = findViewById(R.id.btnatras)

        btnatras.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnAdd.setOnClickListener {
            val intent = Intent(this, Registrar::class.java)
            startActivity(intent)
        }

        var list: ArrayList<Personas> = conexion.mostrarContactos()
        var contactos: List<Personas> = conexion.seleccionartodo()
//        var adp: ArrayAdapter<String>
//        adp = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        val adp2 = AdaptadorCustom(this, list)
        listview.adapter = adp2
        listview.setOnItemClickListener { parent, view, position, id ->

            val id = contactos[position].id
            val nombre = contactos[position].nombre
            val apellido_paterno = contactos[position].apellido_paterno
            val apellido_materno = contactos[position].apellido_materno
            val correo = contactos[position].correo
            val direccion = contactos[position].direccion
            val fecha_nacimiento = contactos[position].fecha_nacimiento
            val Numerotel = contactos[position].Numerotel
            val Numerocas = contactos[position].Numerocas
            val NumeroTrabajo = contactos[position].NumeroTrabajo

            val intent = Intent(this, Editar::class.java)

            intent.putExtra("id", id)
            intent.putExtra("nombre", nombre)
            intent.putExtra("apellido_paterno", apellido_paterno)
            intent.putExtra("apellido_materno", apellido_materno)
            intent.putExtra("correo", correo)
            intent.putExtra("direccion", direccion)
            intent.putExtra("fecha_nacimiento", fecha_nacimiento)
            intent.putExtra("Numerotel", Numerotel)
            intent.putExtra("Numerocas", Numerocas)
            intent.putExtra("NumeroTrabajo", NumeroTrabajo)
            startActivity(intent)
            finish()
        }

        }
}