package com.tangerine.taipeitour.views.attractions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.color.MaterialColors
import com.tangerine.core.model.AttractionsUiState
import com.tangerine.core.model.AttractionsUpdate
import com.tangerine.core.model.Language
import com.tangerine.core.model.UiState
import com.tangerine.core.source.R
import com.tangerine.taipeitour.databinding.FragmentAttractionsBinding
import com.tangerine.taipeitour.views.attractions.detail.AttractionDetailsFragment
import com.tangerine.taipeitour.views.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class AttractionsFragment : BaseFragment<FragmentAttractionsBinding>() {
    private val attractionsAdapter: AttractionsAdapter by inject()

    private val aVModel: AttractionsViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttractionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        putObservers()
    }

    private fun setUpViews() {
        //Recycler view attractions
        binding.rvAttractions.adapter = attractionsAdapter
        attractionsAdapter.clickListener = {
            findNavController().navigate(
                com.tangerine.taipeitour.R.id.action_attractionsFragment_to_attractionDetailsFragment,
                AttractionDetailsFragment.setArguments(it)
            )
        }

        //Toolbar
        binding.toolbar.let {
            if (activity is AppCompatActivity) (activity as AppCompatActivity).setSupportActionBar(
                it
            )

            //Icon menu
            it.overflowIcon =
                ContextCompat.getDrawable(
                    activity?.applicationContext!!,
                    com.tangerine.taipeitour.R.drawable.ic_translate_12sdp
                )
            it.setBackgroundColor(
                MaterialColors.getColor(
                    binding.root,
                    org.koin.android.R.attr.colorPrimary
                )
            )

            //Title color
            it.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }

    private fun putObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                aVModel.attractionUiState.collect { uiState ->
                    showLoading(false)

                    uiState.data.let {
                        when (uiState.state) {
                            UiState.SUCCESS, UiState.IDLE -> {
                                attractionsAdapter.collection = it.attractionsList

                                //Scroll back to start in new lang was updated
                                when (it.updateType) {
                                    AttractionsUpdate.NEW_LANG.ordinal -> binding.rvAttractions.smoothScrollToPosition(
                                        0
                                    )

                                    else -> {}
                                }

                                //Update title according to selecting lang
                                binding.toolbar.title =
                                    Language.getLanguageFromCode(it.currentLang).appName
                            }

                            UiState.ERROR -> Toast.makeText(
                                requireContext(),
                                uiState.handleError(),
                                Toast.LENGTH_LONG
                            ).show()

                            UiState.LOADING -> showLoading(true)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        attractionsAdapter.clickListener = {}
        super.onDestroyView()
    }
}