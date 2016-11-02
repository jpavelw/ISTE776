package com.iste776.jpavelw.doodle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jpavelw on 10/18/16.
 */

public class DoodleView extends View {

    private final Paint paintBrush = new Paint();
    private final Path path = new Path();

    public DoodleView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attributeSet, int defStyleAttr){
        this.paintBrush.setColor(Color.BLACK);
        //this.paintBrush.setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        this.paintBrush.setAntiAlias(true);
        this.paintBrush.setStyle(Paint.Style.STROKE);
        this.paintBrush.setStrokeWidth(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawLine(0, 0, getWidth(), getHeight(), this.paintBrush);
        canvas.drawPath(this.path, this.paintBrush);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.path.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                this.path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        invalidate();
        return true;
    }
}
