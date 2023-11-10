package com.example.cognify

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

//Por Bruno Dipietro 2023
//Este fragment tiene la unica funcion de ser una prueba de lectura de datos
//Ya es innecesario, pero queda por las dudas
//Todo está hecho de forma rustica <--


class paciente1Fragment : Fragment() {

    companion object {
        fun newInstance() = paciente1Fragment()
    }

    private lateinit var viewModel: Paciente1ViewModel

    //Variables del firebase
    private lateinit var database: FirebaseDatabase
    private lateinit var usersReference: DatabaseReference

    //Campos de datos donde se van a mostrar los datos
    private lateinit var textoDatos: TextView
    private lateinit var textoNombre: TextView
    private lateinit var textoApellido: TextView
    private lateinit var textoEdad: TextView

    private lateinit var firebaseAuth: FirebaseAuth //Variable del firebase


    val db = FirebaseFirestore.getInstance() //Instanciamos la base de datos

    //var datos = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Convertimos el "return inflater..." en un val view, al final retornamos view
        val view = inflater.inflate(R.layout.fragment_paciente1, container, false)

        database = FirebaseDatabase.getInstance()
        textoDatos = view.findViewById(R.id.textViewShowData)

        textoNombre = view.findViewById(R.id.textViewShowName)
        textoApellido = view.findViewById(R.id.textViewShowLastName)
        textoEdad = view.findViewById(R.id.textViewShowAge)

        leer()
        return view //Retornamos el view antes mencionado
    }



    private fun leer() {
        usersReference = database.reference.child("UsersData")
        val datosPersonalesReference = usersReference.child("yYNof0hM5jMPTdJOD7dyndwUepb2").child("datos").child("zaky")
        val query = datosPersonalesReference.orderByKey().limitToLast(1)


        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) { //Mostrar los datos
                    //Obtenemos los valores ENTRENAMIENTO
                    val datoTiempo = userSnapshot.child("tiempo").getValue(String::class.java)
                    val datoPresiones = userSnapshot.child("presiones").getValue(String::class.java)
                    val datoCorrectas = userSnapshot.child("presiones correctas").getValue(String::class.java)

                    textoNombre.text = "Tiempo mensaje:  $datoTiempo"
                    textoApellido.text = "Presiones: $datoPresiones"
                    textoEdad.text = "Correctas: $datoCorrectas"
                    textoDatos.text = ""

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(getActivity(), "Error al leer datos: ${databaseError.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
