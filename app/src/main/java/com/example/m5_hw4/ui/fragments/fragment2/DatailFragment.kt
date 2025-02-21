package com.example.m5_hw4.ui.fragments.fragment2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.m5_hw4.R
import com.example.m5_hw4.databinding.FragmentDatailBinding
import dev.androidbroadcast.vbpd.viewBinding


class DatailFragment : Fragment(R.layout.fragment_datail) {

    private val binding by viewBinding(FragmentDatailBinding::bind)
    private val viewModel: DatailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterId = arguments?.getInt("character_id") ?: return
        viewModel.getCharacterById(characterId)

        observeViewModel()

        setupExpandableLayouts()

    }

    private fun observeViewModel() {
        viewModel.character.observe(viewLifecycleOwner) { character ->
            binding.characterName.text = character.name
            binding.characterStatus.text = character.status
            binding.characterLocation.text = character.location.name
            binding.characterGender.text = character.gender

            Glide.with(this)
                .load(character.image)
                .into(binding.characterImage)

            binding.expandable.secondLayout.findViewById<TextView>(R.id.tv_character_info)?.text =
                "ID: ${character.id}\nSpecies: ${character.species}\nType: ${character.type}"

            binding.expandable2.secondLayout.findViewById<TextView>(R.id.tv_origin)?.text =
                "Origin: ${character.origin.name}"

            binding.expandable3.secondLayout.findViewById<TextView>(R.id.tv_first_seen)?.text =
                "First seen in: ${character.episode.firstOrNull() ?: "Unknown"}"
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupExpandableLayouts() {
        val expandables = listOf(
            binding.expandable,
            binding.expandable2,
            binding.expandable3
        )

        expandables.forEach { expandable ->
            expandable.parentLayout.setOnClickListener {
                if (expandable.isExpanded) {
                    expandable.collapse()
                } else {
                    expandable.expand()
                }
            }
        }
    }
}