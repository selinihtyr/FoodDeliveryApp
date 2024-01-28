package com.selin.fooddeliveryapp.ui.menu

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.databinding.FragmentSettingsBinding
import com.selin.fooddeliveryapp.ui.shared.SharedViewModel
import com.selin.fooddeliveryapp.utils.constans.AppConstants.REQUEST_CODE

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
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
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val photoUri = data.data
            sharedViewModel.savePhoto(photoUri.toString())
        }
    }

    private fun initViews() {
        with(binding) {
            ivNameArrow.setOnClickListener {
                val editText = EditText(context)
                val dialog = AlertDialog.Builder(context)
                    .setTitle("Change Name")
                    .setView(editText)
                    .setPositiveButton("OK") { _, _ ->
                        val newName = editText.text.toString()
                        sharedViewModel.saveUserName(newName)
                    }
                    .setNegativeButton("Cancel", null)
                    .create()
                dialog.show()
            }

            ivMailArrow.setOnClickListener {
                val editText = EditText(context)
                val dialog = AlertDialog.Builder(context)
                    .setTitle("Change Mail")
                    .setView(editText)
                    .setPositiveButton("OK") { _, _ ->
                        val newMail = editText.text.toString()
                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(newMail).matches()) {
                            sharedViewModel.saveMail(newMail)
                        } else {
                            Toast.makeText(context, "Invalid mail address", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .create()
                dialog.show()
            }

            ivPhotoArrow.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_CODE)


                sharedViewModel.savePhoto(ivPhoto.toString())
            }

            ivPasswordArrow.setOnClickListener {
                showPasswordChangeDialog()
            }
        }
    }

    private fun showPasswordChangeDialog() {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_password_change, null)
        val etOldPassword: EditText = dialogView.findViewById(R.id.etOldPassword)
        val etNewPassword: EditText = dialogView.findViewById(R.id.etNewPassword)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Change Password")
            .setView(dialogView)
            .setPositiveButton("Change") { _, _ ->
                val oldPassword = etOldPassword.text.toString()
                val newPassword = etNewPassword.text.toString()

                val user = FirebaseAuth.getInstance().currentUser
                val credential = EmailAuthProvider.getCredential(user?.email!!, oldPassword)
                user.reauthenticate(credential).addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { updatePasswordTask ->
                                if (updatePasswordTask.isSuccessful) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Password changed successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Failed to change password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Invalid old password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

}