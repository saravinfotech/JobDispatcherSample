package com.example.jobschedularsample

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import java.util.*

class MyCustomJob():JobService() {

    var randomNumber = 0
    var isRandomNumberGeneratorMode = false
    val TAG = "MyCustomJob"

    val min =0
    val max = 100

    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.i(TAG, "onJobStarted")
        doBackGroundWork()
        return true;
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
       return true
    }


    private fun doBackGroundWork(){
        Thread((Runnable {
            isRandomNumberGeneratorMode = true
            startRandomNumberGenerator()
        })).start()
    }
    private fun startRandomNumberGenerator(){
        while (isRandomNumberGeneratorMode){
            try{
                Thread.sleep(1000)
                if(isRandomNumberGeneratorMode){
                    randomNumber = Random().nextInt(max)+min
                    Log.i(
                        TAG,
                        "Thread id : ${Thread.currentThread().id}" + ", Random Number :" + randomNumber
                    )
                }
            }catch (e: Exception){
                Log.e(TAG, e.localizedMessage)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRandomNumberGenerator()
        Log.i(TAG,"Service Destroyed")
    }

    private fun stopRandomNumberGenerator() {
        isRandomNumberGeneratorMode = false
    }
}