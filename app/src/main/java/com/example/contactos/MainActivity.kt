package com.example.contactos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var autenticacion: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        autenticacion = FirebaseAuth.getInstance()

        val registrar: TextView = findViewById(R.id.edtRegistrar)
        val edtUsuario: EditText = findViewById(R.id.edtUsuario)
        val edtPass: EditText = findViewById(R.id.edtPass)
        val btnIngresar: Button = findViewById(R.id.btnIngresar)

        btnIngresar.setOnClickListener {
            val correo = edtUsuario.text.toString()
            val passw = edtPass.text.toString()
            if (correo.equals("") or passw.equals("")){
                edtUsuario.setError("Required")
                edtPass.setError("Required")
            }else{
                login(correo, passw)
            }
        }

        registrar.setOnClickListener {
            val intent = Intent(this, RegistrarUsuario::class.java)
            startActivity(intent)
        }
    }

    private fun login(email: String, pass: String) {
        autenticacion.signInWithEmailAndPassword(email, pass).addOnCompleteListener{
            if (it.isSuccessful){
                val idUser = FirebaseAuth.getInstance().currentUser!!.uid
                val intent = Intent(this, ListaContactos::class.java)
                intent.putExtra("id", idUser)
                Mensage("Inicio Exitoso!!")
                startActivity(intent)
            }
            else{
                Mensage("Usuario o contraseña incorrecta!")
            }
        }
    }
    fun Mensage(mensaje: String){
        Toast.makeText(this, "${mensaje}", Toast.LENGTH_SHORT).show()
    }
}