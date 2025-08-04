package com.example.taskager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CompletedTaskFragment : Fragment() {

    private lateinit var completedAdapter: CompletedTaskAdapter

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TaskRepository.loadTasks(requireContext())
        return inflater.inflate(R.layout.fragment_completedtask, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.completedTaskRecyclerView)
        completedAdapter = CompletedTaskAdapter(TaskRepository.completedTasks)
        recyclerView.adapter = completedAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}
