package com.example.statesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class StateListAdapter(
    private val states: List<State>,
    private val onItemClick: (State) -> Unit
) : RecyclerView.Adapter<StateListAdapter.StateViewHolder>() {

    inner class StateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvState: TextView = itemView.findViewById(R.id.tvState)
        private val tvPopulation: TextView = itemView.findViewById(R.id.tvPopulation)
        private val imgState: ImageView = itemView.findViewById(R.id.imgState)

        fun bind(state: State) {
            tvState.text = state.name
            tvPopulation.text = "Populasi: ${state.population}"

            // Memuat gambar (bendera) menggunakan Glide
            Glide.with(itemView.context)
                .load(state.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(imgState)

            // Klik item
            itemView.setOnClickListener {
                onItemClick(state)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_state, parent, false)
        return StateViewHolder(view)
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        holder.bind(states[position])
    }

    override fun getItemCount(): Int {
        return states.size
    }
}