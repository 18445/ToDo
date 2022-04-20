package com.example.todo.ui.widget

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.PathMeasure.POSITION_MATRIX_FLAG
import android.text.TextPaint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.widget
 * @ClassName:      WeatherView
 * @Author:         Yan
 * @CreateDate:     2022年04月16日 23:14:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    温度曲线的自定义view
 */

class WeatherView constructor(
    context: Context,
    attrs: AttributeSet?,
) : View(context, attrs) {

    //画笔
    private val mPathPaint = Paint()

    //温度点
//    private val mTempPoints = mutableListOf<Int>()
    private lateinit var mTempPoints : MutableList<Int>
    private lateinit var mHourText : MutableList<String>

    //贝塞尔曲线点
    private val  mPath = Path()

    //画小圆
    private val mPointPaint = Paint()
    private val mCorner = 5f

    //下方虚线点
    private var mBottomPath = Path()

    //上方提示点
    private var currentText : String = ""
    private val mHintPath = Path()
    private val mTextPaint = Paint()
    private var roundRectRadius = 15f
    private var moveDis = 10f
    private val rectF = RectF()
    private val triangleHeight = 5f

    private val tipHeight by lazy {
        40f
    }
    private val tipWidth by lazy {
        75f
    }
    private var offsetTop = 75f
    private val mHintPaint = Paint()
    private var mCurrent = 0f
    private val mMeasure = PathMeasure()

    //总长度
    private var mTotalLength = 0f

    //给中间曲线的种空间
    private val mTotalSpare by lazy {
        height / 4f
    }
    //每一份所占有的空间
    private val mPartSpare by lazy {
        mTotalSpare / (mMaxTemp - mMinTemp)
    }

    //每一格的偏移量
    private val mOffset by lazy{
        width / 10f
    }

    //获得最高与最低和平均温度
    private var mMaxTemp = 0
    private var mMinTemp = 0
    private var mAvgTemp = 0

    //背景着色器
    private val mBottomPaint = Paint()

    //颜色
    private var mStartColor = Color.parseColor("#1E90FF")
    private var mEndColor = Color.TRANSPARENT

    //着色器
    private val mLinearGradient : LinearGradient by lazy {
        LinearGradient(width / 2f, 0f, width / 2f, height.toFloat(), mStartColor, mEndColor, Shader.TileMode.MIRROR)
    }

    //下方文字偏移量
    private val mOffsetBottomText = 15f

    private var isShow = false

    init {

        mPathPaint.also {
            it.isAntiAlias = true
            it.strokeWidth = 6f
            it.style = Paint.Style.STROKE
            it.color = Color.parseColor("#1E90FF")
        }
        mHintPaint.also {
//            it.color = Color.LTGRAY
            it.color = Color.parseColor("#B0C4DE")
            it.strokeWidth = 30f
            it.style = Paint.Style.FILL
            it.textAlign = Paint.Align.CENTER
            it.textSize = 25f
        }
        mTextPaint.also {
            it.strokeWidth = 12f
            it.color = Color.BLACK
            it.textSize = 25f
            it.style = Paint.Style.FILL
            it.textAlign = Paint.Align.CENTER
        }
        mPointPaint.also {
            it.strokeWidth = 15f
            it.color = Color.parseColor("#6495ED")
            it.isAntiAlias = true
            it.style = Paint.Style.FILL_AND_STROKE
        }
        mBottomPaint.also {
            it.isAntiAlias = true
            it.strokeWidth = 12f
            it.style = Paint.Style.FILL
        }


    }


    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if(isShow){
            drawBezier(canvas)
    //        drawTipView(canvas)
    //        drawText(canvas)
            drawBottom(canvas)
            drawPointsAndText(canvas)
        }
    }

    //初始滑动距离
    private var initX = 0f
    private var totalOffset = 0f
    var offset = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                initX = event.x
                return true
            }
            MotionEvent.ACTION_MOVE ->{
                offset =  event.x - initX
                initX = event.x
                totalOffset += offset

                if( -totalOffset < mTotalLength + mOffset - width && totalOffset <= 0){
                    this.scrollX = -totalOffset.toInt()
                    moveDis -= offset *( mTotalLength + 2.3F * mOffset - width) / width

                    //当前到达的位置
                    val currentPosition = (moveDis / (mTotalLength + mOffset) * mTempPoints.size ).toInt()

//
                    if(currentPosition in 0..11){
                        val currentPoint = mTempPoints[currentPosition]
                        val verticalOffset =  (currentPoint - mAvgTemp) * height / 6
                        Log.d("offsetPoint",currentPoint.toString())
                        Log.d("verticalOffset",verticalOffset.toString())
//                        offsetTop = 75f + verticalOffset
                        currentText = "$currentPoint°"
                        invalidate()
                    }

                }else{
                    totalOffset -= offset
                }
            }

            MotionEvent.ACTION_UP ->{
//                if(-10f<-totalOffset ){
//                    totalOffset = 10f
//                }
//                if(totalOffset > 2 * width){
//                    totalOffset = 2f * width
//                }
            }
        }

        return super.onTouchEvent(event)
    }

    private fun drawBottom(canvas: Canvas){
        mBottomPaint.shader = mLinearGradient
//        mMeasure.setPath(mPath,false)
//        mMeasure.getSegment(0f,mMeasure.length,mBottomPath,false)
        mBottomPath.lineTo(mTotalLength + mOffset,height.toFloat())
        mBottomPath.lineTo(0f,height.toFloat())
        canvas.drawPath(mBottomPath,mBottomPaint)
    }

    private fun initMaxAndMin(){
        var sum = mTempPoints[0]
        mMaxTemp = mTempPoints[0]
        mMinTemp = mTempPoints[0]
        for (i in 1 until mTempPoints.size){
            mMaxTemp = if(mMaxTemp > mTempPoints[i]) mMaxTemp else mTempPoints[i]
            mMinTemp = if(mMinTemp < mTempPoints[i]) mMinTemp else mTempPoints[i]
            sum += mTempPoints[0]
        }

//        mAvgTemp = sum / mTempPoints.size
        mAvgTemp =( mMaxTemp + mMinTemp )/ 2

        mTotalLength = mOffset * (mTempPoints.size - 1)

    }

    private fun drawPointsAndText(canvas: Canvas){
        var total = 0f

//        for(i in 0 until mTempPoints.size - 1){
//            val currentPoint = mTempPoints[i]
//            val offsetPoint = (mAvgTemp-currentPoint) * height / 6f
//            val verticalPoint = height / 2 + offsetPoint
//            total += mOffset
//            canvas.drawCircle(total,verticalPoint,7.5f,mPointPaint)
//        }


        for(i in 0 until mTempPoints.size - 1){
            total += mOffset
            val currentPoint = mTempPoints[i]
            val offsetPoint = (mAvgTemp - currentPoint ) * mPartSpare + height / 2
            canvas.drawCircle(total,offsetPoint,7.5f,mPointPaint)
            drawText(canvas,total,offsetPoint,i)
            if(i % 2 == 0){
                drawBottomText(canvas,total,mHourText[i])
            }
        }
    }

    private fun drawBezier(canvas: Canvas){
        initMaxAndMin()

        var total = 0f

        mPath.reset()

        val firstPoint = mTempPoints[0]
        val firstOffsetPoint = (mAvgTemp - firstPoint ) * mPartSpare + height / 2
        mPath.moveTo(0f,firstOffsetPoint)

        for(i in 0 until mTempPoints.size - 1){
            total += mOffset
            val currentPoint = mTempPoints[i]
            val offsetPoint = (mAvgTemp - currentPoint ) * mPartSpare + height / 2
            mPath.lineTo(total,offsetPoint)
        }

        total += mOffset
        val endPoint = mTempPoints[mTempPoints.size - 1]
        val endOffsetPoint = (mAvgTemp - endPoint ) * mPartSpare + height / 2
        mPath.lineTo(total,endOffsetPoint)

        mBottomPath = mPath
        canvas.drawPath(mPath,mPathPaint)

//        mPath.moveTo(0f,height / 2f)
//        mPath.lineTo(mOffset,height/2f)
//        for(i in 0 until mTempPoints.size - 2 step 1){
//            //前中后三个点
//            val prePoint = mTempPoints[i]
//            val nowPoint = mTempPoints[i+1]
//            val nextPoint = mTempPoints[i+2]
//
//            //前面与后面的中间点
//            val preMid =( prePoint + nowPoint )/2f
//            val nextMid =( nowPoint + nextPoint )/2f
//
//            val offSetPre = (mAvgTemp-preMid) * height / 6
//            val offSetNext = (mAvgTemp-nextMid) * height / 6
//
//            mPath.rQuadTo(mOffset,offSetPre,mOffset,offSetNext)
////            mTotalLength += mOffset
//
//        }
//
////        mTotalLength += mOffset
//
//        mPath.rLineTo(mOffset,0f)
//        mMeasure.setPath(mPath,false)

    }

    /**
     * 绘制进度上边提示百分比的view
     *
     * @param canvas
     */
    private fun drawTipView(canvas: Canvas) {
        offsetTop = height/2.5f
//        mMeasure.getMatrix(mCurrent,matrix,POSITION_MATRIX_FLAG)
//        matrix.mapRadius()
        drawRoundRect(canvas)
        drawTriangle(canvas)
    }

    /**
     * 绘制圆角矩形
     *
     * @param canvas
     */
    private fun drawRoundRect(canvas: Canvas) {
        rectF.set(moveDis, height/2 - offsetTop,
            tipWidth + moveDis, height/2 - offsetTop +tipHeight)
        canvas.drawRoundRect(rectF, roundRectRadius, roundRectRadius, mHintPaint)
    }

    /**
     * 绘制三角形
     *
     * @param canvas
     */
    private fun drawTriangle(canvas: Canvas) {
        mHintPath.moveTo(tipWidth / 2 - triangleHeight + moveDis, height/2 - offsetTop +tipHeight)
        mHintPath.lineTo(tipWidth / 2 + moveDis, height/2 - offsetTop +tipHeight + triangleHeight)
        mHintPath.lineTo(tipWidth / 2 + triangleHeight + moveDis, height/2 - offsetTop +tipHeight)
        canvas.drawPath(mHintPath, mHintPaint)
        mHintPath.reset()
    }

//    private fun drawText(canvas: Canvas){
//        canvas.drawText(currentText,moveDis+tipWidth/2,2* (height/2 - offsetTop +tipHeight) /3,mTextPaint)
//    }
    private fun drawText(canvas: Canvas,x : Float,y : Float,index : Int){
//        canvas.drawText(currentText,moveDis+tipWidth/2,2* (height/2 - offsetTop +tipHeight) /3,mTextPaint)
        canvas.drawText("${mTempPoints[index]}°",x,y - 25,mTextPaint)
    }

    private fun drawBottomText(canvas: Canvas,x : Float,text: String){

        canvas.drawText(text,x,height-mOffsetBottomText,mHintPaint)
    }
    fun initHourText(mutableList: MutableList<String>){
        mHourText = mutableList
    }
    fun initTempPoint(mutableList: MutableList<Int>){
        mTempPoints = mutableList
    }

    fun show(){
        isShow = true
        invalidate()
    }
}