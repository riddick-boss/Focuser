package abandonedstudio.app.focuser.ui.focusmethods.methodslist

import abandonedstudio.app.focuser.databinding.MethodItemBinding
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MethodsListRVAdapter : RecyclerView.Adapter<MethodsListRVAdapter.MethodsListRVViewHolder>() {

    private var methodsList = mutableListOf<String>()

    inner class MethodsListRVViewHolder(val binding: MethodItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: MutableList<String>) {
        if (list.isEmpty()) {
            return
        }
        methodsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MethodsListRVViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MethodItemBinding.inflate(inflater)
        return MethodsListRVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MethodsListRVViewHolder, position: Int) {
        val method = methodsList[holder.absoluteAdapterPosition]
        holder.binding.methodNameTV.text = method
    }

    override fun getItemCount(): Int {
        return methodsList.size
    }
}