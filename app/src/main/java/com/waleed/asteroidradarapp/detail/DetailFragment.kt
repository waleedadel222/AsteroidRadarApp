package com.waleed.asteroidradarapp.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.waleed.asteroidradarapp.R
import com.waleed.asteroidradarapp.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).detailAsteroid

        binding.asteroid = asteroid

        binding.helpButton.setOnClickListener {
            showAstronomicalDialog()
        }

        return binding.root
    }

    private fun showAstronomicalDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton("Done", null)
        builder.create().show()
    }
}