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
import android.widget.TextView;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.R;
import com.carr3r.waltsnspiders.SoundController;
import com.carr3r.waltsnspiders.listeners.OnLightboxFinishes;
import com.carr3r.waltsnspiders.visual.Score;

/**
 * Created by wneto on 27/10/2015.
 */
public class LightboxGameOver extends DialogFragment implements View.OnClickListener {

    protected Score score;

    public LightboxGameOver(Score score) {
        this.score = score;
    }

    protected OnLightboxFinishes listener;

    public void setListener(OnLightboxFinishes newListener) {
        listener = newListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.lightbox_game_over, container,
                false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().getWindow().setDimAmount(0f);
        getDialog().setCanceledOnTouchOutside(false);

        Environment.getInstance().registerHighestScore(score);

        String strFormat = getResources().getString(R.string.you_got_to_level_and_made);

        ((TextView) rootView.findViewById(R.id.message)).setText(String.format(strFormat, score.getLevel(), score.getPoints()));

        SoundController.getInstance().playBackgroundSound(SoundController.background_gameover);

        ((Button) rootView.findViewById(R.id.closeButton)).setOnClickListener(this);

        // Do something else
        return rootView;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                //do your stuff
                getDialog().dismiss();
                if (listener != null)
                    listener.onLightBoxFinishes(LightboxGameOver.class);
            }
        };
    }

    @Override
    public void onClick(View v) {

        getDialog().dismiss();
        if (listener != null)
            listener.onLightBoxFinishes(LightboxGameOver.class);

    }

    public void setOnFinishListener(OnLightboxFinishes newListener) {
        listener = newListener;
    }
}