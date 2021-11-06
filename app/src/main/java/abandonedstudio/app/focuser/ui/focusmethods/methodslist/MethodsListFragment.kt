package abandonedstudio.app.focuser.ui.focusmethods.methodslist

import abandonedstudio.app.focuser.databinding.MethodsListBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MethodsListFragment : Fragment() {

    private val viewModel: MethodsListViewModel by viewModels()
    private var _binding: MethodsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var methodsListAdapter: MethodsListRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MethodsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        recyclerView
        setupMethodsRV()
        setupListForMethodsRV()

//        favourite method on top of the screen
        showFavouriteMethodIfExists()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupMethodsRV() = binding.methodsRV.apply {
        methodsListAdapter = MethodsListRVAdapter()
        adapter = methodsListAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupListForMethodsRV() {
        viewLifecycleOwner.lifecycleScope.launch {
            methodsListAdapter.submitList(viewModel.getMethods(viewModel.getSavedFavouriteMethodId()))
        }
    }

    private fun showFavouriteMethodIfExists() {
        viewLifecycleOwner.lifecycleScope.launch {
            val method = viewModel.getSavedFavouriteMethodId()
            if (method == null) {
                binding.favouriteMethodINC.root.visibility = View.GONE
                binding.dividerV.visibility = View.GONE
            } else {
//                here set name of method and action on click
            }
        }
    }

}
