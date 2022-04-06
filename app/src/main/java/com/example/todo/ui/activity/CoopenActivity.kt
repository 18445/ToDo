package com.example.todo.ui.activity


import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.base.BaseActivity
import com.example.todo.databinding.ActivityCoopenBinding
import com.example.todo.ui.viewModel.RegisterViewModel
import kotlin.math.max


class CoopenActivity : BaseActivity() {

    //按钮的高度
    private var mArcHeaderHeight = 0

    //偏移量
    private val mOffset = 100f

    //上方和下方两个不同的alpha通道
    private val mBtnAlphaTop : MutableLiveData<Float> = MutableLiveData()
    private val mBtnAlphaBottom : MutableLiveData<Float> = MutableLiveData()
    private val mBtnMarginTop : MutableLiveData<Float> = MutableLiveData()
    private val mBtnMarginBottom : MutableLiveData<Float> = MutableLiveData()

    private lateinit var mRegisterViewModel : RegisterViewModel


    //1/2 1/4 屏幕的大小
    private val mHalfScreenHeight by lazy {
        mScreenHeight / 2
    }
    private val mQuarterScreenHeight by lazy {
        mScreenHeight / 4
    }

    override fun initData() {
        mBtnAlphaBottom.value = 1f
        mBtnAlphaTop.value = 0f
    }

    override fun initView() {

    }

    override fun startHttp() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mRegisterViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        mRegisterViewModel.userInfo.observe(this){
            
        }

        val coopenBinding : ActivityCoopenBinding = DataBindingUtil.setContentView(this, R.layout.activity_coopen)

        coopenBinding.logo.also {
            it.fillLogoTextArray("To Do Something")
            it.startAnim()
        }

        coopenBinding.ahvTop.also { arcHeaderView ->

            //获得高度
            arcHeaderView.post {
                mArcHeaderHeight = if(mArcHeaderHeight == 0) arcHeaderView.measuredHeight else mArcHeaderHeight
                    Log.d("getHeight",mArcHeaderHeight.toString()+"  "+arcHeaderView.measuredHeight.toString())
            }

            //回调
            arcHeaderView.arcHeightChange {

//                  上方ArcView收缩状态 -> 拉伸状态
                    if(mArcHeaderHeight != 0){
                        mBtnAlphaBottom.value = foldAlpha(it)
//                        mBtnMarginTop.value = (1 - unFoldAlpha(it)) * mOffset
                        mBtnMarginTop.value = foldTranslation(it)
                    }
//                  下方ArcView拉伸状态 -> 收缩状态
                    mBtnAlphaTop.value = unFoldAlpha(it)

            }
        }

        //底部Button
        coopenBinding.mbtnLoginBottom.also {
            mBtnAlphaBottom.observe(this){ mAlpha ->
                coopenBinding.btnAlphaBottom = mAlpha
            }
        }

        //顶部Button
        coopenBinding.mbtnLoginTop.also {
            mBtnAlphaTop.observe(this){ mAlpha ->
                Log.d(TAG,"currentAlpha:$mAlpha")
                coopenBinding.btnAlphaTop = mAlpha
            }
        }

    }

    //收缩状态改变alpha通道
    private fun foldAlpha(mHeight : Int) : Float{
        val tempAlpha = (mHalfScreenHeight  + mArcHeaderHeight- mHeight * 1f) / mHalfScreenHeight
        return max(0f,tempAlpha)
    }


    private fun foldTranslation(mHeight : Int) : Float {
        return mHeight.toFloat()
    }

    //拉伸状态改变alpha通道
    private fun unFoldAlpha(mHeight: Int): Float {
        return (mHeight * 1.25f - mHalfScreenHeight ) / mHalfScreenHeight
    }
}