package com.example.aktivist

import android.app.usage.UsageEvents
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SearchFragment : Fragment() {

    private lateinit var adapter: SearchResultsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var database: FirebaseDatabase
    private lateinit var eventsRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment için XML'i bağlama
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        val spinnerFilter = view.findViewById<Spinner>(R.id.spinnerFilter)
        val btnSearch = view.findViewById<Button>(R.id.btnSearch)
        recyclerView = view.findViewById(R.id.recyclerViewResults)

        // Firebase Veritabanı Referansı
        database = FirebaseDatabase.getInstance()
        eventsRef = database.getReference("events")

        // RecyclerView Ayarları
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchResultsAdapter(emptyList())
        recyclerView.adapter = adapter

        // Arama Butonu Tıklama
        btnSearch.setOnClickListener {
            val query = etSearch.text.toString()
            val filter = spinnerFilter.selectedItem.toString()
            performSearch(query, filter)
        }
    }

    private fun performSearch(query: String, filter: String) {
        eventsRef.get().addOnSuccessListener { snapshot ->
            val results = mutableListOf<UsageEvents.Event>()

            for (eventSnapshot in snapshot.children) {
                val name = eventSnapshot.child("name").value.toString()
                val date = eventSnapshot.child("date").value.toString()
                val location = eventSnapshot.child("location").value.toString()

                // Filtreleme ve Arama
                if (name.contains(query, ignoreCase = true) &&
                    (filter == "Tüm Kategoriler" || filter == location)) {
                   // results.add(UsageEvents.Event(name, date, location))
                }
            }

            // Sonuçları RecyclerView'a Gönder
            adapter = SearchResultsAdapter(results)
            recyclerView.adapter = adapter
        }.addOnFailureListener { exception ->
            Log.e("Firebase", "Arama işlemi başarısız oldu.", exception)
        }
    }


}