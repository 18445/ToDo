package com.example.todo.ui.widget

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import kotlin.math.max
import kotlin.math.min


/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.view
 * @ClassName:      ArcHeaderView
 * @Author:         Yan
 * @CreateDate:     2022年04月04日 20:25:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    弧形顶部View
 */
class ArcHeaderView constructor(
    context: Context,
    attrs: AttributeSet?,
) : View(context, attrs) {

    //判断是否折叠
    private var isFold = true
    //画笔
    private val mPaint = Paint()

    //屏幕大小
    private var mWidth = 0
    private var mHeight = 0

    //贝塞尔曲线
    private val mPath = Path()

    //贝塞尔点
    private var mStartPointF = PointF()
    private var mEndPointF = PointF()
    private var mControlPointF = PointF()

    //圆弧高度
    private val mArcHeight = 100

    //颜色
    private var mStartColor = 0
    private var mEndColor = 0

    //着色器
    private lateinit var mLinearGradient : LinearGradient

    //默认设置的高度
    private var mOldHeight = 0

    //屏幕高度
    private var mScreenHeight = 0

    //外部回调
    private lateinit var onArcHeightChange : (Int) -> Unit

    init {
        val resources: Resources = this.resources
        val dm: DisplayMetrics = resources.displayMetrics
        mScreenHeight = dm.heightPixels

        mPaint.also {
            it.isAntiAlias = true
            it.strokeWidth = 12f
            it.style = Paint.Style.FILL
        }

        mStartColor = Color.parseColor("#FF3A80")
        mEndColor = Color.parseColor("#FF3745")

        mStartPointF = PointF(0f,0f)
        mEndPointF = PointF(0f,0f)
        mControlPointF = PointF(0f,0f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)


        mWidth = w
        mHeight = h

        mOldHeight = if(mOldHeight == 0) mHeight else mOldHeight
        onArcHeightChange(h)

        mLinearGradient = LinearGradient(mWidth / 2f, 0f, mWidth / 2f, mHeight.toFloat(), mStartColor, mEndColor, Shader.TileMode.MIRROR)

        invalidate()
    }

    //画path的矩形
    private fun drawPathRect(){
        mPath.reset()
        mPaint.reset()
        mPath.also {
            it.moveTo(0f,0f)
            it.addRect(0f,0f, width.toFloat(), (height-mArcHeight).toFloat(),Path.Direction.CCW)
        }
    }

    //画出每个点
    private fun drawPoints(){
        mStartPointF.also {
            it.x = 0f
            it.y = (height - mArcHeight).toFloat()
        }

        mEndPointF.also {
            it.x = mWidth.toFloat()
            it.y = (height - mArcHeight).toFloat()
        }

        mControlPointF.also {
            it.x = mWidth/2f
            it.y = (height + mArcHeight).toFloat()
        }
    }

    //贝塞尔曲线
    private fun drawPath(){
        mPath.moveTo(mStartPointF.x, mStartPointF.y)
        mPath.quadTo(mControlPointF.x, mControlPointF.y, mEndPointF.x, mEndPointF.y)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPath.reset()
        drawPathRect()
        drawPoints()
        drawPath()
        mPaint.shader = mLinearGradient
        canvas.drawPath(mPath, mPaint)
    }

    //初始滑动距离
    private var initY = 0f
    var offsetMax = 0f
    var offsetMin = 0f
    var offset = 0f
    var currentBottom = 0
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                initY = event.y
                return true
            }
            MotionEvent.ACTION_MOVE ->{
                offset =  event.y - initY
                currentBottom = (bottom + offset).toInt()
                if(bottom <= mScreenHeight / 2 && isFold){
                    layout(left,top,right, (bottom + offset).toInt())
                }else if(mScreenHeight / 2 <= bottom && bottom <= mScreenHeight  && !isFold){
                    layout(left,top,right, (bottom + offset).toInt())
                }
                offsetMax = max(event.y - initY,offsetMax)
                offsetMin = min(event.y-initY,offsetMin)
                initY = event.y
            }
            MotionEvent.ACTION_UP ->{
                if(isFold){
                    if(offsetMax > 15){
                        offsetMax = 0f
                        offsetMin = 0f
                        startUnFold(currentBottom)
                        isFold = false
                    }else{
                        offsetMax = 0f
                        offsetMin = 0f
                        startFold(currentBottom)
                    }
                }
                if(!isFold){
                    if(offsetMin < -15){
                        offsetMax = 0f
                        offsetMin = 0f
                        startFold(currentBottom)
                        invalidate()
                        isFold = true
                    }else{
                        offsetMax = 0f
                        offsetMin = 0f
                        startUnFold(currentBottom)
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    

    private fun startUnFold(currentHeight : Int){
        ValueAnimator.ofInt(currentHeight,mScreenHeight - mArcHeight )
            .also{ valueAnimator ->
                valueAnimator.duration = 1000
                valueAnimator.addUpdateListener {
                    val currentValue = it.animatedValue as Int
                    layoutParams.height = currentValue
                    requestLayout()
                }
            }.start()
    }

    private fun startFold(currentHeight : Int){
        ValueAnimator.ofInt(currentHeight,mOldHeight)
            .also{ valueAnimator ->
                valueAnimator.duration = 1000
                valueAnimator.addUpdateListener {
                    val currentValue = it.animatedValue as Int
                    layoutParams.height = currentValue
                    requestLayout()
                }
            }.start()
    }

    //外部可以设置颜色变化趋势
    fun setColor(@ColorInt startColor : Int,@ColorInt endColor : Int){
        mStartColor = startColor
        mEndColor = endColor
        mLinearGradient = LinearGradient(mWidth / 2f, 0f, mWidth / 2f, mHeight.toFloat(), mStartColor, mEndColor, Shader.TileMode.MIRROR)
    }

    //根据改变的高度进行回调操作
    fun arcHeightChange(ArcHeightChange : (Int) -> Unit ){
        onArcHeightChange = ArcHeightChange
    }

}