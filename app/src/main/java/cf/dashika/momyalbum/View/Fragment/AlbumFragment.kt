package cf.dashika.momyalbum.View.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import cf.dashika.momyalbum.Adapter.AlbumAdapter
import cf.dashika.momyalbum.DI.InjectorUtils
import cf.dashika.momyalbum.ViewModels.AlbumViewModel
import cf.dashika.momyalbum.databinding.AlbumFragmentBinding

class AlbumFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = AlbumFragmentBinding.inflate(inflater, container, false)
        val adapter = AlbumAdapter()
        binding.rememberList.adapter = adapter
        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: AlbumAdapter, binding: AlbumFragmentBinding) {
        val factory = InjectorUtils.mamyListViewModelFactory(requireContext())
        val viewModel = ViewModelProviders.of(this, factory)
            .get(AlbumViewModel::class.java)

    }

}
