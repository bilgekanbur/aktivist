package com.example.aktivist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aktivist.databinding.FragmentForgotPasswordBinding
import com.example.aktivist.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exitButton.setOnClickListener {
            val loginscreen=LoginFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, loginscreen)
                .addToBackStack(null) // Geri tuşu ile LoginFragment'e dönmek için
                .commit()
        }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    }



