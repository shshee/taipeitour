package com.tangerine.taipeitour.views.attractions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.color.MaterialColors
import com.tangerine.taipeitour.views.attractions.detail.AttractionDetailsFragment
import com.tangerine.taipeitour.R
import com.tangerine.taipeitour.databinding.FragmentAttractionsBinding
import com.tangerine.taipeitour.models.AnimType
import com.tangerine.taipeitour.views.base.BaseFragment
import com.tangerine.taipeitour.views.main.MainActivityViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class AttractionsFragment : BaseFragment<FragmentAttractionsBinding>() {
    private val attractionsAdapter: AttractionsAdapter by inject()

    private val aVModel: MainActivityViewModel by activityViewModel()

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
            goTo(AttractionDetailsFragment.getInstance(it), AnimType.FADE)
        }

        //Toolbar
        binding.toolbar.let {
            if(activity is AppCompatActivity) (activity as AppCompatActivity).setSupportActionBar(it)

            //Icon menu
            it.overflowIcon =
                ContextCompat.getDrawable(
                    activity?.applicationContext!!,
                    R.drawable.ic_translate_12sdp
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
        //On changed languages
        aVModel.currentLang.observe(this as LifecycleOwner) {
            showLoading(true)
            aVModel.getAttractions(it.code, false)
        }

        //On updated attractions
        aVModel.attractions.observe(this as LifecycleOwner) {
            attractionsAdapter.collection = it.orEmpty()
            showLoading(false)
        }

        //On language changed
        aVModel.attractions.observe(this as LifecycleOwner) {
            binding.toolbar.title = aVModel.currentLang.value?.appName
        }
    }

    override fun onDestroyView() {
        attractionsAdapter.clickListener = {}
        super.onDestroyView()
    }
}