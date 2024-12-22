package com.example.aktivist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ConcertsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_concerts, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bundle'dan gelen verileri alıyoruz
        val eventItems = arguments?.getParcelableArrayList<EventItem>("events")
        eventItems?.let { showEventItems(it) }
    }
    private fun setupRecyclerView() {
        val recyclerView = recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = EventAdapter(emptyList()) // Başlangıçta boş bir liste ile başlat
    }

    private fun showEventItems(eventItems: List<EventItem>) {
        adapter = EventAdapter(eventItems)
        recyclerView.adapter = adapter
    }
}
