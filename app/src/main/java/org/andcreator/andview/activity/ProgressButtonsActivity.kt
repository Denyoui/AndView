package org.andcreator.andview.activity

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import kotlinx.android.synthetic.main.activity_progress_buttons.*
import org.andcreator.andview.R
import org.andcreator.andview.uilt.SetTheme
import org.andcreator.andview.view.ProgressButtonView

class ProgressButtonsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SetTheme.setTheme(this)
        window.statusBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        setContentView(R.layout.activity_progress_buttons)
        progressView.color = ContextCompat.getColor(this, R.color.downloadButton)
        progressView.borderWidth = 5f
        progressView.radius = 20f
//        progressView.progress = 0.6f
        progressView.textSize = 50f

        progressView.onStart = {
            startLoadingThread()
        }
        progressView.onPause = {
            loadingThread?.state = ProgressButtonView.ProgressState.Paused
        }
        progressView.onResume = {
            loadingThread?.state = ProgressButtonView.ProgressState.Loading
        }
        progressView.onOpen = {
            progressView?.state = ProgressButtonView.ProgressState.Loading
            startLoadingThread()
        }

        initOtherView()
    }

    fun initOtherView() {
        progressView2.radius = 0f
        progressView2.borderWidth = 1f
        progressView2.textSize = 40f
        progressView2.progress = 0.7f
        progressView2.state = ProgressButtonView.ProgressState.Paused
        progressView2.color = ContextCompat.getColor(this,R.color.downloadButton2)

        progressView3.radius = 100f
        progressView3.borderWidth = 5f
        progressView3.textSize = 40f
        progressView3.progress = 0.49f
        progressView3.state = ProgressButtonView.ProgressState.Loading
        progressView3.color = ContextCompat.getColor(this,R.color.downloadButton3)

        progressView4.radius = 30f
        progressView4.borderWidth = 2f
        progressView4.color = ContextCompat.getColor(this,R.color.downloadButton4)
        progressView4.idleText = "冒泡"
        progressView4.pausedText = "潜水"

        progressView5.radius = 5f
        progressView5.borderWidth = 1f
        progressView5.textSize = 20f
        progressView5.progress = 1f
        progressView5.state = ProgressButtonView.ProgressState.Finished
        progressView5.color = ContextCompat.getColor(this,R.color.downloadButton5)

        progressView6.radius = 5f
        progressView6.borderWidth = 1f
        progressView6.textSize = 20f
        progressView6.color = ContextCompat.getColor(this,R.color.downloadButton6)

    }

    var loadingThread: LoadingThread? = null

    private fun startLoadingThread() {
        loadingThread = LoadingThread()
        loadingThread?.onProgress = {
            progressView.progress = it
            if (it >= 1) {
                progressView?.state = ProgressButtonView.ProgressState.Finished
            }
        }
        loadingThread?.start()
    }

    inner class LoadingThread : Thread() {
        var state = ProgressButtonView.ProgressState.Loading
        var onProgress: ((Float) -> Unit)? = null
        var progress = 0f

        override fun run() {
            super.run()
            while (state == ProgressButtonView.ProgressState.Loading || state == ProgressButtonView.ProgressState.Paused) {
                if (progress > 1) {
                    break
                }
                if (state != ProgressButtonView.ProgressState.Paused) {
                    progress += Math.random().toFloat() * 0.1f
                    runOnUiThread {
                        onProgress?.invoke(Math.min(progress, 1f))
                    }
                }
                Thread.sleep(100)
            }
        }
    }
}
