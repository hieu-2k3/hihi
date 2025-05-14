package com.example.demonews.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demonews.databinding.ItemNewsBinding
import com.example.demonews.model.Article
interface NewsInterface{
    fun itemClicked(article: Article)
}
class NewsAdapter(private var context: Context) : RecyclerView.Adapter<NewsAdapter.itemHolder>(){
    private var arr = arrayListOf<Article>()
    var listener: NewsInterface? = null
    inner class itemHolder(private var binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root){
        fun setUpView(article: Article){
            Glide.with(context)
                .load(article.urlToImage)
                .into(binding.imgNews)

            binding.tvTitle.text = article.title

            binding.tvDes.text = article.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return itemHolder(binding)
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: itemHolder, position: Int) {
        val article = arr[position]
        holder.setUpView(article)
        holder.itemView.setOnClickListener {
            listener?.itemClicked(article)
        }
    }

    fun setUpList(newArr: List<Article>){
        arr = newArr as ArrayList<Article>
        notifyDataSetChanged()
    }
}