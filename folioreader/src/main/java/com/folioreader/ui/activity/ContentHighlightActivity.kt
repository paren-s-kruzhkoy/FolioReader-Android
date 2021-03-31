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
        //        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }
        publication = intent.getSerializableExtra(Constants.PUBLICATION) as Publication
        mConfig = getSavedConfig(this)
        mIsNightMode = mConfig != null && mConfig!!.isNightMode
        initViews()
    }

    private fun initViews() {
        customToolbar = findViewById(R.id.customToolbar)
        customToolbar.setButtonsClickListener {
            if (it == R.id.btnBack) finish()
        }
        val viewPager = findViewById<ViewPager>(R.id.viewPager).apply {
            adapter = ViewPagerAdapter()
        }
        findViewById<TabLayout>(R.id.tabLayout).setupWithViewPager(viewPager)

//        UiUtil.setColorIntToDrawable(mConfig.getThemeColor(), ((ImageView) findViewById(R.id.btn_close)).getDrawable());
//        findViewById(R.id.layout_content_highlights).setBackgroundDrawable(UiUtil.getShapeDrawable(mConfig.getThemeColor()));
//
//        if (mIsNightMode) {
//            findViewById(R.id.toolbar).setBackgroundColor(Color.BLACK);
//            findViewById(R.id.btn_contents).setBackgroundDrawable(UiUtil.createStateDrawable(mConfig.getThemeColor(), ContextCompat.getColor(this, R.color.black)));
//            findViewById(R.id.btn_highlights).setBackgroundDrawable(UiUtil.createStateDrawable(mConfig.getThemeColor(), ContextCompat.getColor(this, R.color.black)));
//            ((TextView) findViewById(R.id.btn_contents)).setTextColor(UiUtil.getColorList(ContextCompat.getColor(this, R.color.black), mConfig.getThemeColor()));
//            ((TextView) findViewById(R.id.btn_highlights)).setTextColor(UiUtil.getColorList(ContextCompat.getColor(this, R.color.black), mConfig.getThemeColor()));
//
//        } else {
//            ((TextView) findViewById(R.id.btn_contents)).setTextColor(UiUtil.getColorList(ContextCompat.getColor(this, R.color.white), mConfig.getThemeColor()));
//            ((TextView) findViewById(R.id.btn_highlights)).setTextColor(UiUtil.getColorList(ContextCompat.getColor(this, R.color.white), mConfig.getThemeColor()));
//            findViewById(R.id.btn_contents).setBackgroundDrawable(UiUtil.createStateDrawable(mConfig.getThemeColor(), ContextCompat.getColor(this, R.color.white)));
//            findViewById(R.id.btn_highlights).setBackgroundDrawable(UiUtil.createStateDrawable(mConfig.getThemeColor(), ContextCompat.getColor(this, R.color.white)));
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            int color;
//            if (mIsNightMode) {
//                color = ContextCompat.getColor(this, R.color.black);
//            } else {
//                int[] attrs = {android.R.attr.navigationBarColor};
//                TypedArray typedArray = getTheme().obtainStyledAttributes(attrs);
//                color = typedArray.getColor(0, ContextCompat.getColor(this, R.color.white));
//            }
//            getWindow().setNavigationBarColor(color);
//        }
//
//        loadContentFragment();
//        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        findViewById(R.id.btn_contents).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadContentFragment();
//            }
//        });
//
//        findViewById(R.id.btn_highlights).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadHighlightsFragment();
//            }
//        });
    }

//    private void loadContentFragment() {
//        findViewById(R.id.btn_contents).setSelected(true);
//        findViewById(R.id.btn_highlights).setSelected(false);
//        TableOfContentFragment contentFrameLayout = TableOfContentFragment.newInstance(publication,
//                getIntent().getStringExtra(Constants.CHAPTER_SELECTED),
//                getIntent().getStringExtra(Constants.BOOK_TITLE));
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.parent, contentFrameLayout);
//        ft.commit();
//    }
//
//    private void loadHighlightsFragment() {
//        findViewById(R.id.btn_contents).setSelected(false);
//        findViewById(R.id.btn_highlights).setSelected(true);
//        String bookId = getIntent().getStringExtra(FolioReader.EXTRA_BOOK_ID);
//        String bookTitle = getIntent().getStringExtra(Constants.BOOK_TITLE);
//        HighlightFragment highlightFragment = HighlightFragment.newInstance(bookId, bookTitle);
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.parent, highlightFragment);
//        ft.commit();
//    }

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

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position) {
                0 -> "Оглавление"
                1 -> "Закладки"
                else -> throw IllegalStateException()
            }
        }
    }
}