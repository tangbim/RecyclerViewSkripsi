// MainActivity.kt
package com.example.skripsixml

import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.Choreographer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var shimmerRecyclerView: RecyclerView
    private lateinit var recyclerViewManager: RecyclerView.LayoutManager
    private lateinit var shimmerRecyclerViewManager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    private lateinit var shimmerAdapter: RecyclerView.Adapter<*>
    private lateinit var shimmerView: ShimmerFrameLayout
    private var startTimeRender: Long = 0
    private var endTime: Long = 0



    // Tambahkan FPSMonitor
    private val fpsMonitor = FPSMonitor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mulai Catat waktu awal aplikasi
        startTimeRender = SystemClock.elapsedRealtime()
        setContentView(R.layout.activity_main)

        shimmerView = findViewById(R.id.shimmer_view_container)
        shimmerRecyclerView = findViewById(R.id.shimmer_recycler_view)
        recyclerView = findViewById(R.id.recycler_view)

        shimmerRecyclerViewManager = LinearLayoutManager(this)
        recyclerViewManager = LinearLayoutManager(this)
        shimmerAdapter = ShimmerAdapter(20)
        myAdapter = MyAdapter(emptyList())

        shimmerRecyclerView.layoutManager = shimmerRecyclerViewManager
        shimmerRecyclerView.adapter = shimmerAdapter

        recyclerView.layoutManager = recyclerViewManager
        recyclerView.adapter = myAdapter


    }

    override fun onResume() {
        super.onResume()
        fpsMonitor.start()
        fetchBooks()


    }

    override fun onPause() {
        super.onPause()
        fpsMonitor.stop()
    }

    private fun fetchBooks() {
        val apiKey = "AIzaSyAhl2qVvUISf4YCw6p6lX72KtaPgXcGNCM"
        ApiClient.instance.getBooks("programming", apiKey,20)
            .enqueue(object : Callback<BookResponse> {
                override fun onResponse(
                    call: Call<BookResponse>,
                    response: Response<BookResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val books = response.body()!!.items.map {
                            Book(
                                bookImage = it.volumeInfo.imageLinks?.thumbnail ?: "",
                                bookTitle = it.volumeInfo.title,
                                bookYear = it.volumeInfo.publishedDate?: "Unknown"
                            )
                        }
                        // Update adapter dengan data yang baru diambil
                        myAdapter = MyAdapter(books)
                        recyclerView.adapter = myAdapter

                        // Hentikan efek shimmer dan tampilkan RecyclerView
                        shimmerView.stopShimmer()
                        endTime = SystemClock.elapsedRealtime()
                        val renderingTime = endTime - startTimeRender
                        shimmerView.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE

                        // Catat waktu selesai dan hitung waktu rendering

                        Log.i("Rendering time","Rendering time XML: $renderingTime ms")
                    }
                }

                override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                    println("Error fetching data: ${t.message}")
                }
            })
    }



    class FPSMonitor {
        private var frameCount = 0
        private var startTimeFps = 0L
        private val frameCallback = object : Choreographer.FrameCallback {
            override fun doFrame(frameTimeNanos: Long) {
                frameCount++
                val currentTime = SystemClock.elapsedRealtime()
                val elapsedTime = currentTime - startTimeFps
                if (elapsedTime >= 1000) {
                    val fps = frameCount / (elapsedTime / 1000.0)
                    println("FPS: $fps")
                    frameCount = 0
                    startTimeFps = currentTime
                }
                Choreographer.getInstance().postFrameCallback(this)
            }
        }
        fun start() {
            startTimeFps = SystemClock.elapsedRealtime()
            Choreographer.getInstance().postFrameCallback(frameCallback)
        }

        fun stop() {
            Choreographer.getInstance().removeFrameCallback(frameCallback)
        }


    }
}
