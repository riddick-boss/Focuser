package abandonedstudio.app.focuser.ui.focusmethods.method

import abandonedstudio.app.focuser.databinding.MethodBinding
import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethod
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MethodFragment : Fragment() {

    private val viewModel: MethodViewModel by viewModels()
    private var _binding: MethodBinding? = null
    private val binding get() = _binding!!

    private val args: MethodFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MethodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            loadMethodUI(viewModel.getMethod(args.methodId))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //    TODO: try to reorganize code, so importing FocusMethod will NOT be needed
    private fun loadMethodUI(method: FocusMethod) {
        binding.nameTV.text = method.name
    }
}