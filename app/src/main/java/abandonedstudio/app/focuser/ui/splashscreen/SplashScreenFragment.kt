package abandonedstudio.app.focuser.ui.splashscreen

import abandonedstudio.app.focuser.R
import abandonedstudio.app.focuser.databinding.SplashScreenBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private val viewModel: SplashScreenViewModel by viewModels()
    private var _binding: SplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        set fullscreen
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

//        load saved theme
        viewModel.loadAndSetSavedTheme()

        val fadeInAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_1_5sec)

        binding.rocketLottie.animation = fadeInAnim

        lifecycleScope.launch {
            delay(4500L)
            Navigation.findNavController(view)
                .navigate(R.id.action_splashScreenFragment_to_mainDrawerHostFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        clear fullscreen so it won't affect other fragments
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        _binding = null
    }
}
