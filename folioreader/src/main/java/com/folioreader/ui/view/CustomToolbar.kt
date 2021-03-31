package com.folioreader.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageSwitcher
import android.widget.ImageView
import com.folioreader.R

class CustomToolbar @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) : FrameLayout(context, attributeSet), View.OnClickListener {
    private val _back get() = findViewById<ImageView>(R.id.btnBack)
    private val _content get() = findViewById<ImageView>(R.id.btnContent)
    private val _bookmark get() = findViewById<ImageSwitcher>(R.id.switcherBookmark)
    private val _config get() = findViewById<ImageView>(R.id.btnConfig)
    private val _search get() = findViewById<ImageView>(R.id.btnSearch)

    private var _callback: (Int) -> Unit = {}

    init {
        inflate(context, R.layout.custom_toolbar, this)
        _back.setOnClickListener(this)
        _content.setOnClickListener(this)
        _bookmark.setOnClickListener(this)
        _config.setOnClickListener(this)
        _search.setOnClickListener(this)
    }

    fun setButtonsClickListener(callback: (viewId: Int) -> Unit) {
        _callback = callback
    }

    fun showBookmark() {
        if (_bookmark.nextView.id != R.id.fill) _bookmark.showNext()
    }

    fun hideBookmark() {
        if (_bookmark.nextView.id != R.id.empty) _bookmark.showPrevious()
    }

    override fun onClick(view: View) {
        _callback(view.id)
    }
}