package com.javaindo.lautnusantara.view.result_catch.fragment

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class SelectDateFragment : DialogFragment(), DatePickerDialog.OnDateSetListener{

    private lateinit var editText : EditText

//    companion object{
//        fun newInstance()
//    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        final Calendar calendar = Calendar.getInstance();
//        int yy = calendar.get(Calendar.YEAR);
//        int mm = calendar.get(Calendar.MONTH);
//        int dd = calendar.get(Calendar.DAY_OF_MONTH);
//        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
//    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
//        edit.setText( day + "." + month + "." + year);
    }
}