package com.example.aktivist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.aktivist.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // "Forgot Password" ekranına geçiş
        binding.forgotPasswordText.setOnClickListener {
            val passwordscreen = ForgotPasswordFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, passwordscreen)
                .addToBackStack(null)
                .commit()
        }
        //"Register" Ekranına geçiş
        binding.registerText.setOnClickListener {
            val recordscreen = RecordFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, recordscreen)
                .addToBackStack(null)
                .commit()
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "Giriş başarılı!", Toast.LENGTH_SHORT).show()
                            (activity as? LoginActivity)?.navigateToMainActivity()
                        } else {
                            Toast.makeText(requireContext(), "Hata: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
