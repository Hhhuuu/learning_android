package ru.mamapapa.task2;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {
    private static final String STATE_COUNTER = "counter";
    private static final String STATE_SHOW_BALL = "show_ball";
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;

    private Button reset;
    private ImageView priceFirst;
    private ImageView priceSecond;
    private ImageView priceThird;

    private Random random = new Random();
    private int counter = nextPosBall();
    private boolean isShowBall = false;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        reset = findViewById(R.id.resetButton);
        priceFirst = getImageViewByPos(FIRST);
        priceSecond = getImageViewByPos(SECOND);
        priceThird = getImageViewByPos(THIRD);

        List<ImageView> imageViews = Arrays.asList(priceFirst, priceSecond, priceThird);

        reset.setOnClickListener(v -> {
            counter = nextPosBall();
            for (ImageView imageView : imageViews) {
                imageView.setImageDrawable(getDrawable(R.drawable.box));
            }
            isShowBall = false;
            updateShow();
        });

        priceFirst.setOnClickListener(v -> {
            showPrice(FIRST, priceFirst);
            updateShow();
        });

        priceSecond.setOnClickListener(v -> {
            showPrice(SECOND, priceSecond);
            updateShow();
        });

        priceThird.setOnClickListener(v -> {
            showPrice(THIRD, priceThird);
            updateShow();
        });
        restoreState(bundle);
        updateShow();
    }

    private int nextPosBall() {
        return random.nextInt(3);
    }

    private void showPrice(int pos, ImageView view) {
        Drawable drawable = getPriceDrawable();
        String showText = getShowText(R.string.lose);
        if (counter == pos) {
            view.setImageDrawable(drawable);
            showText = getShowText(R.string.win);
        } else {
            updateShawBall(drawable);
        }
        isShowBall = true;
        Toast.makeText(MainActivity.this, showText, Toast.LENGTH_LONG).show();
    }

    private String getShowText(int id) {
        return getText(id).toString();
    }

    private void restoreState(Bundle bundle) {
        if (bundle != null) {
            restoreCounter(bundle);
            restoreShowBall(bundle);
        }
    }

    private void restoreCounter(Bundle bundle) {
        counter = bundle.getInt(STATE_COUNTER, 0);
    }

    private void restoreShowBall(Bundle bundle) {
        isShowBall = bundle.getBoolean(STATE_SHOW_BALL, true);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(STATE_COUNTER, counter);
        bundle.putBoolean(STATE_SHOW_BALL, isShowBall);
    }

    public void updateShow() {
        reset.setVisibility(isShowBall ? View.VISIBLE : View.INVISIBLE);
        if (isShowBall) {
            updateShawBall(getPriceDrawable());
        }
    }

    private Drawable getPriceDrawable() {
        return getDrawable(R.drawable.price);
    }

    private void updateShawBall(Drawable drawable) {
        ImageView imageView = getImageViewByPos(counter);
        imageView.setImageDrawable(drawable);
    }

    private ImageView getImageViewByPos(int pos) {
        int resourceId;
        switch (pos) {
            case FIRST:
                resourceId = R.id.priceFirst;
                break;
            case SECOND:
                resourceId = R.id.priceSecond;
                break;
            case THIRD:
                resourceId = R.id.priceThird;
                break;
            default:
                throw new IllegalArgumentException("Неверно задан счетчик");
        }
        return findViewById(resourceId);
    }
}
