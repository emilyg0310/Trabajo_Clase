package com.example.lugares

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lugares.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        //SE DEFINE EL METODO PARA EL LOGIN
        binding.btLogin.setOnClickListener{
            haceLogin();
        }

        //SE DEFINE EL METODO PARA EL REGISTRO
        binding.btRegister.setOnClickListener{
            haceRegistro();
        }
    }

    private fun haceRegistro() {
        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()

        //SE HACE EL REGISTRO
        auth.createUserWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this)
            {task -> if(task.isSuccessful)
            {
                Log.d("creando usuario","Registrado")
                val user = auth.currentUser
                actualiza(user)
            }else{
                Log.d("creando usuario","Fallo")
                Toast.makeText(baseContext,"Fallo",Toast.LENGTH_LONG).show()
                actualiza(null)
            }
            }
    }

    private fun actualiza(user: FirebaseUser?) {
        if(user != null)
        {
            val intent = Intent(this,Principal::class.java)
            startActivity(intent)
        }
    }

    //ESTO HARA QUE UNA VEZ AUTENTICADO ... NO PIDA MAS A MENOS QUE SE CIERRE
    public override fun onStart(){
        super.onStart()
        val usuario = auth.currentUser
        actualiza(usuario)
    }

    private fun haceLogin() {
        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()

        //SE HACE EL LOGIN
        auth.signInWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this)
            {task -> if(task.isSuccessful)
            {
                Log.d("Autenticando","Autenticado")
                val user = auth.currentUser
                actualiza(user)
            }else{
                Log.d("Autenticando","Fallo")
                Toast.makeText(baseContext,"Fallo",Toast.LENGTH_LONG).show()
                actualiza(null)
            }
            }
    }
}