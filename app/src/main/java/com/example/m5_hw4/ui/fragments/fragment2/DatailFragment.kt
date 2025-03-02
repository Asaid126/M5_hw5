package com.example.m5_hw4.ui.fragments.fragment2

import android.annotation.SuppressLint

import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.m5_hw4.BaseFragment
import com.example.m5_hw4.R
import com.example.m5_hw4.databinding.FragmentDatailBinding
import com.example.m5_hw4.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DatailFragment :
    BaseFragment<FragmentDatailBinding, DatailViewModel>(
        FragmentDatailBinding::inflate
    ) {

//    private val binding by viewBinding(FragmentDatailBinding::bind)
    override val viewModel: DatailViewModel by viewModels()
    private val args: DatailFragmentArgs by navArgs()

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        observeViewModel()
//        setupExpandableLayouts()
//    }

    @SuppressLint("SetTextI18n")
    override fun setupObservers() = with(binding) {
        viewModel.getCharacterById(args.id).observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success-> {
                    characterName.text = resource.data.name
                    characterStatus.text = resource.data.status
                    characterLocation.text = resource.data.location.name
                    characterGender.text = resource.data.gender

                    Glide.with(this@DatailFragment)
                        .load(resource.data.image)
                        .into(characterImage)

                    expandable.secondLayout.findViewById<TextView>(R.id.tv_character_info)?.text =
                        "ID: ${resource.data.id}\nSpecies: ${resource.data.species}\nType: ${resource.data.type}"

                    expandable2.secondLayout.findViewById<TextView>(R.id.tv_origin)?.text =
                        "Origin: ${resource.data.origin.name}"

                    expandable3.secondLayout.findViewById<TextView>(R.id.tv_first_seen)?.text =
                        "First seen in: ${resource.data.episode.firstOrNull() ?: "Unknown"}"

                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()

                }
                is Resource.Loading -> {}
            }
        }
    }

    override fun setupViews() {
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