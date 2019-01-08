package cf.dashika.momyalbum.View.Fragment

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cf.dashika.momyalbum.Adapter.MediaAdapter
import cf.dashika.momyalbum.R
import cf.dashika.momyalbum.ViewModels.ChooseMediaViewModel
import cf.dashika.momyalbum.databinding.FragmentChooseMediaBinding
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.impl.OnItemClickListener


class ChooseMediaFragment : Fragment() {

    private lateinit var adapter: MediaAdapter

    companion object {
        fun newInstance() = ChooseMediaFragment()
    }

    private lateinit var viewModel: ChooseMediaViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentChooseMediaBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@ChooseMediaFragment)
        }

        initMedias(binding)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChooseMediaViewModel::class.java)
        openAlbum()
    }

    fun onCreateOptionsMenu(menu: Menu): Boolean {
        activity!!.menuInflater.inflate(R.menu.menu_choose_media, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.bnvSave -> {
                viewModel.save()
            }
        }
        return true
    }

    private fun initMedias(binding: FragmentChooseMediaBinding) {
        val layoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true)
        layoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())
        binding.mediaList.setHasFixedSize(true)
        binding.mediaList.layoutManager = layoutManager
        binding.mediaList.addOnScrollListener(CenterScrollListener())
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(view: RecyclerView, scrollState: Int) {
                binding.bnvMedia.visibility = GONE
            }
        }
        binding.mediaList.addOnScrollListener(scrollListener)

        adapter = MediaAdapter(context, OnItemClickListener { view, position ->
            binding.bnvMedia.visibility = VISIBLE
            binding.bnvMedia.setOnNavigationItemSelectedListener { item ->
                binding.bnvMedia.visibility = GONE
                when (item.itemId) {
                    R.id.bnvEdit -> {
                        val directions =
                            ChooseMediaFragmentDirections.ActionChooseMediaFragmentToEditMediaFragment(viewModel.albumFiles!![position].path)
                        view.findNavController().navigate(directions)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.bnvRemove -> {
                        viewModel.albumFiles!!.remove(viewModel.albumFiles!![position])
                        adapter.notifyItemChanged(position)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.bnvPreview -> {
                        previewAlbum(position)
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                false
            }
        })

        binding.mediaList.adapter = adapter
    }

    private fun openAlbum() {
        Album.album(this)
            .multipleChoice()
            .columnCount(2)
            .selectCount(10)
            .camera(true)
            .cameraVideoQuality(1)
            .cameraVideoLimitDuration(Integer.MAX_VALUE.toLong())
            .cameraVideoLimitBytes(Integer.MAX_VALUE.toLong())
            .checkedList(viewModel.albumFiles)
            .onResult { result ->
                viewModel.albumFiles = result
                adapter.notifyDataSetChanged(viewModel.albumFiles!!)
            }
            .onCancel {
            }
            .start()
    }

    private fun previewAlbum(position: Int) {
        if (viewModel.albumFilesIsEmpty()) {
        } else {
            Album.galleryAlbum(this)
                .checkable(true)
                .checkedList(viewModel.albumFiles)
                .currentPosition(position)
                .onResult { result ->
                    viewModel.albumFiles = result
                    adapter.notifyDataSetChanged(viewModel.albumFiles!!)
                }
                .start()
        }
    }
}


