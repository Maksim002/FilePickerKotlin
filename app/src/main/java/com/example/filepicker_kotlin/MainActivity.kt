package com.example.filepicker_kotlin

import android.Manifest
import android.R.attr
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.nbsp.materialfilepicker.MaterialFilePicker
import com.nbsp.materialfilepicker.ui.FilePickerActivity
import com.timelysoft.tsjdomcom.service.Status
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    private var viewModel = MainViewModel()
    val FILE_PICKER_REQUEST_CODE = 1
    private var files = ArrayList<MultipartBody.Part>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_text.setOnClickListener {
            loadFiles()
        }
    }

    private fun loadFiles() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_DENIED
            ) {
                val permissions = arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                requestPermissions(permissions, FILE_PICKER_REQUEST_CODE)
            } else {
                launchPicker()
            }
        } else {
            launchPicker()
        }
    }

    private fun launchPicker() {
        MaterialFilePicker()
            .withActivity(this)
            .withRequestCode(FILE_PICKER_REQUEST_CODE)
            .withHiddenFiles(true)
//            .withPath(alarmsFolder.absolutePath)
            // Root path (user won't be able to come higher than it)
//            .withRootPath("/storage")
            .withFilter(Pattern.compile(".*\\.(xlsx|xls)$"))
            .withTitle("Select PDF file")
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val path: String? = data?.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)
            println()
            if (path != null) {
                val file = File(path)
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

                //попробуй отправить этот файл
                //попроуй сделать запрос в этом проекте еслё все хорошо проёжет то тогда бует на тот проект переходить
                val myfile = MultipartBody.Part.createFormData("File", file.name, requestFile)
                files.add(myfile)

                viewModel.saveFile(files).observe(this, Observer { result->
                    val msg = result.msg
                    when(result.status){
                        Status.SUCCESS ->{
                            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                        }
                        Status.NETWORK, Status.ERROR ->{
                            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }
    }
}
