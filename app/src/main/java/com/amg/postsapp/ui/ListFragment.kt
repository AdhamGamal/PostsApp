package com.amg.postsapp.ui

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.amg.postsapp.R
import com.amg.postsapp.databinding.FragmentListBinding
import com.amg.postsapp.ui.adapters.PostsAdapter
import com.amg.postsapp.ui.adapters.StateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private var animater: Animatable? = null
    private var searchJob: Job? = null

    @Inject
    lateinit var adapter: PostsAdapter

    @Inject
    lateinit var stateAdapter: StateAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        search()

        // I don't like it when there is no list items filter
        binding.list.itemAnimator = null

        binding.swipeRefreshLayout.setOnRefreshListener {
            // Delete all and reload data
            binding.list.scrollToPosition(0)
            viewModel.deleteAll()
            search()
        }

    }


    @ExperimentalPagingApi
    private fun initAdapter() {
        binding.list.adapter = adapter.withLoadStateFooter(
                footer = stateAdapter
        )

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading && !viewModel.isLoading) {

                viewModel.isLoading = true
                binding.loading.visibility = toVisibility(true)

            } else if (loadState.refresh is LoadState.NotLoading && viewModel.isLoading) {

                viewModel.isLoading = false
                binding.swipeRefreshLayout.isRefreshing = false

                binding.loading.visibility = toVisibility(false)
                if (animater != null && animater!!.isRunning) {
                    animater!!.stop()
                }
            }
        }
    }

    @ExperimentalPagingApi
    private fun search() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getPosts().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    @ExperimentalPagingApi
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.reload -> {
            animater = (item.icon as Animatable)
            animater!!.start()
            // Refresh after loading data failure
            adapter.refresh()

            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.reload, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}