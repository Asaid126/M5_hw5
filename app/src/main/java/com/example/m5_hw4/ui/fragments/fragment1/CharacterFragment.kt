package com.example.m5_hw4.ui.fragments.fragment1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m5_hw4.R
import com.example.m5_hw4.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_character) {

    private val binding by viewBinding(FragmentCharacterBinding::bind)
    private val viewModel: CharacterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupObserve()

        binding.pgCharacter.visibility = View.VISIBLE
        viewModel.getAllCharacters()
    }

    private fun initialize() {
        val characterAdapter = CharacterAdapter { characterId ->
            val bundle = Bundle().apply {
                putInt("character_id", characterId)
            }
            findNavController().navigate(R.id.action_characterFragment_to_datailFragment, bundle)
        }

        binding.rvCharacter.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObserve() {
        viewModel.characters.observe(viewLifecycleOwner) { response ->
            response.results?.let { results ->
                (binding.rvCharacter.adapter as CharacterAdapter).submitList(results)

                with(binding) {
                    rvCharacter.visibility = View.VISIBLE
                    pgCharacter.visibility = View.GONE
                }
            }
        }
        viewModel.error.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            binding.pgCharacter.visibility = View.GONE

        }
    }}
