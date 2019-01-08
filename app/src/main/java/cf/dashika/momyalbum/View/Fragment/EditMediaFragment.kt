package cf.dashika.momyalbum.View.Fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import cf.dashika.momyalbum.Adapter.EditMedia.EditingToolsAdapter
import cf.dashika.momyalbum.Adapter.EditMedia.FilterListener
import cf.dashika.momyalbum.Adapter.EditMedia.FilterViewAdapter
import cf.dashika.momyalbum.Adapter.EditMedia.ToolType
import cf.dashika.momyalbum.R
import cf.dashika.momyalbum.View.Fragment.EditMedia.EmojiBSFragment
import cf.dashika.momyalbum.View.Fragment.EditMedia.PropertiesBSFragment
import cf.dashika.momyalbum.View.Fragment.EditMedia.StickerBSFragment
import cf.dashika.momyalbum.View.Fragment.EditMedia.TextEditorDialogFragment
import cf.dashika.momyalbum.ViewModels.EditMediaViewModel
import cf.dashika.momyalbum.databinding.FragmentEditMediaBinding
import com.livinglifetechway.quickpermissions.annotations.WithPermissions
import ja.burhanrashid52.photoeditor.*
import kotlinx.android.synthetic.main.fragment_edit_media.*
import java.io.File
import java.io.IOException

