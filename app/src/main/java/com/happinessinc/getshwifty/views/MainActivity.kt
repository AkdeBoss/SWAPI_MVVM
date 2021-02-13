package com.happinessinc.getshwifty

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.youtube.player.YouTubePlayerView
import com.happinessinc.getshwifty.databinding.ActivityMainBinding
import com.happinessinc.getshwifty.repo.api.ApiResponse
import com.happinessinc.getshwifty.repo.models.LaunchEvent
import com.happinessinc.getshwifty.viewmodels.MainViewModel
import com.happinessinc.getshwifty.views.EventDialogFragment
import com.happinessinc.getshwifty.views.OfflineDialogFragment
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
        startNetworkCallback()

        adapter= LaunchEventRecyclerAdapter()

        launch_event_rv.layoutManager = LinearLayoutManager(this@MainActivity)
        launch_event_rv.adapter = adapter

        adapter.clickListner(object :LaunchEventRecyclerAdapter.eventListner{
            override fun onItemClick(event: LaunchEvent) {
                if(viewModel.isOnline.value!!) {

                    val ft = supportFragmentManager.beginTransaction()
                    val newFragment = EventDialogFragment.newInstance(event.id)
                    newFragment.show(ft, "Details")

                }else{
                    val offlineFragment = OfflineDialogFragment()
                    offlineFragment.show(supportFragmentManager, "Offline")
                }
            }

        })


        viewModel.mutableData.observe(this@MainActivity, Observer {
            if(it!=null)
                when (it.status) {
                    ApiResponse.Status.SUCCESS -> {
                        if (!it.data.isNullOrEmpty()) {
                            adapter.addList(ArrayList(it.data))
                            shimmerFrameLayout.visibility = View.GONE
                            refresh_layout.visibility = View.VISIBLE
                            refresh_layout.isRefreshing = false
                        }
                    }
                    ApiResponse.Status.ERROR -> {

                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()

                        refresh_layout.visibility = View.GONE
                        refresh_layout.isRefreshing = false
                        shimmerFrameLayout.stopShimmer()
                        shimmerFrameLayout.visibility = View.GONE


                    }

                    ApiResponse.Status.LOADING -> {
                        shimmerFrameLayout.visibility = View.VISIBLE
                        refresh_layout.isRefreshing = true
                    }

                }


        })

        refresh_layout.setOnRefreshListener {
            if(viewModel.isOnline.value!!) {
                viewModel.forceFetchNetworkData()
            }else{
                refresh_layout.isRefreshing=false
                val offlineFragment = OfflineDialogFragment()
                offlineFragment.show(supportFragmentManager, "Offline")
            }
        }
    }


override fun onWindowFocusChanged(hasFocus: Boolean) {
    super.onWindowFocusChanged(hasFocus)
    if (hasFocus) hideSystemUI()
}

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    fun startNetworkCallback() {
        val cm: ConnectivityManager =
            application.getSystemService(CONNECTIVITY_SERVICE)    as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()
        cm.registerNetworkCallback(
            builder.build(),
            object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    runOnUiThread {
                   viewModel.isOnline.postValue(true)
                    }
                }

                override fun onLost(network: Network) {
                    runOnUiThread {
                        viewModel.isOnline.postValue(false)
                    }
                }
            })
    }

}

