package cf.dashika.momyalbum.View

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import cf.dashika.momyalbum.R
import cf.dashika.momyalbum.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        navController = Navigation.findNavController(this, R.id.nav_album_fragment)
    }

}
