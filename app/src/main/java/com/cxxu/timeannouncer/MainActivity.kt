package com.cxxu.timeannouncer

import Lg.d
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.SystemClock.sleep
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.cxxu.timeannouncer.StringTool.showToast
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    lateinit var timeChangeReceiver: TimeChangeReceiver
    lateinit var viewModel: MainViewModel
    lateinit var timeText :TextView
    val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var timeText = findViewById<TextView>(R.id.timeText)
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_TICK")
        timeChangeReceiver = TimeChangeReceiver()
        /**android.content.ContextWrapper :: public android.content.Intent registerReceiver(android.content.BroadcastReceiver receiver,
        android.content.IntentFilter filter)*/
        registerReceiver(timeChangeReceiver, intentFilter)

//        MainViewModel实例必须通过ViewModelProvider间接获取(灵活性/兼容性),ViewModel具有独立的声明周期>Activity
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        /*执行内容刷新*/
//        while (true) {
//            "while..".showToast()
//            refreshCounter()
//        }
        Lg.d(TAG,"执行了refresher()")
        counterViewRefresher()
    }

    /*testing...*/
    private fun timeRefresher() {
//        var timeText = findViewById<TextView>(R.id.timeText)
        val PERIOD = 1000
//        while (true){
//        /*明明定义的变量timeText在后面用了,但AS还是提示没有用到?,
//        那么考虑您的程序是否永远也执行不到那个使用该变量的地方(比如这些使用变量的代码写在了某个死循环之后)*/
//        }
        sleep(PERIOD.toLong())
        val sec = calendar.get(Calendar.SECOND)
        viewModel.counter = sec
        Lg.d(TAG, "test the while..")
        timeText.text = viewModel.counter.toString()
    }

    /*countAdd refresh*/
    private fun counterViewRefresher() {
        var timeText = findViewById<TextView>(R.id.timeText)
        timeText.text = viewModel.counter.toString()
    }

    fun countAddBtn(view:View){
        viewModel.counter++
        counterViewRefresher()

    }
    fun countSubBtn(view: View){
        viewModel.counter--
        counterViewRefresher()
    }
    lateinit var manager: NotificationManager

    private fun createNotificationChannel() {
        d(TAG, "creating NotificationChannel...")
        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        android O 就是android sdk26:(字母O不是数字0)
//        SDK_INT 表示当前运行该软件的android sdk版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 =
                NotificationChannel("normal", "Normal", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel1)
            val channel2 =
                NotificationChannel("important", "Important", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel2)
        }
    }

    private fun buildNotificationContent(str: String): Notification {
        d(TAG, "building the NotificationContent...")
        /*利用androidX中的NotificationCompat.Builder实例化的对象可以兼容老的sdk级别*/
        val notification = NotificationCompat.Builder(this, "important")
            .setContentTitle("Time Notice!")
            .setContentText(str)
            .setSmallIcon(R.drawable.clock_icon_foreground)
            .setAutoCancel(true)
            .build()
        return notification
    }

    override fun onDestroy() {
        super.onDestroy()
//        动态注册的BroadCastReceiver需要注销
        unregisterReceiver(timeChangeReceiver)
    }


    inner class TimeChangeReceiver : BroadcastReceiver() {

        var min = 0
        override fun onReceive(context: Context, intent: Intent) {
/*注意,由于这里接收的广播是每分钟一次广播(而秒的变化的广播(可能没有),自然无法试试播报秒数)*/
            val calendar = Calendar.getInstance()

            min = calendar.get(Calendar.MINUTE)
            Lg.d(TAG, "minute:$min")
            "$min".showToast()
//            Toast.makeText(context, "${min.toString()}", Toast.LENGTH_LONG).show()
            if (min % 30 == 0) {
                val hour = calendar.get(Calendar.HOUR)
//                Lg.d(TAG, "now preparing notification")
                min = calendar.get(Calendar.MINUTE)
                Lg.d(TAG, "minute:$min")
                val contentStr = "the time is $hour:$min  now"
                createNotificationChannel()
                val notification = buildNotificationContent(contentStr)
                manager.notify(1, notification)
            }


        }

    }

    /*监听按钮方法(注意参数,注意,采用这种方式注册点击事件,您的函数不可以使用private,同时参数为view:View 方可被布局编辑器识别到*/
    fun showTime(view: View) {
        val calendar = Calendar.getInstance()
        val h = calendar.get(Calendar.HOUR)
        val min = calendar.get(Calendar.MINUTE)
        val sec = calendar.get(Calendar.SECOND)
        "现在时刻:$h:$min:$sec".showToast()
    }

}