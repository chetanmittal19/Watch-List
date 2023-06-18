package com.example.watchlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.watchlist.R
import com.example.watchlist.data.network.ErrorCode
import com.example.watchlist.data.network.Status
import com.example.watchlist.databinding.FragmentMovieListBinding

/**
 * A simple [Fragment] subclass.
 */
class MovieListFragment : Fragment() {
    private lateinit var _binding: FragmentMovieListBinding
    private val binding get() = _binding
    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this)[MovieListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(binding.movieList){
            adapter = MovieAdapter {
                findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(it)
                )
            }
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            (binding.movieList.adapter as MovieAdapter).submitList(it)
            if (it.isEmpty()) {
                viewModel.fetchFromNetwork()
            }
        }

        viewModel.loadingStatus.observe(viewLifecycleOwner) { loadingStatus ->
            when (loadingStatus?.status) {
                Status.LOADING -> {
                    binding.loadingStatus.visibility = View.VISIBLE
                    binding.statusError.visibility = View.INVISIBLE
                }
                Status.SUCCESS -> {
                    binding.loadingStatus.visibility = View.INVISIBLE
                    binding.statusError.visibility = View.INVISIBLE
                }
                Status.ERROR -> {
                    binding.loadingStatus.visibility = View.INVISIBLE
                    showErrorMessage(loadingStatus.errorCode, loadingStatus.message)
                    binding.statusError.visibility = View.VISIBLE
                }
                else -> {}
            }
            binding.swipeRefresh.isRefreshing = false
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun showErrorMessage(errorCode: ErrorCode?, message: String?) {
        when (errorCode) {
            ErrorCode.NO_DATA -> binding.statusError.text = getString(R.string.error_no_data)
            ErrorCode.NETWORK_ERROR -> binding.statusError.text = getString(R.string.error_network)
            ErrorCode.UNKNOWN_ERROR -> binding.statusError.text = getString(R.string.error_unknown, message)
            else -> {}
        }
    }
}
