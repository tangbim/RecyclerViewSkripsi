package com.example.skripsixml

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout

class MainActivity : AppCompatActivity() {
    private val books = listOf(
        Book(R.drawable.buku1, "Python Programming in Context", "2019"),
        Book(R.drawable.buku2, "Core Python Programming", "2001"),
        Book(
            R.drawable.buku3,
            "The C# Programming Language (Covering C# 4.0), Portable Documents",
            "2010"
        ),
        Book(R.drawable.buku4, "Programming PHP", "2002"),
        Book(R.drawable.buku5, "Beginner's Step-by-Step Coding Course", "2020"),
        Book(R.drawable.buku6, "Java Programming for Beginners", "2017"),
        Book(R.drawable.buku7, "Rust Programming By Example", "2018"),
        Book(
            R.drawable.buku8,
            "An Experiential Introduction to Principles of Programming Languages",
            "2022"
        ),
        Book(R.drawable.buku9, "Learn to Code by Solving Problems", "2021"),
        Book(R.drawable.buku10, "Understanding Programming Languages", "1996"),
        Book(R.drawable.buku11, "Planning Extreme Programming", "2001"),
        Book(R.drawable.buku12, "Masterminds of Programming", "2009"),
        Book(R.drawable.buku13, "The Science of Programming", "1981"),
        Book(R.drawable.buku14, "Introduction to Programming Languages", "2013"),
        Book(R.drawable.buku15, "COMPUTER PROGRAMMING IN C, SECOND EDITION", "2007"),
        Book(R.drawable.buku16, "A Programming Primer", "2013"),
        Book(R.drawable.buku17, "How to Design Programs, second edition", "2018"),
        Book(R.drawable.buku18, "You Can Do It!", "2004"),
        Book(R.drawable.buku19, "The Ruby Programming Language", "2008"),
        Book(R.drawable.buku20, "Elementary Synchronous Programming", "2019"),
    )

    private lateinit var recyclerView: RecyclerView
    private lateinit var shimmerRecyclerView: RecyclerView
    private lateinit var recyclerViewManager: RecyclerView.LayoutManager
    private lateinit var shimmerRecyclerViewManager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    private lateinit var shimmerAdapter: RecyclerView.Adapter<*>
    private lateinit var shimmerView: ShimmerFrameLayout
    private var startTime: Long = 0
    private var endTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        // Atur LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Buat Adapter dengan data
        val adapter = MyAdapter(books)

        // Atur Adapter ke RecyclerView
        recyclerView.adapter = adapter

    }
}


