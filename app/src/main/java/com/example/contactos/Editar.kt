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

class Editar : AppCompatActivity() {
    internal val conexion = BaseDatos(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar)

        val edtnombreed: EditText = findViewById(R.id.edtnombreed)
        val edtapellido_paternoed: EditText = findViewById(R.id.edtapellido_paternoed)
        val edtapellido_maternoed: EditText = findViewById(R.id.edtapellido_maternoed)
        val edtcorreoed: EditText = findViewById(R.id.edtcorreoed)
        val edtdireccioned: EditText = findViewById(R.id.edtdireccioned)
        val edtfecha_nacimientoed: EditText = findViewById(R.id.edtfecha_nacimientoed)
        val edtNumeroteled: EditText = findViewById(R.id.edtNumeroteled)
        val edtNumerocased: EditText = findViewById(R.id.edtNumerocased)
        val edtNumeroTrabajoed : EditText = findViewById(R.id.edtNumeroTrabajoed)
        val btnsaveed: ImageView = findViewById(R.id.btnsaveed)
        val btndelete: ImageView = findViewById(R.id.btndelete)
        var btnatras : ImageView = findViewById(R.id.btnatras)

        btnatras.setOnClickListener {
            val intent = Intent(this, ListaContactos::class.java)
            startActivity(intent)
        }

        var b: Bundle? = intent.extras
        val id = b!!.getInt("id").toString()
        val nombre = b.getString("nombre")
        val apellido_paterno = b.getString("apellido_paterno")
        val apellido_materno = b.getString("apellido_materno")
        val correo = b.getString("correo")
        val direccion = b.getString("direccion")
        val fecha_nacimiento = b.getString("fecha_nacimiento")
        val Numerotel = b.getString("Numerotel")
        val Numerocas = b.getString("Numerocas")
        val NumeroTrabajo = b.getString("NumeroTrabajo")

        edtnombreed.setText(nombre)
        edtapellido_paternoed.setText(apellido_paterno)
        edtapellido_maternoed.setText(apellido_materno)
        edtcorreoed.setText(correo)
        edtdireccioned.setText(direccion)
        edtfecha_nacimientoed.setText(fecha_nacimiento)
        edtNumeroteled.setText(Numerotel)
        edtNumerocased.setText(Numerocas)
        edtNumeroTrabajoed.setText(NumeroTrabajo)

        btnsaveed.setOnClickListener {
            if (edtnombreed.text.toString().equals("") or edtapellido_paternoed.text.toString().equals("") or edtapellido_maternoed.text.toString().equals("") or edtcorreoed.text.toString().equals("") or edtNumeroteled.text.toString().equals("")){
                Toast.makeText(this, "Los campos con (*) son obligatorios", Toast.LENGTH_SHORT).show()
            }else if(!validarEmail(edtcorreoed.text.toString())){
                edtcorreoed.setError("Email no válido")
            }else if (edtNumeroteled.text.toString().length>10){
                Toast.makeText(this, "El maximo de carcteres es de 10 para los numeros", Toast.LENGTH_SHORT).show()
            }else{
                val siActualiza = conexion.actualizarData(id,
                    edtnombreed.text.toString(),
                    edtapellido_paternoed.text.toString(),
                    edtapellido_maternoed.text.toString(),
                    edtcorreoed.text.toString(),
                    edtdireccioned.text.toString(),
                    edtfecha_nacimientoed.text.toString(),
                    edtNumeroteled.text.toString(),
                    edtNumerocased.text.toString(),
                    edtNumeroTrabajoed.text.toString())
                if (siActualiza == true){
                    Toast.makeText(this, "Contacto actualizado!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ListaContactos::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "No pudo ser actualizado", Toast.LENGTH_SHORT).show()
                }
            }
        }
        btndelete.setOnClickListener {
            val borrar = conexion.borrarData(id)
            if(borrar  !=0){
                val intent = Intent(this, ListaContactos::class.java)
                startActivity(intent)
                Toast.makeText(this, "Elemento borrado ${id}", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this , "No se pudo borrar!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validarEmail(correo: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(correo).matches()
    }
}