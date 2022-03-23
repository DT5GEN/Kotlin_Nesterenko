package com.gb.kotlin_1728_2_1.lesson_5

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.gb.kotlin_1728_2_1.R
import com.gb.kotlin_1728_2_1.databinding.ActivityMainWebviewBinding
import com.gb.kotlin_1728_2_1.view.main.MainFragment
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection
import kotlin.concurrent.thread

class MainActivityWebView : AppCompatActivity() {


    lateinit var binding: ActivityMainWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWebviewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    var clickListener: View.OnClickListener = object : View.OnClickListener {
        @RequiresApi(Build.VERSION_CODES.N)

        override fun onClick(p0: View?) {
            try {
                val url = URL(binding.etUrl.text.toString())
                val handler = Handler()
                Thread {
                    var httpsURLConnection: HttpsURLConnection? = null

                    try {
                        val httpsURLConnection = (url.openConnection() as HttpsURLConnection)
                            .apply {
                                requestMethod = "GET"
                                readTimeout = 10000
                            }
                        val bufferedReader =
                            BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
                        val resultConnection = convertBufferToResult(bufferedReader)
                        handler.post {
                            binding.webView.loadDataWithBaseURL(
                                null, resultConnection,
                                "text/html; charset=utf-8",
                                "utf-8", null
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("e", "Fail connection", e)
                        e.printStackTrace()
                    } finally {
                        httpsURLConnection?.disconnect()
                    }
                }.start()
            } catch (e: MalformedURLException) {
                Log.e("i", "Fail URI", e)
                e.printStackTrace()
            }
        }



        private fun convertBufferToResult(bufferedReader: BufferedReader): String {
            return bufferedReader.lines().collect(Collectors.joining("\n"))
        }

    }


}