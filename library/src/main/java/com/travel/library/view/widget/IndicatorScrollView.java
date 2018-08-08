package com.travel.library.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.travel.library.R;


/**
 * Created by wisn on 2017/11/17.
 * viewpage指示器
 */
public class IndicatorScrollView extends View implements ViewPager.OnPageChangeListener {
    private int mPosition;
    private float mOffset;
    Bitmap dotDefault;
    Bitmap dotSelected;
    private Matrix mMatrix;
    private Paint mPaint;
    private int mDrawableSize;
    private int mDrawableMargin;
    private int mScrollCount;
    private boolean isScroll;
    private int mWidthSize;
    private int mHeightSize;
    private int mStartWidth;

    public IndicatorScrollView(Context context) {
        super(context);
        init(context, null);
    }


    public IndicatorScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public IndicatorScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndicatorScrollView);
        mDrawableSize = a.getDimensionPixelSize(R.styleable.IndicatorScrollView_drawableSize, 50);
        mDrawableMargin = a.getDimensionPixelSize(R.styleable.IndicatorScrollView_drawableMargin, 20);
        mScrollCount = a.getInteger(R.styleable.IndicatorScrollView_scrollCount, 4);
        isScroll = a.getBoolean(R.styleable.IndicatorScrollView_isScroll, true);
        dotDefault =
                Bitmap.createScaledBitmap(((BitmapDrawable) a.getDrawable(R.styleable.IndicatorScrollView_drawableSelect))
                                .getBitmap(),
                        mDrawableSize, mDrawableSize, true);
        dotSelected =
                Bitmap.createScaledBitmap(((BitmapDrawable) a.getDrawable(R.styleable.IndicatorScrollView_drawableUnSelect))
                        .getBitmap(), mDrawableSize, mDrawableSize, true);
        a.recycle();
        mMatrix = new Matrix();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void setmScrollCount(int mScrollCount) {
        this.mScrollCount = mScrollCount;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        mWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        mHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            mWidthSize = (mDrawableSize + mDrawableMargin) * mScrollCount;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            mHeightSize = mDrawableSize + mDrawableMargin;
        }
        mStartWidth = (mWidthSize - (mDrawableSize + mDrawableMargin) * mScrollCount + mDrawableMargin) / 2;
        setMeasuredDimension(mWidthSize, mHeightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mScrollCount; i++) {
            mMatrix.setTranslate(mStartWidth + (mDrawableSize + mDrawableMargin) * i, 0);
            canvas.drawBitmap(this.dotDefault, mMatrix, mPaint);
        }
        mMatrix.setTranslate(mStartWidth +
                (mDrawableSize + mDrawableMargin) * mPosition +
                (mDrawableSize + mDrawableMargin) * mOffset, 0);
        canvas.drawBitmap(this.dotSelected, mMatrix, mPaint);
        super.onDraw(canvas);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (!isScroll) return;
        mPosition = position;
        mOffset = positionOffset;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        if (!isScroll) {
            mPosition = position;
            mOffset = 0;
            invalidate();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
