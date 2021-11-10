package abandonedstudio.app.focuser.ui.focusmethods.methodslist

import abandonedstudio.app.focuser.databinding.MethodItemBinding
import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethod
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MethodsListRVAdapter : RecyclerView.Adapter<MethodsListRVAdapter.MethodsListRVViewHolder>() {

    private lateinit var listener: OnItemClick
    private var methodsList = listOf<FocusMethod>()

    inner class MethodsListRVViewHolder(val binding: MethodItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<FocusMethod>) {
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
        holder.binding.methodNameTV.text = method.name
        holder.binding.starIB.setOnClickListener {
            listener.addToFavourite(method)
        }
        holder.binding.root.setOnClickListener {
            listener.openMethod(method)
        }
        holder.binding.root.setOnLongClickListener {
            listener.deleteMethod(method)
            true
        }
    }

    override fun getItemCount(): Int {
        return methodsList.size
    }

    interface OnItemClick {
        fun addToFavourite(method: FocusMethod)
        fun openMethod(method: FocusMethod)
        fun deleteMethod(method: FocusMethod)
    }

    fun setOnItemClickListener(listener: OnItemClick) {
        this.listener = listener
    }

}
