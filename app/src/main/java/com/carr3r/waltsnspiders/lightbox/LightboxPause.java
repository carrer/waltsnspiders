package com.carr3r.waltsnspiders.lightbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.GameView;
import com.carr3r.waltsnspiders.R;
import com.carr3r.waltsnspiders.listeners.OnLightboxFinishes;

/**
 * Created by wneto on 27/10/2015.
 */
public class LightboxPause extends DialogFragment implements View.OnClickListener {

    protected OnLightboxFinishes listener;

    public void setListener(OnLightboxFinishes newListener) {
        listener = newListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.lightbox_pause, container,
                false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().getWindow().setDimAmount(0f);
        getDialog().setCanceledOnTouchOutside(false);

        ((Button) rootView.findViewById(R.id.resumeButton)).setOnClickListener(this);
        ((Button) rootView.findViewById(R.id.review)).setOnClickListener(this);

        // Do something else
        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {

                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.leave_game))
                        .setMessage(getString(R.string.you_are_about_to_leave))
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(1);
                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)
                        .show();
            }
        };
    }

    public void setOnFinishListener(OnLightboxFinishes newListener) {
        listener = newListener;
    }

    @Override
    public void onClick(View v) {
        getDialog().dismiss();

        if (v.getId() == R.id.resumeButton) {
            if (listener != null)
                listener.onLightBoxFinishes(LightboxPause.class);
        } else {
            LightboxTrivia lightboxTriviaFragment = new LightboxTrivia(Environment.getInstance().getGameController().getQuiz());
            lightboxTriviaFragment.setOnFinishListener(Environment.getInstance().getGameController());
            lightboxTriviaFragment.setShouldReleasePause(true);
            lightboxTriviaFragment.show(GameView.getMainActivity().getSupportFragmentManager(), null);
        }

    }
}