package abandonedstudio.app.focuser.ui.focusmethods.host

import abandonedstudio.app.focuser.R
import abandonedstudio.app.focuser.databinding.FocusMethodsHostBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FocusMethodsHostFragment : Fragment() {

    private var _binding: FocusMethodsHostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FocusMethodsHostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val navHostFragment = childFragmentManager.findFragmentById(R.id.focus_method_nav_host) as NavHostFragment
//        val bottomNavController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(Navigation.findNavController(requireActivity(), R.id.focus_method_nav_host))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}