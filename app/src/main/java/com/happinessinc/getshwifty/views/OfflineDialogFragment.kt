package com.happinessinc.getshwifty.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.happinessinc.getshwifty.databinding.FragmentLaunchDetailsBinding
import com.happinessinc.getshwifty.databinding.FragmentOfflineStatusBinding
import com.happinessinc.getshwifty.repo.api.ApiResponse
import com.happinessinc.getshwifty.viewmodels.MainViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfflineDialogFragment() : DialogFragment() {

    private lateinit var binding: FragmentOfflineStatusBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //For night mode theme

        binding = FragmentOfflineStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.okButn.setOnClickListener {
            dismiss()
        }
    }

}
