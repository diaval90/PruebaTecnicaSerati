package com.example.contactos

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern


data class Usuario(val nombre:String, val apellido_paterno:String, val apellido_materno:String, val correo:String, val direccion:String, val fecha_nacimiento:String, val Contrasena:String, val Confirmar_Contrasena:String)

class RegistrarUsuario : AppCompatActivity() {
    private  var autenticacion: FirebaseAuth?=null
    private  val database = FirebaseDatabase.getInstance()
    private var conexion = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuario)

        autenticacion = FirebaseAuth.getInstance()

        val btnsaver : ImageView = findViewById(R.id.btnsaver)
        val btnatrasr: ImageView = findViewById(R.id.btnatrasr)
        val edtnombrer :EditText = findViewById(R.id.edtnombrer)
        val edtapellido_paternor :EditText = findViewById(R.id.edtapellido_paternor)
        val edtapellido_maternor :EditText = findViewById(R.id.edtapellido_maternor)
        val edtcorreor :EditText = findViewById(R.id.edtcorreor)
        val edtdireccionr :EditText = findViewById(R.id.edtdireccionr)
        val edtfecha_nacimientor :EditText = findViewById(R.id.edtfecha_nacimientor)
        val edtPassr :EditText = findViewById(R.id.edtPassr)
        val edtPassconfirm :EditText = findViewById(R.id.edtPassconfirm)
        var btnatras : ImageView = findViewById(R.id.btnatrasr)

        btnatras.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnsaver.setOnClickListener {
            val nam = edtnombrer.text.toString()
            val apellidoP = edtapellido_paternor.text.toString()
            val apellidoM = edtapellido_maternor.text.toString()
            val email = edtcorreor.text.toString()
            val dire = edtdireccionr.text.toString()
            val fechana = edtfecha_nacimientor.text.toString()
            val pass = edtPassr.text.toString()
            val passco = edtPassconfirm.text.toString()
            if (nam.equals("") or apellidoP.equals("") or apellidoM.equals("") or email.equals("") or pass.equals("") or passco.equals("")){
                Toast.makeText(this, "Los campos con (*) son obligatorios", Toast.LENGTH_SHORT).show()
            }else if(pass != passco){
                Toast.makeText(this, "Las contrasenas no coinciden", Toast.LENGTH_SHORT).show()
            }else if (pass.length>6){
                Toast.makeText(this, "La contraseña no puede tener mas de 6 caracteres", Toast.LENGTH_SHORT).show()
            }else if (!validarEmail(email)){
            edtcorreor.setError("Email no válido")
            }else{
                registrarUser(nam,apellidoP, apellidoM, email, dire, fechana, pass, passco)
            }
        }
    }

    fun registrarUser(nombre:String, apellido_paterno:String, apellido_materno:String, correo:String, direccion:String, fecha_nacimiento:String, Contrasena:String, Confirmar_Contrasena:String){
        autenticacion!!.createUserWithEmailAndPassword(correo, Contrasena)
            .addOnCompleteListener(this){task->
                if(task.isSuccessful){
                    val Uid = FirebaseAuth.getInstance().currentUser!!.uid
                    Toast.makeText(applicationContext, "Registrado con exito!!", Toast.LENGTH_SHORT).show()
                    conexion.child("users").child(Uid).setValue(Usuario(nombre, apellido_paterno, apellido_materno, correo, direccion, fecha_nacimiento, Contrasena, Confirmar_Contrasena))
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("id", Uid)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(applicationContext, "No se pudo registrar!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validarEmail(correo: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(correo).matches()
    }
}