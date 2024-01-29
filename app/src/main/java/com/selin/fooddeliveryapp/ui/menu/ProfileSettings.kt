package com.selin.fooddeliveryapp.ui.menu


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.databinding.FragmentSettingsBinding
import com.selin.fooddeliveryapp.ui.shared.SharedViewModel
import com.selin.fooddeliveryapp.utils.constans.AppConstants

class ProfileSettings: Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        observe()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstants.REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val photoUri = data.data
            sharedViewModel.savePhoto(photoUri.toString())
        }
    }

    private fun initViews() {
        with(binding) {
            ivNameArrow.setOnClickListener { showChangeNameDialog() }
            ivMailArrow.setOnClickListener { showChangeMailDialog() }
            ivPhotoArrow.setOnClickListener { openImagePicker() }
            ivPasswordArrow.setOnClickListener { showPasswordChangeDialog() }
        }
    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.passwordChangeResult.collect { result ->
                result?.let {
                    viewModel.showMessage(it)
                }
            }
        }
    }

    private fun showChangeNameDialog() {
        val editText = EditText(requireContext())
        val dialog = createInputDialog("Change Name", editText) { newName ->
            sharedViewModel.saveUserName(newName)
        }
        dialog.show()
    }

    private fun showChangeMailDialog() {
        val editText = EditText(requireContext())
        val dialog = createInputDialog("Change Mail", editText) { newMail ->
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(newMail).matches()) {
                sharedViewModel.saveMail(newMail)
            } else {
                viewModel.showMessage("Invalid mail")
            }
        }
        dialog.show()
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, AppConstants.REQUEST_CODE)
    }

    private fun showPasswordChangeDialog() {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_password_change, null)
        val etOldPassword: EditText = dialogView.findViewById(R.id.etOldPassword)
        val etNewPassword: EditText = dialogView.findViewById(R.id.etNewPassword)

        val dialog = createAlertDialog("Change Password", dialogView) {
            val oldPassword = etOldPassword.text.toString()
            val newPassword = etNewPassword.text.toString()
            viewModel.changePassword(oldPassword, newPassword)
        }

        dialog.show()
    }

    private fun createInputDialog(
        title: String,
        editText: EditText,
        positiveAction: (String) -> Unit
    ) = AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setView(editText)
        .setPositiveButton("OK") { _, _ -> positiveAction(editText.text.toString()) }
        .setNegativeButton("Cancel", null)
        .create()

    private fun createAlertDialog(
        title: String,
        view: View,
        positiveAction: () -> Unit
    ) = AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setView(view)
        .setPositiveButton("Change") { _, _ -> positiveAction() }
        .setNegativeButton("Cancel", null)
        .create()
}