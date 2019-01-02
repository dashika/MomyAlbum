package cf.dashika.momyalbum.View.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import cf.dashika.momyalbum.Adapter.AlbumAdapter
import cf.dashika.momyalbum.DI.InjectorUtils
import cf.dashika.momyalbum.R
import cf.dashika.momyalbum.ViewModels.AlbumViewModel
import cf.dashika.momyalbum.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAlbumBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@AlbumFragment)
            fabAddRemember.setOnClickListener { view ->
                view.findNavController().navigate(R.id.action_albumFragment_to_chooseMediaFragment)
            }
        }
        val adapter = AlbumAdapter()
        binding.rememberList.adapter = adapter
        subscribeUi(adapter, binding)

        return binding.root
    }

    private fun subscribeUi(adapter: AlbumAdapter, binding: FragmentAlbumBinding) {
        val factory = InjectorUtils.mamyListViewModelFactory(requireContext())
        val viewModel = ViewModelProviders.of(this, factory)
            .get(AlbumViewModel::class.java)

    }

}
