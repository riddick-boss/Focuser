package abandonedstudio.app.focuser.ui.gloablsettings

import abandonedstudio.app.focuser.databinding.GlobalSettingsBinding
import abandonedstudio.app.focuser.helpers.ui.theming.ThemeMode
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GlobalSettingsFragment : Fragment() {

    private val viewModel: GlobalSettingsViewModel by viewModels()
    private var _binding: GlobalSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GlobalSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            getSavedThemeAndCheckButton()
        }

        binding.lightThemeRB.setOnClickListener {
            viewModel.setLightTheme()
        }

        binding.nightThemeRB.setOnClickListener {
            viewModel.setNightTheme()
        }

        binding.autoThemeRB.setOnClickListener {
            viewModel.setAutoTheme()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun getSavedThemeAndCheckButton(){
        when(viewModel.loadSavedTheme().first()){
            ThemeMode.LIGHT -> binding.lightThemeRB.isChecked = true
            ThemeMode.NIGHT -> binding.nightThemeRB.isChecked = true
            ThemeMode.AUTO -> binding.autoThemeRB.isChecked = true
        }
    }
}