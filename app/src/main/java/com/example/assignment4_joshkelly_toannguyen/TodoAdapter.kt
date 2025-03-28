package com.example.assignment4_joshkelly_toannguyen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val todoList: MutableList<TodoItem>,
    private val keys: MutableList<String>,
    private val onClick: (TodoItem, String) -> Unit,
    private val onLongClick: (String) -> Unit

) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleText: TextView = itemView.findViewById(R.id.text_title)
        val infoText: TextView = itemView.findViewById(R.id.text_info)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int){
        val todo = todoList[position]
        val key = keys[position]

        holder.titleText.text = todo.title
        holder.infoText.text = "UserId : ${todo.userId} | ID: ${todo.id}"


       holder.itemView.setOnClickListener{
           onClick(todo, key)
       }

        holder.itemView.setOnClickListener{
            onLongClick(key)
            true
        }


    }

    override fun getItemCount(): Int = todoList.size








}
