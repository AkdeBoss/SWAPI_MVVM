package com.happinessinc.getshwifty.views

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.happinessinc.getshwifty.R
import com.happinessinc.getshwifty.databinding.FragmentLaunchDetailsBinding
import com.happinessinc.getshwifty.utils.DeveloperKey
import com.happinessinc.getshwifty.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EventDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentLaunchDetailsBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //For night mode theme

        binding = FragmentLaunchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSingleEventData(requireArguments().getString("id")!!)
        viewModel.eventData.observe(viewLifecycleOwner, Observer { response ->
            if (response.data != null) {


                val fragment: YouTubePlayerFragment? = childFragmentManager.findFragmentById(R.id.child_fragment_container) as YouTubePlayerFragment?
                fragment?.initialize(
                    DeveloperKey.DEVELOPER_KEY,
                    object : YouTubePlayer.OnInitializedListener {
                        override fun onInitializationSuccess(
                            p0: YouTubePlayer.Provider?,
                            p1: YouTubePlayer?,
                            p2: Boolean
                        ) {
                            try {
                                p1!!.cueVideo(response.data.links!!.youtube_id)
                            } catch (ex: Exception) {

                            }
                        }

                        override fun onInitializationFailure(
                            p0: YouTubePlayer.Provider?,
                            p1: YouTubeInitializationResult?
                        ) {
                            Log.e("YOUTUBE", p1.toString())
                        }

                    })


                binding.wikiBtn.setOnClickListener {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(response.data.links!!.wikipedia)
                    startActivity(i)
                }


                binding.youtubeBtn.setOnClickListener {
                    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
                    val webIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(response.data.links!!.webcast)
                    )
                    try {
                        requireActivity().startActivity(appIntent)
                    } catch (ex: ActivityNotFoundException) {
                        requireActivity().startActivity(webIntent)
                    }
                }


                binding.closeBtn.setOnClickListener { dismiss() }
                binding.event = response.data
                binding.isError = false
            } else {
                binding.isError = true
                binding.errorView.errorText.text = response.message
                binding.errorView.okButn.setOnClickListener {
                    dismiss()
                }
            }
        })
    }

    override fun getTheme(): Int {
        return R.style.FullScrnTheme
    }

    companion object {

        /**
         * Create a new instance of CustomDialogFragment, providing "num" as an
         * argument.
         */
        fun newInstance(id: String): EventDialogFragment {
            val f = EventDialogFragment()
            // Supply num input as an argument.
            val args = Bundle()
            args.putString("id", id)
            f.arguments = args
            return f
        }
    }
}
