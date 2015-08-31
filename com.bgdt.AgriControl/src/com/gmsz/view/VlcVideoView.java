package com.gmsz.view;

import java.lang.ref.WeakReference;

import org.videolan.libvlc.EventHandler;
import org.videolan.libvlc.IVideoPlayer;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaList;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * Class name:VlcVideoView Description: ��װVlc�Ķ���
 * 
 * @author LiangYan
 */
public class VlcVideoView {

	private String mFilePath;

	// display surface
	private SurfaceView mSurface;
	private SurfaceHolder holder;

	// media player
	private LibVLC libvlc;
	private int mVideoWidth;
	private int mVideoHeight;
	private final static int VideoSizeChanged = -1;

	private Activity mContext;

	private int width;
	private int height;
	private TextView loading;// �����е�textview

	public VlcVideoView(SurfaceView surfaceView, String url, Activity ac, int width, int height, TextView loading) {
		mContext = ac;
		mFilePath = url;
		mSurface = surfaceView;
		holder = mSurface.getHolder();
		holder.addCallback(mSHCallback);

		this.width = width;
		this.height = height;
		this.loading = loading;
	}

	/*************
	 * Activity
	 *************/
	IVideoPlayer mVideoPlayer = new IVideoPlayer() {
		@Override
		public void setSurfaceSize(int width, int height, int visible_width, int visible_height, int sar_num,
				int sar_den) {
			Message msg = Message.obtain(mHandler, VideoSizeChanged, width, height);
			msg.sendToTarget();
		}

		@Override
		public void eventHardwareAccelerationError() {

		}

	};

	/*************
	 * Surface
	 *************/
	SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
		public void surfaceCreated(SurfaceHolder holder) {
			Log.i("Demo", "SurfaceView�����ˣ�");
		}

		public void surfaceChanged(SurfaceHolder surfaceholder, int format, int width, int height) {
			Log.i("Demo", "SurfaceView���ˣ�");
			if (libvlc != null)
				libvlc.attachSurface(holder.getSurface(), mVideoPlayer);
		}

		public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		}
	};

	/*************
	 * Events
	 *************/

	private Handler mHandler = new MyHandler(this);

	private static class MyHandler extends Handler {
		private WeakReference<VlcVideoView> mOwner;

		public MyHandler(VlcVideoView owner) {
			mOwner = new WeakReference<VlcVideoView>(owner);
		}

		@Override
		public void handleMessage(Message msg) {
			VlcVideoView player = mOwner.get();

			// SamplePlayer events
			if (msg.what == VideoSizeChanged) {
				Log.i("Demo", "msg.what" + msg.what + ";VideoSizeChanged" + VideoSizeChanged);
				player.setSize(msg.arg1, msg.arg2);

				player.hideLoading();
				return;
			}

			// Libvlc events
			Bundle b = msg.getData();
			switch (b.getInt("event")) {
			case EventHandler.MediaPlayerEndReached:
				// player.releasePlayer();
				break;
			case EventHandler.MediaPlayerPlaying:
			case EventHandler.MediaPlayerPaused:
			case EventHandler.MediaPlayerStopped:
			default:
				break;
			}
		}
	}

	/**
	 * ��ʼ������Ƶ
	 */
	public void createPlayer() {
		String media = mFilePath;
		try {
			long l = System.currentTimeMillis();
			// Create a new media player
			libvlc = new LibVLC();
			libvlc.setHardwareAcceleration(LibVLC.HW_ACCELERATION_DECODING);
			libvlc.setSubtitlesEncoding("");
			libvlc.setAout(LibVLC.AOUT_OPENSLES);
			libvlc.setTimeStretching(true);
			libvlc.setChroma("RV32");
			libvlc.setVerboseMode(true);
			libvlc.restart(mContext);
		
			EventHandler.getInstance().addHandler(mHandler);
			holder.setFormat(PixelFormat.RGBX_8888);
			holder.setKeepScreenOn(true);
			MediaList list = libvlc.getMediaList();
			list.clear();
			list.add(new Media(libvlc, LibVLC.PathToURI(media)), false);
			libvlc.playIndex(0);

			Log.i("Demo", "��Ƶ��������ʱ�䣺" + (System.currentTimeMillis() - l) + ",libvlc" + libvlc.toString());
		} catch (Exception e) {
			Log.e("Demo", "��ʼ�����쳣");
		}
	}

	/**
	 * ֹͣ���ڲ��ŵ���Ƶ ����ֹͣ��Ҫʱ���������������һ���߳�ȥ��
	 */
	public void releasePlayer() {
		hideLoading();//����
		if (libvlc == null)
			return;
		new Thread(new Runnable() {
			public void run() {
				try {
					long l1 = System.currentTimeMillis();
					EventHandler.getInstance().removeHandler(mHandler);
					libvlc.stop();
					libvlc.detachSurface();
					holder = null;
					libvlc.closeAout();
					libvlc.destroy();
					libvlc = null;

					mVideoWidth = 0;
					mVideoHeight = 0;

					Log.i("Demo", "�ر�����ʱ��6��" + (System.currentTimeMillis() - l1));
				} catch (Exception e) {
					Log.e("Demo", "�ر��쳣");
				}
				
			}
		}).start();
	}

	/**
	 * 
	 * Description: ����size
	 */
	private void setSize(int width, int height) {
		mVideoWidth = width;
		mVideoHeight = height;
		if (mVideoWidth * mVideoHeight <= 1)
			return;

		// force surface buffer size
		holder.setFixedSize(mVideoWidth, mVideoHeight);
		// set display size
		LayoutParams lp = mSurface.getLayoutParams();
		lp.width = this.width;
		lp.height = this.height;
		mSurface.setLayoutParams(lp);
		mSurface.invalidate();
	}

	/**
	 * ������Ƶ����������е���
	 */
	private void hideLoading() {
		Log.i("Demo", "����������...");
		if (loading != null) {
			loading.setVisibility(View.INVISIBLE);
		}
	}

	/*************
	 * Events
	 *************/

}
