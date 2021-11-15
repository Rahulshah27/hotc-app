package com.example.hotcapp.presentation.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.hotcapp.R
import com.example.hotcapp.common.Constants
import com.example.hotcapp.presentation.model.FileModel
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.activity_splash.*
import java.io.File

class HomeActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val filePaths = "/emulated/0/HOTC/"
    private val folderPath = "/emulated/0/HOTC/"
    internal var myExternalFile: File? = null
    private val isExternalStorageReadOnly: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)
        }
    private val isExternalStorageAvailable: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED.equals(extStorageState)
        }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        WindowInsetsControllerCompat(window, activitySplash).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.isAppearanceLightStatusBars = true
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            myExternalFile = File(getStorageDirectory(), "HOTC")
//        }


        if (savedInstanceState == null) {
//            val filesListFragment = HomeFragment.build {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    path = File(Environment.getStorageDirectory(), "HOTC").absolutePath
//                }
//            }
            val bundle = Bundle()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                bundle.putString(filePaths,Environment.getStorageDirectory().path+filePaths)
                val fragment = HomeFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment)
                    .commit()
            }
        }

    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun addReplaceFragment(fragment: Fragment?, addOrReplace: Int, backStackValue: String) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        when (addOrReplace) {
            1 -> {

                val bundle = Bundle()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    bundle.putString(filePaths,Environment.getStorageDirectory().path+filePaths)
                }else{
                    bundle.putString(filePaths,"storage/$filePaths")
                }

                fragment?.arguments = bundle
                transaction.replace(nav_fragment.id, fragment!!)
                manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

//                Toast.makeText(this,supportFragmentManager.backStackEntryCount.toString(),Toast.LENGTH_SHORT).show()

                transaction.commit()
            }
            2 -> {

                val bundle = Bundle()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    bundle.putString(filePaths,Environment.getStorageDirectory().path+filePaths)
                }
                fragment?.arguments = bundle
                transaction.replace(nav_fragment.id, fragment!!)
                transaction.addToBackStack(backStackValue)
                transaction.commit()

            }
            else -> {

                transaction.replace(nav_fragment.id, fragment!!)
                manager.popBackStack()
                transaction.commit()

            }
        }
    }
    fun getFilesFromPath(path: String, showHiddenFiles: Boolean = true, onlyFolders: Boolean = true): List<File> {
        val file = File(path)
        return file.listFiles()
            .filter { onlyFolders }
            .toList()
    }
    fun getFileModelsFromFiles(files: List<File>): List<FileModel> {
        return files.map {
            FileModel(it.path, Constants.FileType.getFileType(it), it.name, convertFileSizeToMB(it.length()), it.extension, it.listFiles()?.size
                ?: 0)
        }
    }

    fun convertFileSizeToMB(sizeInBytes: Long): Double {
        return (sizeInBytes.toDouble()) / (1024 * 1024)
    }


}