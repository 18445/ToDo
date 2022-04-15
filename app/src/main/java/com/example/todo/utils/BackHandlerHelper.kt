package com.example.todo.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import com.example.todo.base.OnBackPressListener


/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.utils
 * @ClassName:      BackHandlerHelper
 * @Author:         Yan
 * @CreateDate:     2022年04月14日 19:49:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:
 *
 * 将back事件分发给 FragmentManager 中管理的子Fragment，如果该 FragmentManager 中的所有Fragment都
 * 没有处理back事件，则尝试 FragmentManager.popBackStack()
 *
*/


object BackHandlerHelper {

    private fun handleBackPress(fragmentManager: FragmentManager): Boolean {
        val fragments: MutableList<Fragment> = fragmentManager.fragments

        for (i in fragments.indices.reversed()) {
            val child: Fragment = fragments[i]

            if(child is NavHost){
                child.childFragmentManager.fragments.forEach {
                    fragments.add(it)
                }
            }

            Log.d("fragmentManager1",fragments.toString())

//            if (isFragmentBackHandled(child)) {
//                return true
//            }
        }
//        if (fragmentManager.backStackEntryCount > 0) {
//            fragmentManager.popBackStack()
//            return true
//        }

        fragments.forEach {
            if (it is OnBackPressListener && it.handleBackPress()){
                return true
            }
        }

        return false
    }

    fun handleBackPress(fragment: Fragment): Boolean {
        return handleBackPress(fragment.childFragmentManager)
    }

    fun handleBackPress(fragmentActivity: FragmentActivity): Boolean {
        return handleBackPress(fragmentActivity.supportFragmentManager)
    }

}