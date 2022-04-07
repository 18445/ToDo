package com.example.todo.ui.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import androidx.core.util.isNotEmpty
import kotlin.math.min


/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.widget
 * @ClassName:      AnimLogoView
 * @Author:         Yan
 * @CreateDate:     2022年04月04日 18:20:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     开屏闪动LOGO
 */
class AnimLogoView constructor(
    context: Context,
    attrs: AttributeSet?,
) : View(context, attrs) {

    //记录每个字符的位置
    private val mLogoText : ArrayList<String> = ArrayList()

    // 最终合成logo后的坐标
    private val mQuietPoints = SparseArray<PointF>()

    // logo被随机打散的坐标
    private val mRadonPoints = SparseArray<PointF>()

    //绘画文字的画笔
    private val mTextPaint = Paint()

    //Logo偏移量
    private val mLogoOffset = 50f

    //每个文字的边距
    private val mTextPadding = 10

    //动画的进程
    private var mOffsetAnimProgress = 0f

    //动画持续时间
    private val mOffsetDuration = 2500L
    //渐变持续时间
    private val mGradientDuration = 4000L

    //判断动画是否结束
    private var isOffsetAnimEnd = false

    //屏幕的大小
    private var mHeight = 0
    private var mWidth = 0

    //颜色
    private val mTextColor = Color.rgb(135,206,250)//浅蓝色
    private val mGradientColor = Color.GREEN

    //变换的变化量
    private var mMatrixTranslate = 0
    private var mGradientMatrix = Matrix()
    //线性着色器
    private lateinit var mLinearGradient : LinearGradient

    //动画
    private lateinit var mGradientAnimation : ValueAnimator
    private lateinit var mOffsetAnimation : ValueAnimator



    init {
        //画笔初始化
        mTextPaint.also {
            it.textSize = 75f
            it.color = Color.rgb(135,206,250)//浅蓝色
            it.isAntiAlias = true
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mHeight = h
        mWidth = w
        initLogoCoordinate()
        initGradientAnimation()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawPoints(canvas)
    }

    //画出每个点的坐标
    private fun drawPoints(canvas: Canvas){
        if(!isOffsetAnimEnd){
            mTextPaint.alpha = min(255f, 255 * mOffsetAnimProgress + 100).toInt()
            for(i in 0 until mQuietPoints.size()){
                val quietPointF = mQuietPoints[i]
                val radonPointF = mRadonPoints[i]
                val x = radonPointF.x + (quietPointF.x - radonPointF.x) * mOffsetAnimProgress
                val y = radonPointF.y + (quietPointF.y - radonPointF.y) * mOffsetAnimProgress
                canvas.drawText(mLogoText[i],x,y,mTextPaint)
            }
        }else{
            for (i in 0 until mQuietPoints.size()) {
                val quietPointF = mQuietPoints[i]
                canvas.drawText(mLogoText[i], quietPointF.x, quietPointF.y, mTextPaint)
            }
            mGradientMatrix.setTranslate(mMatrixTranslate.toFloat(), 0f)
            mLinearGradient.setLocalMatrix(mGradientMatrix)
        }
    }

    //初始化渐变动画
    private fun initGradientAnimation(){

        mGradientAnimation = ValueAnimator.ofInt(0,2 * mWidth)
            .also { valueAnimator ->
                valueAnimator.duration = mGradientDuration
                valueAnimator.addUpdateListener {
                    mMatrixTranslate = it.animatedValue as Int
                    invalidate()
                }
            }
        mLinearGradient = LinearGradient(-mWidth.toFloat(),0f,0f,0f,
            intArrayOf(mTextColor, mGradientColor, mTextColor),
            floatArrayOf(0f,0.5f,1f),
            Shader.TileMode.CLAMP
        )
        mGradientMatrix = Matrix()

    }

    //初始化Logo的坐标
    private fun initLogoCoordinate(){
        val centerY = mHeight/2f + mTextPaint.textSize + mLogoOffset
        var totalLength = 0f
        for(i in 0 until mLogoText.size){
            val currentChar = mLogoText[i]
            val currentLength = mTextPaint.measureText(currentChar)
            totalLength += if(i != mLogoText.size - 1){
                currentLength + mTextPadding
            }else{
                currentLength
            }
        }

        //检查长度是否不合法
        if(totalLength > mWidth){
            throw (IllegalStateException("This view can not display all text of logoName, please change text size."))
        }

        var startX = (mWidth - totalLength) / 2

        //每个点的确切坐标
        if(mQuietPoints.isNotEmpty()){
            mQuietPoints.clear()
        }
        for(i in 0 until mLogoText.size){
            val currentString = mLogoText[i]
            val currentLength = mTextPaint.measureText(currentString)
            mQuietPoints.put(i, PointF(startX,centerY))
            startX += currentLength + mTextPadding
        }

        if (mRadonPoints.size() > 0) {
            mRadonPoints.clear()
        }
        // 构建随机初始坐标
        for (i in 0 until mLogoText.size) {
            mRadonPoints.put(
                i, PointF(Math.random().toFloat() * mWidth, Math.random().toFloat() * mHeight)
            )
        }
    }

    //位移动画
    private fun initOffsetAnimation(){
        mOffsetAnimation = ValueAnimator.ofFloat(0f,1f)
            .also { valueAnimator ->
                valueAnimator.duration = mOffsetDuration
                valueAnimator.interpolator = AccelerateDecelerateInterpolator()
                valueAnimator.addUpdateListener {
                    mOffsetAnimProgress = it.animatedValue as Float
                    if (mQuietPoints.size() <= 0 || mRadonPoints.size() <= 0){
                        return@addUpdateListener
                    }
                    invalidate()
                }
                valueAnimator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                            isOffsetAnimEnd = true
                            mTextPaint.shader = mLinearGradient
                            mGradientAnimation.start()
                    }
                })
            }
    }

    //文字移动动画
    private fun initTextMove(){

    }


    //传入文字并转换为Array里的string
    fun fillLogoTextArray(logoName : String){
        if(TextUtils.isEmpty(logoName)){
            return
        }
        if(mLogoText.isNotEmpty()){
            mLogoText.clear()
        }
        for(c in logoName){
            mLogoText.add(c.toString())
        }
    }

    fun startAnim(){
        initGradientAnimation()
        initOffsetAnimation()
        mOffsetAnimation.start()
    }


}