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

                sharedViewModel.loadPhoto()
            }
        }
    }

}