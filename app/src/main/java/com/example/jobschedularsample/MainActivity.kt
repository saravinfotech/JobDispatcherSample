package com.example.jobschedularsample

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.jobschedularsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var count=0
    var myCustomJob = MyCustomJob()
    lateinit var jobScheduler:JobScheduler
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        binding.startJobBtn.setOnClickListener {
            startJob()
        }

        binding.stopJobBtn.setOnClickListener{
            stopJob()
        }
    }

    private fun stopJob() {
        Log.i(TAG,"Job cancelled")
        jobScheduler.cancel(101)
    }

    private fun startJob() {
        var componentName = ComponentName(this,MyCustomJob::class.java)
        var jobInfo = JobInfo.Builder(101,componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_CELLULAR)
                .setPeriodic(15*60*1000)
                .setPersisted(true)
                .build()

        if(jobScheduler.schedule(jobInfo) == JobScheduler.RESULT_SUCCESS){
            Log.i(TAG,"Thread id is "+Thread.currentThread().id+"' job successfully scheduled")
        }else{
            Log.i(TAG,"Thread id is "+Thread.currentThread().id+"' job could not be scheduled")
        }
    }
}