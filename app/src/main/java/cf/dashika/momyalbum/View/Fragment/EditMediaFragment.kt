package cf.dashika.momyalbum.View.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import cf.dashika.momyalbum.ViewModels.EditMediaViewModel
import cf.dashika.momyalbum.databinding.FragmentEditMediaBinding

class EditMediaFragment : Fragment() {

    companion object {
        fun newInstance() = EditMediaFragment()
    }

    private lateinit var viewModel: EditMediaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            FragmentEditMediaBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditMediaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
