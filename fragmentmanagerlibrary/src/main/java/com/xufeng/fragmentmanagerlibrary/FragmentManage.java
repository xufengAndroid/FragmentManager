package com.xufeng.fragmentmanagerlibrary;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.HashMap;
import java.util.Map;

/**
 * 
* @Title: FragmentManage.java 
* @Package com.xufeng.xflibrary.manage 
* @Description: TODO(Fragment管理) 
* @author 徐峰004245  (QQ：284535970)
* @version V1.0
 */
public class FragmentManage implements ISelected{
	private Context mContext;
	private FragmentManager fragmentManager;
	private Map<String, Fragment> fmap;// 当需要组管理时使用
	private Map<Fragment, Boolean> moveMap;
	private Map<Fragment, Integer> indexMap;
	private Fragment cruFragment;
	private int fid;
	private String selectKey;

	public FragmentManage(Context context, int fid, FragmentManager fragmentManager) {
		this.mContext = context;
		this.fid = fid;
		this.fragmentManager = fragmentManager;
		fmap = new HashMap<String, Fragment>();
		moveMap = new HashMap<Fragment, Boolean>();
		indexMap = new HashMap<Fragment, Integer>();
	}

	/**
	 * 添加Fragment
	 * @param key
	 * @param fragment
	 */
	public void put(String key, Fragment fragment) {
		put(key, fragment, false);
	}

	
	/**
	 * 添加Fragment
	 * @param key
	 * @param fragment
	 * @param isShowMove true  Fragment 不缓存
	 */
	public void put(String key, Fragment fragment, boolean isShowMove) {
		if (null == fmap.get(key)) {
			try {
				removeFragment(key);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fmap.put(key, fragment);
			moveMap.put(fragment, isShowMove);
			indexMap.put(fragment, indexMap.size() + 1);
		}
	}

	/**
	 * 取得当前显示Fragment
	 * @return
	 */
	public Fragment getCruFragment() {
		return cruFragment;
	}

	/**
	 * 删除fragment
	 * 
	 * @param key
	 */
	public void removeFragment(String key) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		Fragment f = fragmentManager.findFragmentByTag(key);
		if (null != f) {
			transaction.remove(f);
		}
		transaction.commitAllowingStateLoss();
		if (null != fmap) {
			if (cruFragment == fmap.get(key)) {
				cruFragment = null;
			}
			fmap.remove(key);
		}
	}

	/**
	 * 显示当前fragment 隐藏之前fragment
	 * 
	 * @param key
	 */
	public void show(String key) {
		Fragment fragment = fmap.get(key);
		if (cruFragment == fragment) {
			return;
		}

		FragmentTransaction transaction = null;
		if (null == cruFragment
				|| indexMap.get(fragment) > indexMap.get(cruFragment)) {
			transaction = fragmentManager.beginTransaction();
//					.setCustomAnimations(
//							R.anim.push_left_in, R.anim.push_left_out, R.anim.push_left_in,
//							R.anim.push_left_out);
		} else {
			transaction = fragmentManager.beginTransaction();
//					.setCustomAnimations(
//					R.anim.push_right_in, R.anim.push_right_out, R.anim.push_right_in,
//					R.anim.push_right_out);
		}
	
		if (!fragment.isAdded()) {
			if (null != cruFragment) {
				if (moveMap.get(cruFragment)) {
					transaction.remove(cruFragment);
				} else {
					transaction.hide(cruFragment);
				}
			}
			transaction.add(fid, fragment, key);
		} else {
			if (null != cruFragment) {
				if (moveMap.get(cruFragment)) {
					transaction.remove(cruFragment);
				} else {
					transaction.hide(cruFragment);
				}
			}
			transaction.show(fragment);
		}
		cruFragment = fragment;
		this.selectKey = key;
		transaction.commitAllowingStateLoss();
	}

	@Override
	public void selected(String key) {
		show(key);
	}

	public String getSelectKey() {
		return selectKey;
	}
}
