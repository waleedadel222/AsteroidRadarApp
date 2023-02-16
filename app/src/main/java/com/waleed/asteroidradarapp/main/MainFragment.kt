package com.waleed.asteroidradarapp.main

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.waleed.asteroidradarapp.R
import com.waleed.asteroidradarapp.databinding.FragmentMainBinding


class MainFragment : Fragment(), MenuProvider {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainBinding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mainBinding = FragmentMainBinding.inflate(inflater)
        val viewModelFactory = MainViewModelFactory(requireActivity().application)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        mainBinding.lifecycleOwner = this
        mainBinding.viewModel = mainViewModel

        val adapter = MainFragmentAdapter(
            MainFragmentAdapter.AdapterClickListener {
                mainViewModel.showAsteroidInDetail(it)
                mainViewModel.doneShowAsteroidInDetail()
            }
        )

        mainViewModel.selectedAsteroid.observe(viewLifecycleOwner) { asteroid ->

            asteroid?.let {
                this.findNavController()
                    .navigate(MainFragmentDirections.actionMainToDetailFragment(it))
            }
        }

        mainBinding.asteroidRecyclerView.adapter = adapter

        mainViewModel.asteroids.observe(viewLifecycleOwner) {
            adapter.setAsteroidsList(it)
            adapter.notifyItemChanged(0)
        }


        mainViewModel.pictureOfDay.observe(viewLifecycleOwner) {
            mainBinding.pictureDescriptionTextView.text = it?.title ?: ""
            mainBinding.pictureOfTheDay = it

        }


        // register menu to the fragment
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)


        return mainBinding.root
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.filter_all -> {
                mainViewModel.allAsteroids()
            }
            R.id.filter_week -> {
                mainViewModel.weekAsteroids()
            }
            R.id.filter_today -> {
                mainViewModel.todayAsteroids()
            }
        }
        return true
    }

}