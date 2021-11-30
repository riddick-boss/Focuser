package abandonedstudio.app.focuser.ui.focusmethods.addmethod

import abandonedstudio.app.focuser.R
import abandonedstudio.app.focuser.databinding.AddMethodBinding
import abandonedstudio.app.focuser.helpers.ui.addmethod.ErrorType
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
                Log.d("validation", "coroutine")
                if (it.isEmpty()) {
                    Log.d("validation", "cancel")
                    return@collectLatest
                }
                if (ErrorType.NONE in it) {
                    Log.d("validation", "adding")
                    viewModel.addMethod()
                    Toast.makeText(requireContext(), "Method added", Toast.LENGTH_SHORT).show()
                    setDefaultValuesToEditPoles()
                }
                if (ErrorType.EMPTY_NAME in it) {
                    Log.d("validation", "empty name")
                    binding.nameETL.error = getString(R.string.enter_name_error)
                }
                if (ErrorType.INTERVAL_ZERO in it) {
                    Log.d("validation", "interval zero")
                    binding.intervalsTV.apply {
                        text = getString(R.string.intervals_error)
                        setTextColor(Color.parseColor("#FF0000"))
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
                    binding.minutesNP.value,
                    binding.repetitionsNP.value,
                    binding.intervalBreakNP.value
                )
            }
        }

//        intervals field
        binding.intervalsSC.isChecked = viewModel.intervalsState
        binding.intervalsSC.setOnCheckedChangeListener { _, isChecked ->
            viewModel.intervalsState = isChecked
            toggleFieldVisibilityBasedOnSwitchState(isChecked, binding.intervalsCL)
        }
        setIntervalsNumPickers()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDefaultValuesToEditPoles() {
        viewModel.setInitialValues()
//        name edittext
        binding.nameETL.error = null
        binding.nameETL.editText?.text?.clear()
//        intervals field
        binding.intervalsSC.isChecked = true
        toggleFieldVisibilityBasedOnSwitchState(true, binding.intervalsCL)
        setIntervalsNumPickers()
        binding.intervalsTV.apply {
            text = getString(R.string.intervals)
            setTextColor(Color.parseColor("#651FFF"))
        }
    }

    //    remember to toggle variable in viewModel also
    private fun toggleFieldVisibilityBasedOnSwitchState(switchChecked: Boolean, fieldView: View) {
        fieldView.apply {
            visibility = if (switchChecked) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun setIntervalsNumPickers(
        hoursMin: Int = 0,
        hoursMax: Int = 24,
        repetitionsMin: Int = 1,
        repetitionsMax: Int = 99,
        breakMin: Int = 1,
        breakMax: Int = 600
    ) {
        binding.hoursNP.apply {
            minValue = hoursMin
            maxValue = hoursMax
            value = viewModel.intervalHours
        }
        binding.minutesNP.apply {
            minValue = 0
            maxValue = 59
            value = viewModel.intervalMinutes
        }
        binding.repetitionsNP.apply {
            minValue = repetitionsMin
            maxValue = repetitionsMax
            value = viewModel.intervalRepetitions
        }
        binding.intervalBreakNP.apply {
            minValue = breakMin
            maxValue = breakMax
            value = viewModel.intervalBreak
        }
    }

}
