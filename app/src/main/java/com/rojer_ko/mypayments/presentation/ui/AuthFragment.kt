package com.rojer_ko.mypayments.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.rojer_ko.mypayments.R
import com.rojer_ko.mypayments.domain.model.DataResult
import com.rojer_ko.mypayments.presentation.converter.ErrorStringConverter
import com.rojer_ko.mypayments.presentation.viewmodel.AuthViewModel
import com.rojer_ko.mypayments.utils.AppTextWatcher
import com.rojer_ko.mypayments.utils.Consts
import com.rojer_ko.mypayments.utils.showToast
import kotlinx.android.synthetic.main.fragment_auth.*
import org.koin.android.viewmodel.ext.android.viewModel

class AuthFragment : BaseFragment() {

    override val layout = R.layout.fragment_auth
    private val viewModel by viewModel<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeTokenState()
        checkToken()
        initViews()
        observeLoginState()
        observeFormState()
    }

    override fun onStart() {
        super.onStart()
        login()
    }

    private fun observeTokenState() {
        viewModel.tokenState.observe(viewLifecycleOwner, {
            when (it) {
                is DataResult.Success<*> -> {
                    navigateToPaymentsFragment()
                }
                else -> Unit
            }
        })
    }

    private fun checkToken() {
        viewModel.getToken()
    }

    private fun initViews() {
        login_text_input.addTextChangedListener(AppTextWatcher {
            checkFormState()
        })
        password_text_input.addTextChangedListener(AppTextWatcher {
            checkFormState()
        })
    }

    private fun observeFormState() {
        viewModel.formState.observe(viewLifecycleOwner, {
            login_btn.isEnabled = it
        })
    }

    private fun checkFormState() {
        val fields = listOf(
            login_text_input.text.toString(),
            password_text_input.text.toString()
        )
        viewModel.checkForm(fields)
    }

    private fun login() {
        login_btn.setOnClickListener {
            val login = login_text_input.text.toString()
            val secret = password_text_input.text.toString()
            viewModel.login(login, secret)
        }
    }

    private fun observeLoginState() {
        viewModel.loginState.observe(viewLifecycleOwner, {
            when (it) {
                is DataResult.Process -> {
                    auth_progress_bar.visibility = View.VISIBLE
                    changeFormEnableState(false)
                }
                is DataResult.Success<*> -> {
                    auth_progress_bar.visibility = View.GONE
                    navigateToPaymentsFragment()
                }
                is DataResult.Error -> {
                    val error = context?.let { context ->
                        ErrorStringConverter.convertToContainer(
                            context,
                            it.error.message.toString()
                        )
                    } ?: Consts.Error.BAD_RESPONSE.text
                    showToast(error)
                    changeFormEnableState(true)
                    auth_progress_bar.visibility = View.GONE
                }
            }
        })
    }

    private fun changeFormEnableState(isFieldsEnabled: Boolean) {
        login_text_input_layout.isEnabled = isFieldsEnabled
        password_text_input_layout.isEnabled = isFieldsEnabled
        login_btn.isEnabled = isFieldsEnabled
    }

    private fun navigateToPaymentsFragment() {
        findNavController().navigate(R.id.action_authFragment_to_paymentsFragment)
    }
}