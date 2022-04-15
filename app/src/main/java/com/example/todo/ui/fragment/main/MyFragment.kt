package com.example.todo.ui.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.todo.R
import com.example.todo.base.BaseFragment
import com.example.todo.databinding.FragmentMyBinding
import com.example.todo.utils.toast
import kotlinx.android.synthetic.main.fragment_my.*

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.fragment.main
 * @ClassName:      MyFragment
 * @Author:         Yan
 * @CreateDate:     2022年04月10日 23:06:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    MyFragment
 *
 */

class MyFragment : BaseFragment(){
    override fun initData() {

    }

    override fun initView(view: View) {
        myFragment.navigationView.also { navigationView ->
            ActionBarDrawerToggle(requireActivity(),myFragment.drawerLayout,myFragment.toolbar,0, 0).syncState()

            navigationView.getChildAt(0).isVerticalScrollBarEnabled = false
            navigationView.setNavigationItemSelectedListener {
                toast(it.toString())
                myFragment.drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        }

        myFragment.toolbar.also { toolbar ->
            toolbar.inflateMenu(R.menu.menu_my)
            toolbar.setOnMenuItemClickListener {
                toast(it.toString())

                myFragment.drawerLayout.close()
                true
            }
            toolbar.title = "收集箱"
            toolbar.menu.also { menu ->
                setIconEnable(menu,true)
            }
        }

        val mLayoutParams = navigationView.layoutParams
        val width = resources.displayMetrics.widthPixels
        mLayoutParams.width = width
        navigationView.layoutParams = mLayoutParams


        myFragment.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val contextView = myFragment.drawerLayout.getChildAt(0)

//                val widthDrawer = contextView.measuredWidth
//                val heightDrawer= contextView.measuredHeight

//                val heightOffset = (heightDrawer * slideOffset * 0.1).toInt()
//                val widthOffset = (widthDrawer * slideOffset * 0.1).toInt()

//                contextView.layout(widthOffset,heightOffset,widthDrawer-widthOffset,heightDrawer-heightOffset)

                contextView.translationX = drawerView.measuredWidth * slideOffset
            }

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerStateChanged(newState: Int) {

            }

        })

    }

    override fun startHttp() {

    }

    private fun setIconEnable(menu: Menu, enable: Boolean) {
        try {
            val clazz = menu::class.java
            val m = clazz.getDeclaredMethod(
                "setOptionalIconsVisible",
                Boolean::class.javaPrimitiveType
            )
            m.isAccessible = true
            m.invoke(menu, enable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private lateinit var myFragment : FragmentMyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myFragment = FragmentMyBinding.inflate(inflater)
        return myFragment.root
    }


    override fun handleBackPress(): Boolean {
        val drawer = myFragment.drawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            return false
        }
        return true
    }

}