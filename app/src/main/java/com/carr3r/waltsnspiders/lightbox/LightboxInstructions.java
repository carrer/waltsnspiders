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
import android.widget.CheckBox;
import android.widget.TextView;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.R;
import com.carr3r.waltsnspiders.listeners.OnLightboxFinishes;

/**
 * Created by wneto on 27/10/2015.
 */
public class LightboxInstructions extends DialogFragment implements View.OnClickListener {

    public static String TAG = "LightboxInstructions";
    protected OnLightboxFinishes listener;
    protected CheckBox checkHide;
    protected Button skipButton;
    protected Button nextButton;
    protected Button priorButton;
    protected TextView display;
    protected int index = 1;
    protected MyThread typing;
    protected String[] instructions;

    public void setListener(OnLightboxFinishes newListener) {
        listener = newListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.lightbox_instructions, container,
                false);

        instructions = new String[5];
        instructions[0] = getString(R.string.game_plot_1);
        instructions[1] = getString(R.string.game_plot_2);
        instructions[2] = getString(R.string.game_plot_3);
        instructions[3] = getString(R.string.game_plot_4);
        instructions[4] = getString(R.string.game_plot_5);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().getWindow().setDimAmount(0f);
        getDialog().setCanceledOnTouchOutside(false);

        checkHide = (CheckBox) rootView.findViewById(R.id.do_not_show);
        skipButton = (Button) rootView.findViewById(R.id.skipBtn);
        nextButton = (Button) rootView.findViewById(R.id.nextBtn);
        priorButton = (Button) rootView.findViewById(R.id.priorBtn);
        display = (TextView) rootView.findViewById(R.id.display);

        skipButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        priorButton.setOnClickListener(this);

        return rootView;
    }

    public void onResume() {
        super.onResume();
        createThread(index);
    }

    public void createThread(int index) {
        if (typing != null) {
            typing.cancel();
            typing = null;
        }
        typing = new MyThread(index);
        typing.start();
    }

    private void updateButtons() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (index < instructions.length)
                    nextButton.setVisibility(View.VISIBLE);
                else
                    nextButton.setVisibility(View.INVISIBLE);

                if (index > 1)
                {
                    priorButton.setVisibility(View.VISIBLE);
                    if (index == 4)
                        Environment.getInstance().getGameController().setShowArrows(true);
                    else
                        Environment.getInstance().getGameController().setShowArrows(false);
                }
                else
                    priorButton.setVisibility(View.INVISIBLE);

                if (index == instructions.length)
                    skipButton.setText(getString(R.string.lets_start));
                else
                    skipButton.setText(getString(R.string.skip));
            }
        });
    }

    private void goNext() {
        if (index < instructions.length)
            index++;
        updateButtons();
        createThread(index);
    }

    private void goPrior() {
        if (index > 1)
            index--;
        updateButtons();
        createThread(index);
    }

    private class MyThread extends Thread {
        protected String content;
        protected int i = 0;

        public MyThread(int index) {
            content = instructions[index - 1];
        }

        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted() && i <= content.length()) {
                    final String current = content.substring(0, ++i);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            display.setText(current);
                        }
                    });
                    Thread.sleep(50);
                }
            } catch (Exception e) {
            }
        }

        public void cancel() {
            interrupt();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                //do your stuff
                Environment.getInstance().getGameController().setShowArrows(false);
                getDialog().dismiss();
                if (listener != null)
                    listener.onLightBoxFinishes(LightboxGameOver.class);
            }
        };
    }

    public void setOnFinishListener(OnLightboxFinishes newListener) {
        listener = newListener;
    }

    @Override
    public void onClick(View v) {
        Environment.getInstance().getGameController().setShowArrows(false);
        if (v.getId() == R.id.skipBtn) {
            if (checkHide.isChecked())
                Environment.getInstance().disableInstructions();
            getDialog().dismiss();
            if (listener != null)
                listener.onLightBoxFinishes(LightboxInstructions.class);
        } else if (v.getId() == R.id.priorBtn) {
            goPrior();
        } else if (v.getId() == R.id.nextBtn) {
            goNext();
        }
    }
}