package kz.rdd

import android.content.Context
import android.content.Intent
import kz.rdd.forefront_nav.MainActivityIntentReceiver

class MainActivityIntentReceiverImpl(
    private val context: Context
) : MainActivityIntentReceiver {
    override fun getIntent(): Intent {
        return Intent(context, MainActivity::class.java)
    }
}
