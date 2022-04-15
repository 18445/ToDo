package com.example.todo.utils

import android.annotation.TargetApi
import android.app.*
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.todo.R
import java.lang.reflect.Method


/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.utils
 * @ClassName:      NotificationUtil
 * @Author:         Yan
 * @CreateDate:     2022年04月11日 21:26:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     创造通知的工具类
 */


object NotificationUtil {
    const val TAG = "NotificationUtil"

    //请求代码
    private const val RequestCode = 1

    //新消息
    private const val NEW_MESSAGE = "chat"

    //群消息
    const val NEW_GROUP = "chat_group"

    //其他消息
    private const val OTHER_MESSAGE = "other"

    /**
     * 提示
     */
    private const val Ticker = "您有一条新的消息"

    private const val CHECK_OP_NO_THROW = "checkOpNoThrow"

    //发布通知
    private const val OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION"

    //通知ID
    private const val NotifyId = 0

    //Notification对象
    lateinit var notificationManager: NotificationManager

    //default 的设置
    var sound : Boolean = true
    var vibrate : Boolean = true
    var lights : Boolean = true

    /**
     * 适配 Android8.0  创建通知渠道
     * 需要保证在通知弹出之前调用就可以了。并且创建通知渠道的代码只在第一次执行的时候才会创建，
     * 以后每次执行创建代码系统会检测到该通知渠道已经存在了，因此不会重复创建，也并不会影响任何效率。
     */
    fun setNotificationChannel(context : Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var importance = NotificationManager.IMPORTANCE_HIGH
            var channelId = NEW_MESSAGE
            var channelName = "新消息通知"
            createNotificationChannel(context, channelId, channelName,importance)
            channelId = OTHER_MESSAGE
            channelName = "其他通知"
            importance = NotificationManager.IMPORTANCE_DEFAULT
            createNotificationChannel(context, channelId, channelName,importance)
        }
    }

    /**
     * 创建配置通知渠道
     *
     * @param channelId   渠道id
     * @param channelName 渠道name
     * @param importance  优先级
     */
    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context:Context, channelId:String, channelName:String, importance:Int) {

//        createNotificationGroup(context,channelId,channelName)
        val channel = NotificationChannel(channelId, channelName, importance)
        // 禁止该渠道使用角标
        channel.setShowBadge(false)
        // 设置渠道组id
//        channel.setGroup(channelId)
        // 配置通知渠道的属性
//        channel .setDescription("渠道的描述")
        // 设置通知出现时的闪灯（如果 android 设备支持的话）
        channel.enableLights(true)
        // 设置通知出现时的震动（如果 android 设备支持的话）
        channel.enableVibration(true)
        // 如上设置使手机：静止1秒，震动2秒，静止1秒，震动3秒
//        channel.setVibrationPattern(new long[]{1000, 2000, 1000,3000})
        // 设置锁屏是否显示通知
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        // 设置呼吸灯颜色
        channel.lightColor = Color.BLUE
        // 设置是否可以绕过请勿打扰模式
        channel.setBypassDnd(true)
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as (NotificationManager)
        notificationManager.createNotificationChannel(channel)
    }


    /**
     * 创建渠道组(若通知渠道比较多的情况下可以划分渠道组)
     *
     * @param groupId   渠道组id
     * @param groupName 渠道组name
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createNotificationGroup(context:Context,groupId:String,groupName:String) {
        val group = NotificationChannelGroup(groupId, groupName)
        notificationManager =  context.getSystemService(Context.NOTIFICATION_SERVICE) as (NotificationManager)
        notificationManager.createNotificationChannelGroup(group)
    }

    /**
     * 发送通知
     *
     * @param largeIcon    大图标
     * @param smallIcon    小图标
     * @param contentTitle 标题
     * @param subText      小标题/副标题
     * @param contentText  内容
     * @param priority     优先级
     * @param ticker       通知首次弹出时，状态栏上显示的文本
     * @param view         自定义通知栏
     * @param notifyId     定义是否显示多条通知栏
     * @param cls          意图类
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun show(context:Context, largeIcon:Int = R.mipmap.ic_launcher,
             smallIcon:Int = R.mipmap.ic_launcher, contentTitle:String = "",
             subText:String = "", contentText:String = "",
             priority:Int = NotificationManager.IMPORTANCE_HIGH, ticker:String = Ticker, view: RemoteViews? = null,
             notifyId:Int = NotifyId,  channelId:String = NEW_MESSAGE,  cls:Class<*>? = null) {
        //flags
        // FLAG_ONE_SHOT:表示此PendingIntent只能使用一次的标志
        // FLAG_IMMUTABLE:指示创建的PendingIntent应该是不可变的标志
        // FLAG_NO_CREATE : 指示如果描述的PendingIntent尚不存在，则只返回null而不是创建它。
        // FLAG_CANCEL_CURRENT :指示如果所描述的PendingIntent已存在，则应在生成新的PendingIntent,取消之前PendingIntent
        // FLAG_UPDATE_CURRENT : 指示如果所描述的PendingIntent已存在，则保留它，但将其额外数据替换为此新Intent中的内容
        var pendingIntent: PendingIntent? = null


        // 添加隐示意图
        if(cls != null){
            val intent = Intent(context, cls);
            pendingIntent = PendingIntent.getActivity(context, RequestCode, intent, FLAG_UPDATE_CURRENT)
        }

        // 获取通知服务管理器
        val manager =  context.getSystemService(Context.NOTIFICATION_SERVICE) as (NotificationManager)

        // 判断应用通知是否打开
        if (!openNotificationChannel(context, manager, channelId)) {
            return
        }

        // 创建 NEW_MESSAGE 渠道通知栏  在API级别26.1.0中推荐使用此构造函数 Builder(context, 渠道名)
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)  NotificationCompat.Builder(context, channelId) else NotificationCompat.Builder(context)
        builder.setLargeIcon(BitmapFactory.decodeResource(context.resources, largeIcon)) // 设置自动收报机和通知中显示的大图标。
        .setSmallIcon(smallIcon) // 小图标
        .setContentText(contentText) // 内容
        .setContentTitle(contentTitle) // 标题
        .setSubText(subText) // APP名称的副标题
        .setPriority(priority) // 设置优先级 PRIORITY_DEFAULT
            .setTicker(ticker) // 设置通知首次弹出时，状态栏上显示的文本
        .setContent(view)
            .setWhen(System.currentTimeMillis()) // 设置通知发送的时间戳
            .setShowWhen(true)// 设置是否显示时间戳
            .setAutoCancel(true)// 点击通知后通知在通知栏上消失
            .setDefaults(getDefault())// 设置默认的提示音、振动方式、灯光等 使用的默认通知选项
            .setContentIntent(pendingIntent) // 设置通知的点击事件
            // 锁屏状态下显示通知图标及标题
            // 1、VISIBILITY_PUBLIC 在所有锁定屏幕上完整显示此通知
            // 2、VISIBILITY_PRIVATE 隐藏安全锁屏上的敏感或私人信息
            // 3、VISIBILITY_SECRET 不显示任何部分
            //.setVisibility(Notification.VISIBILITY_PRIVATE)// 部分手机没效果
            .setFullScreenIntent(pendingIntent, true)// 悬挂式通知8.0需手动打开
//                .setColorized(true)
//                .setGroupSummary(true)// 将此通知设置为一组通知的组摘要
//                .setGroup(NEW_GROUP)// 使用组密钥
//                .setDeleteIntent(pendingIntent)// 当用户直接从通知面板清除通知时 发送意图
//                .setFullScreenIntent(pendingIntent,true)
//                .setContentInfo("大文本")// 在通知的右侧设置大文本。
//                .setContent(RemoteViews RemoteView)// 设置自定义通知栏
//                .setColor(Color.parseColor("#ff0000"))
//                .setLights()//希望设备上的LED闪烁的argb值以及速率
//                .setTimeoutAfter(3000)//指定取消此通知的时间（如果尚未取消）。

        // 通知栏id
        manager.notify(notifyId, builder.build()) // build()方法需要的最低API为16 ,
    }

    /**
     * 判断应用通知是否打开
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun isNotificationEnabled(context:Context):Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            return notificationManagerCompat.areNotificationsEnabled()
        }
        val mAppOps =  context.getSystemService(Context.APP_OPS_SERVICE) as (AppOpsManager)
        val appOpsClass: Class<*>?
        /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager::class.java.name)
            val checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String::class.java)
            val opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION)
            val value =  opPostNotificationValue.get(Integer::class.java) as (Int)
            return ( checkOpNoThrowMethod.invoke(mAppOps, value, context.applicationInfo.uid, context.packageName) == AppOpsManager.MODE_ALLOWED)
        } catch (e:Exception) {
            e.printStackTrace()
        }
        return false
    }


    /**
     * 判断应用渠道通知是否打开（适配8.0）
     *
     * @return true 打开
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun openNotificationChannel(context:Context ,manager : NotificationManager,channelId:String ):Boolean {
        // 判断通知是否有打开
        if (!isNotificationEnabled(context)) {
            toNotifySetting(context, null)
            return false
        }
        // 判断渠道通知是否打开
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = manager.getNotificationChannel(channelId)
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                // 没打开调往设置界面
                toNotifySetting(context, channel.id)
                return false
            }
        }
        return true
    }

    /**
     * 手动打开应用通知
     */
    private fun toNotifySetting(context:Context,channelId: String? ) {
        val intent = Intent()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //适配 8.0及8.0以上(8.0需要先打开应用通知，再打开渠道通知)
            if (TextUtils.isEmpty(channelId)) {
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            } else {
                intent.action = Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
            }
        } else // 适配 5.0及5.0以上
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra("app_package", context.packageName)
            intent.putExtra("app_uid", context.applicationInfo.uid)
        context.startActivity(intent)
    }

    /**
     * 设定权限的默认值
     */
    private fun getDefault():Int{
        val default = 0
        if(sound){
            default.or(Notification.DEFAULT_SOUND)
        }

        if (vibrate){
            default.or(Notification.DEFAULT_VIBRATE)
        }

        if(lights){
            default.or(Notification.DEFAULT_LIGHTS)
        }

        return default
    }

    fun setDefault(isSound:Boolean = true,isVibrate:Boolean = true,isLights : Boolean = true){
        sound = isSound
        vibrate = isVibrate
        lights = isLights
    }

    fun clear(){

        if(this::notificationManager.isInitialized){
            notificationManager.cancelAll()
        }

    }

}