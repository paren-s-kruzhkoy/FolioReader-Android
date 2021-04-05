package com.folioreader.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.TextView
import com.folioreader.R

private const val PAGE_COUNT_SPLITTER = " из "

class CustomHorizontalSeekBarView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null): FrameLayout(context, attributeSet), SeekBar.OnSeekBarChangeListener{
    private val _seekBar get() = findViewById<SeekBar>(R.id.seekBar)
    private val _pageCount get() = findViewById<TextView>(R.id.pageCount)

    var currentPage = 1
        set(value) {
            changePageCount(value, totalCount)
            field = value
        }

    var totalCount = 1
        set(value) {
            changePageCount(currentPage, value)
            field = value
        }

    private var _callback: (Int) -> Unit = {}

    init {
        inflate(context, R.layout.custom_horizontal_seekbar, this)
        _seekBar.setOnSeekBarChangeListener(this)
    }

    private fun changePageCount(cur: Int, total: Int) {
        _seekBar.apply {
            max = total
            progress = cur
        }
        _pageCount.text = "$cur$PAGE_COUNT_SPLITTER$total"
    }

    override fun onProgressChanged(s: SeekBar, progress: Int, p2: Boolean) {
        changePageCount(progress, totalCount)
        _callback(progress)
    }

    override fun onStartTrackingTouch(s: SeekBar) {
    }

    override fun onStopTrackingTouch(s: SeekBar) {
    }

    fun setGoToPageCallback(callback: (Int) -> Unit) {
        _callback = callback
    }
}