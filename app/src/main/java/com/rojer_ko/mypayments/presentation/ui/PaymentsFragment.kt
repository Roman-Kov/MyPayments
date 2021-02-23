package com.rojer_ko.mypayments.presentation.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.rojer_ko.mypayments.R
import com.rojer_ko.mypayments.domain.model.DataResult
import com.rojer_ko.mypayments.presentation.viewmodel.PaymentsViewModel
import com.rojer_ko.mypayments.utils.showToast
import kotlinx.android.synthetic.main.fragment_payments.*
import org.koin.android.viewmodel.ext.android.viewModel

class PaymentsFragment : BaseFragment() {

    override val layout = R.layout.fragment_payments
    private val viewModel by viewModel<PaymentsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(view)
    }

    private fun setToolbar(view: View) {
        val toolbar: Toolbar = view.findViewById(R.id.payments_toolbar)
        toolbar.title = resources.getString(R.string.toolbar_payments_title)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        this.setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_payments_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.payments_logout -> {
                showLogoutSnackbar()
            }
            R.id.payments_refresh -> {
                refreshPayments()
            }
            else -> Unit
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        observePaymentsResult()
        refreshPayments()
    }

    private fun observePaymentsResult() {
        viewModel.paymentsResult.observe(viewLifecycleOwner, {
            when (it) {
                is DataResult.Process -> {
                    payments_progress_bar.visibility = View.VISIBLE
                }
                is DataResult.Success<*> -> {
                    payments_progress_bar.visibility = View.GONE
                }
                is DataResult.Error -> {
                    showToast(it.error.message.toString())
                    payments_progress_bar.visibility = View.GONE
                }
            }
        })
    }

    private fun refreshPayments() {
        viewModel.getPayments()
    }

    private fun showLogoutSnackbar() {
        view?.let {
            Snackbar.make(
                it,
                R.string.logout_from_account,
                resources.getInteger(R.integer.appSnackbarDuration)
            )
                .setAction(R.string.ok) {
                    logout()
                }.show()
        }
    }

    private fun logout() {
        viewModel.logout()
        navigateToAuthFragment()
    }

    private fun navigateToAuthFragment() {
        findNavController().popBackStack(R.id.paymentsFragment, true)
        findNavController().navigate(R.id.authFragment)
    }
}
