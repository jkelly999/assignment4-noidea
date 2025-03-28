package com.example.assignment4_joshkelly_toannguyen

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : AppCompatActivity() {

    private lateinit var todoKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        val titleText = findViewById<TextView>(R.id.detail_title)
        val infoText = findViewById<TextView>(R.id.detail_info)
        val deleteButton = findViewById<Button>(R.id.btn_delete)

        val title = intent.getStringExtra("title")
        val userId = intent.getIntExtra("userId", 0)
        val id = intent.getIntExtra("id", 0)
        todoKey = intent.getStringExtra("key") ?: ""

        titleText.text = title
        infoText.text = "UserId: $userId, ID: $id"

        deleteButton.setOnClickListener{
            val ref = FirebaseDatabase.getInstance().getReference("todos").child(todoKey)
            ref.removeValue().addOnSuccessListener {
                Toast.makeText(this, "Todo deleted", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener{
                Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show()
            }

        }
    }
}