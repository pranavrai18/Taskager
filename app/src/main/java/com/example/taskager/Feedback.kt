package com.example.taskager

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

class Feedback : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Use the correct layout file for feedback
        return inflater.inflate(R.layout.fragment_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val r = view.findViewById<RatingBar>(R.id.ratingbar1)
        val rbtn = view.findViewById<Button>(R.id.ratingbtn)
        val feed = view.findViewById<EditText>(R.id.feedback)
        val charcnt = view.findViewById<TextView>(R.id.charcount)
        val submitfdb = view.findViewById<Button>(R.id.submitfeedback)

        r.setOnRatingBarChangeListener { _, rating, _ ->
            rbtn.visibility = if (rating > 0f) Button.VISIBLE else Button.INVISIBLE
        }

        rbtn.setOnClickListener {
            val rate = r.rating
            Snackbar.make(it, "Rated $rate star(s)", Snackbar.LENGTH_SHORT).show()
        }

        feed.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(i: Editable?) {
                val l = i?.length ?: 0
                charcnt.text = "$l / 250"
                submitfdb.visibility = if (l > 0) Button.VISIBLE else Button.GONE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        submitfdb.setOnClickListener {
            Snackbar.make(it, "Thanks for your valuable feedback 😊", Snackbar.LENGTH_LONG).show()
        }
    }
}
