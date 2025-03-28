
package com.example.assignment4_joshkelly_toannguyen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class DisplayFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(context, "DisplayFragment loaded", Toast.LENGTH_SHORT).show()
        val view = inflater.inflate(R.layout.fragment_display, container, false)

        val displayBtn = view.findViewById<Button>(R.id.btn_display)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_add)

        val recyclerView = view.findViewById<RecyclerView>(R.id.todo_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(view.context)


        val todoList = mutableListOf<TodoItem>()
        val keyList = mutableListOf<String>()

        val adapter = TodoAdapter(todoList, keyList,
            onClick = { todo, key ->
            val intent = Intent(view.context, DetailActivity::class.java)
            intent.putExtra("title", todo.title)
            intent.putExtra("userId", todo.userId)
            intent.putExtra("id",todo.id)
            intent.putExtra("key", key)
                startActivity(intent)
        },
            onLongClick = { key ->
                val dbRef = FirebaseDatabase.getInstance().getReference("todos").child(key)
                dbRef.removeValue().addOnSuccessListener {
                    Toast.makeText(view.context, "Todo deleted", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(view.context, "Failed to delete", Toast.LENGTH_SHORT).show()
                }
                true
            }
        )

        recyclerView.adapter = adapter
        Log.d("DISPLAY_DEBUG", "Adapter set with ${todoList.size} items")

        displayBtn.setOnClickListener {
            Log.d("DISPLAY_DEBUG", "Display button clicked")
            val dbRef = FirebaseDatabase.getInstance().getReference("todos")

            dbRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot){
                    Log.d("DISPLAY_DEBUG", "snapshot value: ${snapshot.value}")
                    todoList.clear()
                    keyList.clear()
                    for (child in snapshot.children){
                        val todo = child.getValue(TodoItem::class.java)
                        if(todo != null){
                            todoList.add(todo)
                            keyList.add(child.key ?:"")
                        }
                    }
                    Log.d("DISPLAY_DEBUG", "notifyDataSetChanged called, list size: ${todoList.size}")
                    adapter.notifyDataSetChanged()
                    Toast.makeText(view.context, "Items loaded: ${todoList.size}", Toast.LENGTH_SHORT).show()

                }
                override fun onCancelled(error: DatabaseError){
                    Toast.makeText(view.context, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            })
        }

        fab.setOnClickListener{
            val intent = Intent(view.context, AddItemActivity::class.java)
            startActivity(intent)
        }
        return view
    }

}