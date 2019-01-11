package cf.dashika.momyalbum.View.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import cf.dashika.momyalbum.Model.Entity.Mamy
import cf.dashika.momyalbum.ViewModels.MamyProfileViewModel
import cf.dashika.momyalbum.databinding.FragmentMamyProfileBinding
import java.util.*

class MamyProfileFragment : Fragment() {

    companion object {
        fun newInstance() = MamyProfileFragment()
    }

    private lateinit var viewModel: MamyProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMamyProfileBinding.inflate(inflater, container, false)
            .apply {
                setLifecycleOwner(this@MamyProfileFragment)
            }
        setHasOptionsMenu(true)
        val mamy = getArgs()
        initView(mamy, binding)

        return binding.root
    }

    private fun initView(mamy: Mamy?, binding: FragmentMamyProfileBinding) {
        if (mamy == null) return
        binding.tvName.setText(mamy.name)
        binding.tvBirthday.setText(Date(mamy.birthday).toString())
        binding.tvCity.setText(mamy.city)
    }

    private fun getArgs() = MamyProfileFragmentArgs.fromBundle(arguments!!).mamy

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MamyProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
