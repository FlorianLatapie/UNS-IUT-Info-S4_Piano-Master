package com.example.pianomaster;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PianoActivity extends Activity {
    private ProgressDialog pDialog;
    private int pourcentagePB = 0;
    private Handler mHandler;

    private static int count = 0;
    private static int nbPoint = 0;
    private static int score = 0;
    private static long delaiEntreChaqueQuestion = 3000;

    private int niveau;

    private MediaPlayer media_do;
    private MediaPlayer media_do_diese;
    private MediaPlayer media_re;
    private MediaPlayer media_re_diese;
    private MediaPlayer media_mi;
    private MediaPlayer media_fa;
    private MediaPlayer media_fa_diese;
    private MediaPlayer media_sol;
    private MediaPlayer media_sol_diese;
    private MediaPlayer media_la;
    private MediaPlayer media_si_bemol;
    private MediaPlayer media_si;

    private MediaPlayer currentSong;
    private TextView tvNbTentative;
    private List<String> listNote = new ArrayList<>();
    private int numNote = 0;

    private static String url_ressources = "https://androidpianomaster.000webhostapp.com/ressources";
    QuestionPiano question = null;
    TextView tvQuestion, tvNiveau, tvScore;
    Button btn_reecoutez;
    private int nbTentative = 0;

    Intent intent;
    private List<QuestionPiano> questionPianoList;
    private List<MediaPlayer> listMediaPlayer = new ArrayList<>();
    private ProgressBar pb;

    View btn_do;
    View btn_do_diese;
    View btn_re;
    View btn_re_diese;
    View btn_mi;
    View btn_fa;
    View btn_fa_diese;
    View btn_sol;
    View btn_sol_diese;
    View btn_la;
    View btn_si_bemol;
    View btn_si;

    TextView tv_do;
    TextView tv_do_diese;
    TextView tv_re;
    TextView tv_re_diese;
    TextView tv_mi;
    TextView tv_fa;
    TextView tv_fa_diese;
    TextView tv_sol;
    TextView tv_sol_diese;
    TextView tv_la;
    TextView tv_si_bemol;
    TextView tv_si;

    TextView tvNbQuestion;
    ProgressBar pgCirclePiano;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveau_piano);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        tvNbTentative = findViewById(R.id.tvNbTentative);
        pb = findViewById(R.id.progressBar);
        mHandler = new Handler();
        currentSong = new MediaPlayer();

        btn_do = findViewById(R.id.b_do);
        btn_do_diese = findViewById(R.id.b_do_diese);
        btn_re = findViewById(R.id.b_re);
        btn_re_diese = findViewById(R.id.b_re_diese);
        btn_mi = findViewById(R.id.b_mi);
        btn_fa = findViewById(R.id.b_fa);
        btn_fa_diese = findViewById(R.id.b_fa_diese);
        btn_sol = findViewById(R.id.b_sol);
        btn_sol_diese = findViewById(R.id.b_sol_diese);
        btn_la = findViewById(R.id.b_la);
        btn_si_bemol = findViewById(R.id.b_si_bemol);
        btn_si = findViewById(R.id.b_si);

        tv_do = findViewById(R.id.tv_do);
        tv_do_diese = findViewById(R.id.tv_do_diese);
        tv_re = findViewById(R.id.tv_re);
        tv_re_diese = findViewById(R.id.tv_re_diese);
        tv_mi = findViewById(R.id.tv_mi);
        tv_fa = findViewById(R.id.tv_fa);
        tv_fa_diese = findViewById(R.id.tv_fa_diese);
        tv_sol = findViewById(R.id.tv_sol);
        tv_sol_diese = findViewById(R.id.tv_sol_diese);
        tv_la = findViewById(R.id.tv_la);
        tv_si_bemol = findViewById(R.id.tv_si_bemol);
        tv_si = findViewById(R.id.tv_si);

        btn_reecoutez = findViewById(R.id.b_recommencer_piano);

        tvNiveau = findViewById(R.id.tv_titre_niveau_piano);
        tvQuestion = findViewById(R.id.tv_question_piano);
        tvScore = findViewById(R.id.tv_score_piano);

        media_do = MediaPlayer.create(getApplicationContext(), R.raw.note_do);
        media_do_diese = MediaPlayer.create(getApplicationContext(), R.raw.do_diese);
        media_re = MediaPlayer.create(getApplicationContext(), R.raw.re);
        media_re_diese = MediaPlayer.create(getApplicationContext(), R.raw.re_diese);
        media_mi = MediaPlayer.create(getApplicationContext(), R.raw.mi);
        media_fa = MediaPlayer.create(getApplicationContext(), R.raw.fa);
        media_fa_diese = MediaPlayer.create(getApplicationContext(), R.raw.fa_diese);
        media_sol = MediaPlayer.create(getApplicationContext(), R.raw.sol);
        media_sol_diese = MediaPlayer.create(getApplicationContext(), R.raw.sol_diese);
        media_la = MediaPlayer.create(getApplicationContext(), R.raw.la);
        media_si_bemol = MediaPlayer.create(getApplicationContext(), R.raw.si_bemol);
        media_si = MediaPlayer.create(getApplicationContext(), R.raw.si);

        tvQuestion.setText(getString(R.string.ecoutez_jouez)/*"Ecoutez et jouez"*/);
        tvNbQuestion = findViewById(R.id.tv_num_question_piano);
        pgCirclePiano = findViewById(R.id.progressPiano);

        intent = new Intent(PianoActivity.this, PianoActivity.class);
        if (count > 1) {
            nbTentative = 0;
            count = 0;
            numNote = 0;
            Intent intent = new Intent(PianoActivity.this, CreerQuestionActivity.class);
            intent.putExtra("nbQuestion", "4");
            SharedPreferences sp = getSharedPreferences("score", Activity.MODE_PRIVATE);
            int getScore = sp.getInt("getScore", -1);
            score = getScore + nbPoint;
            tvScore.setText(score+"/4 points");
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("getScore", score);
            //editor.putInt("getNiveau", question.getNumNiveau());
            editor.apply();
            nbPoint = 0;
            startActivity(intent);
        } else {
            questionPianoList = getIntent().getParcelableArrayListExtra("listQuestionPiano");
            SharedPreferences sp = getSharedPreferences("score", Activity.MODE_PRIVATE);
            int getScore = sp.getInt("getScore", -1);
            score = getScore + nbPoint;
            tvScore.setText(score+"/4 points");

            if (questionPianoList == null) {
                System.out.println(getIntent().getExtras());
            } else {
                System.out.println(questionPianoList);
                new GetRessources().execute();
                question = questionPianoList.get(count);
                listNote = question.getReponse();
                if (Locale.getDefault().getDisplayLanguage().toString().equals("français")){
                    tvQuestion.setText(question.getTitre());
                }
                else {
                    tvQuestion.setText(question.getTitreEn());
                }
                tvNiveau.setText(getString(R.string.niveau)+" " + question.getNumNiveau() /*+ " - "+getString(R.string.question) +" "+ question.getNumQuestion()*/);
                tvNbQuestion.setText(question.getNumQuestion()+"/4");
                pgCirclePiano.setProgress(Integer.parseInt(question.getNumQuestion())*25);
            }
        }

        new Thread(() -> {
            btn_do.setOnClickListener(v -> {
                media_do.start();
                listMediaPlayer.add(media_do);
                checkNote("do");
            });
        }).start();


        new Thread(() -> {
            btn_do_diese.setOnClickListener(v -> {
                media_do_diese.start();
                listMediaPlayer.add(media_do_diese);
                checkNote("do_diese");
            });
        }).start();

        new Thread(() -> {
            btn_re.setOnClickListener(v -> {
                media_re.start();
                listMediaPlayer.add(media_re);
                checkNote("re");
            });
        }).start();

        new Thread(() -> {
            btn_re_diese.setOnClickListener(v -> {
                media_re_diese.start();
                listMediaPlayer.add(media_re_diese);
                checkNote("re_diese");
            });
        }).start();

        new Thread(() -> {
            btn_mi.setOnClickListener(v -> {
                media_mi.start();
                listMediaPlayer.add(media_mi);
                checkNote("mi");
            });
        }).start();

        new Thread(() -> {
            btn_fa.setOnClickListener(v -> {
                media_fa.start();
                listMediaPlayer.add(media_fa);
                checkNote("fa");
            });
        }).start();

        new Thread(() -> {
            btn_fa_diese.setOnClickListener(v -> {
                media_fa_diese.start();
                listMediaPlayer.add(media_fa_diese);
                checkNote("fa_diese");
            });
        }).start();

        new Thread(() -> {
            btn_sol.setOnClickListener(v -> {
                media_sol.start();
                listMediaPlayer.add(media_sol);
                checkNote("sol");
            });
        }).start();

        new Thread(() -> {
            btn_sol_diese.setOnClickListener(v -> {
                media_sol_diese.start();
                listMediaPlayer.add(media_sol_diese);
                checkNote("sol_diese");
            });
        }).start();

        new Thread(() -> {
            btn_la.setOnClickListener(v -> {
                media_la.start();
                listMediaPlayer.add(media_la);
                checkNote("la");
            });
        }).start();

        new Thread(() -> {
            btn_si_bemol.setOnClickListener(v -> {
                media_si_bemol.start();
                listMediaPlayer.add(media_si_bemol);
                checkNote("si_bemol");
            });
        }).start();

        new Thread(() -> {
            btn_si.setOnClickListener(v -> {
                media_si.start();
                listMediaPlayer.add(media_si);
                checkNote("si");
            });
        }).start();

        btn_reecoutez.setOnClickListener(v -> {
            try {
                runProgressBar(3000, pb, btn_reecoutez);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentSong.start();
        });
        new GetRessources().execute();
    }

    private class GetRessources extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        // appelee automatiquement après onPreExecute
        protected Void doInBackground(Void... arg0) {
            if (question != null) {
                String url = question.getUrl() + question.getIdAudio();
                System.out.println("background task lanched piano activity");
                try {
                    //"https://androidpianomaster.000webhostapp.com/ressources/Niveau1/1.wav"
                    currentSong.reset();
                    currentSong.setDataSource(url);
                    currentSong.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    currentSong.prepare();
                    currentSong.start();
                    runProgressBar(3000, pb, btn_reecoutez);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    @SuppressLint("SetTextI18n")
    public void checkNote(String note) {
        Intent intent = new Intent(PianoActivity.this, PianoActivity.class);
        if (tvQuestion.getText().equals(getString(R.string.recommencer)/*"Recommencer"*/)) tvQuestion.setText(getString(R.string.ecoutez_jouez)/*"Ecoutez et jouez !"*/);
        if (numNote <= listNote.size() - 1 && listNote.get(numNote).equals(note)) {
            numNote++;
            if (numNote == 4) {
                colorNote();
                count++;
                nbPoint++;
                currentSong.stop();
                stopAndResetSound();
                new Thread(() -> {
                    try {
                        Thread.sleep(delaiEntreChaqueQuestion);
                        intent.putParcelableArrayListExtra("listQuestionPiano", (ArrayList<? extends Parcelable>) questionPianoList);
                        stopAndResetSound();
                        startActivity(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } else {
            tvQuestion.setText("Recommencer");
            if(nbTentative<3) {
                nbTentative++;
            }
            tvNbTentative.setText("" + nbTentative);
            if (nbTentative == 3) {
                colorNote();
                count++;
                currentSong.stop();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(delaiEntreChaqueQuestion);
                            intent.putParcelableArrayListExtra("listQuestionPiano", (ArrayList<? extends Parcelable>) questionPianoList);
                            stopAndResetSound();
                            startActivity(intent);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            numNote = 0;
        }
    }

    public void stopAndResetSound(){
        for(MediaPlayer mediaPlayer : listMediaPlayer){
            mediaPlayer.release();
        }
    }

    @SuppressLint("SetTextI18n")
    public void colorNote() {
        int i=0;
        btn_do.setClickable(false);
        btn_do_diese.setClickable(false);
        btn_re.setClickable(false);
        btn_re_diese.setClickable(false);
        btn_mi.setClickable(false);
        btn_fa.setClickable(false);
        btn_fa_diese.setClickable(false);
        btn_sol.setClickable(false);
        btn_sol_diese.setClickable(false);
        btn_la.setClickable(false);
        btn_si_bemol.setClickable(false);
        btn_si.setClickable(false);
        btn_reecoutez.setClickable(false);
        for (String bonneNote : listNote) {
            i++;
            getButtonByName(bonneNote).setBackground(getDrawable(R.drawable.button_brep));
            System.out.println(bonneNote);
            if(!getTextViewByName(bonneNote).getText().equals(""))
                getTextViewByName(bonneNote).setText(getTextViewByName(bonneNote).getText()+", "+i);
            else
                getTextViewByName(bonneNote).setText(""+i);
        }
    }

    public View getButtonByName(String name) {
        switch (name) {
            case "do":
                return btn_do;
            case "do_diese":
                return btn_do_diese;
            case "re":
                return btn_re;
            case "re_diese":
                return btn_re_diese;
            case "mi":
                return btn_mi;
            case "fa":
                return btn_fa;
            case "fa_diese":
                return btn_fa_diese;
            case "sol":
                return btn_sol;
            case "sol_diese":
                return btn_sol_diese;
            case "la":
                return btn_la;
            case "si_bemol":
                return btn_si_bemol;
            case "si":
                return btn_si;
            default:
                return btn_do;
        }
    }

    public TextView getTextViewByName(String name) {
        switch (name) {
            case "do":
                return tv_do;
            case "do_diese":
                return tv_do_diese;
            case "re":
                return tv_re;
            case "re_diese":
                return tv_re_diese;
            case "mi":
                return tv_mi;
            case "fa":
                return tv_fa;
            case "fa_diese":
                return tv_fa_diese;
            case "sol":
                return tv_sol;
            case "sol_diese":
                return tv_sol_diese;
            case "la":
                return tv_la;
            case "si_bemol":
                return tv_si_bemol;
            case "si":
                return tv_si;
            default:
                return tv_do;
        }
    }

    private void runProgressBar(int nbMS, ProgressBar pb, Button bRecommencer) throws InterruptedException {
        //todo bouton recommencer griser
        bRecommencer.setEnabled(false);
        pourcentagePB = 0;
        pb.setProgress(pourcentagePB);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (pourcentagePB < 100) {
                    pourcentagePB++;
                    android.os.SystemClock.sleep(nbMS / 100);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            pb.setProgress(pourcentagePB);
                        }
                    });
                }
                ;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        bRecommencer.setEnabled(true);
                        //TODO bouton recommencer visible
                    }
                });
            }
        });
        t.start();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sp = getSharedPreferences("score", Activity.MODE_PRIVATE);
        SharedPreferences sp2 = getSharedPreferences("niveau", Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("getNiveau", 0);
        editor.apply();

        SharedPreferences.Editor editor2 = sp2.edit();
        editor2.putInt("getScore", 0);
        editor2.apply();
        Intent intent = new Intent(this, LevelActivity.class);
        intent.putExtra("numNiveau", 0);
        startActivity(intent);
    }
}
