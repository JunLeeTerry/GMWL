package com.bigdata.bgis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RatingBar;

public class SpeakerVoiceBar extends RatingBar {

	private OnRatingBarChanging mOnRatingBarChanging;

	public SpeakerVoiceBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public SpeakerVoiceBar(Context context) {
		super(context);
	}

	public SpeakerVoiceBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_UP:

			if (mOnRatingBarChanging != null)
				mOnRatingBarChanging.onRatingChanging(this.getRating());

			break;
		}
		return super.onTouchEvent(event);
	}

	public void setOnRatingBarChange(OnRatingBarChanging changing) {
		mOnRatingBarChanging = changing;
	}

	public interface OnRatingBarChanging {
		void onRatingChanging(float f);
	}

}
