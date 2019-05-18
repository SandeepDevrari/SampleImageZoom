package devrari.sandeep.zoomimagesample.helper;

import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

public class sZoomer implements View.OnTouchListener, GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener {
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    private float factor=1.0f;
    private ImageView iv;
    private AppCompatImageView crossIv;
    private boolean doubleTap=true,isScaled,moving=false;
    private PointF startPoint=new PointF(),movePoint=new PointF();
    private PointF real=new PointF();
    private int pointerOne;

    public sZoomer(Context context, AppCompatImageView crossIv) {
        this.crossIv=crossIv;
        this.scaleGestureDetector = new ScaleGestureDetector(context,this);
        this.gestureDetector=new GestureDetector(context,this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        iv = (ImageView) v;
        boolean t;
        t=gestureDetector.onTouchEvent(event);
        if(!t) {
            scaleGestureDetector.onTouchEvent(event);
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        if(isScaled) {
                            if(event.getEdgeFlags()!=MotionEvent.EDGE_BOTTOM && event.getEdgeFlags()!=MotionEvent.EDGE_TOP
                            &&event.getEdgeFlags()!=MotionEvent.EDGE_LEFT && event.getEdgeFlags()!=MotionEvent.EDGE_RIGHT) {
                                pointerOne=event.getPointerId(0);
                                startPoint.set(event.getX(event.findPointerIndex(pointerOne)), event.getY(event.findPointerIndex(pointerOne)));
                                moving = true;
                            }
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        iv.setX(iv.getLeft());
                        iv.setY(iv.getTop());
                        moving=false;
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:{
                        if(moving && event.getPointerCount()==1) {
                            movePoint.set(event.getX(),event.getY());
                            if(checkBorder(movePoint.x,movePoint.y)) {
                                int index = event.findPointerIndex(pointerOne);
                                if (index != -1) {
                                    if (event.getHistorySize() > 0) {
                                        float distX = event.getX(index) - startPoint.x;
                                        float distY = event.getY(index) - startPoint.y;
                                        boolean signX, signY;
                                        if (distX >= 0) {
                                            signX = true;
                                        } else {
                                            signX = false;
                                            distX *= -1;
                                        }
                                        if (distY >= 0) {
                                            signY = true;
                                        } else {
                                            signY = false;
                                            distY *= -1;
                                        }
                                        distX = Math.max(0, Math.min(distX, 70));
                                        distY = Math.max(0, Math.min(distY, 70));
                                        if (!signX) {
                                            distX *= -1;
                                        }
                                        if (!signY) {
                                            distY *= -1;
                                        }
                                        iv.animate().translationXBy(distX).translationYBy(distY).setStartDelay(0).setDuration(0).start();
                                    }
                                }
                            }
                        }
                        break;
                    }
                    case MotionEvent.ACTION_POINTER_DOWN:{
                        moving=false;
                        break;
                    }
                    case MotionEvent.ACTION_POINTER_UP:{
                        break;
                    }
                }
        }
        return true;
    }

    private boolean checkBorder(float x, float y) {
        return (y<iv.getHeight() && x<iv.getWidth() && x>iv.getHeight()/6 && y>iv.getHeight()/6);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            detector.setStylusScaleEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            detector.setQuickScaleEnabled(true);
        }
        factor*=detector.getScaleFactor();
        factor=Math.max(1.0f,Math.min(factor,3.00f));
        iv.setPivotX(detector.getFocusX());
        iv.setPivotY(detector.getFocusY());
        iv.animate().scaleX(factor).scaleY(factor).setStartDelay(0).setDuration(0).start();
        if(factor<=1.1f){
            crossIv.animate().alpha(1).setStartDelay(0).setInterpolator(new AccelerateInterpolator(2)).setDuration(200).start();
            isScaled=false;
        }else {
            crossIv.animate().alpha(0).setStartDelay(0).setInterpolator(new AccelerateInterpolator(2)).setDuration(200).start();
            isScaled = true;
        }
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        moving=false;
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }


    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if(doubleTap) {
            iv.setPivotX(e.getX());
            iv.setPivotY(e.getY());
            iv.animate().setStartDelay(2).scaleX(2.0f).setDuration(400).start();
            iv.animate().setStartDelay(2).scaleY(2.0f).setDuration(400).start();
            crossIv.animate().alpha(0).setStartDelay(0).setInterpolator(new AccelerateInterpolator(2)).setDuration(200).start();
            isScaled=true;
        }else{
            iv.animate().setStartDelay(2).scaleX(1.0f).setDuration(400).start();
            iv.animate().setStartDelay(2).scaleY(1.0f).setDuration(400).start();
            crossIv.animate().alpha(1).setStartDelay(0).setInterpolator(new AccelerateInterpolator(2)).setDuration(200).start();
            isScaled=false;
        }
        doubleTap=!doubleTap;
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

}
