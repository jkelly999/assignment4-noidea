package com.example.assignment4_joshkelly_toannguyen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_api, container, false)

        val apiCallBtn = view.findViewById<Button>(R.id.btn_api_call)
        val resultText = view.findViewById<TextView>(R.id.text_result)

        apiCallBtn.setOnClickListener {
            RetrofitClient.instance.getTodos().enqueue(object : Callback<List<Todo>> {
                override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
                    if (response.isSuccessful) {
                        val todos = response.body()
                        val dbRef = FirebaseDatabase.getInstance().getReference("todos")

                        todos?.forEach { todo ->
                            dbRef.push().setValue(todo)
                        }

                        resultText.text = "Saved ${todos?.size} todos to Firebase"
                        Toast.makeText(view.context, "Todos saved", Toast.LENGTH_SHORT).show()
                    } else {
                        resultText.text = "Failed: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                    resultText.text = "Error: ${t.message}"
                }
            })
        }

        return view
    }
}
