package abandonedstudio.app.focuser.ui.focusmethods.method

import abandonedstudio.app.focuser.databinding.MethodBinding
import abandonedstudio.app.focuser.helpers.service.IntervalServiceHelper
import abandonedstudio.app.focuser.service.IntervalService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception

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
            viewModel.loadMethod(args.methodId)
            loadMethodUI()
        }

        binding.startMethodB.setOnClickListener {
            if (viewModel.intervalState) {
//                start interval service
                deliverActionToService(IntervalServiceHelper.ACTION_START_OT_RESUME_SERVICE)
                toggleIntervalUI()
            }
        }

        IntervalService.millisCountDownInterval.asLiveData().observe(viewLifecycleOwner, {
            binding.intervalCountingTV.text =
                IntervalServiceHelper.remainingTimeMinutesFromMillisText(it)
            try {
                binding.intervalCountingPB.progress =
                    IntervalServiceHelper.remainingTimeMinutesFromMillis(it)
            } catch (e: Exception) {
                Log.d("timer", e.toString())
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadMethodUI() {
        binding.nameTV.text = viewModel.name
        if (viewModel.intervalState) {
            binding.intervalsCL.visibility = View.VISIBLE
            binding.intervalHoursTV.text = viewModel.intervalHours.toString()
            binding.intervalMinutesTV.text = viewModel.intervalMinutes.toString()
            binding.initervalRepetitionsTV.text = viewModel.intervalRepetitions.toString()
        } else {
            binding.intervalsCL.visibility = View.GONE
        }
    }

//    private fun intervalField()

    //    deliver intent to service -> not starting service every time function is called
    private fun deliverActionToService(action: String) =
        Intent(requireContext(), IntervalService::class.java).also {
            it.action = action
            it.putExtra(IntervalServiceHelper.FLAG_HOURS, viewModel.intervalHours)
            it.putExtra(IntervalServiceHelper.FLAG_MINUTES, viewModel.intervalMinutes)
            it.putExtra(IntervalServiceHelper.FLAG_REPETITIONS, viewModel.intervalRepetitions)
            it.putExtra(IntervalServiceHelper.FLAG_BREAK_DURATION, viewModel.intervalBreak)
            requireContext().startService(it)
        }

    private fun toggleIntervalUI() {
        binding.intervalHoursTV.visibility = View.INVISIBLE
        binding.intervalIndicatorHTV.visibility = View.INVISIBLE
        binding.intervalMinutesTV.visibility = View.INVISIBLE
        binding.intervalIndicatorMinTV.visibility = View.INVISIBLE
        binding.intervalCountingTV.visibility = View.VISIBLE
        binding.intervalCountingPB.apply {
            visibility = View.VISIBLE
            max = viewModel.intervalHours * 60 + viewModel.intervalMinutes
            progress = max
        }
    }

}
