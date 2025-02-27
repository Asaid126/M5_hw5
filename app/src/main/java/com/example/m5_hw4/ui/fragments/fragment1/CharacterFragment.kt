package com.example.m5_hw4.ui.fragments.fragment1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m5_hw4.R
import com.example.m5_hw4.data.model.characters.Character
import com.example.m5_hw4.databinding.FragmentCharacterBinding
import com.example.m5_hw4.Resource
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_character) {

    private val binding by viewBinding(FragmentCharacterBinding::bind)
    private val viewModel: CharacterViewModel by viewModels()
    private lateinit var charactersAdapter: CharacterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setupObserve()
    }

    private fun initialize() {
        charactersAdapter = CharacterAdapter { model -> onCharacterClick(model) }
        binding.rvCharacter.apply {
            adapter = charactersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onCharacterClick(model: Character) {
        val action =
            CharacterFragmentDirections.actionCharacterFragmentToDatailFragment(model.id)
        findNavController().navigate(action)
    }

    private fun setupObserve() {
        viewModel.getAllCharacters().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> viewLifecycleOwner.lifecycleScope.launch {
                    binding.pgCharacter.visibility = View.GONE
                    charactersAdapter.submitData(resource.data)
                }
                is Resource.Error -> {
                    binding.pgCharacter.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.pgCharacter.visibility = View.VISIBLE
                }
            }
        }
    }
}