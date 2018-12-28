package cf.dashika.momyalbum.View.Fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import cf.dashika.momyalbum.Adapter.MediaAdapter
import cf.dashika.momyalbum.DI.InjectorUtils
import cf.dashika.momyalbum.ViewModels.ChooseMediaViewModel
import cf.dashika.momyalbum.databinding.ChooseMediaFragmentBinding
import com.yanzhenjie.album.Action
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import com.yanzhenjie.album.impl.OnItemClickListener
import com.yanzhenjie.album.widget.divider.Api21ItemDivider
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CenterScrollListener






class ChooseMediaFragment : Fragment() {

    companion object {
        fun newInstance() = ChooseMediaFragment()
    }

    private lateinit var adapter: MediaAdapter
    private var mAlbumFiles: ArrayList<AlbumFile>? = null
    private lateinit var viewModel: ChooseMediaViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = cf.dashika.momyalbum.databinding.ChooseMediaFragmentBinding.inflate(inflater, container, false)


        val divider = Api21ItemDivider(Color.TRANSPARENT, 10, 10)
        binding.mediaList.addItemDecoration(divider)

        val layoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true)
        layoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())

        binding.mediaList.layoutManager = layoutManager
        binding.mediaList.addOnScrollListener(CenterScrollListener())

        adapter = MediaAdapter(context, object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                //  previewAlbum(position)
            }
        })

        binding.mediaList.adapter = adapter

     //   subscribeUi(adapter, binding)

        Album.album(this)
            .multipleChoice()
            .columnCount(2)
            .selectCount(10)
            .camera(true)
            .cameraVideoQuality(1)
            .cameraVideoLimitDuration(Integer.MAX_VALUE.toLong())
            .cameraVideoLimitBytes(Integer.MAX_VALUE.toLong())
            .checkedList(mAlbumFiles)
            .widget(
                Widget.newDarkBuilder(activity)
                    //.title(mToolbar.getTitle().toString())
                    .build()
            )
            .onResult { result ->
                mAlbumFiles = result
                adapter.notifyDataSetChanged(mAlbumFiles!!)
            }
            .onCancel {
            }
            .start()

        return binding.root
    }
//
//    private fun subscribeUi(adapter: MediaAdapter, binding: ChooseMediaFragmentBinding) {
//        val factory = InjectorUtils.mamyListViewModelFactory(requireContext())
//        val viewModel = ViewModelProviders.of(this, factory)
//            .get(ChooseMediaViewModel::class.java)
//
//    }
}
