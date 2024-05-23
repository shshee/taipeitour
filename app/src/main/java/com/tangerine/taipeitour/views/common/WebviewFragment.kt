package com.tangerine.taipeitour.views.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.tangerine.taipeitour.databinding.FragmentWebviewBinding
import com.tangerine.taipeitour.utils.setOnSingleClickListener
import com.tangerine.taipeitour.views.base.BaseFragment


class WebviewFragment : BaseFragment<FragmentWebviewBinding>() {
    companion object {
        private val URL = "URL"

        fun getInstance(url: String) = WebviewFragment().apply {
            arguments = Bundle().apply {
                putString(URL, url)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarHeader.toolbarBack.setOnSingleClickListener {
            goOneBack()
        }

        arguments?.getString(URL)?.let {
            binding.webviewAttraction.settings.run {
                javaScriptEnabled = true
                domStorageEnabled = true
            }

            //Handle redirection
            binding.webviewAttraction.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    request?.url?.toString()?.let { it1 -> view?.loadUrl(it1) }
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }

            binding.webviewAttraction.loadUrl(it)
        }
    }
}