package com.king.android.common.widget;

import com.king.android.common.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class CleanableEditText extends EditText implements android.view.View.OnFocusChangeListener{

	private Drawable mDelIcon = null;

	public CleanableEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public CleanableEditText(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public CleanableEditText(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public CleanableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
	private void init(Context context) {
		//��ȡͼƬ��Դ
		mDelIcon = ContextCompat.getDrawable(context, R.drawable.selector_btn_clear);

		addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (mOnTextChangedListener != null)
					mOnTextChangedListener.onTextChanged(s, start, before, count);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				if (mOnTextChangedListener != null)
					mOnTextChangedListener.beforeTextChanged(s, start, count, after);
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() > 0){
					showDelIcon(true);
				} else {
					showDelIcon(false);
				}
				if (mOnTextChangedListener != null)
					mOnTextChangedListener.afterTextChanged(s);
			}
		});
	}

	private void showDelIcon(boolean visiable) {

		if(!visiable) {
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		}else {
			setCompoundDrawablesWithIntrinsicBounds(null, null, mDelIcon, null);
		}

	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		if(mDelIcon != null && event.getAction() == MotionEvent.ACTION_UP) {
			int x = (int) event.getX() ;
			//�жϴ������Ƿ���ˮƽ��Χ��
			boolean isInnerWidth = (x > (getWidth() - getTotalPaddingRight())) &&
					(x < (getWidth() - getPaddingRight()));

			//��ȡɾ��ͼ��ı߽磬����һ��Rect����
			Rect rect = mDelIcon.getBounds();

			//��ȡɾ��ͼ��ĸ߶�
			int height = rect.height();
			int y = (int) event.getY();

			//����ͼ��ײ����ؼ��ײ��ľ���
			int distance = (getHeight() - height) /2;

			//�жϴ������Ƿ�����ֱ��Χ��(���ܻ��е����)
			//���������������distance����distance+ͼ������ĸ߶ȣ�֮�ڣ�����Ϊ����ɾ��ͼ��

			boolean isInnerHeight = (y > distance) && (y < (distance + height));
			if(isInnerWidth && isInnerHeight) {
				setText("");
			}
		}

		return super.onTouchEvent(event);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		if(hasFocus) {
			if (length() > 0){
				showDelIcon(true);
			} else {
				showDelIcon(false);
			}
		}else {
			showDelIcon(false);
		}
	}

	public interface OnTextChangedListener{
		public void onTextChanged(CharSequence s, int start, int before, int count);
		public void beforeTextChanged(CharSequence s, int start, int count, int after);
		public void afterTextChanged(Editable s);
	}
	
	private OnTextChangedListener mOnTextChangedListener = null;
	public void setOnTextChangedListener(OnTextChangedListener l){
		this.mOnTextChangedListener = l;
	}

}
