package com.example.memeapp

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

    lateinit var memeImage: ImageView
    lateinit var progressBar: ProgressBar


    var url2:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memeImage = findViewById(R.id.memeImage)
        progressBar = findViewById(R.id.progressBar)

        val next: Button = findViewById(R.id.next)
        val share: Button = findViewById(R.id.share)

        next.setOnClickListener {
            loadData()
        }

        share.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plane"
            intent.putExtra(Intent.EXTRA_TEXT, "by Meme App $url2")
            val chooser = Intent.createChooser(intent , "share meme...")
            startActivity(chooser)
        }

        loadData()


    }

    private fun loadData() {
        val url = "https://meme-api.herokuapp.com/gimme"
        progressBar.isVisible = true
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null, { response ->
                url2 = response.getString("url")
                Glide.with(this).load(url2).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                }).into(memeImage)
            }, {
                Toast.makeText(this, "Check internet connection", Toast.LENGTH_SHORT).show()
            })

        MySingleton.getInstance(this).addToRequestQueue(JsonObjectRequest)
    }
}