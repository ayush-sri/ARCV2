package com.arc.arcv2.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.arc.arcv2.R;

import java.text.AttributedCharacterIterator;

class CustomProgressView extends View
{

    private static final float INDERTIMINATE_MIN_SWEEP=15f;
    public static final int RESETTING_ANGLE=620;
    private Paint outer;
    private Paint inner;
    private float thickness;
    float innerPadding;
    private int animDuration;
    private RectF outerCircleRect;
    private RectF innerCircleRect;

    @ColorInt
    private int outerCol;

    @ColorInt
    private int innerCol;

    private int steps;
    private int size;
    private float startAngle;
    private float indertiminateSweep;
    private float indetermnateRotateOffset;
    private AnimatorSet indeterminateAnimator;
    public CustomProgressView(Context context) {
        super(context);
        init(null,0);
    }
    public CustomProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public CustomProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    private void init(AttributeSet attr, int defStyle) {
        outer = new Paint(Paint.ANTI_ALIAS_FLAG);
        inner = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.outerCircleRect = new RectF();
        this.innerCircleRect = new RectF();

        Resources resources = getResources();

        final TypedArray a = getContext().obtainStyledAttributes(
                attr,
                R.styleable.CustomProgressView,
                defStyle,
                0
        );
        thickness=a.getDimensionPixelSize(R.styleable.CustomProgressView_dpv_thickness,resources.getDimensionPixelSize(R.dimen.default_thickness));
        innerPadding = a.getDimensionPixelSize(
                R.styleable.CustomProgressView_dpv_inner_padding,
                resources.
                        getDimensionPixelSize(
                                R.dimen.default_inner_padding)
        );
        outerCol=a.getColor(R.styleable.CustomProgressView_dpv_outer_color,
               ContextCompat.getColor(getContext(),R.color.progress_outer)
        );
        innerCol=a.getColor(R.styleable.CustomProgressView_dpv_inner_color,
                ContextCompat.getColor(getContext(),R.color.progress_inner)
                );
        animDuration = a.getInteger(R.styleable.CustomProgressView_dpv_anim_duration,
                resources.getInteger(R.integer.default_anim_duration)
                );
        steps = resources.getInteger(R.integer.default_anim_step);
        startAngle = resources.getInteger(R.integer.default_start_angle);
        a.recycle();
        setPaint();
    }

    private void setPaint() {
        outer.setColor(outerCol);
        outer.setStyle(Paint.Style.STROKE);
        outer.setStrokeWidth(thickness);
        outer.setStrokeCap(Paint.Cap.BUTT);
        inner.setColor(innerCol);
        inner.setStyle(Paint.Style.STROKE);
        inner.setStrokeCap(Paint.Cap.BUTT);
        inner.setStrokeWidth(thickness);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(outerCircleRect,
                startAngle+indetermnateRotateOffset,
                       indertiminateSweep,
                       false,
                         outer);
        canvas.drawArc(innerCircleRect,
                startAngle+indetermnateRotateOffset+180,
                 indertiminateSweep,
                false,
                inner);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int xPad=getPaddingLeft() + getPaddingRight();
        int yPad=getPaddingTop() + getPaddingBottom();
        int width = getMeasuredWidth() - xPad;
        int height = getMeasuredHeight() - yPad;
        size = (width < height)?width:height;
        setMeasuredDimension(size+xPad,size+yPad);
        updateRectangleBounds();
    }

