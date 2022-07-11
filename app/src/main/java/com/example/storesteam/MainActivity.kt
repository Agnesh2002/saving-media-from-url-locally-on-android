package com.example.storesteam

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.net.URL
import java.net.URLConnection


class MainActivity : AppCompatActivity() {

    private var url: URL = URL("http://techslides.com/demos/sample-videos/small.mp4")
    private var startTime = System.currentTimeMillis()

//    "http://192.168.1.12:8080/video"
//    "http://techslides.com/demos/sample-videos/small.mp4"
//    "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val b: Button = findViewById(R.id.btn_initiate)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }

        b.setOnClickListener(View.OnClickListener {

            //Open a connection to that URL.
            val urlConnection: URLConnection = url.openConnection()
            //this timeout affects how long it takes for the app to realize there's a //connection problem
            urlConnection.readTimeout = TIMEOUT_CONNECTION
            urlConnection.connectTimeout = TIMEOUT_SOCKET

            Thread(Runnable {
                val inputStream = urlConnection.getInputStream()
                val inStream = BufferedInputStream(inputStream, 1024 * 5)
                val outStream = FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/streamerec7.mkv")
                val buff = ByteArray(1024 * 5)
                //Read bytes (and store them) until there is nothing more to read(-1)
                var len: Int?
                while ((inStream.read(buff).also { len = it }) != -1) {
                    outStream.write(buff, 0, len!!)
                }

                //clean up
                outStream.flush()
                outStream.close()
                inStream.close()

                this.runOnUiThread(Runnable {
                    Toast.makeText(this, "Download completed in " + ((System.currentTimeMillis() - startTime) / 1000) + " sec", Toast.LENGTH_SHORT).show()
                })

                Log.i("STATUS", "download completed in " + ((System.currentTimeMillis() - startTime) / 1000) + " sec")
            }).start()

        })


    }

    companion object {
        const val TIMEOUT_CONNECTION = 5000//5sec
        const val TIMEOUT_SOCKET = 30000//30sec
    }

}


fun oldcode() {
//    var TIMEOUT_CONNECTION = 5000;//5sec
//    var TIMEOUT_SOCKET = 30000;//30sec
//    var url:URL = URL("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
//    var startTime = System.currentTimeMillis();
//    // Log.i(TAG, "image download beginning: "+imageURL);
//
//    //Open a connection to that URL.
//    var ucon: URLConnection = url.openConnection()
//
//    //this timeout affects how long it takes for the app to realize there's a //connection problem
//    ucon.readTimeout = TIMEOUT_CONNECTION;
//    ucon.connectTimeout = TIMEOUT_SOCKET;
//
//    Thread(Runnable {
//        var iS: InputStream = ucon.getInputStream()
//        var inStream: BufferedInputStream =  BufferedInputStream(iS, 1024 * 5);
//        var outStream: FileOutputStream=  FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/streamerec.mp4");
//        var buff = ByteArray(1024 * 5)
//
//        //Read bytes (and store them) until there is nothing more to read(-1)
//        var len: Int? = inStream.read(buff)
//        while (len!= -1)
//        {
//            outStream.write(buff,0,len!!);
//        }
//
//        //clean up
//        outStream.flush();
//        outStream.close();
//        inStream.close();
//
//        Log.i("STATUS", "download completed in "
//                + ((System.currentTimeMillis() - startTime) / 1000)
//                + " sec")
//    }).start()

}