package com.selin.fooddeliveryapp.ui.menu

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.selin.fooddeliveryapp.databinding.FragmentSettingsBinding
import com.selin.fooddeliveryapp.ui.SharedViewModel

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

    private fun initViews() {
        val userNameTextView: TextView = binding.tvName

        with(binding) {
            ivNameArrow.setOnClickListener {
                val editText = EditText(context)
                val dialog = AlertDialog.Builder(context)
                    .setTitle("Change Name")
                    .setMessage("Enter new name")
                    .setView(editText)
                    .setPositiveButton("OK") { _, _ ->
                        val newName = editText.text.toString()
                        userNameTextView.text = newName
                        sharedViewModel.saveUserName(newName)
                    }
                    .setNegativeButton("Cancel", null)
                    .create()
                dialog.show()
            }
        }
    }
}