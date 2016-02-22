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
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;

public class WaveAnimationView extends View {

    private static final int BX = 100;
    private static final int BY = 300;
    private static final int W = 600;
    private static final int H = 300;
    private static final int A = 150;

    private Paint mPaint = null;
    private boolean isMoving = false;

    private int k = 0;

        BounceInterpolator mInterpolator = new BounceInterpolator();
    //OvershootInterpolator mInterpolator = new OvershootInterpolator();
    //CycleInterpolator mInterpolator = new CycleInterpolator(3);
    //    AnticipateOvershootInterpolator mInterpolator = new AnticipateOvershootInterpolator();

    public WaveAnimationView(Context context) {
        super(context);
    }

    public WaveAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        prepareAnimation();
    }

    public WaveAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint = createPaint();
        float d = mInterpolator.getInterpolation((float) k / 100);

        // 赤色の部分
        mPaint.setColor(Color.parseColor("#F0FF0134"));
        Path path = new Path();
        // 始点：左上
        path.moveTo(BX, BY);
        // 上部の波線
        path.cubicTo(BX + W / 2, BY - (1 + -1 * d) * A, BX + W / 2, BY + (1 + -1 * d) * A,
                     BX + W, BY);
        // 右下の曲線
        path.cubicTo(BX + W, BY + H + 30, BX + W / 2 - 30, BY + H, BX + W / 2, BY + H);
        // 下部の直線
        path.cubicTo(BX + W / 2, BY + H, BX + 30, BY + H, BX + 30, BY + H);
        // 左下の丸角
        path.cubicTo(BX, BY + H, BX, BY + H - 30, BX, BY + H - 30);
        // 左部の直線
        path.cubicTo(BX, BY + H - 30, BX, BY, BX, BY);
        canvas.drawPath(path, mPaint);

        // 紫色の部分
        mPaint.setColor(Color.parseColor("#A0BB0FFD"));
        Path path2 = new Path();
        // 始点：左上
        path2.moveTo(BX, BY);
        // 上部の波線
        path2.cubicTo(BX + W / 2 + A, BY + (1 + -1 * d) * A, BX + W / 2 + A,
                      BY - (1 + -1 * d) * A, BX + W, BY - 50);
        // 右部の直線
        path2.cubicTo(BX + W, BY - 50, BX + W, BY, BX + W, BY);
        // 右下の曲線
        path2.cubicTo(BX + W, BY + H + 30, BX + W / 2 - 30, BY + H, BX + W / 2, BY + H);
        // 下部の直線
        path2.cubicTo(BX + W / 2, BY + H, BX + 30, BY + H, BX + 30, BY + H);
        // 左下の丸角
        path2.cubicTo(BX, BY + H, BX, BY + H - 30, BX, BY + H - 30);
        // 左部の直線
        path2.cubicTo(BX, BY + H - 30, BX, BY, BX, BY);
        canvas.drawPath(path2, mPaint);

        k += 3;
        if (k >= 100) {
            k = 0;
            this.isMoving = false;
        }
    }

    public void moveLogo() {
        this.isMoving = true;
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