    private void updateRectangleBounds() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        this.outerCircleRect.set(paddingLeft + thickness,
                paddingTop + thickness,
                size - paddingLeft - thickness,
                 size - paddingTop - thickness);
        this.innerCircleRect.set(paddingLeft + thickness + innerPadding,
                paddingTop + thickness + innerPadding,
                size - paddingLeft - thickness - innerPadding,
                size - paddingTop - thickness - innerPadding);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        size = (w < h)? w : h;
        updateRectangleBounds();
    }

    private AnimatorSet createIndeterminateAnimator(float step)
    {
        final float maxsweep = 360f * (steps -1) / steps + INDERTIMINATE_MIN_SWEEP;
        final float start = -90f + step *(maxsweep - INDERTIMINATE_MIN_SWEEP);

        final ValueAnimator frontEndExtend = ValueAnimator.ofFloat(INDERTIMINATE_MIN_SWEEP,
                maxsweep);
        frontEndExtend.setDuration(animDuration / steps /2);
        frontEndExtend.setInterpolator(new DecelerateInterpolator(1));
        frontEndExtend.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                indertiminateSweep = (Float)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        frontEndExtend.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                frontEndExtend.removeAllListeners();
                frontEndExtend.cancel();
            }
        });


        final ValueAnimator rotateAnimator = ValueAnimator
                .ofFloat(step * 720f / steps,
                        (step + .5f) * 720f / steps);
        rotateAnimator.setDuration(animDuration / steps / 2);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                indetermnateRotateOffset = (Float) valueAnimator.getAnimatedValue();
            }
        });

        rotateAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rotateAnimator.removeAllListeners();
                rotateAnimator.cancel();
            }
        });

        final ValueAnimator backEndRetract = ValueAnimator.ofFloat(start,
                start + maxsweep - INDERTIMINATE_MIN_SWEEP);
        backEndRetract.setDuration(animDuration / steps / 2);
        backEndRetract.setInterpolator( new DecelerateInterpolator(1));
        backEndRetract.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                startAngle = (Float) valueAnimator.getAnimatedValue();
                indertiminateSweep = maxsweep - startAngle + start;
                invalidate();
                if (startAngle > RESETTING_ANGLE)
                {
                    resetAnimation();
                }
            }
        });

        backEndRetract.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                backEndRetract.cancel();
                backEndRetract.removeAllListeners();
            }
        });

        final ValueAnimator rotateAnimator1 = ValueAnimator.ofFloat(
                (step + .5f) * 720f / steps,
                (step + 1) * 720f / steps);
        rotateAnimator1.setDuration(animDuration / steps / 2);
        rotateAnimator1.setInterpolator(new LinearInterpolator());
        rotateAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                indetermnateRotateOffset = (Float) valueAnimator.getAnimatedValue();

            }
        });

        rotateAnimator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rotateAnimator1.removeAllListeners();
                rotateAnimator1.cancel();
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.play(frontEndExtend).with(rotateAnimator);
        set.play(backEndRetract).with(rotateAnimator1).after(rotateAnimator);

        return set;

    }

    private void resetAnimation() {
        startAngle = getResources().getInteger(R.integer.default_start_angle);

        if(indeterminateAnimator != null && indeterminateAnimator.isRunning())
        {
            indeterminateAnimator.cancel();
        }

        indertiminateSweep = INDERTIMINATE_MIN_SWEEP;

        indeterminateAnimator = new AnimatorSet();
        AnimatorSet prevSet = null;
        AnimatorSet nextSet;

        for (int k=0; k < steps;k++)
        {
            nextSet = createIndeterminateAnimator(k);
            AnimatorSet.Builder builder = indeterminateAnimator.play(nextSet);
            if (prevSet != null)
            {
                builder.after(prevSet);
            }
            prevSet = nextSet;
        }
        indeterminateAnimator.start();
    }

    public void startAnimation()
    {
        resetAnimation();
    }


    public void stopAnimation()
    {
        if (indeterminateAnimator != null)
        {
            indeterminateAnimator.cancel();
            indeterminateAnimator.removeAllListeners();
            indeterminateAnimator = null;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    @Override
    public void setVisibility(int visibility) {
        int currentVisisbility = getVisibility();
        super.setVisibility(visibility);

        if(visibility != currentVisisbility)
        {
            if(visibility==View.VISIBLE)
                resetAnimation();
            else if (visibility == View.GONE || visibility == View.INVISIBLE)
                stopAnimation();
        }
    }
}