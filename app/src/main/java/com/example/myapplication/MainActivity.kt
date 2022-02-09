package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat.startActivityForResult

import android.widget.Toast

import android.content.ActivityNotFoundException
import android.webkit.WebChromeClient.FileChooserParams
import android.os.Build
import com.example.webviewwrapper.MercuryoWebViewActivity

open class MainActivity : MercuryoWebViewActivity(R.id.webview) {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}