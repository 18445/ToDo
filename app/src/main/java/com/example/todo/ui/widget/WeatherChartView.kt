//package com.example.todo.ui.widget
//
//
///**
// *
// * @ProjectName:    ToDo
// * @Package:        com.example.todo.ui.widget
// * @ClassName:      WeatherChartView
// * @Author:         Yan
// * @CreateDate:     2022年04月17日 16:08:00
// * @UpdateRemark:   更新说明：
// * @Version:        1.0
// * @Description:     //TODO
// */
//
//
///*
// * Copyright (c) 2016 Kaku咖枯 <kaku201313@163.com | 3772304@qq.com>
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
// */
//
//import android.content.Context
//import android.graphics.*
//import android.util.AttributeSet
//import android.view.View
//import com.example.todo.R
//
///**
// * 折线温度双曲线
// *
// * @author 咖枯
// * @version 1.0 2015/11/06
// */
//class WeatherChartView : View {
//    /**
//     * x轴集合
//     */
//    private val mXAxis = FloatArray(6)
//
//    /**
//     * 白天y轴集合
//     */
//    private val mYAxisDay = FloatArray(6)
//
//    /**
//     * 夜间y轴集合
//     */
//    private val mYAxisNight = FloatArray(6)
//
//    /**
//     * 白天温度集合
//     */
//    private var mTempDay = IntArray(6)
//
//    /**
//     * 夜间温度集合
//     */
//    private var mTempNight = IntArray(6)
//
//    /**
//     * 控件高
//     */
//    private var mHeight = 0
//
//    /**
//     * 字体大小
//     */
//    private var mTextSize = 0f
//
//    /**
//     * 圓半径
//     */
//    private var mRadius = 0f
//
//    /**
//     * 圓半径今天
//     */
//    private var mRadiusToday = 0f
//
//    /**
//     * 文字移动位置距离
//     */
//    private var mTextSpace = 0f
//
//    /**
//     * 线的大小
//     */
//    private var mStokeWidth = 0f
//
//    /**
//     * 白天折线颜色
//     */
//    private var mColorDay = 0
//
//    /**
//     * 夜间折线颜色
//     */
//    private var mColorNight = 0
//
//    /**
//     * 字体颜色
//     */
//    private var mTextColor = 0
//
//    /**
//     * 屏幕密度
//     */
//    private var mDensity = 0f
//
//    /**
//     * 控件边的空白空间
//     */
//    private var mSpace = 0f
//
//    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
//
//        val a = context.obtainStyledAttributes(attrs, R.styleable.WeatherChartView)
//        val densityText = resources.displayMetrics.scaledDensity
//        mTextSize = a.getDimensionPixelSize(
//            R.styleable.WeatherChartView_textSize,
//            (14 * densityText).toInt()
//        ).toFloat()
//        mColorDay = a.getColor(
//            R.styleable.WeatherChartView_dayColor,
//            resources.getColor(R.color.colorAccent)
//        )
//        mColorNight = a.getColor(
//            R.styleable.WeatherChartView_nightColor,
//            resources.getColor(R.color.colorPrimary)
//        )
//        mTextColor = a.getColor(R.styleable.WeatherChartView_textColor, Color.WHITE)
//        a.recycle()
//        mDensity = resources.displayMetrics.density
//        mRadius = 3 * mDensity
//        mRadiusToday = 5 * mDensity
//        mSpace = 3 * mDensity
//        mTextSpace = 10 * mDensity
//        mStokeWidth = 2 * mDensity
//    }
//
//    constructor(context: Context?) : super(context) {}
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        if (mHeight == 0) {
//            // 设置控件高度，x轴集合
//            setHeightAndXAxis()
//        }
//        // 计算y轴集合数值
//        computeYAxisValues()
//        // 画白天折线图
//        drawChart(canvas, mColorDay, mTempDay, mYAxisDay, 0)
//        // 画夜间折线图
//        drawChart(canvas, mColorNight, mTempNight, mYAxisNight, 1)
//    }
//
//    /**
//     * 计算y轴集合数值
//     */
//    private fun computeYAxisValues() {
//        // 存放白天最低温度
//        var minTempDay = mTempDay[0]
//        // 存放白天最高温度
//        var maxTempDay = mTempDay[0]
//        for (item in mTempDay) {
//            if (item < minTempDay) {
//                minTempDay = item
//            }
//            if (item > maxTempDay) {
//                maxTempDay = item
//            }
//        }
//
//        // 存放夜间最低温度
//        var minTempNight = mTempNight[0]
//        // 存放夜间最高温度
//        var maxTempNight = mTempNight[0]
//        for (item in mTempNight) {
//            if (item < minTempNight) {
//                minTempNight = item
//            }
//            if (item > maxTempNight) {
//                maxTempNight = item
//            }
//        }
//
//        // 白天，夜间中的最低温度
//        val minTemp = if (minTempNight < minTempDay) minTempNight else minTempDay
//        // 白天，夜间中的最高温度
//        val maxTemp = if (maxTempDay > maxTempNight) maxTempDay else maxTempNight
//
//        // 份数（白天，夜间综合温差）
//        val parts = (maxTemp - minTemp).toFloat()
//        // y轴一端到控件一端的距离
//        val length = mSpace + mTextSize + mTextSpace + mRadius
//        // y轴高度
//        val yAxisHeight = mHeight - length * 2
//
//        // 当温度都相同时（被除数不能为0）
//        if (parts == 0f) {
//            for (i in 0 until LENGTH) {
//                mYAxisDay[i] = yAxisHeight / 2 + length
//                mYAxisNight[i] = yAxisHeight / 2 + length
//            }
//        } else {
//            val partValue = yAxisHeight / parts
//            for (i in 0 until LENGTH) {
//                mYAxisDay[i] = mHeight - partValue * (mTempDay[i] - minTemp) - length
//                mYAxisNight[i] = mHeight - partValue * (mTempNight[i] - minTemp) - length
//            }
//        }
//    }
//
//    /**
//     * 画折线图
//     *
//     * @param canvas 画布
//     * @param color  画图颜色
//     * @param temp   温度集合
//     * @param yAxis  y轴集合
//     * @param type   折线种类：0，白天；1，夜间
//     */
//    private fun drawChart(
//        canvas: Canvas,
//        color: Int,
//        temp: IntArray,
//        yAxis: FloatArray,
//        type: Int
//    ) {
//        // 线画笔
//        val linePaint = Paint()
//        // 抗锯齿
//        linePaint.isAntiAlias = true
//        // 线宽
//        linePaint.strokeWidth = mStokeWidth
//        linePaint.color = color
//        // 空心
//        linePaint.style = Paint.Style.STROKE
//
//        // 点画笔
//        val pointPaint = Paint()
//        pointPaint.isAntiAlias = true
//        pointPaint.color = color
//
//        // 字体画笔
//        val textPaint = Paint()
//        textPaint.isAntiAlias = true
//        textPaint.color = mTextColor
//        textPaint.textSize = mTextSize
//        // 文字居中
//        textPaint.textAlign = Paint.Align.CENTER
//        val alpha1 = 102
//        val alpha2 = 255
//        for (i in 0 until LENGTH) {
//            // 画线
//            if (i < LENGTH - 1) {
//                // 昨天
//                if (i == 0) {
//                    linePaint.alpha = alpha1
//                    // 设置虚线效果
//                    linePaint.pathEffect =
//                        DashPathEffect(floatArrayOf(2 * mDensity, 2 * mDensity), 0F)
//                    // 路径
//                    val path = Path()
//                    // 路径起点
//                    path.moveTo(mXAxis[i], yAxis[i])
//                    // 路径连接到
//                    path.lineTo(mXAxis[i + 1], yAxis[i + 1])
//                    canvas.drawPath(path, linePaint)
//                } else {
//                    linePaint.alpha = alpha2
//                    linePaint.pathEffect = null
//                    canvas.drawLine(
//                        mXAxis[i],
//                        yAxis[i], mXAxis[i + 1], yAxis[i + 1], linePaint
//                    )
//                }
//            }
//
//            // 画点
//            if (i != 1) {
//                // 昨天
//                if (i == 0) {
//                    pointPaint.alpha = alpha1
//                    canvas.drawCircle(mXAxis[i], yAxis[i], mRadius, pointPaint)
//                } else {
//                    pointPaint.alpha = alpha2
//                    canvas.drawCircle(mXAxis[i], yAxis[i], mRadius, pointPaint)
//                }
//                // 今天
//            } else {
//                pointPaint.alpha = alpha2
//                canvas.drawCircle(mXAxis[i], yAxis[i], mRadiusToday, pointPaint)
//            }
//
//            // 画字
//            // 昨天
//            if (i == 0) {
//                textPaint.alpha = alpha1
//                drawText(canvas, textPaint, i, temp, yAxis, type)
//            } else {
//                textPaint.alpha = alpha2
//                drawText(canvas, textPaint, i, temp, yAxis, type)
//            }
//        }
//    }
//
//    /**
//     * 绘制文字
//     *
//     * @param canvas    画布
//     * @param textPaint 画笔
//     * @param i         索引
//     * @param temp      温度集合
//     * @param yAxis     y轴集合
//     * @param type      折线种类：0，白天；1，夜间
//     */
//    private fun drawText(
//        canvas: Canvas,
//        textPaint: Paint,
//        i: Int,
//        temp: IntArray,
//        yAxis: FloatArray,
//        type: Int
//    ) {
//        when (type) {
//            0 ->                 // 显示白天气温
//                canvas.drawText(
//                    temp[i].toString() + "°",
//                    mXAxis[i], yAxis[i] - mRadius - mTextSpace, textPaint
//                )
//            1 ->                 // 显示夜间气温
//                canvas.drawText(
//                    temp[i].toString() + "°",
//                    mXAxis[i], yAxis[i] + mTextSpace + mTextSize, textPaint
//                )
//        }
//    }
//
//    /**
//     * 设置高度，x轴集合
//     */
//    private fun setHeightAndXAxis() {
//        mHeight = height
//        // 控件宽
//        val width = width
//        // 每一份宽
//        val w = (width / 12).toFloat()
//        mXAxis[0] = w
//        mXAxis[1] = w * 3
//        mXAxis[2] = w * 5
//        mXAxis[3] = w * 7
//        mXAxis[4] = w * 9
//        mXAxis[5] = w * 11
//    }
//
//    /**
//     * 设置白天温度
//     *
//     * @param tempDay 温度数组集合
//     */
//    fun setTempDay(tempDay: IntArray) {
//        mTempDay = tempDay
//    }
//
//    /**
//     * 设置夜间温度
//     *
//     * @param tempNight 温度数组集合
//     */
//    fun setTempNight(tempNight: IntArray) {
//        mTempNight = tempNight
//    }
//
//    companion object {
//        /**
//         * x,y轴集合数
//         */
//        private const val LENGTH = 6
//    }
//}
