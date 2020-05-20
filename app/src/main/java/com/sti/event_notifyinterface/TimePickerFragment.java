package com.sti.event_notifyinterface;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    public interface TimePickerListener{
        void onTimeSet(TimePicker timePicker, int hour,int min);
    }
    TimePickerListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener= (TimePickerListener) context;
        }catch (Exception e)
        {
            throw new ClassCastException(getActivity().toString()+" must implements TimePicker");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c= Calendar.getInstance();
        int h= c.get(Calendar.HOUR_OF_DAY);
        int m= c.get(Calendar.MINUTE);


        return new TimePickerDialog(getActivity(),this,h,m,DateFormat.is24HourFormat(getContext()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        mListener.onTimeSet(timePicker, i, i1);
    }
}
