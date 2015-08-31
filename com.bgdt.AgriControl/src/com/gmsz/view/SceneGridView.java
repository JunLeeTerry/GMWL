/**
 * 
 */
package com.gmsz.view;

import com.gmsz.adapter.BaseDetailInfoAdapter;
import com.gmsz.adapter.FrameAdapter;
import com.gmsz.demo4.R;
import com.gmsz.domain.BaseDetailInfo;
import com.gmsz.domain.Frame;
import com.gmsz.main.MainActivity;
import com.gmsz.utils.CalFrameUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * �Զ���GridViewʵ���϶�����
 * @author zhoufm
 *
 */
public class SceneGridView extends GridView {
	    private WindowManager windowManager;  
	    private WindowManager.LayoutParams windowParams;  
	
	// ��������ĳ�Ա����
		private ImageView dragImageView;//�϶�item��preview  
		private int dragSrcPosition;//��ʼ��ק��λ��   
		private int dragPosition;// ������ק��λ��   
		// x,y����ļ���
		private int dragPointX;//�����item��x����   
		private int dragPointY;//�����item��y����   
		private int dragOffsetX;
		private int dragOffsetY;
		
	
		private int scaledTouchSlop;
		private int upScrollBounce;
		private int downScrollBounce;
		
		public static Frame currFrame = null;// �Ϸ����϶����ұ߻�����Ϣ
		
		
		

	public SceneGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	 @Override  
	    public boolean onInterceptTouchEvent(MotionEvent ev) {  
	  
	        if (ev.getAction() == MotionEvent.ACTION_DOWN) {  
	            int x = (int)ev.getX();  
	            int y = (int)ev.getY();  
	              
	            this.dragSrcPosition = this.dragPosition = pointToPosition(x, y);  
	            if (this.dragPosition == -1) {  
	                return super.onInterceptTouchEvent(ev);  
	            }  
	            ViewGroup itemView = (ViewGroup)getChildAt(this.dragPosition -   
	                        getFirstVisiblePosition());  
	            //�õ���ǰ����item�ڲ���ƫ���� �������item���Ͻǵ�����   
	          //  this.itemHeight = itemView.getHeight();  
	            this.dragPointX = (x - itemView.getLeft());  
	            this.dragPointY = (y - itemView.getTop());  
	            this.dragOffsetX = (int)(ev.getRawX() - x);  
	            this.dragOffsetY = (int)(ev.getRawY() - y);  
	            
	           // View dragger = itemView.findViewById(R.id.imageView);
	            View dragger = itemView.findViewById(R.id.webView);
				// ���ѡ���϶�ͼ��
				if (dragger != null && dragPointX > dragger.getLeft() && dragPointX < dragger.getRight()
						&& dragPointY > dragger.getTop() && dragPointY < dragger.getBottom() + 20) {

					upScrollBounce = Math.min(y - scaledTouchSlop, getHeight() / 4);
					downScrollBounce = Math.max(y + scaledTouchSlop, getHeight() * 3 / 4);

					
					  //ÿ�ζ�����һ��cache����������һ��bitmap   
					Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache());
					itemView.setDrawingCacheEnabled(true);
					startDrag(bm, x, y);
				}
				// ��¼��ǰ�϶�������һ��item
				FrameAdapter adapter = (FrameAdapter) getAdapter();
				currFrame = (Frame) adapter.getItem(dragSrcPosition);
				return false;
	        }
				
	          
	        return super.onInterceptTouchEvent(ev);  
	    }  
	 
	 
	  private void startDrag(Bitmap bm, int x, int y) {  
	        stopDrag();  
	         
	        this.windowParams = new WindowManager.LayoutParams();  
	        //Gravity.TOP|Gravity.LEFT;��������   
	        this.windowParams.gravity = Gravity.TOP|Gravity.LEFT;  
	        //�õ�preview���Ͻ��������Ļ������   
	        this.windowParams.x = (x - this.dragPointX + this.dragOffsetX);  
	        this.windowParams.y = (y - this.dragPointY + this.dragOffsetY);  
	  
	        //������קitem�Ŀ�͸�   
	        this.windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;      
	        this.windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;  
	        this.windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE                           
	                             | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE                           
	                             | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON                           
	                             | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;   
	        this.windowParams.format = PixelFormat.TRANSLUCENT;  
	        this.windowParams.windowAnimations = 0;  
	  
	        ImageView imageView = new ImageView(getContext());  
	        imageView.setImageBitmap(bm);  
	        this.windowManager = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE));//��window��  
	        this.windowManager.addView(imageView, this.windowParams);  
	        this.dragImageView = imageView;  
	    }  

	  private void stopDrag() {  
	       if (this.dragImageView != null) {  
	          this.windowManager.removeView(this.dragImageView);  
	          this.dragImageView = null;  
	       }  
	
	    } 
	  
	  @Override  
	    public boolean onTouchEvent(MotionEvent ev) {  
			if (dragImageView != null && dragPosition != INVALID_POSITION) {
				int action = ev.getAction();
				switch (action) {
				case MotionEvent.ACTION_UP:
					int upX = (int) ev.getX();
					int upY = (int) ev.getY();
					stopDrag();
					onDrop(upX, upY);
					break;
				case MotionEvent.ACTION_MOVE:
					int moveX = (int) ev.getX();
					int moveY = (int) ev.getY();
					onDrag(moveX, moveY);
					break;
				default:
					break;
				}
				return true;
			}
			return super.onTouchEvent(ev); 
	    }  
	  
	  /**
		 * ʵ���ϵ�����item��������Ƴ���
		 */
		public void onDrag(int x, int y) {
			if (dragImageView != null) {
				windowParams.alpha = 0.8f;
				windowParams.x = x - dragPointX + dragOffsetX;
				windowParams.y = y - dragPointY + dragOffsetY;
				windowManager.updateViewLayout(dragImageView, windowParams);
			}

			int tempPosition = pointToPosition(x, y);
			if (tempPosition != INVALID_POSITION) {
				dragPosition = tempPosition;
			}

			// ����
			if (y < upScrollBounce || y > downScrollBounce) {
				// ʹ��setSelection��ʵ�ֹ���
				setSelection(dragPosition);
			}
		}
		
		/**
		 * ��������
		 * 
		 * @param x
		 * @param y
		 */
		public void onDrop(int x, int y) {

			// Ϊ�˱��⻬�����ָ��ߵ�ʱ�򣬷���-1������
			int tempPosition = pointToPosition(x, y);
			if (tempPosition != INVALID_POSITION) {
				dragPosition = tempPosition;
			}
			// ���� ����gridview������
	/*		FrameAdapter adapter = (FrameAdapter) MainActivity.sceneGv.getAdapter();
			int position = CalFrameUtil.getCurrItem(adapter.getCount(), x, y);
			if (position >= 0) {
				Frame frame = (Frame) adapter.getItem(position);
				if (!currDragBaseInfo.getUrl().equals(frame.getUrl())
						|| !currDragBaseInfo.getName().equals(frame.getName())) {
					Log.i("Demo", "�϶���");
					frame.setName(currDragBaseInfo.getName());
					frame.setUrl(currDragBaseInfo.getUrl());
					frame.setType(currDragBaseInfo.getType());
					frame.setData(currDragBaseInfo.getData());
					frame.setChange(true);

					adapter.notifyDataSetChanged();
				}
			}*/

		}
	

}
