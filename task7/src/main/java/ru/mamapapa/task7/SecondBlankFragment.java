package ru.mamapapa.task7;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

import static ru.mamapapa.task7.CustomBroadcastReceiver.BROADCAST_DATA_KEY;

public class SecondBlankFragment extends Fragment {

    private static final String THIRD_FRAGMENT = "thirdFragment";
    private OnFragmentListener listener;
    private View button;
    private Context context;

    public static SecondBlankFragment newInstance() {
        return new SecondBlankFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.buttonOnFragment);
        button.setOnClickListener(v -> onButtonPressed());
    }

    public void onButtonPressed() {
        if (listener != null) {
            addingFragment();
        }
        startService();
    }

    private void addingFragment() {
        String text = listener.getTextFromEditText();
        FragmentManager childFragmentManager = getChildFragmentManager();
        childFragmentManager.beginTransaction()
                .add(R.id.secondFragment, ThirdBlankFragment.newInstance(text), THIRD_FRAGMENT)
                .commit();
    }

    private void startService() {
        Intent intent = GeneratedTextIntentService.newInstance(context);
        intent.putExtra(BROADCAST_DATA_KEY, UUID.randomUUID().toString());
        context.startService(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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
