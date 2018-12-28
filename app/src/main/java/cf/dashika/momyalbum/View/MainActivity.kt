package cf.dashika.momyalbum.View

import android.app.ProgressDialog.show
import android.os.Bundle
import androidx.annotation.UiThread
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import cf.dashika.momyalbum.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            cf.dashika.momyalbum.R.layout.activity_main
        )

        navController = Navigation.findNavController(this, cf.dashika.momyalbum.R.id.nav_album_fragment)

    }


    @UiThread
    suspend fun makeNetworkRequest() {
        // slowFetch is another suspend function so instead of
        // blocking the main thread  makeNetworkRequest will `suspend` until the result is
        // ready
        val result = slowFetch()
        // continue to execute after the result is ready
        show(this, "", result)
    }

    suspend fun slowFetch(): String {

        return "";
    }

}
