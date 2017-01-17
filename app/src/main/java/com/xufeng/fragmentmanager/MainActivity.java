package com.xufeng.fragmentmanager;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xufeng.fragmentmanager.databinding.MainActBinding;
import com.xufeng.fragmentmanagerlibrary.FragmentManage;
import com.xufeng.fragmentmanagerlibrary.RadioViewGroup;

/**
 * Created by xufeng on 2017/1/17.
 */

public class MainActivity extends AppCompatActivity {

    RadioViewGroup radioViewGroup;
    FragmentManage fragmentManage;

    MainActBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.main_act);

        // 创建Fragment管理器
        fragmentManage = new FragmentManage(this, R.id.fram_context, getSupportFragmentManager());
        // 创建单选控件管理器
        radioViewGroup = new RadioViewGroup(this, true);
        radioViewGroup.put("1", binding.btnIndex);// 把 首页 装入单选控件管理器
        radioViewGroup.put("2", binding.btnFind);// 把 路线 装入单选控件管理器
        radioViewGroup.put("3", binding.btnChat);// 把 搜索 装入单选控件管理器
        radioViewGroup.put("4", binding.btnMy);// 把 通讯录 装入单选控件管理器
        fragmentManage.put("1", new IndexFragment());// 颜值 装入Fragment管理器
        fragmentManage.put("2", new FindFragment());// 评审也 装入Fragment管理器
        fragmentManage.put("3", new ChatFragment());// 运用页 装入Fragment管理器
        fragmentManage.put("4", new MyFragment());// 通讯录页 装入Fragment管理器
        radioViewGroup.setSelected(fragmentManage);// Fragment管理器 交给 单选控件管理器 管理

        radioViewGroup.selected("1");
    }
}
