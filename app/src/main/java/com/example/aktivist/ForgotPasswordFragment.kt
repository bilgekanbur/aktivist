package com.example.aktivist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.aktivist.databinding.FragmentForgotPasswordBinding
import com.example.aktivist.databinding.FragmentRecordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay


class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.changePasswordButton.setOnClickListener {
            val email = binding.emailField.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(requireContext(),"Lütfen e-posta adresinizi girin.", Toast.LENGTH_SHORT).show()
            } else {
                sendPasswordResetEmail(email)
            }

            val loginscreen=LoginFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, loginscreen)
                .addToBackStack(null) // Geri tuşu ile LoginFragment'e dönmek için
                .commit()
        }
    }
    private fun sendPasswordResetEmail(email: String) {

        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->if (task.isSuccessful) {

                    Toast.makeText(
                        requireContext(),
                        "Şifre sıfırlama e-postası gönderildi. Lütfen e-postanızı kontrol edin.",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    val exception = task.exception
                    if (exception is FirebaseAuthInvalidUserException) {
                        Toast.makeText(requireContext(), "Böyle bir kullanıcı bulunamadı.", Toast.LENGTH_SHORT).show()
                    } else {
                        val errorMessage = exception?.localizedMessage ?: "Bir hata oluştu."
                        Toast.makeText(requireContext(), "Hata: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}