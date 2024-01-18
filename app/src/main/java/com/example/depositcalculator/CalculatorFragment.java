package com.example.depositcalculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class CalculatorFragment extends Fragment {
    private long result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        Button button = view.findViewById(R.id.resultButton);

        EditText amount = view.findViewById(R.id.amountC);
        SeekBar time = view.findViewById(R.id.timeC);
        SeekBar percent = view.findViewById(R.id.percent);
        TextView timeText = view.findViewById(R.id.timeT);
        TextView percentText = view.findViewById(R.id.percentT);
        Bundle bundle = new Bundle();

        amount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    String text = String.valueOf(amount.getText());
                    StringBuilder newText = new StringBuilder();
                    int  count  = 0;
                    for (int i = text.length() - 1; i >= 0; --i) {
                        if(text.charAt(i) == ' ') {
                            continue;
                        }
                        ++count;
                        if (count % 3 == 0 && i != 0) {
                            newText.insert(0, " " + text.charAt(i));
                        } else {
                            newText.insert(0, text.charAt(i));
                        }
                    }
                    amount.setText(newText);
                }
                return false;
            }
        });
        time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeText.setText(String.valueOf(time.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        percent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentText.setText(String.valueOf(percent.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button.setOnClickListener(v -> {
            String text = amount.getText().toString().replaceAll(" ", "");
            result = (long)(Integer.parseInt(text) * (Math.pow(1 + ((double) percent.getProgress() / 100), time.getProgress())));
            bundle.putString("result", String.valueOf(result));
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_calculatorFragment_to_resultFragment, bundle);
        });

        return view;
    }
}