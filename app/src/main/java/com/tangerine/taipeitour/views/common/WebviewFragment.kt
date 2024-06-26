package com.tangerine.taipeitour.views.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.tangerine.core.ultis.setOnSingleClickListener
import com.tangerine.taipeitour.databinding.FragmentWebviewBinding
import com.tangerine.taipeitour.views.base.BaseFragment


class WebviewFragment : BaseFragment<FragmentWebviewBinding>() {
    companion object {
        private val URL = "URL"

        fun setArguments(url: String) = Bundle().apply {
            putString(URL, url)
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
            showLoading(false)
            findNavController().popBackStack()
        }

        arguments?.getString(URL)?.let {
            showLoading(true)

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

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    showLoading(false)
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    Toast.makeText(
                        activity,
                        "Your Internet Connection May not be active Or " + error?.description,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            binding.webviewAttraction.loadUrl(it)
        }
    }
}