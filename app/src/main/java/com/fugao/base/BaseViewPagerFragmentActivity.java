package com.fugao.base;
import java.util.ArrayList;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.WindowManager;

import com.fugao.R;
import com.fugao.model.PatientBean;
import com.fugao.view.PagerSlidingTabStrip;

/**
 * 有actionbar 的 viewpage +tab 基类
 * getSupportFragmentManager
 * 
 * @author findchen TODO 2013-6-18下午1:43:31
 */
public abstract class BaseViewPagerFragmentActivity extends
		FragmentActivity {

	private ViewPager pager;
	private PagerSlidingTabStrip tabs;
	private String[] titles;
	public PagerSlidingTabStripAdapter adapter;
	private ActionBar actionBar;
	public PatientBean currentPatientBean;
	DisplayMetrics displayMetrics = new DisplayMetrics();
	private int phoneWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		phoneWidth = displayMetrics.widthPixels;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_view_pager_fragment);
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.nav_bg));
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(setActionBarTitle());

		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		fragments = setFragments();

	//	titles = getResources().getStringArray(setTitleArrayId());
		titles=setTitleArray();
		adapter = new PagerSlidingTabStripAdapter(getSupportFragmentManager(),
				fragments, titles);

		pager.setAdapter(adapter);

		// final int pageMargin = (int) TypedValue.applyDimension(
		// TypedValue.COMPLEX_UNIT_DIP, 3, getResources()
		// .getDisplayMetrics());
		// pager.setPageMargin(pageMargin);
		tabs.setViewPager(pager);
		tabs.setIndicatorColor(getResources().getColor(
				R.color.detail_tabindictor));

		int tabChildSize = fragments.size();
		int titleCount = 0;
		String titleString = "";
		for (String string : titles) {
			titleString += string;
		}
		titleCount = titleString.length();
		if (titleCount <= 9) {
			int width = phoneWidth / tabChildSize;
			tabs.setTabWidth(width);
		}
		initView();
		initData();
		initListener();
	}

	public void changeFragments(ArrayList<Fragment> fragments) {
		adapter.setFragments(fragments);
		pager.setAdapter(adapter);
	}

	public int getCurrentItem() {
		return pager.getCurrentItem();
	}

	public abstract void initView();

	public abstract void initData();

	public abstract String setActionBarTitle();

	public abstract void initListener();

	public abstract ArrayList<Fragment> setFragments();

	/**
	 * 设置viewpager 的标题
	 * @return
	 */
	public abstract String[] setTitleArray();
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
