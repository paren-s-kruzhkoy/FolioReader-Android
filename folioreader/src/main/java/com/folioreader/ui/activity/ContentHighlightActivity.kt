package com.folioreader.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.folioreader.Config
import com.folioreader.Constants
import com.folioreader.FolioReader
import com.folioreader.R
import com.folioreader.ui.fragment.HighlightFragment
import com.folioreader.ui.fragment.TableOfContentFragment
import com.folioreader.ui.view.CustomToolbar
import com.folioreader.util.AppUtil.Companion.getSavedConfig
import com.google.android.material.tabs.TabLayout
import org.readium.r2.shared.Publication

class ContentHighlightActivity: AppCompatActivity() {
    private var mIsNightMode = false
    private var mConfig: Config? = null
    private var publication: Publication? = null
    private lateinit var customToolbar: CustomToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_highlight)

        publication = intent.getSerializableExtra(Constants.PUBLICATION) as Publication
        mConfig = getSavedConfig(this)
        mIsNightMode = mConfig != null && mConfig!!.isNightMode
        initViews()
    }

    private fun initViews() {
        customToolbar = findViewById(R.id.customToolbar)
        customToolbar.apply {
            setMode(CustomToolbar.Companion.Mode.ONLY_BACK)
            setButtonsClickListener {
            if (it == R.id.btnBack) finish()
        }
        }
        val viewPager = findViewById<ViewPager>(R.id.viewPager).apply {
            adapter = ViewPagerAdapter()
        }
        findViewById<TabLayout>(R.id.tabLayout).setupWithViewPager(viewPager)
    }

    private inner class ViewPagerAdapter: FragmentStatePagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int = 2

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> TableOfContentFragment.newInstance(
                        publication,
                        intent.getStringExtra(Constants.CHAPTER_SELECTED),
                        intent.getStringExtra(Constants.BOOK_TITLE)
                )
                1 -> HighlightFragment.newInstance(
                        intent.getStringExtra(FolioReader.EXTRA_BOOK_ID),
                        intent.getStringExtra(Constants.BOOK_TITLE)
                )
                else -> throw IllegalStateException()
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when(position) {
                0 -> "Оглавление"
                1 -> "Закладки"
                else -> throw IllegalStateException()
            }
        }
    }
}