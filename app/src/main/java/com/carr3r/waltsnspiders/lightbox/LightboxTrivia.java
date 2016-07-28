package com.carr3r.waltsnspiders.lightbox;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.carr3r.waltsnspiders.Quiz;
import com.carr3r.waltsnspiders.R;
import com.carr3r.waltsnspiders.SoundController;
import com.carr3r.waltsnspiders.listeners.OnLightboxFinishes;

/**
 * Created by wneto on 27/10/2015.
 */
public class LightboxTrivia extends DialogFragment implements View.OnClickListener {

    protected Quiz quiz;
    protected OnLightboxFinishes listener;
    protected boolean stillVisible = true;
    protected ImageView j1;
    protected ImageView j2;
    protected ImageView j3;
    protected ImageView j4;
    protected boolean shouldReleasePause = false;

    public LightboxTrivia(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setShouldReleasePause(boolean v) {
        shouldReleasePause = v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.lightbox_trivia, container,
                false);

        SoundController.getInstance().playBackgroundSound(SoundController.background_quiz);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().getWindow().setDimAmount(0f);
        getDialog().setCanceledOnTouchOutside(false);

        ((TextView) rootView.findViewById(R.id.description)).setText(quiz.getDescription());
        ((TextView) rootView.findViewById(R.id.answerA)).setText(quiz.getAnswers()[0]);
        ((TextView) rootView.findViewById(R.id.answerB)).setText(quiz.getAnswers()[1]);
        ((TextView) rootView.findViewById(R.id.answerC)).setText(quiz.getAnswers()[2]);
        ((TextView) rootView.findViewById(R.id.answerD)).setText(quiz.getAnswers()[3]);

        if (shouldReleasePause)
            ((Button) rootView.findViewById(R.id.closeButton)).setText(getString(R.string.resume));
        else
            ((Button) rootView.findViewById(R.id.closeButton)).setText(getString(R.string.go));

        j1 = (ImageView) rootView.findViewById(R.id.letterA);
        j2 = (ImageView) rootView.findViewById(R.id.letterB);
        j3 = (ImageView) rootView.findViewById(R.id.letterC);
        j4 = (ImageView) rootView.findViewById(R.id.letterD);

        ((Button) rootView.findViewById(R.id.closeButton)).setOnClickListener(this);

        (new Thread() {
            public void run() {
                int index = 1;
                final int r[] = new int[4];
                while (stillVisible) {
                    switch (index++) {
                        case 1:
                            r[0] = R.drawable.jewel1_p1;
                            r[1] = R.drawable.jewel2_p1;
                            r[2] = R.drawable.jewel3_p1;
                            r[3] = R.drawable.jewel4_p1;
                            break;
                        case 2:
                            r[0] = R.drawable.jewel1_p2;
                            r[1] = R.drawable.jewel2_p2;
                            r[2] = R.drawable.jewel3_p2;
                            r[3] = R.drawable.jewel4_p2;
                            break;
                        case 3:
                            r[0] = R.drawable.jewel1_p3;
                            r[1] = R.drawable.jewel2_p3;
                            r[2] = R.drawable.jewel3_p3;
                            r[3] = R.drawable.jewel4_p3;
                            break;
                        case 4:
                            r[0] = R.drawable.jewel1_p4;
                            r[1] = R.drawable.jewel2_p4;
                            r[2] = R.drawable.jewel3_p4;
                            r[3] = R.drawable.jewel4_p4;
                            break;
                    }

                    if (index > 4)
                        index = 1;

                    if (stillVisible && getActivity() != null)
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                j1.setImageResource(r[0]);
                                j2.setImageResource(r[1]);
                                j3.setImageResource(r[2]);
                                j4.setImageResource(r[3]);
                            }
                        });

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        // Do something else
        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                onClick(null);
            }
        };
    }

    public void setOnFinishListener(OnLightboxFinishes newListener) {
        listener = newListener;
    }

    @Override
    public void onClick(View v) {

        stillVisible = false;
        getDialog().dismiss();
        if (listener != null)
            if (v == null) {
                if (shouldReleasePause)
                    listener.onLightBoxFinishes(LightboxPause.class);
                else
                    listener.onLightBoxFinishes(LightboxGameOver.class);
            }
        if (!shouldReleasePause)
            listener.onLightBoxFinishes(LightboxTrivia.class);
        else
            listener.onLightBoxFinishes(LightboxPause.class);

    }
}