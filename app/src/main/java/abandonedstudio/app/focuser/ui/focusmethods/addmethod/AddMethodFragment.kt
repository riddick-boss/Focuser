package abandonedstudio.app.focuser.ui.focusmethods.addmethod

import abandonedstudio.app.focuser.R
import abandonedstudio.app.focuser.databinding.AddMethodBinding
import abandonedstudio.app.focuser.helpers.ui.addmethod.ErrorType
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddMethodFragment : Fragment() {

    private val viewModel: AddMethodViewModel by viewModels()
    private var _binding: AddMethodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddMethodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.validationErrorType.collectLatest {
                if (it.isEmpty()) {
                    return@collectLatest
                }
                if (ErrorType.NONE in it) {
                    viewModel.addMethod()
                    Toast.makeText(requireContext(), "Method added", Toast.LENGTH_SHORT).show()
                    setDefaultValuesToEditPoles()
                }
                if (ErrorType.EMPTY_NAME in it) {
                    binding.nameETL.error = getString(R.string.enter_name_error)
                }
                if (ErrorType.INTERVAL_ZERO in it) {
                    binding.intervalsTV.apply {
                        text = getString(R.string.intervals_error)
//                        TODO: set text color to red
                    }
                }
            }
        }

        binding.addB.setOnClickListener {
            val name = binding.nameETL.editText?.text.toString()
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.validateData(
                    name,
                    binding.intervalsSC.isChecked,
                    binding.hoursNP.value,
                    binding.minutesNP.value
                )
            }
        }

//        intervals field
        binding.intervalsSC.setOnCheckedChangeListener { _, isChecked ->
            toggleFieldVisibilityBasedOnSwitchState(isChecked, binding.intervalsCL)
        }
        setIntervalsNumPickerMinMax()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDefaultValuesToEditPoles() {
//        name edittext
        binding.nameETL.error = null
        binding.nameETL.editText?.text?.clear()
//        intervals field
        binding.intervalsSC.isChecked = true
        toggleFieldVisibilityBasedOnSwitchState(true, binding.intervalsCL)
        setIntervalsNumPickerMinMax()
    }

    private fun toggleFieldVisibilityBasedOnSwitchState(switchChecked: Boolean, fieldView: View) {
        fieldView.apply {
            visibility = if (switchChecked) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun toggleField(view: View) {
        view.apply {
            visibility = if (visibility == View.VISIBLE) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

    private fun setIntervalsNumPickerMinMax(hoursMin: Int = 0, hoursMax: Int = 24) {
        binding.hoursNP.apply {
            minValue = hoursMin
            maxValue = hoursMax
        }
        binding.minutesNP.apply {
            minValue = 0
            maxValue = 59
        }
    }

}
