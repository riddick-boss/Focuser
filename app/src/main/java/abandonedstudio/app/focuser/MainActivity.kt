package abandonedstudio.app.focuser

import abandonedstudio.app.focuser.databinding.ActivityMainBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

//    TODO: nav to fragment
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}