class EditMediaFragment : Fragment(), OnPhotoEditorListener,
    View.OnClickListener,
    PropertiesBSFragment.Properties,
    EmojiBSFragment.EmojiListener,
    StickerBSFragment.StickerListener, EditingToolsAdapter.OnItemSelected, FilterListener {

    private var propertiesBSFragment: PropertiesBSFragment? = null
    private var emojiBSFragment: EmojiBSFragment? = null
    private var stickerBSFragment: StickerBSFragment? = null
    private val editingToolsAdapter = EditingToolsAdapter(this)
    private val filterViewAdapter = FilterViewAdapter(this)
    private var imgEditor: PhotoEditor? = null
    private var isFilterVisible: Boolean = false

    private val constraintSet = ConstraintSet()
    private var rootView: ConstraintLayout? = null
    private var rvTools: RecyclerView? = null
    private var rvFilters: RecyclerView? = null
    private var tvCurrentTool: TextView? = null

    companion object {
        fun newInstance() = EditMediaFragment()
    }

    private lateinit var viewModel: EditMediaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditMediaBinding.inflate(inflater, container, false)

        val path = getArgs()

        binding.pevEditMedia.source.setImageURI(Uri.parse(path))

        propertiesBSFragment = PropertiesBSFragment()
        emojiBSFragment = EmojiBSFragment()
        stickerBSFragment = StickerBSFragment()
        stickerBSFragment!!.setStickerListener(this)
        emojiBSFragment!!.setEmojiListener(this)
        propertiesBSFragment!!.setPropertiesChangeListener(this)
        binding.rvFilterView.adapter = filterViewAdapter
        binding.rvConstraintTools.adapter = editingToolsAdapter

        imgEditor = PhotoEditor.Builder(activity, binding.pevEditMedia)
            .setPinchTextScalable(true)
            .build()
        imgEditor!!.setOnPhotoEditorListener(this)

        rootView = binding.rootView
        rvTools = binding.rvConstraintTools
        rvFilters = binding.rvFilterView
        tvCurrentTool = binding.tvCurrentTool

        binding.imgUndo.setOnClickListener(this)
        binding.imgRedo.setOnClickListener(this)
        binding.imgSave.setOnClickListener(this)
        binding.imgClose.setOnClickListener(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditMediaViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imgUndo -> imgEditor!!.undo()
            R.id.imgRedo -> imgEditor!!.redo()
            R.id.imgSave -> saveImage()
            R.id.imgClose -> closePanel()
        }
    }

    private fun closePanel() {
        if (isFilterVisible) run {
            showFilter(false)
            tvCurrentTool!!.setText(R.string.app_name)
        } else if (!imgEditor!!.isCacheEmpty) run { showSaveDialog() }
    }

    @SuppressLint("MissingPermission")
    @WithPermissions(
        permissions = [Manifest.permission.WRITE_EXTERNAL_STORAGE]
    )
    private fun saveImage() {
        if (requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //showLoading("Saving...")

            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "mamyPicture"
                        + File.separator + ""
                        + System.currentTimeMillis() + ".png"
            )

            try {
                file.createNewFile()

                val saveSettings = SaveSettings.Builder()
                    .setClearViewsEnabled(true)
                    .setTransparencyEnabled(true)
                    .build()

                this.imgEditor!!.saveAsFile(file.absolutePath, saveSettings, object : PhotoEditor.OnSaveListener {
                    override fun onSuccess(@NonNull imagePath: String) {
                        //   hideLoading()
                        // showSnackbar("Image Saved Successfully")
                        pevEditMedia.source.setImageURI(Uri.fromFile(File(imagePath)))
                        MediaStore.Images.Media.insertImage(
                            activity!!.contentResolver,
                            file.absolutePath,
                            file.name,
                            file.name
                        )
                    }

                    override fun onFailure(@NonNull exception: Exception) {
                        // hideLoading()
                        // showSnackbar("Failed to save Image")
                    }
                })
            } catch (e: IOException) {
                e.printStackTrace()
                //hideLoading()
                //showSnackbar(e.message)
            }
        }
    }

    val READ_WRITE_STORAGE = 52

    private fun requestPermission(permission: String): Boolean {
        val isGranted = ContextCompat.checkSelfPermission(activity!!, permission) == PackageManager.PERMISSION_GRANTED
        if (!isGranted) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(permission),
                READ_WRITE_STORAGE
            )
        }
        return isGranted
    }

    override fun onColorChanged(colorCode: Int) {
        imgEditor!!.brushColor = colorCode
        tvCurrentTool!!.setText(R.string.label_brush)
    }

    override fun onOpacityChanged(opacity: Int) {
        imgEditor!!.setOpacity(opacity)
        tvCurrentTool!!.setText(R.string.label_brush)
    }

    override fun onBrushSizeChanged(brushSize: Int) {
        imgEditor!!.brushSize = brushSize.toFloat()
        tvCurrentTool!!.setText(R.string.label_brush)
    }

    override fun onEmojiClick(emojiUnicode: String) {
        imgEditor!!.addEmoji(emojiUnicode)
        tvCurrentTool!!.setText(R.string.label_emoji)
    }

    override fun onStickerClick(bitmap: Bitmap) {
        imgEditor!!.addImage(bitmap)
        tvCurrentTool!!.setText(R.string.label_sticker)
    }

    private fun showSaveDialog() {
        val builder = AlertDialog.Builder(activity!!)
        builder.setMessage("Are you want to exit without saving image ?")
        builder.setPositiveButton("Save") { _, _ -> saveImage() }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.dismiss() }

        //builder.setNeutralButton("Discard", { dialog, which -> finish() })
        builder.create().show()
    }

    override fun onFilterSelected(photoFilter: PhotoFilter) {
        imgEditor!!.setFilterEffect(photoFilter)
    }

    override fun onToolSelected(toolType: ToolType) {
        when (toolType) {
            cf.dashika.momyalbum.Adapter.EditMedia.ToolType.BRUSH -> {
                imgEditor!!.setBrushDrawingMode(true)
                tvCurrentTool!!.setText(R.string.label_brush)
                propertiesBSFragment!!.show(fragmentManager!!, propertiesBSFragment!!.tag)
            }
            cf.dashika.momyalbum.Adapter.EditMedia.ToolType.TEXT -> {
                val textEditorDialogFragment = TextEditorDialogFragment.show(activity!!)
                textEditorDialogFragment.setOnTextEditorListener { inputText, colorCode ->
                    imgEditor!!.addText(inputText, colorCode)
                    tvCurrentTool!!.setText(R.string.label_text)
                }
            }
            cf.dashika.momyalbum.Adapter.EditMedia.ToolType.ERASER -> {
                imgEditor!!.brushEraser()
                tvCurrentTool!!.setText(R.string.label_eraser)
            }
            cf.dashika.momyalbum.Adapter.EditMedia.ToolType.FILTER -> {
                tvCurrentTool!!.setText(R.string.label_filter)
                showFilter(true)
            }
            cf.dashika.momyalbum.Adapter.EditMedia.ToolType.EMOJI -> emojiBSFragment!!.show(
                fragmentManager!!,
                emojiBSFragment!!.tag
            )
            cf.dashika.momyalbum.Adapter.EditMedia.ToolType.STICKER -> stickerBSFragment!!.show(
                fragmentManager!!,
                stickerBSFragment!!.tag
            )
        }
    }


    private fun showFilter(isVisible: Boolean) {
        isFilterVisible = isVisible
        constraintSet.clone(rootView)

        if (isVisible) {
            constraintSet.clear(rvFilters!!.id, ConstraintSet.START)
            constraintSet.connect(
                rvFilters!!.id, ConstraintSet.START,
                ConstraintSet.PARENT_ID, ConstraintSet.START
            )
            constraintSet.connect(
                rvFilters!!.id, ConstraintSet.END,
                ConstraintSet.PARENT_ID, ConstraintSet.END
            )
        } else {
            constraintSet.connect(
                rvFilters!!.id, ConstraintSet.START,
                ConstraintSet.PARENT_ID, ConstraintSet.END
            )
            constraintSet.clear(rvFilters!!.id, ConstraintSet.END)
        }

        val changeBounds = ChangeBounds()
        changeBounds.duration = 350
        changeBounds.interpolator = AnticipateOvershootInterpolator(1.0f)
        TransitionManager.beginDelayedTransition(rootView!!, changeBounds)

        constraintSet.applyTo(rootView)
    }

    private fun getArgs() = EditMediaFragmentArgs.fromBundle(arguments!!).path

    override fun onEditTextChangeListener(rootView: View, text: String, colorCode: Int) {
        val textEditorDialogFragment = TextEditorDialogFragment.show(activity!!, text, colorCode)
        textEditorDialogFragment.setOnTextEditorListener { inputText, colorCode ->
            imgEditor!!.editText(rootView, inputText, colorCode)
            tvCurrentTool!!.setText(R.string.label_text)
        }
    }

    override fun onAddViewListener(viewType: ViewType, numberOfAddedViews: Int) {
    }

    override fun onRemoveViewListener(numberOfAddedViews: Int) {
    }

    override fun onRemoveViewListener(viewType: ViewType, numberOfAddedViews: Int) {
    }

    override fun onStartViewChangeListener(viewType: ViewType) {
    }

    override fun onStopViewChangeListener(viewType: ViewType) {
    }

}
