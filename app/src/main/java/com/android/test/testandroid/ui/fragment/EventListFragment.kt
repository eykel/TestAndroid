package com.android.test.testandroid.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.window.SplashScreen
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.test.testandroid.R
import com.android.test.testandroid.databinding.FragmentEventListBinding
import com.android.test.testandroid.domain.model.EventList
import com.android.test.testandroid.ui.adapter.EventListAdapter
import com.android.test.testandroid.ui.gone
import com.android.test.testandroid.ui.observe
import com.android.test.testandroid.ui.view_model.EventListViewModel
import com.android.test.testandroid.ui.visible
import org.koin.androidx.viewmodel.ext.android.viewModel


class EventListFragment : Fragment() {

    private val viewBinding : FragmentEventListBinding by lazy {
        FragmentEventListBinding.inflate(layoutInflater)
    }

    private val eventListAdapter: EventListAdapter by lazy {
        EventListAdapter(
            onEventClick = {
                /**
                 *Checking the api, I saw that the endpoint to get eventDetails
                 * return the same data that I already have from the getEvents Call
                 * so, instead of pass the id to next fragment and do another call
                 * I'll just pass the event to next fragment and display it
                 */
                findNavController().navigate (
                    EventListFragmentDirections.actionEventListFragmentToEventDetailFragment(it)
                )
            }
        )
    }

    private val viewModel: EventListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return viewBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupView()
    }

    private fun setupObservers() {
        observe(viewModel.eventList){
            handleEventList(it)
        }
        observe(viewModel.progressLiveData){
            showProgress(it)
        }
        observe(viewModel.error){
            setupErrorView(it, R.drawable.ic_error)
        }
    }

    private fun showProgress(showProgress: Boolean) {
        with(viewBinding){
            if(showProgress){
                progress.visible()
                clErrorView.clErrorView.gone()
                eventRecycler.gone()
            }else
                progress.gone()
        }
    }

    private fun setupView() {
        viewBinding.eventRecycler.apply{
            if(Build.VERSION.SDK_INT < 21){
                /**
                 * Theres an elevation on ConstraintLayout of list item, to show the list more...beautiful for some eyes
                 * But, elevation element from android:elevation, just work in API 21 and above, so
                 * lets leave this divider to api < than 21, it's not too ugly, so it's valid =)
                 */
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
            layoutManager = LinearLayoutManager(activity)
            adapter = eventListAdapter
        }

        viewBinding.srRefresh.setOnRefreshListener {
            viewBinding.srRefresh.isRefreshing = false
            viewModel.getEvents()
        }
    }

    private fun handleEventList(eventList: EventList){
        if(eventList.isEmpty())
            setupErrorView(
                getString(R.string.no_content_error_text),
                R.drawable.ic_nocontent
            )
        else {
            viewBinding.clErrorView.clErrorView.gone()
            viewBinding.eventRecycler.visible()
            eventListAdapter.submitList(eventList)
        }
    }

    private fun setupErrorView(errorMessage: String, @DrawableRes errorIcon: Int){
        with(viewBinding.clErrorView){
            clErrorView.visible()
            ivErrorViewIcon.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), errorIcon)
            )
            tvErrorViewText.text = errorMessage
            btTryAgain.setOnClickListener {
                viewModel.getEvents()
            }
        }

    }
}