package com.cxxu.timeannouncer

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
/*让这个类继承字Application,以保证其他子类不受影响*/
class GetContextApplication :Application(){
    /**由于这里获取的不是Activity或Service中的Context，而是Application
    中的Context，它全局只会存在一份实例，并且在整个应用程序的生命周
    期内都不会回收，因此是不存在内存泄漏风险的。那么我们可以使用如下
    注解，让Android Studio忽略上述警告提示：
    要告知系统，当程序启动的时候应该初始化
    本Application子类，而不是默认的Application类*/
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}