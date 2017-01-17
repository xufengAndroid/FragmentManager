package com.xufeng.fragmentmanagerlibrary;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * 
* @Title: RadioViewGroup.java 
* @Package com.xufeng.xflibrary.tool 
* @Description: TODO(组显示管理类) 
* @author 徐峰004245  (QQ：284535970)
* @version V1.0
 */
public class RadioViewGroup implements ISelected {

	private Context mContext;
	private Map<Object, View> map;
	private View selectedView;
	private ViewPager viewPager;

	private ISelected iSelected;
	
	/**
	 * 
	 * @param context
	 * @param isMap false:不启用组管理
	 */
	public RadioViewGroup(Context context, boolean isMap) {
		this.mContext = context;
		if(isMap){
			map = new HashMap<Object, View>();
		}
	}

	public void setSelected(ISelected iSelected) {
		this.iSelected = iSelected;
	}

	public void setViewPager(ViewPager viewPager){
		this.viewPager = viewPager;
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				selected(arg0+"");
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	public void putByViewGroup(ViewGroup vg){
		for (int i = 0; i < vg.getChildCount(); i++) {
			View v = vg.getChildAt(i);
			v.setOnClickListener(new View.OnClickListener() {
				private int i ;
				public View.OnClickListener set(int i){
					this.i = i;
					return this;
				}
				@Override
				public void onClick(View v) {
					selected(i+"");
					viewPager.setCurrentItem(i);
				}
			}.set(i));
			put(i+"",v);
		}
	}
	
	/**
	 * 通过@+id  put
	 * @param view
	 */
	protected void putById(View view) {
		map.put(view.getId(), view);
	}
	
	/**
	 * 通过key   put
	 * @param key
	 * @param view
	 */
	public void put(final String key, View view){
		view.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				selected(key);
			}
		});
		map.put(key, view);

	}

	/**
	 * 选中 
	 */
	@Override
	public void selected(String key){
		if(null!=iSelected){
			iSelected.selected(key);
		}
		selected(map.get(key));
	}
	/**
	 * 选中 
	 */
	public void selected(View view){
		if(null!=selectedView){
			selectedView.setSelected(false);
		}
		selectedView = view;
		selectedView.setSelected(true);
	}



}
