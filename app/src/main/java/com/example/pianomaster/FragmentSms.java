package com.example.pianomaster;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class FragmentSms extends Fragment {
    private IntActivityClick mCallBack;
    public FragmentSms(){    }

    @Override
    public void setTargetFragment(Fragment fragment, int requestCode) {
        //TODO
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_sms, container, false);
        final EditText editNumSms = rootView.findViewById(R.id.edit_num);
        editNumSms.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int longueur = editNumSms.getText().length();
                if (longueur != 0) {
                    Button btnSuivant = (Button) rootView.findViewById(R.id.btn_suivant);
                    btnSuivant.setOnClickListener(v -> {
                        if (!(editNumSms.getText().toString().equals(""))) {
                            mCallBack.onButtonClicked(editNumSms.getText().toString());
                        }
                    });
                }
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
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
