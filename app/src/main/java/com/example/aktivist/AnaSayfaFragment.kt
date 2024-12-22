package com.example.aktivist

import TicketmasterResponse
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.aktivist.databinding.FragmentAnaSayfaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnaSayfaFragment : Fragment() {
    private var _binding: FragmentAnaSayfaBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.icMicrophone.setOnClickListener {
            navigateToConcertFragment()
        }

        binding.mikrofonHalka.setOnClickListener {
            navigateToConcertFragment()
        }
//--------------------------------------------------------------------------
        binding.icSport.setOnClickListener {
            navigateToSportFragment()
        }

        binding.sportHalka.setOnClickListener {
            navigateToSportFragment()
        }
//---------------------------------------------------------------------------
        binding.tiyatroHalka.setOnClickListener {
            navigateToTiyatroFragment()
        }

        binding.icTiyatro.setOnClickListener {
            navigateToTiyatroFragment()
        }
//-------------------------------------------------------------------------------
        binding.icEducation.setOnClickListener {
            navigateToEducationFragment()
        }

        binding.educationHalka.setOnClickListener {
            navigateToEducationFragment()
        }
//-------------------------------------------------------------------------------
        binding.icFamily.setOnClickListener {
            navigateToFamilyFragment()
        }

        binding.familyHalka.setOnClickListener {
            navigateToFamilyFragment()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnaSayfaBinding.inflate(inflater, container, false)
        return binding.root
    }




    private fun fetchFilteredData(category: String, onSuccess: (ArrayList<EventItem>) -> Unit) {
        val apiKey = "PKIN6PT5uJWU7RUAuiPANAAePYHvr9cO"
        RetrofitInstance.api.getEvents(apiKey, category, "Istanbul")
            .enqueue(object : Callback<TicketmasterResponse> {
                override fun onResponse(
                    call: Call<TicketmasterResponse>,
                    response: Response<TicketmasterResponse>
                ) {
                    if (response.isSuccessful) {
                        val events = response.body()?._embedded?.events
                        if (!events.isNullOrEmpty()) {
                            val eventItems = ArrayList<EventItem>()
                            for (apiEvent in events) {
                                val eventItem = EventItem(
                                    title = apiEvent.name,
                                    information = apiEvent.info ?: "Bilgi yok",
                                    imageResId = R.drawable.avatar,
                                    saved = 0,
                                    date = Dates(
                                        apiEvent.dates.start.localDate ?: "Tarih yok",
                                        apiEvent.dates.start.localTime ?: "Saat yok"
                                    ),
                                    tur = apiEvent.classifications?.firstOrNull()?.segment?.name ?: "Tür yok"
                                )
                                eventItems.add(eventItem)
                            }
                            onSuccess(eventItems)
                        } else {
                            Toast.makeText(context, "Etkinlik bulunamadı.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Hata: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<TicketmasterResponse>, t: Throwable) {
                    Toast.makeText(context, "Bağlantı hatası: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun navigateToConcertFragment() {
        val concertscreen=ConcertsFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, concertscreen)
            .addToBackStack(null) // Geri tuşu ile LoginFragment'e dönmek için
            .commit()
    }
    private fun navigateToSportFragment() {
        val sportscreen=SportFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, sportscreen)
            .addToBackStack(null) // Geri tuşu ile LoginFragment'e dönmek için
            .commit()
    }
    private fun navigateToTiyatroFragment() {
        val tiyatroscreen=TheatreFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, tiyatroscreen)
            .addToBackStack(null) // Geri tuşu ile LoginFragment'e dönmek için
            .commit()
    }
    private fun navigateToFamilyFragment() {
        val Familyscreen=FamilyFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Familyscreen)
            .addToBackStack(null) // Geri tuşu ile LoginFragment'e dönmek için
            .commit()
    }
    private fun navigateToEducationFragment() {
        val educationscreen=EducationFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, educationscreen)
            .addToBackStack(null) // Geri tuşu ile LoginFragment'e dönmek için
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
