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
 * 自定义GridView实现拖动功能
 * @author zhoufm
 *
 */
public class SceneGridView extends GridView {
	    private WindowManager windowManager;  
	    private WindowManager.LayoutParams windowParams;  
	
	// 定义基本的成员变量
		private ImageView dragImageView;//拖动item的preview  
		private int dragSrcPosition;//开始拖拽的位置   
		private int dragPosition;// 结束拖拽的位置   
		// x,y坐标的计算
		private int dragPointX;//相对于item的x坐标   
		private int dragPointY;//相对于item的y坐标   
		private int dragOffsetX;
		private int dragOffsetY;
		
	
		private int scaledTouchSlop;
		private int upScrollBounce;
		private int downScrollBounce;
		
		public static Frame currFrame = null;// 上方被拖动的右边基本信息
		
		
		

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
	            //得到当前点在item内部的偏移量 即相对于item左上角的坐标   
	          //  this.itemHeight = itemView.getHeight();  
	            this.dragPointX = (x - itemView.getLeft());  
	            this.dragPointY = (y - itemView.getTop());  
	            this.dragOffsetX = (int)(ev.getRawX() - x);  
	            this.dragOffsetY = (int)(ev.getRawY() - y);  
	            
	           // View dragger = itemView.findViewById(R.id.imageView);
	            View dragger = itemView.findViewById(R.id.webView);
				// 如果选中拖动图标
				if (dragger != null && dragPointX > dragger.getLeft() && dragPointX < dragger.getRight()
						&& dragPointY > dragger.getTop() && dragPointY < dragger.getBottom() + 20) {

					upScrollBounce = Math.min(y - scaledTouchSlop, getHeight() / 4);
					downScrollBounce = Math.max(y + scaledTouchSlop, getHeight() * 3 / 4);

					
					  //每次都销毁一次cache，重新生成一个bitmap   
					Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache());
					itemView.setDrawingCacheEnabled(true);
					startDrag(bm, x, y);
				}
				// 记录当前拖动的是哪一个item
				FrameAdapter adapter = (FrameAdapter) getAdapter();
				currFrame = (Frame) adapter.getItem(dragSrcPosition);
				return false;
	        }
				
	          
	        return super.onInterceptTouchEvent(ev);  
	    }  
	 
	 
	  private void startDrag(Bitmap bm, int x, int y) {  
	        stopDrag();  
	         
	        this.windowParams = new WindowManager.LayoutParams();  
	        //Gravity.TOP|Gravity.LEFT;这个必须加   
	        this.windowParams.gravity = Gravity.TOP|Gravity.LEFT;  
	        //得到preview左上角相对于屏幕的坐标   
	        this.windowParams.x = (x - this.dragPointX + this.dragOffsetX);  
	        this.windowParams.y = (y - this.dragPointY + this.dragOffsetY);  
	  
	        //设置拖拽item的宽和高   
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
	        this.windowManager = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE));//“window”  
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
		 * 实现拖到哪里item在哪里绘制出来
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

			// 滚动
			if (y < upScrollBounce || y > downScrollBounce) {
				// 使用setSelection来实现滚动
				setSelection(dragPosition);
			}
		}
		
		/**
		 * 更新数据
		 * 
		 * @param x
		 * @param y
		 */
		public void onDrop(int x, int y) {

			// 为了避免滑动到分割线的时候，返回-1的问题
			int tempPosition = pointToPosition(x, y);
			if (tempPosition != INVALID_POSITION) {
				dragPosition = tempPosition;
			}
			// 更新 场景gridview的数据
	/*		FrameAdapter adapter = (FrameAdapter) MainActivity.sceneGv.getAdapter();
			int position = CalFrameUtil.getCurrItem(adapter.getCount(), x, y);
			if (position >= 0) {
				Frame frame = (Frame) adapter.getItem(position);
				if (!currDragBaseInfo.getUrl().equals(frame.getUrl())
						|| !currDragBaseInfo.getName().equals(frame.getName())) {
					Log.i("Demo", "拖动：");
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
