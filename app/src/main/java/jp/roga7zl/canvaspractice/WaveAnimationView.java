package jp.roga7zl.canvaspractice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

public class WaveAnimationView extends View {
    private Paint mPaint = new Paint();
    private boolean isMoving = false;

    private int i = 0;
    private int j = 0;
    private int k = 0;

//    BounceInterpolator mInterpolator = new BounceInterpolator();
    OvershootInterpolator mInterpolator = new OvershootInterpolator();
//    CycleInterpolator mInterpolator = new CycleInterpolator(3);
//    AnticipateOvershootInterpolator mInterpolator = new AnticipateOvershootInterpolator();

    public WaveAnimationView(Context context) {
        super(context);
    }

    public WaveAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

        // 定期的に再描画する
        prepareAnimation();
    }

    public WaveAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bX = 100;
        int bY = 300;
        int w = 600;
        int h = 300;


        mPaint = createPaint();

        // 赤色の部分
        mPaint.setColor(Color.parseColor("#F0FF0134"));
        Path path = new Path();
        // 始点：左上
        path.moveTo(bX, bY);
        // 上部の波線
//        path.cubicTo(bX + w / 2, bY - 150, bX + w / 2, bY + 150, bX + w, bY);
//        path.cubicTo(bX + w / 2, (float) (bY - Math.cos(i) * 150), bX + w / 2, (float) (bY + Math.cos(i) * 150), bX + w, bY);
//        path.cubicTo(bX + w / 2, (float) (bY -  mInterpolator.getInterpolation((float) k / 100)  * 150), bX + w / 2, (float) (bY + mInterpolator.getInterpolation((float) k / 100) * 150), bX + w, bY);
        path.cubicTo(bX + w / 2, (float) (bY -  (1 + -1 * mInterpolator.getInterpolation((float) k / 100))  * 150), bX + w / 2, (float) (bY + (1 + -1 * mInterpolator.getInterpolation((float) k / 100)) * 150), bX + w, bY);
        // 右下の曲線
        path.cubicTo(bX + w, bY + h + 30, bX + w / 2 - 30, bY + h, bX + w / 2, bY + h);
        // 下部の直線
        path.cubicTo(bX + w / 2, bY + h, bX + 30, bY + h, bX + 30, bY + h);
        // 左下の丸角
        path.cubicTo(bX, bY + h, bX, bY + h - 30, bX, bY + h - 30);
        // 左部の直線
        path.cubicTo(bX, bY + h - 30, bX, bY, bX, bY);
        canvas.drawPath(path, mPaint);

        // 紫色の部分
        mPaint.setColor(Color.parseColor("#A0BB0FFD"));
        Path path2 = new Path();
        // 始点：左上
        path2.moveTo(bX, bY);
        // 上部の波線
//        path2.cubicTo(bX + w / 2 + 150, bY + 150, bX + w / 2 + 150, bY - 150, bX + w, bY - 50);
//        path2.cubicTo(bX + w / 2 + 150, (float) (bY + Math.cos(j) * 150), bX + w / 2 + 150, (float) (bY - Math.cos(j) * 150), bX + w, bY - 50);
//        path2.cubicTo(bX + w / 2 + 150, (float) (bY + mInterpolator.getInterpolation((float) k / 100)  * 150), bX + w / 2 + 150, (float) (bY - mInterpolator.getInterpolation((float) k / 100)  * 150), bX + w, bY - 50);
        path2.cubicTo(bX + w / 2 + 150, (float) (bY + (1 + -1 * mInterpolator.getInterpolation((float) k / 100))  * 150), bX + w / 2 + 150, (float) (bY - (1 + -1 * mInterpolator.getInterpolation((float) k / 100))  * 150), bX + w, bY - 50);
        // 右部の直線
        path2.cubicTo(bX + w, bY - 50, bX + w, bY, bX + w, bY);
        // 右下の曲線
        path2.cubicTo(bX + w, bY + h + 30, bX + w / 2 - 30, bY + h, bX + w / 2, bY + h);
        // 下部の直線
        path2.cubicTo(bX + w / 2, bY + h, bX + 30, bY + h, bX + 30, bY + h);
        // 左下の丸角
        path2.cubicTo(bX, bY + h, bX, bY + h - 30, bX, bY + h - 30);
        // 左部の直線
        path2.cubicTo(bX, bY + h - 30, bX, bY, bX, bY);
        canvas.drawPath(path2, mPaint);

        i = (i + 1) % 600;
        j = (j + 3) % 600;

        k+=3;
        if (k >= 100) {
            k = 0;
            this.isMoving = false;
        }
    }

    public void moveLogo() {
        this.isMoving = true;
    }

    public void stopLogo() {
        this.isMoving = false;
    }

    private Paint createPaint() {
        Paint paint = new Paint();
        // 太さ
        paint.setStrokeWidth(5);
        // アンチエイリアス
        paint.setAntiAlias(true);
        // 塗りつぶす
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    private void prepareAnimation() {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isMoving) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // 再描画
                                invalidate();
                            }
                        });
                    }
                }
            }
        })).start();
    }
}
