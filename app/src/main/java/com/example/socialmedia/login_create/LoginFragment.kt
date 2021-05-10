package com.example.socialmedia.login_create

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.example.socialmedia.R
import com.example.socialmedia.databinding.FragmentLoginBinding
import com.example.socialmedia.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : LoginUtils() {

    private val viewModel : LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        initContent()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.createUser.setOnClickListener {
            if(binding.createUser.text.toString() == getString(R.string.create_user)) {
                binding.createUser.text = getString(R.string.already_user)
                binding.loginBtn.apply {
                    text = getString(R.string.create)
                    setTextColor(resources.getColor(R.color.white, null))
                }
                binding.textView.text = getString(R.string.welcome_signup)
            } else {
                binding.createUser.text = getString(R.string.create_user)
                binding.loginBtn.apply {
                    text = getString(R.string.login)
                    setTextColor(resources.getColor(R.color.white, null))
                }
                binding.textView.text = getString(R.string.welcome_login)
            }
        }
    }

    private fun initContent() {
        viewModel.loginState.observe(requireActivity(), {observe(it)})
    }

    private fun observe(it: LoginState) {
        when(it) {
            is LoginState.Success -> {
                Log.e("MainActivity","Login Success")
                showAlertDialog(R.string.alert, "Login Success")
            }
            is LoginState.Failed -> {
                Utils.logger(javaClass.simpleName, "Login Failed: ${it.message}")
                showAlertDialog(R.string.alert, it.message)
            }
            else -> {}
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showAlertDialog(title: Int, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}