package com.jhonelee.chat.weidget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by JhoneLee on 2017/4/25.
 */

public class CircleImageView extends ImageView {

    private int width;
    private int height;
    private Bitmap bitmap;
    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = measureWith(widthMeasureSpec);
        height = measureWith(heightMeasureSpec);
    }
    private void LoadImage(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();

        if (bitmapDrawable != null) {
            bitmap = bitmapDrawable.getBitmap();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        LoadImage();
        if (bitmap!=null){
            int min = Math.min(width,height);
            bitmap = Bitmap.createScaledBitmap(bitmap,min,min,false);
            canvas.drawBitmap(creatCircleImg(bitmap,min),0,0,null);
        }
    }

    /**
     * 测量宽和高，这一块可以是一个模板代码(Android群英传)
     * @param width
     * @return
     */
    private int measureWith(int width) {
        int result = 0;
        //从MeasureSpec对象中提取出来具体的测量模式和大小
        int mode = MeasureSpec.getMode(width);
        int size = MeasureSpec.getSize(width);
        if (mode == MeasureSpec.EXACTLY) {
            //测量的模式，精确
            result = size;
        } else {
            result = 60;
        }
        return result;
    }

    private Bitmap creatCircleImg(Bitmap bitmap,int min){
        Bitmap m = null;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        m = Bitmap.createBitmap(min,min, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(m);
        canvas.drawCircle(min/2,min/2,min/2,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,0,0,paint);
        return  m;
    }
}
