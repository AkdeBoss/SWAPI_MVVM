package com.happinessinc.getshwifty

import android.app.PictureInPictureParams
import android.content.Intent
import android.os.Bundle
import android.util.Rational
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.happinessinc.getshwifty.databinding.ActivityMainBinding
import com.happinessinc.getshwifty.repo.api.ApiResponse
import com.happinessinc.getshwifty.repo.api.LaunchEventsApiDataSource
import com.happinessinc.getshwifty.utils.DeveloperKey
import com.happinessinc.getshwifty.utils.YouTubeFailureRecoveryActivity
import com.happinessinc.getshwifty.viewmodels.MainViewModel
import com.happinessinc.getshwifty.views.adapters.LaunchEventRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var youTubePlayerView: YouTubePlayerView
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter:LaunchEventRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shimmerFrameLayout.startShimmer()
        adapter= LaunchEventRecyclerAdapter()

        launch_event_rv.layoutManager = LinearLayoutManager(this@MainActivity)
        launch_event_rv.adapter = adapter


        viewModel.data.observe(this@MainActivity, Observer {
            when (it.status) {
                ApiResponse.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        adapter.addList(ArrayList(it.data))
                        shimmerFrameLayout.visibility= View.GONE
                        refresh_layout.visibility= View.VISIBLE
                        refresh_layout.isRefreshing=false
                    }
                }
                ApiResponse.Status.ERROR ->{
                    Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    refresh_layout.visibility= View.GONE
                    refresh_layout.isRefreshing=false

                }

                ApiResponse.Status.LOADING ->{
                    shimmerFrameLayout.visibility= View.VISIBLE
                refresh_layout.visibility= View.GONE
                    refresh_layout.isRefreshing=true

                }

            }

        })

        refresh_layout.setOnRefreshListener {
            viewModel.forceFetchNetworkData()
        }

//        youTubePlayerView = findViewById(R.id.u_player)
//        youTubePlayerView.initialize(DeveloperKey.DEVELOPER_KEY, this)

    }

//    override fun onInitializationSuccess(
//            p0: YouTubePlayer.Provider?,
//            p1: YouTubePlayer?,
//            p2: Boolean
//    ) {
//        if(p1!=null) {
//            val videoId = "0a_00nJ_Y88"
//            p1.loadVideo(videoId, 0)
//            //Trigger PiP mode
//            try {
//                val rational = Rational(youTubePlayerView.getWidth(), youTubePlayerView.getHeight())
//                val mParams = PictureInPictureParams.Builder()
//                    .setAspectRatio(rational)
//                    .build()
//                enterPictureInPictureMode(mParams)
//            } catch (e: IllegalStateException) {
//                e.printStackTrace()
//            }
//        }
//
//
//
//    }
//
//    override fun getYouTubePlayerProvider(): YouTubePlayer.Provider {
//        return youTubePlayerView
//    }
}

abstract class YoutubeFragment : DialogFragment(), YouTubePlayer.OnInitializedListener {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }
    companion object {
        const val TAG = "PurchaseConfirmationDialog"
    }

    private val RECOVERY_DIALOG_REQUEST = 1

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?,
                                         errorReason: YouTubeInitializationResult) {
        if (errorReason.isUserRecoverableError) {
            errorReason.getErrorDialog(requireActivity(), RECOVERY_DIALOG_REQUEST).show()
        } else {
            val errorMessage = String.format(getString(R.string.error_player), errorReason.toString())
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
        }
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(DeveloperKey.DEVELOPER_KEY, this)
        }
    }

    abstract fun getYouTubePlayerProvider(): YouTubePlayer.Provider


}
