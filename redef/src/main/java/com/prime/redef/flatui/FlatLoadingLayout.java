package com.prime.redef.flatui;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prime.redef.R;
import com.prime.redef.core.Action;
import com.prime.redef.utils.ViewUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;

public class FlatLoadingLayout extends RelativeLayout {

    private enum State {
        IDLE,
        LOADING,
        ERROR
    }

    private final static int FADE_DURATION = 300;
    private final static int BUTTON_CLICK_TRESHOLD = 150;

    private AVLoadingIndicatorView indicator;
    private LinearLayout errorLayout;
    private TextView errorText;
    private Button retryButton;

    private State state = State.IDLE;
    private long lastButtonPressed = 0;

    private Action onButtonClickedListener;

    public FlatLoadingLayout(Context context) {
        super(context);
        init(context);
    }

    public FlatLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FlatLoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public FlatLoadingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.layout_loading, this);

        indicator = findViewById(R.id.indicator);
        errorLayout = findViewById(R.id.error_layout);
        errorText = findViewById(R.id.error_message);
        retryButton = findViewById(R.id.retry_button);

        indicator.setVisibility(INVISIBLE);
        errorLayout.setVisibility(INVISIBLE);
        retryButton.setEnabled(false);
        retryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked();
            }
        });
    }

    private boolean fadeInView(final View view, final Action afterFade) {
        view.clearAnimation();
        view.animate().cancel();

        if (view.getVisibility() == VISIBLE) {
            if (afterFade != null)
                afterFade.action();
            return false;
        }

        view.setVisibility(VISIBLE);

        Animation anim = new AlphaAnimation(0, 1);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(FADE_DURATION);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (afterFade != null)
                    afterFade.action();
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        view.startAnimation(anim);
        return true;
    }

    private boolean fadeOutView(final View view, final Action afterFade) {
        view.clearAnimation();
        view.animate().cancel();

        if (view.getVisibility() != VISIBLE) {
            if (afterFade != null)
                afterFade.action();
            return false;
        }

        Animation anim = new AlphaAnimation(1, 0);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(FADE_DURATION);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(INVISIBLE);
                if (afterFade != null)
                    afterFade.action();
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        view.startAnimation(anim);
        return true;
    }

    private void dissappearView(final View view) {
        view.clearAnimation();
        view.animate().cancel();

        if (view.getVisibility() == VISIBLE) {
            view.setVisibility(INVISIBLE);
        }
    }

    private void onButtonClicked() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastButtonPressed < BUTTON_CLICK_TRESHOLD)
            return;
        lastButtonPressed = currentTime;
        if (state != State.ERROR)
            return;

        if (onButtonClickedListener != null)
            onButtonClickedListener.action();
    }

    public void loading() {
        if (state == State.LOADING) return;
        state = State.LOADING;

        fadeInView(indicator, null);

        // NOT: İkisinden biri olabilirdi
        //fadeOutView(errorLayout, null);
        dissappearView(errorLayout);
    }

    public void idle() {
        idle(null);
    }

    public void idle(final Action onFinish) {
        if (state == State.IDLE) return;
        state = State.IDLE;

        boolean fadeRequired;

        fadeRequired = fadeOutView(indicator, null);
        fadeRequired |= fadeOutView(errorLayout, null);

        if (fadeRequired) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (onFinish != null)
                        onFinish.action();
                }
            }, FADE_DURATION);
        } else {
            if (onFinish != null)
                onFinish.action();
        }
    }

    public void error(String message) {
        errorText.setText(message);
        retryButton.setEnabled(true);

        if (state == State.ERROR)
            return;

        state = State.ERROR;

        // NOT: İkisinden biri olabilirdi
        //fadeOutView(indicator, null);
        dissappearView(indicator);

        fadeInView(errorLayout, null);
    }

    public void setOnButtonClickedListener(Action onButtonClickedListener) {
        this.onButtonClickedListener = onButtonClickedListener;
    }
}
