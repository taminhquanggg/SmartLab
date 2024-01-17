package com.example.smartlab.businessObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import com.example.smartlab.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthYearPickerDialog extends DialogFragment {
    private DatePickerDialog.OnDateSetListener listener;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.custom_date_picker_dialog, null);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            monthPicker.setTextColor(Color.BLACK);
            yearPicker.setTextColor(Color.BLACK);
        }

        List<String> values = new ArrayList<>();
        values.add("Tháng 01");
        values.add("Tháng 02");
        values.add("Tháng 03");
        values.add("Tháng 04");
        values.add("Tháng 05");
        values.add("Tháng 06");
        values.add("Tháng 07");
        values.add("Tháng 08");
        values.add("Tháng 09");
        values.add("Tháng 10");
        values.add("Tháng 11");
        values.add("Tháng 12");

        String[] valuesArray = values.toArray(new String[0]);

        monthPicker.setDisplayedValues(valuesArray);
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(values.size() - 1);
        monthPicker.setValue(cal.get(Calendar.MONTH));

        yearPicker.setMinValue(cal.get(Calendar.YEAR));
        yearPicker.setMaxValue(cal.get(Calendar.YEAR));
        yearPicker.setValue(cal.get(Calendar.YEAR));

        builder.setView(dialog)
                .setPositiveButton("Xong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0);
                    }
                });
        return builder.create();
    }
}
