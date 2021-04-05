package com.folioreader.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageSwitcher
import android.widget.ImageView
import androidx.constraintlayout.widget.Group
import com.folioreader.R

class CustomToolbar @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) : FrameLayout(context, attributeSet), View.OnClickListener {
    private val _back get() = findViewById<ImageView>(R.id.btnBack)
    private val _content get() = findViewById<ImageView>(R.id.btnContent)
    private val _bookmark get() = findViewById<ImageSwitcher>(R.id.switcherBookmark)
    private val _config get() = findViewById<ImageView>(R.id.btnConfig)
    private val _search get() = findViewById<ImageView>(R.id.btnSearch)

    private val _searchGroup get() = findViewById<Group>(R.id.searchGroup)
    private val _defaultGroup get() = findViewById<Group>(R.id.defaultGroup)

    private var _mode: Mode = Mode.DEFAULT
        set(value) {
            when(value) {
                Mode.DEFAULT -> {
                    _searchGroup.visibility = GONE
                    _defaultGroup.visibility = VISIBLE
                }
                Mode.SEARCH -> {
                    _searchGroup.visibility = VISIBLE
                    _defaultGroup.visibility = GONE
                }
                Mode.ONLY_BACK -> {
                    _searchGroup.visibility = GONE
                    _defaultGroup.visibility = GONE
                }
            }
            field = value
        }
    private var _callback: (Int) -> Unit = {}

    val searchView get() = findViewById<FolioSearchView>(R.id.search)

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

    fun setMode(mode: Mode) {
        _mode = mode
    }

    override fun onClick(view: View) {
        _callback(view.id)
    }

    companion object {
        enum class Mode {
            DEFAULT, SEARCH, ONLY_BACK
        }
    }
}