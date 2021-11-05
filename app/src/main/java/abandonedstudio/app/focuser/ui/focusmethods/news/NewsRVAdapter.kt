package abandonedstudio.app.focuser.ui.focusmethods.news

import abandonedstudio.app.focuser.databinding.MethodsNewsRvItemBinding
import abandonedstudio.app.focuser.model.remote.news.dto.NewsResponse
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NewsRVAdapter : RecyclerView.Adapter<NewsRVAdapter.NewsRVViewHolder>() {

    private var newsList = mutableListOf<NewsResponse>()

    inner class NewsRVViewHolder(val binding: MethodsNewsRvItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: MutableList<NewsResponse>) {
        if (list.isEmpty()) {
            return
        }
        newsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsRVViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MethodsNewsRvItemBinding.inflate(inflater)
        return NewsRVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsRVViewHolder, position: Int) {
        val news = newsList[holder.absoluteAdapterPosition]
        holder.binding.titleTV.text = news.title
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

}
