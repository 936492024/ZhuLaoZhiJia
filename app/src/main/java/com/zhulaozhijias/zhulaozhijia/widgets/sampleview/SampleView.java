package com.zhulaozhijias.zhulaozhijia.widgets.sampleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.adpter.InfoAdapter;


public class SampleView extends View {

    private static final int WIDTH = 30;//布局的格子
    private static final int HEIGHT = 30;//布局的格子

    private Bitmap mBitmaps;
    private Bitmap mBitmap;
    private final Matrix mMatrix = new Matrix();
    private final Matrix mInverse = new Matrix();

    private boolean mIsDebug = false;
    private Paint mPaint = new Paint();
    private float[] mInhalePt = new float[]{0, 0};
    private InhaleMesh mInhaleMesh = null;
    private int width, height;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public SampleView(Context context) {
        this(context, null);
    }

    public SampleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(true);
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.win);

//            mBitmap.setWidth(width);
//            mBitmap.setHeight(height);
        Log.e("mBitmap", mBitmap.getHeight() + "");
        mInhaleMesh = new InhaleMesh(WIDTH, HEIGHT);
        mInhaleMesh.setBitmapSize(width, height);//图形扭曲出发点的图片大小
        mInhaleMesh.setInhaleDir(InhaleMesh.InhaleDir.RIGHT);//控制动画效果
    }

    public void setIsDebug(boolean isDebug)//测试轮廓
    {
        mIsDebug = isDebug;
    }

    public void setInhaleDir(InhaleMesh.InhaleDir dir) {
        mInhaleMesh.setInhaleDir(dir);

        float w = mBitmap.getWidth();
        float h = mBitmap.getHeight();

        float endX = 0;
        float endY = 0;
        float dx = 30;
        float dy = 30;
        mMatrix.reset();

        switch (dir) {
            case DOWN:
                endX = w / 2;
                endY = getHeight() - 20;
                break;

            case UP:
                dy = getHeight() - h - 20;
                endX = w / 2;
                endY = -dy + 10;
                break;

            case LEFT:
                dx = getWidth() - w - 20;
                endX = -dx + 10;
                endY = h / 2;
                break;

            case RIGHT:
                endX = getWidth() - 20;
                endY = h / 2;
                break;
        }

        mMatrix.setTranslate(dx, dy);
        mMatrix.invert(mInverse);
        buildPaths(endX, endY);
        buildMesh(w, h);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float bmpW = mBitmap.getWidth();
        float bmpH = mBitmap.getHeight();

        mMatrix.setTranslate(0, 0);
        //mMatrix.setTranslate(10, 10);
        mMatrix.invert(mInverse);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);

        buildPaths(bmpW / 2, h - 20);
        buildMesh(bmpW, bmpH);
    }

    public boolean startAnimation(boolean reverse) {
        Animation anim = this.getAnimation();
        if (null != anim && !anim.hasEnded()) {
            return false;
        }
        PathAnimation animation = new PathAnimation(0, HEIGHT + 1, reverse,
                new PathAnimation.IAnimationUpdateListener() {
                    @Override
                    public void onAnimUpdate(int index) {
                        mInhaleMesh.buildMeshes(index);
                        invalidate();
                    }
                });

        if (null != animation) {
            animation.setDuration(1500);
            this.startAnimation(animation);
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("leehong2", "onDraw  =========== ");
        canvas.drawColor(0x00FFFFFF);

        canvas.concat(mMatrix);

        canvas.drawBitmapMesh(mBitmap,
                mInhaleMesh.getWidth(),
                mInhaleMesh.getHeight(),
                mInhaleMesh.getVertices(),
                0, null, 0, mPaint);

        // ===========================================
        // Draw the target point.圆点
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        Log.e("mInhalePt", mInhalePt[0] + "-----" + mInhalePt[1]);
        canvas.drawCircle(mInhalePt[0], mInhalePt[1], 1, mPaint);

        if (mIsDebug)//mIsDebug为true会执行这段代码，即表示弧线图
        {
            // ===========================================
            // Draw the mesh vertices.
            canvas.drawPoints(mInhaleMesh.getVertices(), mPaint);

            // ===========================================
            // Draw the paths
            mPaint.setColor(Color.BLUE);
            mPaint.setStyle(Paint.Style.STROKE);
            Path[] paths = mInhaleMesh.getPaths();
            for (Path path : paths) {
                canvas.drawPath(path, mPaint);
            }
        }
    }

    private void buildMesh(float w, float h) {
        mInhaleMesh.buildMeshes(w, h);
    }

    private void buildPaths(float endX, float endY) {
        mInhalePt[0] = width - 80;
        mInhalePt[1] = 0;
        mInhaleMesh.buildPaths(width - 80, 0);
    }

    int mLastWarpX = 0;
    int mLastWarpY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event)//圆点的触摸监听事件
    {
        float[] pt = {event.getX(), event.getY()};
        mInverse.mapPoints(pt);
        Log.e("point", event.getX() + "-----" + event.getY());
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) pt[0];
            int y = (int) pt[1];
            if (mLastWarpX != x || mLastWarpY != y) {
                mLastWarpX = x;
                mLastWarpY = y;
                buildPaths(pt[0], pt[1]);
                invalidate();
            }
        }
        return true;
    }
}
