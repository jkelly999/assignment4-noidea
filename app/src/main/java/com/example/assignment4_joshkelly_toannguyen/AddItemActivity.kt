package com.example.assignment4_joshkelly_toannguyen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase

class AddItemActivity : AppCompatActivity() {
    private lateinit var userIdEditText: EditText
    private lateinit var todoIdEditText: EditText
    private lateinit var titleEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        userIdEditText = findViewById(R.id.edit_user_id)
        todoIdEditText = findViewById(R.id.edit_todo_id)
        titleEditText = findViewById(R.id.edit_title)
        submitButton = findViewById(R.id.btn_submit)

        submitButton.setOnClickListener{
            saveTodoToFirebase()
        }
    }
    private fun saveTodoToFirebase(){
        val userId = userIdEditText.text.toString().trim()
        val todoId = todoIdEditText.text.toString().trim()
        val title = titleEditText.text.toString().trim()

        if(userId.isEmpty() || todoId.isEmpty() || title.isEmpty()){
            Toast.makeText(this, "fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val todo = TodoItem(userId.toInt(), todoId.toInt(), title)

        val dbRef = FirebaseDatabase.getInstance().getReference("todos")
        dbRef.push().setValue(todo).addOnSuccessListener {
            Toast.makeText(this, "Todo saved", Toast.LENGTH_SHORT).show()
            Log.d("FIREBASE_DEBUG", "Todo saved successfully.")
            finish()
        }.addOnFailureListener{
            Log.e("FIREBASE_DEBUG", "Save failed")
            Toast.makeText(this, "failed to save", Toast.LENGTH_SHORT).show()
        }


    }

}