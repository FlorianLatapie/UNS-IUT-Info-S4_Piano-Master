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
    public FragmentSms(){    }

    @Override
    public void setTargetFragment(Fragment fragment, int requestCode) {
        //TODO
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_sms, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
