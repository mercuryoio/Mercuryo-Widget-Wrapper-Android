package com.example.webviewwrapper

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import android.app.Activity
import android.graphics.Bitmap
import android.webkit.WebViewClient
import android.webkit.WebChromeClient.FileChooserParams
import android.os.Build

open class MercuryoWebViewActivity(private val viewID: Int) : Activity() {
    /** Called when the activity is first created.  */
    private var mUploadMessage: ValueCallback<Uri>? = null
    var uploadMessage: ValueCallback<Array<Uri>>? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myWebView: WebView = findViewById(viewID)
        myWebView.settings.domStorageEnabled = true
        myWebView.settings.javaScriptCanOpenWindowsAutomatically = true
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.allowFileAccess = true
        myWebView.settings.allowContentAccess = true
        myWebView.loadUrl("https://my.mrcr.io/")
        myWebView.webViewClient = myWebClient()

        myWebView.webChromeClient = object : WebChromeClient() {
            // For 3.0+ Devices (Start)
            // onActivityResult attached before constructor
            protected fun openFileChooser(uploadMsg: ValueCallback<Uri>?, acceptType: String?) {
                mUploadMessage = uploadMsg
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(i, "File Browser"),
                    FILECHOOSER_RESULTCODE
                )
            }

            // For Lollipop 5.0+ Devices
            override fun onShowFileChooser(
                mWebView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                if (uploadMessage != null) {
                    uploadMessage!!.onReceiveValue(null)
                    uploadMessage = null
                }
                uploadMessage = filePathCallback
                val intent = fileChooserParams.createIntent()
                try {
                    startActivityForResult(intent, REQUEST_SELECT_FILE)
                } catch (e: ActivityNotFoundException) {
                    uploadMessage = null
                    Toast.makeText(
                        applicationContext,
                        "Cannot Open File Chooser",
                        Toast.LENGTH_LONG
                    ).show()
                    return false
                }
                return true
            }

            //For Android 4.1 only
            protected fun openFileChooser(
                uploadMsg: ValueCallback<Uri>?,
                acceptType: String?,
                capture: String?
            ) {
                mUploadMessage = uploadMsg
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(intent, "File Browser"),
                    FILECHOOSER_RESULTCODE
                )
            }

            protected fun openFileChooser(uploadMsg: ValueCallback<Uri>?) {
                mUploadMessage = uploadMsg
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(i, "File Chooser"),
                    FILECHOOSER_RESULTCODE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null) return
                uploadMessage!!.onReceiveValue(FileChooserParams.parseResult(resultCode, intent))
                uploadMessage = null
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            val result =
                if (intent == null || resultCode != MercuryoWebViewActivity.FILECHOOSER_RESULTCODE) null else intent.data
            mUploadMessage!!.onReceiveValue(result)
            mUploadMessage = null
        } else Toast.makeText(
            applicationContext,
            "Failed to Upload Image",
            Toast.LENGTH_LONG
        ).show()
    }

    inner class myWebClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    companion object {
        const val REQUEST_SELECT_FILE = 100
        private const val FILECHOOSER_RESULTCODE = 1
    }
}