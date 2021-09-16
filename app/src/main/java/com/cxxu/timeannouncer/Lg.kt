import android.util.Log

/**
 * the LogUtil singleton class will convenient the debug and toggle the mode whether to print
 * logs in the console in all kinds of levels
 * it mainly sealed the original log utils to the LogUtil*/

object Lg {


    private const val VERBOSE = 1

    private const val DEBUG = 2

    private const val INFO = 3

    private const val WARN = 4

    private const val ERROR = 5

    private var level = DEBUG

    val stackTrace = Thread.currentThread().stackTrace

    /*getThreadStackTrace-> getStackTrace-> onCreate(调用栈(越早越深)*/
//        val methodName=stackTrace[0].methodName+" "
//        val methodName1=stackTrace[1].methodName+" "
    fun getLgInvokerName():String {
        /*根据实际情况将4调整为3或其他数值*/
        return stackTrace[4].methodName + "():"
    }


    @JvmStatic
    fun v(tag: String, msg: String) {
        if (level <= VERBOSE) {
            val msg= getLgInvokerName()+msg
            Log.v(tag, msg)
        }
    }

    @JvmStatic
    fun d(tag: String, msg: String) {
//        val stackTrace = Thread.currentThread().stackTrace
//        /*getThreadStackTrace-> getStackTrace-> onCreate(调用栈(越早越深)*/
////        val methodName=stackTrace[0].methodName+" "
////        val methodName1=stackTrace[1].methodName+" "
//        val lgDInvoker = stackTrace[3].methodName + " "
        val msg= getLgInvokerName()+msg
        if (level <= DEBUG) {
            Log.d(tag,  msg)
        }
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        if (level <= INFO) {
            Log.i(tag, msg)
        }
    }

    @JvmStatic
    fun w(tag: String, msg: String) {
        if (level <= WARN) {
            Log.w(tag, msg)
        }
    }

    @JvmStatic
    fun e(tag: String, msg: String) {
        if (level <= ERROR) {
            Log.e(tag, msg)
        }
    }

}
