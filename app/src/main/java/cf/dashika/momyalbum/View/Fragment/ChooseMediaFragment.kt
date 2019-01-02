package cf.dashika.momyalbum.View.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import cf.dashika.momyalbum.Adapter.MediaAdapter
import cf.dashika.momyalbum.R
import cf.dashika.momyalbum.ViewModels.ChooseMediaViewModel
import cf.dashika.momyalbum.databinding.FragmentChooseMediaBinding
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.impl.OnItemClickListener


class ChooseMediaFragment : Fragment() {

    companion object {
        fun newInstance() = ChooseMediaFragment()
    }

    private lateinit var adapter: MediaAdapter
    private var mAlbumFiles: ArrayList<AlbumFile>? = null
    private lateinit var viewModel: ChooseMediaViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding =
            FragmentChooseMediaBinding.inflate(inflater, container, false)

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
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.bnvRemove -> {
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.bnvPreview -> {
                        return@setOnNavigationItemSelectedListener true
                    }

                }
                false
            }
        })

        binding.mediaList.adapter = adapter

        Album.album(this)
            .multipleChoice()
            .columnCount(2)
            .selectCount(10)
            .camera(true)
            .cameraVideoQuality(1)
            .cameraVideoLimitDuration(Integer.MAX_VALUE.toLong())
            .cameraVideoLimitBytes(Integer.MAX_VALUE.toLong())
            .checkedList(mAlbumFiles)
            .afterFilterVisibility(true)
            .onResult { result ->
                mAlbumFiles = result
                adapter.notifyDataSetChanged(mAlbumFiles!!)
            }
            .onCancel {
            }
            .start()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChooseMediaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}


