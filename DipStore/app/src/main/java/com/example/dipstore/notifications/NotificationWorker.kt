package com.example.dipstore.notifications

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.dipstore.common.showNotification
import com.example.dipstore.common.sleep
import com.example.dipstore.database.DatabaseActivity

class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        val appContext = applicationContext

        //makeStatusNotification("work started",appContext)
        showNotification(appContext,"",DatabaseActivity::class.java,"Work started")
        Log.d("started","work started")
        sleep(9)
        Log.d("finished","work finished")
        //makeStatusNotification("work finished",appContext)
        showNotification(appContext,"",DatabaseActivity::class.java,"Work Finished")
        return Result.retry()
    }

}