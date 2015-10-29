package com.tz.xiyoulibrary.bubblesbackview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.Random;
import com.tz.xiyoulibrary.R;

/**
 * Created by tianzhao on 2015/10/25. Date 21:38
 */
public class BubbleBackView extends View {

	private int circleSum;// 圆的数量
	private int circleRadio;// 圆的半径
	private int circleColor;// 圆的颜色
	private int backColor;// 背景颜色
	private Paint backPaint;// 背景画笔
	private Paint circlePaint;// 圆的画笔

	private Circle[] circles;
	private int[] direction = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

	private Random random;

	private float width;
	private float height;
	private int[] location = new int[2];

	private MyThread mMyThread;
	private boolean running = true;

	public BubbleBackView(Context context) {
		super(context);
		backPaint = new Paint();
		init();
	}

	public BubbleBackView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.BubbleBackView);
		circleSum = ta.getInteger(R.styleable.BubbleBackView_circleSum, 10);
		circleColor = ta.getColor(R.styleable.BubbleBackView_circleColor,
				Color.parseColor("#11ffffff"));
		backColor = ta.getColor(R.styleable.BubbleBackView_backColor,
				getResources().getColor(R.color.theme_color));
		circleRadio = ta.getInteger(R.styleable.BubbleBackView_circleRadio, 50);
		ta.recycle();
		init();
	}

	/**
	 * 初始化画笔
	 */
	private void init() {
		backPaint = new Paint();
		backPaint.setColor(backColor);

		circlePaint = new Paint();
		circlePaint.setColor(circleColor);
	}

	/**
	 * 比onDraw先执行
	 * <p/>
	 * 一个MeasureSpec封装了父布局传递给子布局的布局要求，每个MeasureSpec代表了一组宽度和高度的要求。
	 * 一个MeasureSpec由大小和模式组成
	 * 它有三种模式：UNSPECIFIED(未指定),父元素部队自元素施加任何束缚，子元素可以得到任意想要的大小;
	 * EXACTLY(完全)，父元素决定自元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
	 * AT_MOST(至多)，子元素至多达到指定大小的值。
	 * <p/>
	 * 　　它常用的三个函数： 1.static int getMode(int
	 * measureSpec):根据提供的测量值(格式)提取模式(上述三个模式之一) 2.static int getSize(int
	 * measureSpec):根据提供的测量值(格式)提取大小值(这个大小也就是我们通常所说的大小) 3.static int
	 * makeMeasureSpec(int size,int mode):根据提供的大小值和模式创建一个测量值(格式)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 获取控件宽度
		width = getMeasuredWidth();
		// 获取控件高度
		height = getMeasuredHeight();
		// 获取控件相对于父控件的位置坐标
		this.getLocationInWindow(location);

		// 初始化圆
		initCircle();
	}

	/**
	 * 初始化圆
	 */
	private void initCircle() {
		circles = new Circle[circleSum];
		random = new Random();
		for (int i = 0; i < circleSum; i++) {
			int d = random.nextInt(direction.length);
			int x = random.nextInt((int) width);
			int y = random.nextInt((int) height);
			circles[i] = new Circle(x, y, d);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 绘制背景
		canvas.drawRect(0, 0, location[0] + width, location[1] + height,
				backPaint);
		// 遍历绘制每一个圆
		for (Circle c : circles) {
			canvas.drawCircle(c.getX(), c.getY(), circleRadio, circlePaint);
		}
		if (mMyThread == null) {
			mMyThread = new MyThread();
			mMyThread.start();
		}
	}

	/**
	 * 不在窗口显示的时候停止线程
	 */
	@Override
	protected void onDetachedFromWindow() {
		running = false;
		super.onDetachedFromWindow();
	}

	class MyThread extends Thread {
		@Override
		public void run() {
			while (running) {
				for (int i = 0; i < circleSum; i++) {
					Circle c = circles[i];
					changeDirection(c);
					if (c.getY() < 0) {// 超出上边
						int d;
						while (true) {
							d = random.nextInt(direction.length);
							if (d % 2 != 0)
								break;
						}
						circles[i].setDirection(d);
					} else if (c.getY() > height) {// 超出下边
						int d;
						while (true) {
							d = random.nextInt(direction.length);
							if (d % 2 != 1)
								break;
						}
						circles[i].setDirection(d);
					} else if (c.getX() < 0) {// 超出左边
						int d;
						while (true) {
							d = random.nextInt(direction.length);
							if (d == 6 || d == 10 || d == 3 || d == 7
									|| d == 11)
								break;
						}
						circles[i].setDirection(d);
					} else if (c.getX() > width) {// 超出右边
						int d;
						while (true) {
							d = random.nextInt(direction.length);
							if (d == 4 || d == 8 || d == 2 || d == 5 || d == 9)
								break;
						}
						circles[i].setDirection(d);
					}
				}
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				postInvalidate();
			}
		}

		private void changeDirection(Circle c) {
			float dx = 0.3f;
			switch (c.getDirection()) {
			case 0:// 上
				c.setY(c.getY() - dx);
				break;
			case 1:// 下
				c.setY(c.getY() + dx);
				break;
			case 2:// 左
				c.setX(c.getX() - dx);
				break;
			case 3:// 右
				c.setX(c.getX() + dx);
				break;
			case 4:// 左上
				c.setX(c.getX() - dx);
				c.setY(c.getY() - dx);
				break;
			case 5:// 左下
				c.setX(c.getX() - dx);
				c.setY(c.getY() + dx);
				break;
			case 6:// 右上
				c.setX(c.getX() + dx);
				c.setY(c.getY() - dx);
				break;
			case 7:// 右下
				c.setX(c.getX() + dx);
				c.setY(c.getY() + dx);
				break;
			case 8:// 左上
				c.setX(c.getX() - dx);
				c.setY(c.getY() - dx * 2);
				break;
			case 9:// 左下
				c.setX(c.getX() - dx * 2);
				c.setY(c.getY() + dx);
				break;
			case 10:// 右上
				c.setX(c.getX() + dx);
				c.setY(c.getY() - dx * 2);
				break;
			case 11:// 右下
				c.setX(c.getX() + dx);
				c.setY(c.getY() + dx * 2);
				break;
			}
		}
	}

	class Circle {
		private float x;
		private float y;
		private int direction;

		public Circle(float x, float y, int d) {
			this.x = x;
			this.y = y;
			this.direction = d;
		}

		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public void setY(float y) {
			this.y = y;
		}

		public float getY() {
			return y;
		}

		public void setDirection(int direction) {
			this.direction = direction;
		}

		public int getDirection() {
			return direction;
		}
	}

}
