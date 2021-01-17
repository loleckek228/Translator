package com.example.translator.presentation.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.translator.R
import com.example.translator.utils.network.isNetworkAvailable
import com.example.translator.utils.ui.AlertDialogFragment
import kotlinx.android.synthetic.main.activity_description.*

class DescriptionActivity : AppCompatActivity(R.layout.activity_description) {
    companion object {
        private const val DIALOG_FRAGMENT_TAG =
            "com.example.translator.presentation.ui.description.DescriptionActivity.DIALOG_FRAGMENT_TAG"
        private const val WORD_EXTRA =
            "com.example.translator.presentation.ui.description.DescriptionActivity.WORD_EXTRA"

        private const val DESCRIPTION_EXTRA =
            "com.example.translator.presentation.ui.description.DescriptionActivity.DESCRIPTION_EXTRA"

        private const val URL_EXTRA =
            "com.example.translator.presentation.ui.description.DescriptionActivity.URL_EXTRA"

        fun getIntent(
            context: Context,
            word: String,
            description: String?,
            url: String?
        ): Intent = Intent(context, DescriptionActivity::class.java).apply {
            putExtra(WORD_EXTRA, word)
            putExtra(DESCRIPTION_EXTRA, description)
            putExtra(URL_EXTRA, url)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionbarHomeButtonAsUp()

        description_screen_swipe_refresh_layout.setOnRefreshListener {
            startLoadingOrShowError()
        }

        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setData() {
        val bundle = intent.extras

        activity_description_header.text = bundle?.getString(WORD_EXTRA)
        activity_description_textview.text = bundle?.getString(DESCRIPTION_EXTRA)

        val imageLink = bundle?.getString(URL_EXTRA)

        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            useGlideToLoadImage(activity_description_imageview, imageLink)
        }
    }

    private fun startLoadingOrShowError() {
        if (isNetworkAvailable(applicationContext)) {
            setData()
        } else {
            AlertDialogFragment.newInstance(
                getString(R.string.dialog_title_device_is_offline),
                getString(R.string.dialog_message_device_is_offline)
            ).show(
                supportFragmentManager,
                DIALOG_FRAGMENT_TAG
            )

            stopRefreshAnimationIfNeeded()
        }
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (description_screen_swipe_refresh_layout.isRefreshing) {
            description_screen_swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun useGlideToLoadImage(imageView: ImageView, imageLink: String) {
        Glide.with(imageView)
            .load("https:$imageLink")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_error_vector)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimationIfNeeded()
                    return false
                }
            }).apply(
                RequestOptions
                    .placeholderOf(R.drawable.ic_no_photo_vector)
                    .centerCrop()
            ).into(imageView)
    }
}