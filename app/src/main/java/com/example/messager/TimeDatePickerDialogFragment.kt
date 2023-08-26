package com.example.messager

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.util.*

class TimeDatePickerDialogFragment(private val callback: (Long) -> Unit) : DialogFragment(),
    DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var fragmentContext: Context? = null // Store the fragment's context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    override fun onDetach() {
        super.onDetach()
        fragmentContext = null // Clear the stored context when fragment is detached
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        selectedCalendar.set(Calendar.MINUTE, minute)
        selectedCalendar.set(Calendar.SECOND, 0)

        val selectedTimeInMillis = selectedCalendar.timeInMillis

        callback(selectedTimeInMillis)
    }

}
