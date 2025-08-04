import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.taskager.R
import com.example.taskager.TaskRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class Task_history: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_history2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binButton = view.findViewById<ImageButton>(R.id.binButton)
        binButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete Task History")
                .setMessage("Are you sure you want to delete all completed tasks?")
                .setPositiveButton("Yes") { _, _ ->
                    // Clear the completed task history
                    TaskRepository.completedTasks.clear()
                    TaskRepository.saveTasks(requireContext())
                    Toast.makeText(requireContext(), "Task history cleared", Toast.LENGTH_SHORT).show()
                    // If you show this list with an adapter, call notifyDataSetChanged on the adapter here
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}
