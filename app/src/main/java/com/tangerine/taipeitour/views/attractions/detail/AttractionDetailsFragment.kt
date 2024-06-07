package com.tangerine.taipeitour.views.attractions.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tangerine.core.ultis.setOnSingleClickListener
import com.tangerine.taipeitour.databinding.FragmentAttractionDetailsBinding
import com.tangerine.taipeitour.views.base.BaseFragment
import com.tangerine.taipeitour.views.attractions.AttractionsViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AttractionDetailsFragment : BaseFragment<FragmentAttractionDetailsBinding>() {
    companion object {
        private val ATTRACTION_INDEX = "ATTRACTION_INDEX"

        fun getInstance(index: Int) = AttractionDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(ATTRACTION_INDEX, index)
            }
        }
    }

    private val aVModel: AttractionsViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttractionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarHeader.btnBack.setOnSingleClickListener {
            goOneBack()
        }

        onSpillData()
    }

    private fun onSpillData() {
        val index = arguments?.getInt(ATTRACTION_INDEX) ?: return

//        aVModel.attractions.value?.get(index)?.run {
//            binding.textViewTitle.text = name.fromHtml()
//            context?.let {
//                Glide.with(it).load(images.firstOrNull()?.src ?: R.mipmap.ic_launcher)
//                    .into(binding.imageViewAttractionFull)
//            }
//
//            binding.toolbarHeader.textViewPageTitle.text = name.fromHtml()
//            binding.textViewLocation.text = address.fromHtml()
//
//            binding.textViewDescription.text = introduction.fromHtml()
//
//            binding.textViewLink.let {
//                it.text = url.fromHtml()
//
//                it.setOnSingleClickListener {
//                    goTo(WebviewFragment.getInstance(url), com.tangerine.core.model.AnimType.SLIDE_RIGHT)
//                }
//            }
//        }
    }
}