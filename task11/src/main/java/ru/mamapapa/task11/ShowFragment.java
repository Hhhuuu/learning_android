package ru.mamapapa.task11;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShowFragment extends Fragment {

    private OnFragmentListener listener;
    private View updateButton;
    private TextView viewTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_properties, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateButton = view.findViewById(R.id.updateButton);
        viewTextView = view.findViewById(R.id.showPropertyTextView);
        updateButton.setOnClickListener(v -> {
            String keys = listener.getPropertiesKeys().toString();
            viewTextView.setText(keys);
        });
    }

    public void onButtonPressed() {
        if (listener != null) {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attachListener(context);
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
}
