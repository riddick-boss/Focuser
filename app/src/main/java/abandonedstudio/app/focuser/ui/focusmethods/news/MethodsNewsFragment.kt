package abandonedstudio.app.focuser.ui.focusmethods.news

import abandonedstudio.app.focuser.R
import abandonedstudio.app.focuser.databinding.MethodsNewsBinding
import abandonedstudio.app.focuser.model.remote.news.dto.NewsResponse
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MethodsNewsFragment : Fragment() {

    private val viewModel: MethodsNewsViewModel by viewModels()
    private var _binding: MethodsNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsAdapter: NewsRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MethodsNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNewsRV()
        viewLifecycleOwner.lifecycleScope.launch {
            setupListForNewsRV()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupNewsRV() = binding.newsRV.apply {
        newsAdapter = NewsRVAdapter()
        adapter = newsAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private suspend fun setupListForNewsRV() {
        val newsList = viewModel.getNews()
        if (newsList.isNotEmpty()) {
            newsAdapter.submitList(newsList as MutableList<NewsResponse>)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.default_error_message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
