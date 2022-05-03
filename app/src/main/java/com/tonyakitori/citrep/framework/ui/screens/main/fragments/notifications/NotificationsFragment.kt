package com.tonyakitori.citrep.framework.ui.screens.main.fragments.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tonyakitori.citrep.R
import com.tonyakitori.citrep.databinding.FragmentNotificationsBinding
import com.tonyakitori.citrep.domain.entities.NotificationEntity
import com.tonyakitori.citrep.framework.ui.adapters.NotificationsAdapter
import com.tonyakitori.citrep.framework.utils.longToast
import com.tonyakitori.citrep.framework.utils.shortToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding

    private val notificationsViewModel: NotificationsViewModel by viewModel()
    private val adapter: NotificationsAdapter by lazy { NotificationsAdapter(::handleNotificationClick) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        setUpObservers()
        setUpViews()

        return binding.root
    }

    private fun setUpViews() = with(binding) {
        rcvNotifications.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            notificationsViewModel.getNotifications()
        }
    }

    private fun setUpObservers() {
        notificationsViewModel.notificationsLoading.observe(
            viewLifecycleOwner,
            ::handleNotificationsLoading
        )
        notificationsViewModel.notifications.observe(viewLifecycleOwner, ::handleNotificationsData)
        notificationsViewModel.notificationsError.observe(
            viewLifecycleOwner,
            ::handleNotificationsError
        )
    }

    private fun handleNotificationsLoading(show: Boolean) = with(binding) {
        swipeRefresh.isRefreshing = show
    }

    private fun handleNotificationsData(notifications: List<NotificationEntity>) {
        adapter.addNotifications(notifications)
    }

    private fun handleNotificationsError(unit: Unit) {
        shortToast(getString(R.string.ops_error))
    }

    private fun handleNotificationClick() {
        longToast(getString(R.string.not_implemented))
    }

}