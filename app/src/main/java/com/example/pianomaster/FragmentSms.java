package com.example.pianomaster;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentSms extends Fragment {
    private IntActivityClick mCallBack;
    public FragmentSms(){    }

    @Override
    public void setTargetFragment(Fragment fragment, int requestCode) {}



    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_score, container, false);
        final EditText editNumSms = rootView.findViewById(R.id.et_numero_sms);
        final Button btnSuivant = rootView.findViewById(R.id.btn_suivant);
        btnSuivant.setOnClickListener(v -> {
            if (!(editNumSms.getText().toString().equals(""))) {
                mCallBack.onButtonClicked(editNumSms.getText().toString());
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.createCallbackToParentActivity();
    }

    private void createCallbackToParentActivity(){
        try {
            mCallBack = (IntActivityClick) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()
                    + " must implement OnButtonClickedListener");
        }
    }
}
