package abandonedstudio.app.focuser.ui.focusmethods.addmethod

import abandonedstudio.app.focuser.databinding.AddMethodBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

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

        binding.addB.setOnClickListener {
            val name = binding.nameETL.editText?.text.toString()
            if (name.isBlank()){
                binding.nameETL.error = "Enter method name"
            } else {
                viewModel.addMethod(name)
                setDefaultValuesToEditPoles()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDefaultValuesToEditPoles(){
        binding.nameETL.error = null
        binding.nameETL.editText?.text?.clear()
    }

}
