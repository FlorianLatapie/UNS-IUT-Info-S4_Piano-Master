package com.example.pianomaster;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class SMSActivity extends Fragment {
    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;

    private static final String LOG_TAG = "AndroidExample";

    private EditText etNumroSMS;
    private EditText etContenuSMS;
    private TextView tMessage;
    private TextView tvNumTel;

    private Button buttonSend, b_retour;
    private String numTel;
    public SMSActivity(String num){
        this.numTel = num;
    }
    View rootView;

    public SMSActivity(){}

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if(b!=null)
        {
            this.numTel = getArguments().getString("numTel");
        }


    }

    private void askPermissionAndSendSMS() {
        // With Android Level >= 23, you have to ask the user
        // for permission to send SMS.
        if (android.os.Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.M) { // 23

            // Check if we have send SMS permission
            int sendSmsPermisson = ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.SEND_SMS);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSION_REQUEST_CODE_SEND_SMS
                );
                return;
            }
        }
        this.sendSMS_by_smsManager();
    }

    private void sendSMS_by_smsManager()  {

        String phoneNumber = this.etNumroSMS.getText().toString();
        String message = this.etContenuSMS.getText().toString();

        try {
            // Get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            // Send Message
            smsManager.sendTextMessage(phoneNumber,
                    null,
                    message,
                    null,
                    null);

            Log.i( LOG_TAG,"Your sms has successfully sent!");
            Toast.makeText(getActivity(),getString(R.string.sms_envoye),
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.e( LOG_TAG,"Your sms has failed...\n Error :", ex);
            Toast.makeText(getActivity(),getString(R.string.sms_refuse) + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    // When you have the request results
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE_SEND_SMS: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (SEND_SMS).
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i( LOG_TAG,"Permission granted!");
                    Toast.makeText(getActivity(), getString(R.string.autorisation_sms_oui), Toast.LENGTH_LONG).show();

                    this.sendSMS_by_smsManager();
                }
                // Cancelled or denied.
                else {
                    Log.i( LOG_TAG,"Permission denied!");
                    Toast.makeText(getActivity(), getString(R.string.autorisation_sms_non), Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    // When results returned
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_PERMISSION_REQUEST_CODE_SEND_SMS) {
            if (resultCode == RESULT_OK) {
                // Do something with data (Result returned).
                Toast.makeText(getActivity(), getString(R.string.action_ok), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), getString(R.string.action_canceled), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), getString(R.string.action_failed), Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sms, null);

        this.etNumroSMS = (EditText) rootView.findViewById(R.id.et_numero_sms);
        this.etContenuSMS = (EditText) rootView.findViewById(R.id.et_contenu_sms);
        tvNumTel = rootView.findViewById(R.id.tv_numero_tel);
        tMessage = rootView.findViewById(R.id.tv_titre_sms);
        SharedPreferences sp = getActivity().getSharedPreferences("score", Activity.MODE_PRIVATE);
        SharedPreferences sp2 = getActivity().getSharedPreferences("niveau", Activity.MODE_PRIVATE);
        int niveau = sp2.getInt("getNiveau", -1);
        int score = sp.getInt("getScore", -1);

        if(score<=1){
            tMessage.setText(getString(R.string.dommage));
        } else{
            tMessage.setText(getString(R.string.bravo));
        }
        tvNumTel.setText(tvNumTel.getText().toString()+" "+ numTel);
        etContenuSMS.setText(getString(R.string.sms_contenu1)+" "+score+"/4"+" "+getString(R.string.sms_contenu2)+" "+niveau+" "+getString(R.string.sms_contenu3));
        this.buttonSend = (Button) rootView.findViewById(R.id.b_envoyer_sms);

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("getNiveau", 0);
        editor.apply();

        SharedPreferences.Editor editor2 = sp2.edit();
        editor2.putInt("getScore", 0);
        editor2.apply();

        this.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askPermissionAndSendSMS();
            }
        });

        b_retour = rootView.findViewById(R.id.b_retour_accueil);
        b_retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("score", Activity.MODE_PRIVATE);
                SharedPreferences sp2 = getActivity().getSharedPreferences("niveau", Activity.MODE_PRIVATE);

                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("getNiveau", 0);
                editor.apply();

                SharedPreferences.Editor editor2 = sp2.edit();
                editor2.putInt("getScore", 0);
                editor2.apply();
                Intent intent = new Intent(getActivity(), LevelActivity.class);
                intent.putExtra("numNiveau", 0);
                startActivity(intent);
            }
        });

        return rootView;
    }


}