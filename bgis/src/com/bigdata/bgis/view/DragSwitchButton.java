package com.bigdata.bgis.view;

import com.bigdata.bgis.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DragSwitchButton extends View implements OnTouchListener {
	private static final String TAG = "bgis/ProjectorButton";
	
	private OnChangedListener listener; 

	private boolean onSlip = false; // ��¼��ǰ�Ƿ����ڻ���
	private boolean nowStatus = false;// ��¼��ǰ״̬

	private float nowY, downY;// ��¼����ͼƬ��ʱ��������ͻ��������е�������

	private Bitmap switchOnBkg;// ����ʱ�ı���
	private Bitmap switchOffBkg;// �ر�ʱ�ı���
	private Bitmap slipSwitchButton;// �������ص�ͼƬ

	public DragSwitchButton(Context context){
		super(context);
		init();
	}
	
	public DragSwitchButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	// ��ʼ����ͼƬid��
	private void init() {
		switchOnBkg = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.projector_switch_on);
		switchOffBkg = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.projector_switch_off);
		slipSwitchButton = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.projector_switch_slip);
		this.setOnTouchListener(this);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Matrix matrix = new Matrix();
		Paint paint = new Paint();

		float y = 0;

		if (nowY < (switchOnBkg.getHeight() / 2)) {
			canvas.drawBitmap(switchOffBkg, matrix, paint);
		} else {
			canvas.drawBitmap(switchOnBkg, matrix, paint);
		}

		if (onSlip) { // ������ڻ����ģ��򻬿��������λ��Ϊy
			if (nowY >= switchOnBkg.getHeight()) {
				y = switchOnBkg.getHeight() - (slipSwitchButton.getHeight())
						/ 2;
			} else {
				y = nowY - slipSwitchButton.getHeight() / 2;
			}
		} else { // ��������ڻ����ģ��򿴰�ť��״̬�ǿ����ǹ�
			if (nowStatus) {
				y = switchOnBkg.getHeight() - slipSwitchButton.getHeight();
			} else {
				y = 0;
			}
		}

		// �Ŵ��������쳣������»���λ���쳣
		if (nowY < 0) {
			y = 0;
		} else if (nowY > switchOnBkg.getHeight()
				- slipSwitchButton.getHeight()) {
			y = switchOnBkg.getHeight() - slipSwitchButton.getHeight();
		}

		// ���Ƴ���������λ��
		canvas.drawBitmap(slipSwitchButton, 0, y, paint);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (event.getY() > switchOnBkg.getHeight()
					|| event.getX() > switchOnBkg.getWidth()
					) {
				return false;
			} else{
				onSlip = true;
				downY = event.getY();
				nowY = downY;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			nowY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			onSlip = false;
			if (event.getY() >= (switchOnBkg.getHeight()/2)){
				nowStatus = true;
				nowY = switchOnBkg.getHeight() - slipSwitchButton.getHeight();
			} else {
				nowStatus = false;
				nowY = 0;
			}
			if(listener != null){  
                listener.OnChanged(DragSwitchButton.this, nowStatus);  
            }  
            break;
		}
		
		invalidate();
		return true;
	}
	
	
	public void setOnChangedListener(OnChangedListener listener){  
        this.listener = listener;  
    }  
      

    public void setChecked(boolean checked){  
        if(checked){  
            nowY = switchOffBkg.getWidth();  
        }else{  
            nowY = 0;  
        }  
        nowStatus = checked;  
    }  
  
      
    public interface OnChangedListener {  
        public void OnChanged(DragSwitchButton projectorButton, boolean checkState);  
    }  
}
