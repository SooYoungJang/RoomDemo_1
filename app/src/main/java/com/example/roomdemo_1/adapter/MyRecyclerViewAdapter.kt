package com.example.roomdemo_1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo_1.R
import com.example.roomdemo_1.databinding.ListItemBinding
import com.example.roomdemo_1.db.Subscriber
import com.example.roomdemo_1.generated.callback.OnClickListener

class MyRecyclerViewAdapter(private val clickListener:(Subscriber) ->Unit): RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    private val subscriberList= ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater  = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscriberList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscriberList[position],clickListener)
    }

    fun setList(subscribers: List<Subscriber>) {
        subscriberList.clear()
        subscriberList.addAll(subscribers)
    }

    class MyViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(subscriber: Subscriber,clickListener:(Subscriber) ->Unit) {
            binding.subscriber = subscriber
            binding.listItemLayout.setOnClickListener {
                clickListener(subscriber)
            }
        }
    }

}

