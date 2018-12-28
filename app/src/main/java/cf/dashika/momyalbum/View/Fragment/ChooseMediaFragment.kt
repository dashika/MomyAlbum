package cf.dashika.momyalbum.View.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cf.dashika.momyalbum.R

class ChooseMediaFragment : Fragment() {

    companion object {
        fun newInstance() = ChooseMediaFragment()
    }

    private lateinit var viewModel: ChooseMediaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choose_media_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChooseMediaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
