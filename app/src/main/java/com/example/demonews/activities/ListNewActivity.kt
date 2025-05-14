package com.example.demonews.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demonews.R
import com.example.demonews.adapter.NewsAdapter
import com.example.demonews.adapter.NewsInterface
import com.example.demonews.databinding.ActivityListNewBinding
import com.example.demonews.model.Article
import com.example.demonews.model.NewsResponse
import com.example.demonews.network.RetrofitClient
import retrofit2.Call
import androidx.core.net.toUri

class ListNewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListNewBinding
    private var adapter: NewsAdapter? = null
    private var arr: ArrayList<Article>? = null
    private var search: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindData()
        setUpView()
    }

    private fun bindData(){
        var type = intent.getStringExtra("type")
        if(type == "technology"){
            binding.edtNews.visibility = View.GONE
            RetrofitClient.instance.getAppleNews(apiKey = "087aba7ae1bf4fd78d4f94d6d6f4c3b5")
                .enqueue(object : retrofit2.Callback<NewsResponse> {
                    override fun onResponse(
                        call: Call<NewsResponse>,
                        response: retrofit2.Response<NewsResponse>
                    ) {
                        if (response.isSuccessful) {
                            val articles = response.body()?.articles
//                        articles?.forEach {
//                            println("Title: ${it.title}")
//                        }
                            articles?.let { adapter?.setUpList(it) }
                        } else {
                            Log.d("err", "Lỗi server: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        Log.d("loi mang", "Lỗi mạng: ${t.message}")
                    }
                })
        }else if(type == "TopBussiness"){
            binding.edtNews.visibility = View.VISIBLE
            RetrofitClient.instance1.getNewsByQuery("car", apiKey = "087aba7ae1bf4fd78d4f94d6d6f4c3b5")
                .enqueue(object : retrofit2.Callback<NewsResponse> {
                    override fun onResponse(
                        call: Call<NewsResponse>,
                        response: retrofit2.Response<NewsResponse>
                    ) {
                        if (response.isSuccessful) {
                            val articles = response.body()?.articles
//                        articles?.forEach {
//                            println("Title: ${it.title}")
//                        }
                            articles?.let { adapter?.setUpList(it) }
                        } else {
                            Log.d("err", "Lỗi server: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        Log.d("loi mang", "Lỗi mạng: ${t.message}")
                    }
                })
        }
    }

    private fun setUpView(){
        adapter = NewsAdapter(this)
        adapter?.listener = object : NewsInterface{
            override fun itemClicked(article: Article) {
                val url = article.url
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                startActivity(intent)
            }

        }
        binding.rcListNews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcListNews.adapter = adapter

        binding.edtNews.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                search = s.toString()
            }

        })

        binding.edtNews.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.edtNews.text.toString()

                search = query

                RetrofitClient.instance1.getNewsByQuery(search, apiKey = "087aba7ae1bf4fd78d4f94d6d6f4c3b5")
                    .enqueue(object : retrofit2.Callback<NewsResponse> {
                        override fun onResponse(
                            call: Call<NewsResponse>,
                            response: retrofit2.Response<NewsResponse>
                        ) {
                            if (response.isSuccessful) {
                                val articles = response.body()?.articles
//                        articles?.forEach {
//                            println("Title: ${it.title}")
//                        }
                                articles?.let { adapter?.setUpList(it) }
                            } else {
                                Log.d("err", "Lỗi server: ${response.code()}")
                            }
                        }

                        override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                            Log.d("loi mang", "Lỗi mạng: ${t.message}")
                        }
                    })
                true
            } else {
                false
            }
        }
    }
}