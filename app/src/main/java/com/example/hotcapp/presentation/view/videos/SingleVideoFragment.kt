package com.example.hotcapp.presentation.view.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hotcapp.R
import com.example.hotcapp.common.Constants
import com.example.hotcapp.presentation.view.HomeActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_list_videos.*
import kotlinx.android.synthetic.main.fragment_single_videos.*


class SingleVideoFragment : Fragment() {

    private var player: SimpleExoPlayer? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvName.text = null
        ivArrowBack.setOnClickListener {
            (activity as HomeActivity).onBackPressed()
        }


        loadData()

    }
    private fun loadData() {
        try {


            //Video list thumbnails
            val fileName = (activity as HomeActivity).getFilesFromPath(Constants.folderName!!).filter {
                it.name == Constants.VIDEO
            }.mapNotNull {f1->
                f1.listFiles()!!.filter {
                        f2->f2.name == Constants.videoFolderSelected!!.name
                }.mapNotNull {
                        f3->
                    f3.listFiles()!!.filter {

                        it.name == Constants.videoSelected!!.name

                    }.mapNotNull {
//                           videoPlayer.setVideoURI(Uri.parse(it.path))
                        player = SimpleExoPlayer.Builder(requireContext())
                            .build()
                            .also { exoPlayer ->
                                videoPlayer.player = exoPlayer
                                val mediaItem = MediaItem.fromUri(it.absolutePath)
                                exoPlayer.setMediaItem(mediaItem)
                            }




//                           val videoMedia = MediaController(context)
//                           videoMedia.setAnchorView(videoPlayer)
//                           videoPlayer.setMediaController(videoMedia)
//
//                           videoMedia.setMediaPlayer(videoPlayer)
//                           videoPlayer.requestFocus()
//                           videoPlayer.setZOrderMediaOverlay(true)
//
//                           videoPlayer.start()
                    }

                }
            }







        }catch (e:Exception){
            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        player?.release()
    }



    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }
}