package abandonedstudio.app.focuser.ui.focusmethods.news

import abandonedstudio.app.focuser.databinding.MethodsNewsBinding
import abandonedstudio.app.focuser.ui.focusmethods.methodslist.MethodsListViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupNewsRV() = binding.newsRV.apply {
        newsAdapter = NewsRVAdapter()
        adapter = newsAdapter
        layoutManager = LinearLayoutManager(requireContext())
        setupListForNewsRV()
    }

    private fun setupListForNewsRV(){
//        TODO: fetch data from remote
        val list = mutableListOf("fs", "dkdkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk")
        newsAdapter.submitList(list)
    }


}