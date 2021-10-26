package abandonedstudio.app.focuser.ui.maindrawerhost

import abandonedstudio.app.focuser.R
import abandonedstudio.app.focuser.databinding.MainDrawerHostBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainDrawerHostFragment : Fragment() {

    private val viewModel: MainDrawerHostViewModel by viewModels()
    private var _binding: MainDrawerHostBinding? = null
    private val binding get() = _binding!!

    private lateinit var drawerAppBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

//        onBackPressed go to home fragment from main_drawer_nav_graph.xml
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Close app?")
                    .setNeutralButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("OK") { _, _ ->
                        requireActivity().finish()
                    }.show()
            }
        }
    }

    //    on hamburger click open drawer nav view
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainDrawerHostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setup drawer
        val appCompatActivity: AppCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(binding.toolbar)
        val drawerNavHostFragment =
            childFragmentManager.findFragmentById(R.id.drawer_nav_host) as NavHostFragment
        val drawerNavController = drawerNavHostFragment.navController
        val topDestinations = setOf(
            R.id.FocusMethodsHostFragment
        )
        drawerAppBarConfiguration = AppBarConfiguration(topDestinations, binding.drawerLayout)
        appCompatActivity.setupActionBarWithNavController(
            drawerNavController,
            drawerAppBarConfiguration
        )
        binding.drawerNavView.setupWithNavController(drawerNavController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}