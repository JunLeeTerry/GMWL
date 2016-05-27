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

	private boolean onSlip = false; // 记录当前是否正在滑动
	private boolean nowStatus = false;// 记录当前状态

	private float nowY, downY;// 记录滑动图片按时的纵坐标和滑动过程中的纵坐标

	private Bitmap switchOnBkg;// 开启时的背景
	private Bitmap switchOffBkg;// 关闭时的背景
	private Bitmap slipSwitchButton;// 滑动开关的图片

	public DragSwitchButton(Context context){
		super(context);
		init();
	}
	
	public DragSwitchButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	// 初始化，图片id绑定
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

		if (onSlip) { // 如果是在滑动的，则滑块的纵坐标位置为y
			if (nowY >= switchOnBkg.getHeight()) {
				y = switchOnBkg.getHeight() - (slipSwitchButton.getHeight())
						/ 2;
			} else {
				y = nowY - slipSwitchButton.getHeight() / 2;
			}
		} else { // 如果不是在滑动的，则看按钮的状态是开还是关
			if (nowStatus) {
				y = switchOnBkg.getHeight() - slipSwitchButton.getHeight();
			} else {
				y = 0;
			}
		}

		// 排错处理，避免异常情况导致滑块位置异常
		if (nowY < 0) {
			y = 0;
		} else if (nowY > switchOnBkg.getHeight()
				- slipSwitchButton.getHeight()) {
			y = switchOnBkg.getHeight() - slipSwitchButton.getHeight();
		}

		// 绘制出滑块所在位置
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
