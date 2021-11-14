package abandonedstudio.app.focuser

import abandonedstudio.app.focuser.databinding.ActivityMainBinding
import abandonedstudio.app.focuser.helpers.service.IntervalServiceHelper
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Pawel Kremienowski <Kremienowski33@gmail.com>
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        performIntentAction(intent)
    }

    private fun performIntentAction(intent: Intent?) {
        when (intent?.action) {
            IntervalServiceHelper.ACTION_SHOW_METHOD_FRAGMENT -> {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.focus_method_walkthrough_host) as NavHostFragment
                navHostFragment.findNavController().navigate(R.id.action_global_methodFragment)
            }
        }
    }

}
