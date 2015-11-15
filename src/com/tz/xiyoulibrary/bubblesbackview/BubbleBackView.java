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

	private int circleSum;// Բ������
	private int circleRadio;// Բ�İ뾶
	private int circleColor;// Բ����ɫ
	private int backColor;// ������ɫ
	private Paint backPaint;// ��������
	private Paint circlePaint;// Բ�Ļ���

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
		circleRadio = ta.getInteger(R.styleable.BubbleBackView_circleRadio, 30);
		ta.recycle();
		init();
	}

	/**
	 * ��ʼ������
	 */
	private void init() {
		backPaint = new Paint();
		backPaint.setColor(backColor);

		circlePaint = new Paint();
		circlePaint.setColor(circleColor);
	}

	/**
	 * ��onDraw��ִ��
	 * <p/>
	 * һ��MeasureSpec��װ�˸����ִ��ݸ��Ӳ��ֵĲ���Ҫ��ÿ��MeasureSpec������һ���Ⱥ͸߶ȵ�Ҫ��
	 * һ��MeasureSpec�ɴ�С��ģʽ���
	 * ��������ģʽ��UNSPECIFIED(δָ��),��Ԫ�ز�����Ԫ��ʩ���κ���������Ԫ�ؿ��Եõ�������Ҫ�Ĵ�С;
	 * EXACTLY(��ȫ)����Ԫ�ؾ�����Ԫ�ص�ȷ�д�С����Ԫ�ؽ����޶��ڸ����ı߽���������������С��
	 * AT_MOST(����)����Ԫ������ﵽָ����С��ֵ��
	 * <p/>
	 * ���������õ����������� 1.static int getMode(int
	 * measureSpec):�����ṩ�Ĳ���ֵ(��ʽ)��ȡģʽ(��������ģʽ֮һ) 2.static int getSize(int
	 * measureSpec):�����ṩ�Ĳ���ֵ(��ʽ)��ȡ��Сֵ(�����СҲ��������ͨ����˵�Ĵ�С) 3.static int
	 * makeMeasureSpec(int size,int mode):�����ṩ�Ĵ�Сֵ��ģʽ����һ������ֵ(��ʽ)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// ��ȡ�ؼ����
		width = getMeasuredWidth();
		// ��ȡ�ؼ��߶�
		height = getMeasuredHeight();
		// ��ȡ�ؼ�����ڸ��ؼ���λ������
		this.getLocationInWindow(location);

		// ��ʼ��Բ
		initCircle();
	}

	/**
	 * ��ʼ��Բ
	 */
	private void initCircle() {
		circles = new Circle[circleSum];
		random = new Random();
		for (int i = 0; i < circleSum; i++) {
			int d = random.nextInt(direction.length);
			int x = random.nextInt((int) width);
			int y = random.nextInt((int) height);
			int r = random.nextInt(circleRadio)+circleRadio;
			circles[i] = new Circle(x, y, d,r);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// ���Ʊ���
		canvas.drawRect(0, 0, location[0] + width, location[1] + height,
				backPaint);
		// ��������ÿһ��Բ
		for (Circle c : circles) {
			canvas.drawCircle(c.getX(), c.getY(), c.getR(), circlePaint);
		}
		if (mMyThread == null) {
			mMyThread = new MyThread();
			mMyThread.start();
		}
	}

	/**
	 * ���ڴ�����ʾ��ʱ��ֹͣ�߳�
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
					if (c.getY() < 0) {// �����ϱ�
						int d;
						while (true) {
							d = random.nextInt(direction.length);
							if (d % 2 != 0)
								break;
						}
						circles[i].setDirection(d);
					} else if (c.getY() > height) {// �����±�
						int d;
						while (true) {
							d = random.nextInt(direction.length);
							if (d % 2 != 1)
								break;
						}
						circles[i].setDirection(d);
					} else if (c.getX() < 0) {// �������
						int d;
						while (true) {
							d = random.nextInt(direction.length);
							if (d == 6 || d == 10 || d == 3 || d == 7
									|| d == 11)
								break;
						}
						circles[i].setDirection(d);
					} else if (c.getX() > width) {// �����ұ�
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
			case 0:// ��
				c.setY(c.getY() - dx);
				break;
			case 1:// ��
				c.setY(c.getY() + dx);
				break;
			case 2:// ��
				c.setX(c.getX() - dx);
				break;
			case 3:// ��
				c.setX(c.getX() + dx);
				break;
			case 4:// ����
				c.setX(c.getX() - dx);
				c.setY(c.getY() - dx);
				break;
			case 5:// ����
				c.setX(c.getX() - dx);
				c.setY(c.getY() + dx);
				break;
			case 6:// ����
				c.setX(c.getX() + dx);
				c.setY(c.getY() - dx);
				break;
			case 7:// ����
				c.setX(c.getX() + dx);
				c.setY(c.getY() + dx);
				break;
			case 8:// ����
				c.setX(c.getX() - dx);
				c.setY(c.getY() - dx * 2);
				break;
			case 9:// ����
				c.setX(c.getX() - dx * 2);
				c.setY(c.getY() + dx);
				break;
			case 10:// ����
				c.setX(c.getX() + dx);
				c.setY(c.getY() - dx * 2);
				break;
			case 11:// ����
				c.setX(c.getX() + dx);
				c.setY(c.getY() + dx * 2);
				break;
			}
		}
	}

	class Circle {
		private float x;
		private float y;
		private float r;
		private int direction;

		public Circle(float x, float y, int d) {
			this.x = x;
			this.y = y;
			this.direction = d;
		}
		public Circle(float x, float y, int d,int r) {
			this.x = x;
			this.y = y;
			this.direction = d;
			this.r = r;
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

		public float getR() {
			return r;
		}

		public void setR(float r) {
			this.r = r;
		}
	}

}
