package com.zhulaozhijias.zhulaozhijia.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;


public class MenuItem extends LinearLayout {
	private TextView titleView;
	private ImageView iconView;
	private boolean isSelected = false;
	private int selectedImgId;
	private int normalImgId;

	public MenuItem(final Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.ext_menuitem, this);
		titleView = (TextView) findViewById(R.id.text);
		iconView = (ImageView) findViewById(R.id.image);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.MenuItem);
		selectedImgId = ta.getResourceId(R.styleable.MenuItem_selectedIcon, 0);
		normalImgId = ta.getResourceId(R.styleable.MenuItem_normalIicon, 0);
		iconView.setBackgroundResource(normalImgId);
		String title = ta.getString(R.styleable.MenuItem_text);
		setText(title);
		ta.recycle();
	}

	public void setSelected(boolean selected) {
		if (selected) {
			titleView
					.setTextColor(getResources().getColor(R.color.system_color));
			iconView.setBackgroundResource(selectedImgId);
			isSelected = true;
		} else {
			titleView.setTextColor(getResources().getColor(R.color.home_page));
			iconView.setBackgroundResource(normalImgId);
			isSelected = false;
		}
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setText(String text) {
		titleView.setText(text);
	}

}
