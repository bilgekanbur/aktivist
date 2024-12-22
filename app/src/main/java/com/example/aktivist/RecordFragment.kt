package com.example.aktivist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.aktivist.databinding.FragmentLoginBinding
import com.example.aktivist.databinding.FragmentRecordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class RecordFragment : Fragment() {
    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding.RecordButton.setOnClickListener {
            // Kullanıcıdan alınan bilgiler
            val ad = binding.NameText.text.toString().trim()
            val soyad = binding.SurnameText.text.toString().trim()
            val telefon = binding.PhoneText.text.toString().trim()
            val email = binding.EmailText.text.toString().trim()
            val sifre = binding.PasswordText.text.toString().trim()
            val kosulKabulEdildi = binding.checkBox.isChecked

            // Bilgilerin kontrolü
            if (ad.isNotEmpty() && soyad.isNotEmpty() && telefon.isNotEmpty() &&
                email.isNotEmpty() && sifre.isNotEmpty() && kosulKabulEdildi
            ) {
                // Kullanıcıyı kaydet
                registerUser(email, sifre, ad, soyad, telefon)
                Toast.makeText(requireContext(), "Tüm alanlar dolduruldu, kullanıcı başarı ile kayıt ediliyor..", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun ve koşulları kabul edin.", Toast.LENGTH_SHORT).show()
            }
            val loginscreen=LoginFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, loginscreen)
                .addToBackStack(null) // Geri tuşu ile LoginFragment'e dönmek için
                .commit()
        }
    }
    private fun registerUser(email: String, password: String, ad: String, soyad: String, telefon: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid

                    // Kullanıcı bilgilerini Firestore'a kaydet
                    val userMap = hashMapOf(
                        "ad" to ad,
                        "soyad" to soyad,
                        "telefon" to telefon,
                        "email" to email,
                    )

                    if (userId != null) {
                        firestore.collection("users").document(userId)
                            .set(userMap)
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(), "Kayıt başarılı!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(requireContext(), "Veri kaydı hatası: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    // Kayıt sırasında hata
                    Toast.makeText(requireContext(), "Kayıt hatası: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}