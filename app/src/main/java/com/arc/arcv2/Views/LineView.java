package com.arc.arcv2.Views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.arc.arcv2.R;

public class LineView extends View {
    private Path path;
    private Paint paint;
    private float length;
    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init()
    {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
        path.moveTo(0,0);
        path.lineTo(110,0);

        PathMeasure measure = new PathMeasure(path,false);
        length = measure.getLength();
        float intervals [] = new float[]{length,length};
        ObjectAnimator animator = ObjectAnimator.ofFloat(this,"phase",1.0f,0.0f);
        animator.setDuration(1000);
        animator.start();
    }

    public void setPhase(float phase)
    {
        paint.setPathEffect(createPathEffect(length,phase,0.0f));
        invalidate();
    }

    private PathEffect createPathEffect(float length, float phase, float v) {
        return new DashPathEffect(new float[]{length,length},Math.max(phase * length,v));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
    }
}
