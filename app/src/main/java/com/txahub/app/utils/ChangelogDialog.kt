package com.txahub.app.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.txahub.app.R

class ChangelogDialog(context: Context) : Dialog(context) {
    
    private lateinit var webViewChangelog: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnOk: Button
    private lateinit var tvTitle: TextView
    private lateinit var tvVersion: TextView
    private lateinit var updateChecker: UpdateChecker
    private var versionName: String = ""
    private var onDismissListener: (() -> Unit)? = null
    
    fun setVersionName(versionName: String) {
        this.versionName = versionName
    }
    
    fun setOnDismissListener(listener: () -> Unit) {
        this.onDismissListener = listener
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_changelog)
        
        // Setup window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        
        // Setup views
        webViewChangelog = findViewById(R.id.webViewChangelog)!!
        progressBar = findViewById(R.id.progressBar)!!
        btnOk = findViewById(R.id.btnOk)!!
        tvTitle = findViewById(R.id.tvTitle)!!
        tvVersion = findViewById(R.id.tvVersion)!!
        
        updateChecker = UpdateChecker(context)
        
        // Set version
        tvVersion.text = versionName
        
        // Setup WebView
        webViewChangelog.settings.javaScriptEnabled = true
        webViewChangelog.settings.domStorageEnabled = false
        webViewChangelog.settings.loadsImagesAutomatically = true
        webViewChangelog.isVerticalScrollBarEnabled = true
        webViewChangelog.isHorizontalScrollBarEnabled = false
        
        // Load changelog
        loadChangelog()
        
        // Setup OK button
        btnOk.setOnClickListener {
            dismiss()
        }
        
        // Set cancelable
        setCancelable(true)
        setCanceledOnTouchOutside(true)
    }
    
    private fun loadChangelog() {
        progressBar.visibility = View.VISIBLE
        webViewChangelog.visibility = View.GONE
        
        // Lấy changelog cho version hiện tại
        updateChecker.getAllChangelogs { changelogs ->
            context.mainExecutor.execute {
                progressBar.visibility = View.GONE
                webViewChangelog.visibility = View.VISIBLE
                
                // Tìm changelog cho version hiện tại
                val currentChangelog = changelogs.find { it.versionName == versionName }
                
                val displayChangelog = if (currentChangelog == null || currentChangelog.changelog.isBlank()) {
                    """
                    <div style="text-align: center; padding: 20px;">
                        <p style="font-size: 14px; color: #999;">Chưa có thông tin changelog cho phiên bản này.</p>
                    </div>
                    """.trimIndent()
                } else {
                    currentChangelog.changelog
                }
                
                val htmlContent = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <style>
                            body {
                                font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
                                padding: 16px;
                                line-height: 1.6;
                                color: #333;
                                margin: 0;
                                background-color: #ffffff;
                            }
                            h2 { 
                                color: #1976D2; 
                                margin-top: 16px; 
                                margin-bottom: 8px; 
                                font-size: 18px;
                            }
                            h3 {
                                color: #424242;
                                margin-top: 12px;
                                margin-bottom: 6px;
                                font-size: 16px;
                            }
                            ul, ol { 
                                padding-left: 24px; 
                                margin: 8px 0; 
                            }
                            li { 
                                margin: 4px 0; 
                                line-height: 1.5;
                            }
                            p { 
                                margin: 8px 0; 
                            }
                            code {
                                background-color: #f5f5f5;
                                padding: 2px 6px;
                                border-radius: 3px;
                                font-family: 'Courier New', monospace;
                                font-size: 14px;
                            }
                        </style>
                    </head>
                    <body>
                        $displayChangelog
                    </body>
                    </html>
                """.trimIndent()
                
                webViewChangelog.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
            }
        }
    }
    
    override fun dismiss() {
        super.dismiss()
        onDismissListener?.invoke()
    }
}

