package ru.mamapapa.task7;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import static ru.mamapapa.task7.CustomBroadcastReceiver.ACTION;

public class FirstBlankFragment extends Fragment {
    private EditText editText;
    private CustomBroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;
    private Context context;

    private OnFragmentListener listener;

    public static FirstBlankFragment newInstance() {
        return new FirstBlankFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.firstEditText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.setTextFromEditText(editText.getText().toString());
            }
        });
        listener.setTextFromEditText(editText.getText().toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        attachListener(context);

        intentFilter = new IntentFilter(ACTION);
        broadcastReceiver = new CustomBroadcastReceiver();
        broadcastReceiver.setCallback(data -> editText.setText(data));
    }

    private void attachListener(Context context) {
        if (context instanceof OnFragmentListener) {
            listener = (OnFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        context.registerReceiver(broadcastReceiver, intentFilter, null, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        context.unregisterReceiver(broadcastReceiver);
    }
}