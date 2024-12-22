package com.example.aktivist

import TicketmasterResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aktivist.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Kullanıcı giriş durumunu kontrol et
        if (auth.currentUser == null) {
            navigateToLogin()
            return
        }

        // İlk olarak AnaSayfaFragment'i başlat
        loadFragment(AnaSayfaFragment())
        // API çağrısı yap
        fetchEvents()

        // BottomNavigationView tıklama olaylarını ayarla
        binding.bottomNavBar.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.ic_home -> AnaSayfaFragment()
                R.id.ic_search -> SearchFragment()
                R.id.ic_favorite -> FavoriteFragment()
                R.id.ic_settings -> SettingsFragment()
                else -> null
            }
            selectedFragment?.let { loadFragment(it) } != null
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // MainActivity'yi kapat
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun fetchEvents() {
        val apiKey = "PKIN6PT5uJWU7RUAuiPANAAePYHvr9cO" // Geçerli API Key'inizi buraya yazın
        RetrofitInstance.api.getEvents(apiKey, "music", "Istanbul")
            .enqueue(object : Callback<TicketmasterResponse> {
                override fun onResponse(
                    call: Call<TicketmasterResponse>,
                    response: Response<TicketmasterResponse>
                ) {
                    if (response.isSuccessful) {
                        val events = response.body()?._embedded?.events
                        if (!events.isNullOrEmpty()) {
                            for (event in events) {
                                Log.d("Event", "Name: ${event.name}, Date: ${event.dates.start.localDate}")
                            }
                        } else {
                            Log.d("Event", "No events found.")
                        }
                    } else {
                        Log.e("Event", "Error: ${response.code()} - ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<TicketmasterResponse>, t: Throwable) {
                    Log.e("Event", "Failure: ${t.message}")
                }
            })
    }
}
