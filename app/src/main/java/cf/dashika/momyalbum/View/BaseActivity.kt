package cf.dashika.momyalbum.View

import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity() {

    private val READ_WRITE_STORAGE = 52
    private var progressDialog: ProgressDialog? = null

    fun requestPermission(permission: String): Boolean {
        val isGranted = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        if (!isGranted) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                READ_WRITE_STORAGE
            )
        }
        return isGranted
    }

    fun isPermissionGranted(isGranted: Boolean, permission: String) {

    }


    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        when (requestCode) {
            READ_WRITE_STORAGE -> isPermissionGranted(
                grantResults[0] == PackageManager.PERMISSION_GRANTED,
                permissions[0]
            )
        }
    }

    fun showLoading(@NonNull message: String) {
        progressDialog = ProgressDialog(this, android.R.attr.progressBarStyleSmall)
        progressDialog!!.setMessage(message)
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    fun hideLoading() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
        }
    }

    fun showSnackbar(@NonNull message: String) {
        val view = findViewById<View>(android.R.id.content)
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}