package com.example.contactos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import java.util.regex.Pattern

class Registrar : AppCompatActivity() {
    internal val conexion = BaseDatos(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)


        val edtnombre: EditText = findViewById(R.id.edtnombre)
        val edtapellido_paterno: EditText = findViewById(R.id.edtapellido_paterno)
        val edtapellido_materno: EditText = findViewById(R.id.edtapellido_materno)
        val edtcorreo: EditText = findViewById(R.id.edtcorreo)
        val edtdireccion: EditText = findViewById(R.id.edtdireccion)
        val edtfecha_nacimiento: EditText = findViewById(R.id.edtfecha_nacimiento)
        val edtNumerotel: EditText = findViewById(R.id.edtNumerotel)
        val edtNumerocas: EditText = findViewById(R.id.edtNumerocas)
        val edtNumeroTrabajo : EditText = findViewById(R.id.edtNumeroTrabajo)
        val guardar : ImageView = findViewById(R.id.btnsave)
        var btnatras : ImageView = findViewById(R.id.btnatras)

        btnatras.setOnClickListener {
            val intent = Intent(this, ListaContactos::class.java)
            startActivity(intent)
        }

        guardar.setOnClickListener {
            val nombre = edtnombre.text.toString()
            val apellido_paterno = edtapellido_paterno.text.toString()
            val apellido_materno = edtapellido_materno.text.toString()
            val correo = edtcorreo.text.toString()
            val direccion = edtdireccion.text.toString()
            val fecha_nacimiento = edtfecha_nacimiento.text.toString()
            val Numerotel = edtNumerotel.text.toString()
            val Numerocas = edtNumerocas.text.toString()
            val NumeroTrabajo = edtNumeroTrabajo.text.toString()

            if (nombre.equals("") or apellido_paterno.equals("") or apellido_materno.equals("") or correo.equals("") or Numerotel.equals("")){
                Toast.makeText(this, "Los campos con (*) son obligatorios", Toast.LENGTH_SHORT).show()
            }else if(!validarEmail(correo)){
                edtcorreo.setError("Email no válido")
            }else if (Numerotel.length>10){
                Toast.makeText(this, "El maximo de carcteres es de 10 para los numeros", Toast.LENGTH_SHORT).show()
            }else{
                conexion.insertarDatos(nombre, apellido_paterno, apellido_materno, correo, direccion, fecha_nacimiento, Numerotel, Numerocas, NumeroTrabajo)
                Toast.makeText(this, "Contacto creado con exito!!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ListaContactos::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    private fun validarEmail(correo: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(correo).matches()
    }
}