package com.hsulei.portraitchoose.portraitchoose.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.hsulei.portraitchoose.portraitchoose.R;

/**
 * Created by 46697 on 2016/10/11.
 */
//自定义的圆形头像控件
public class CircleImageView extends ImageView {
    private static final String TAG = "CircleImageView";

    //边框颜色
    private int mFrameColor;
    //边框宽度
    private float mFrameWidth;
    //控件的宽度
    private int width;
    //控件的高端
    private int height;

    //背景图片
    private Drawable mBackground;

    //画笔
    private Paint mPaint;

    public CircleImageView(Context context) {
        super(context);
        setAttr(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttr(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttr(context, attrs);
    }

    //设置属性
    private void setAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        int len = array.getIndexCount();//获取属性的数量

        //设置属性
        for (int i = 0; i < len; i++) {
            switch (array.getIndex(i)) {
                case R.styleable.CircleImageView_frameColor:
                    mFrameWidth = array.getDimension(i, 2);
                    Log.i(TAG, "获取的边框的宽度是：" + mFrameWidth);
                    break;
                case R.styleable.CircleImageView_frameWidth:
                    mFrameColor = array.getColor(i, Color.GRAY);
                    Log.i(TAG, "获取的边框颜色是：" + mFrameColor);
                    break;
            }
        }

        //设置画笔属性
        mPaint = new Paint();
        mPaint.setColor(mFrameColor);//设置画笔颜色
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setStrokeWidth(mFrameWidth);//设置线条宽度
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = measureWidth(widthMeasureSpec);
        this.height = measureHeight(heightMeasureSpec);

        Log.i(TAG, "最后的高度：" + height);
        Log.i(TAG, "最后的宽度：" + width);
        //重新设置控件宽高
        this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    //获得宽度
    private int measureWidth(int widthMeasureSpec) {
        int width = 50;//设置默认值
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        }
        return width;
    }

    //获取高度
    private int measureHeight(int heightMeasureSpec) {
        int height = 50;//设置默认值
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        }
        return height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBackground = this.getDrawable();

        Bitmap mBitmap = Bitmap.createBitmap(mBackground.getIntrinsicWidth(), mBackground.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);//创建一个和背景一样大的图片

        int min = Math.min(width, height);//选择最小项
        Bitmap.createScaledBitmap(mBitmap, min, min, false);

        canvas = new Canvas();//把背景设置
        invalidate();//要求绘制
    }
}
