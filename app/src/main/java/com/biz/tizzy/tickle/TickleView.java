package com.biz.tizzy.tickle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

/**
 * Created by tizzy on 1/19/18.
 */

public class TickleView extends View {
    private static final String TAG = "TickleView";

    // paints
    private Paint mBackgoundPaint;
    private Paint mTextPaint;
    private Paint mTearPaint;
    private Paint mGearsPaint;

    private float mLeft;
    private float mRight;
    private float mTop;
    private float mBottom;

    private boolean isCrying;
    private int cryingLength = 0;

    private int tickleLength = 0;

    // Used when creating the view in code
    public TickleView(Context context) {
        this(context, null);
    }

    // Used when inflating the view from XML
    public TickleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBackgoundPaint = new Paint();
        mBackgoundPaint.setColor(0xff141b3f);

        mTextPaint = new Paint();
        mTextPaint.setColor(0xff18a884);
        mTextPaint.setTextSize(160f);

        mTearPaint = new Paint();
        mTearPaint.setColor(0xff7cb3d7);

        mGearsPaint = new Paint();
        mGearsPaint.setColor(0xff7cb3d7);
        mGearsPaint.setTextSize(100f);
        mGearsPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isTickled(current)) {
                    tickle();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTickled(current)) {
                    tickleLength++;
                    tickle();

                    if (isCrying) {
                        cryingLength++;
                    }

                    if (tickleLength > 100) {
                        MediaPlayer gears = MediaPlayer.create(getContext(), R.raw.gears);
                        gears.start();
                        isCrying = true;
                        tickleLength = 0;
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Fill the background
        canvas.drawPaint(mBackgoundPaint);

        // initialize
        if (mRight == 0.0) {
            mLeft = this.getWidth() / 2 - 160;
            mRight = this.getWidth() / 2 + 160;
            mTop = this.getHeight() / 2 + 60;
            mBottom = this.getHeight() / 2 - 60;
        }

        // text
        float textX = mLeft + 20;
        float textY = 20 + (mTop + mBottom) / 2;

        // draw tickle
        canvas.drawText("tickle", textX, textY, mTextPaint);

        if (isCrying) {
            // tears & gears
            if (cryingLength % 2 == 0) {
                canvas.drawOval(mLeft - 15, mTop + 40, mRight - 300, mBottom + 80, mTearPaint);
                canvas.drawOval(mLeft + 400, mTop + 40, mRight + 115, mBottom + 80, mTearPaint);
            } else {
                canvas.drawOval(mLeft - 15, mTop + 80, mRight - 300, mBottom + 120, mTearPaint);
                canvas.drawOval(mLeft + 400, mTop + 80, mRight + 115, mBottom + 120, mTearPaint);
            }

            canvas.drawText("GEARS!!", textX + 200, textY - 200, mGearsPaint);
        }

        if (cryingLength > 30) {
            isCrying = false;
            cryingLength = 0;
        }

    }

    private boolean isTickled(PointF click) {
        boolean left = (click.x >= mLeft);
        boolean right = (click.x <= mRight);
        boolean top = (click.y <= mTop);
        boolean bottom = (click.y >= mBottom);

        return ((left & right) & (top & bottom));
    }

    private void tickle() {

        Random rand = new Random();
        int x = rand.nextInt(17) - 8;
        int y = rand.nextInt(17) - 8;

        mLeft += x;
        mRight += x;
        mBottom += y;
        mTop += y;

    }
}
