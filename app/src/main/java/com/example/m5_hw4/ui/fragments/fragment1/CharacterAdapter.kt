package com.example.m5_hw4.ui.fragments.fragment1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.m5_hw4.R
import com.example.m5_hw4.data.model.characters.Character
import com.example.m5_hw4.databinding.ItemBinding

class CharacterAdapter(
    val onItemClick: (Character) -> Unit
) : PagingDataAdapter<Character, CharacterAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(character: Character) = with(binding) {
            characterName.text = character.name
            characterLocation.text = character.location.name
            characterFirstSeen.text = character.origin.name
            characterStatus.text = character.status
            imgCharacter.load(character.image) {
                crossfade(true)
            }
            colorIndicator.setImageResource(
                when {
                    character.status?.contains("Dead") == true -> R.drawable.ic_circle_red
                    character.status?.contains("Alive") == true -> R.drawable.ic_circle_green
                    else -> R.drawable.ic_circle_grey
                }
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
        holder.itemView.setOnClickListener {
            getItem(position)?.let { character -> onItemClick(character) }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem:Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }
}