package com.gmsz.view;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.gmsz.adapter.BaseDetailInfoAdapter;
import com.gmsz.adapter.FrameAdapter;
import com.gmsz.demo4.R;
import com.gmsz.demo4.R.id;
import com.gmsz.domain.BaseDetailInfo;
import com.gmsz.domain.Frame;
import com.gmsz.main.MainActivity;
import com.gmsz.preview.PreViewAdapter;
import com.gmsz.utils.CalFrameUtil;
/**
 * 
 * Class name:DragGridView
 * Description: 继承GridView 实现拖动功能
 * @author LiangYan
 */

public class DragGridView extends GridView {
	// 定义基本的成员变量
	private ImageView dragImageView;
	private int dragSrcPosition;
	private int dragPosition;
	// x,y坐标的计算
	private int dragPointX;
	private int dragPointY;
	private int dragOffsetX;
	private int dragOffsetY;

	private WindowManager windowManager;
	private WindowManager.LayoutParams windowParams;

	private int scaledTouchSlop;
	private int upScrollBounce;
	private int downScrollBounce;
	
	private ImageView dragger;
	
	public static BaseDetailInfo currDragBaseInfo = null;// 被拖动的右边基本信息

	private Activity context;
	
	public DragGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = (Activity)context;
	}

	@Override
	
	public boolean onInterceptTouchEvent(MotionEvent ev) {
	
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			int x = (int) ev.getX();
			int y = (int) ev.getY();

			dragSrcPosition = dragPosition = pointToPosition(x, y);
			if (dragPosition == AdapterView.INVALID_POSITION) {
				return super.onInterceptTouchEvent(ev);
			}

			ViewGroup itemView = (ViewGroup) getChildAt(dragPosition - getFirstVisiblePosition());
			dragPointX = x - itemView.getLeft();
			dragPointY = y - itemView.getTop();
			dragOffsetX = (int) (ev.getRawX() - x);
			dragOffsetY = (int) (ev.getRawY() - y);

			//View dragger = itemView.findViewById(R.id.detailImg);
			
			dragger = (ImageView)itemView.findViewById(id.detailImg);
			/*dragger.setFocusable(false);
			dragger.setFocusableInTouchMode(true);*/
			// 如果选中拖动图标
			if (dragger != null && dragPointX > dragger.getLeft() && dragPointX < dragger.getRight()
					&& dragPointY > dragger.getTop() && dragPointY < dragger.getBottom() + 20) {

				upScrollBounce = Math.min(y - scaledTouchSlop, getHeight() / 4);
				downScrollBounce = Math.max(y + scaledTouchSlop, getHeight() * 3 / 4);

				itemView.setDrawingCacheEnabled(true);
				Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache());
				startDrag(bm, x, y);
			}

			// 记录当前拖动的是哪一个item
			BaseDetailInfoAdapter adapter = (BaseDetailInfoAdapter) getAdapter();
			currDragBaseInfo = (BaseDetailInfo) adapter.getItem(dragSrcPosition);
			
			//在预览窗口中显示内容
			GridView preview_view = ((MainActivity)context).getPreView();
			preview_view.setAdapter(new PreViewAdapter(context, currDragBaseInfo, R.layout.previewitem));
			return false;
		}
		//return super.onInterceptTouchEvent(ev);
		return false;
	}

	/**
	 * 开始拖动
	 */
	public void startDrag(Bitmap bm, int x, int y) {
		stopDrag();

		windowParams = new WindowManager.LayoutParams();
		windowParams.gravity = Gravity.TOP | Gravity.LEFT;
		windowParams.x = x - dragPointX + dragOffsetX;
		windowParams.y = y - dragPointY + dragOffsetY;
		windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		windowParams.format = PixelFormat.TRANSLUCENT;
		windowParams.windowAnimations = 0;

		ImageView imageView = new ImageView(getContext());
		imageView.setImageBitmap(bm);
		windowManager = (WindowManager) getContext().getSystemService("window");
		windowManager.addView(imageView, windowParams);
		dragImageView = imageView;
	}

	public void stopDrag() {
		if (dragImageView != null) {
			windowManager.removeView(dragImageView);
			dragImageView = null;
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
			//return false;
		}
		return super.onTouchEvent(ev);
		//return false;
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
		FrameAdapter adapter = (FrameAdapter) MainActivity.sceneGv.getAdapter();
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
		}
		
	}
	
}