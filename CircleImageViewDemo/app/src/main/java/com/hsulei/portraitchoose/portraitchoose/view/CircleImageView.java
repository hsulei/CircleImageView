package com.hsulei.portraitchoose.portraitchoose.view;

import android.app.PendingIntent;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.CancellationSignal;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hsulei.portraitchoose.portraitchoose.R;

/**
 * Created by 46697 on 2016/10/11.
 */
//自定义的圆形头像控件
public class CircleImageView extends ImageView {

    private static final String TAG = "CircleImageView";

    private int mFrameWidth = 3;
    private int mFrameColor = Color.GRAY;
    //画笔
    private Paint mPaint;

    //用于展示背景图片的宽高
    private int bitmapWidth;
    private int bitmapHeight;

    public CircleImageView(Context context) {
        super(context);
        initAttr(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    //初始化自定义属性
    public void initAttr(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            int len = array.getIndexCount();//得到定义的属性的长度

            for (int i = 0; i < len; i++) {
                int attr = array.getIndex(i);//得到所有的属性
                switch (attr) {
                    case R.styleable.CircleImageView_frameColor: //得到自定义的圆形边框颜色
                        mFrameColor = array.getColor(attr, Color.GRAY);
                        break;
                    case R.styleable.CircleImageView_frameWidth://得到自定义的圆形边框宽度
                        mFrameWidth = (int) array.getDimension(attr, 3);
                        break;
                }
            }
        }
        //设置Paint参数
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mFrameColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);

        bitmapWidth = width - 2 * mFrameWidth;
        bitmapHeight = height - 2 * mFrameWidth;

        this.setMeasuredDimension(width, height);
    }

    private int measureHeight(int heightMeasureSpec) {
        int height = 0;
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            height = 50;
        }
        return height;
    }

    private int measureWidth(int widthMeasureSpec) {
        int width = 0;
        //得到宽的值和模式
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            width = 50;
        }
        return width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画边框圆
        int min = Math.min(bitmapHeight, bitmapWidth);//得到bitmapHeight和bitmapWidth中较小的值
        BitmapDrawable background = (BitmapDrawable) getDrawable();//得到背景图片

        Bitmap image = Bitmap.createScaledBitmap(background.getBitmap(), min, min, false);//创建一个缩略图
        canvas.drawCircle((min + 2 * mFrameWidth) / 2, (min + 2 * mFrameWidth) / 2, (min + mFrameWidth) / 2, mPaint);//话圆边框


        canvas.drawBitmap(drawCircleBitmap(image, min), mFrameWidth, mFrameWidth, null);//话图片
    }


    private Bitmap drawCircleBitmap(Bitmap background, int min) {
        Bitmap circle = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);//新建一个图片
        Canvas canvas = new Canvas(circle);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(background, 0, 0, paint);
        return circle;


    }
}
