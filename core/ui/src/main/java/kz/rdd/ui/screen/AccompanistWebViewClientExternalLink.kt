package kz.rdd.core.ui.screen

import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.google.accompanist.web.AccompanistWebViewClient

class AccompanistWebViewClientExternalLink : AccompanistWebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, request?.url)
        view?.context?.startActivity(intent)
        return true
    }
}