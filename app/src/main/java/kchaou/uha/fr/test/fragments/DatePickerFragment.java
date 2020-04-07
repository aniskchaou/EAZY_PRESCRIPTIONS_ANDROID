package kchaou.uha.fr.test.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;


public class DatePickerFragment extends DialogFragment {
    //listener
    private DatePickerDialog.OnDateSetListener onDateSet;

    //nouvelle instance DatePickerFragment
    public static DatePickerFragment newInstance(Date date, DatePickerDialog.OnDateSetListener onDateSet) {
        DatePickerFragment f = new DatePickerFragment();
        f.onDateSet = onDateSet;
        Bundle args = new Bundle();
        args.putLong("date", date.getTime());
        f.setArguments(args);
        return f;
    }

    //afficher le calendrier
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        long date = getArguments().getLong("date", 0);
        c.setTimeInMillis(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getContext(), onDateSet, year, month, day);
    }
}
