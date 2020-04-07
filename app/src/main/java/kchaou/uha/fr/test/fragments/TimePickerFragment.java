package kchaou.uha.fr.test.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {


    //proprietes
    int year;
    int month;
    int day;
    int hours;
    int minute;
    int seconds;
    //listener
    private TimePickerDialog.OnTimeSetListener onDateSet;

    static public TimePickerFragment newInstance(Date date, TimePickerDialog.OnTimeSetListener onDateSet) {
        TimePickerFragment f = new TimePickerFragment();
        f.onDateSet = onDateSet;
        Bundle args = new Bundle();
        args.putLong("date", date.getTime());
        f.setArguments(args);
        return f;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        long date = getArguments().getLong("date", 0);
        c.setTimeInMillis(date);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hours = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        seconds = c.get(Calendar.SECOND);

        TimePickerDialog dt = new TimePickerDialog(getContext(), onDateSet, hours, minute, true);

        return dt;
    }
}
