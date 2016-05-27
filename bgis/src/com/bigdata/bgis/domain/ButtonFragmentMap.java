package com.bigdata.bgis.domain;

import android.support.v4.app.Fragment;

import com.bigdata.bgis.view.ToolbarButtonView;

public class ButtonFragmentMap {
	private ToolbarButtonView button;
	private Fragment fragment;
	
	public ButtonFragmentMap(ToolbarButtonView button,Fragment fragment){
		this.button = button;
		this.fragment = fragment;
	}
	
	public ToolbarButtonView getButton(){
		return this.button;
	}
	
	public void setButton(ToolbarButtonView button){
		this.button = button;
	}
	
	public Fragment getFragment(){
		return this.fragment;
	}

	public void setFragment(Fragment fragment){
		this.fragment = fragment;
	}
}

