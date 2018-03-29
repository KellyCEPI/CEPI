package com.example.kelly.cepi; /**
 * Created by Kelly on 14/03/2018.
 */


import android.app.DatePickerDialog;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.DialogFragment;
import android.app.Dialog;
import java.util.Calendar;
import android.widget.TimePicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int annee = c.get(Calendar.YEAR);
        int mois = c.get(Calendar.MONTH) ;
        int jour = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this, annee, mois, jour );
    }


    @Override
    public void onDateSet(DatePicker view, int annee, int mois, int jour) {
        TextView date = (TextView) getActivity().findViewById(R.id.TextViewDate);
        date.setText(String.valueOf(jour) + '/' + String.valueOf(mois+1) + '/' + String.valueOf(annee));
    }
}