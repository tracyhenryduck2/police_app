package com.police.util;

import com.police.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * ���ּ��̽���
 * 
 * @author AnLiang
 * @created 2012-08-15
 * 
 * 
 * */
public class NumKeyBoardPopuWindow extends PopupWindow implements android.view.View.OnClickListener
{

	private LayoutInflater mInflater;
	private View mView;
	private Button one, two, three, four, five, six, seven, eight, nine, zero, delete,confirm;
	private EditText mSearch;
	private Context mContext;
	private OnConfirmListener confirmlistener;

	public NumKeyBoardPopuWindow(Context context,OnConfirmListener confirmlistener)
	{
		super(context);
		mInflater = LayoutInflater.from(context);
		mView = mInflater.inflate(R.layout.numkeyboard, null);
		setContentView(mView);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable());
		initView();
		mContext = context;
		this.confirmlistener = confirmlistener;
	}



	public NumKeyBoardPopuWindow(Context context,int width, int height,OnConfirmListener confirmlistener) {
		super(width, height);
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(context);
		mView = mInflater.inflate(R.layout.numkeyboard, null);
		setContentView(mView);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable());
		initView();
		mContext = context;
		this.confirmlistener = confirmlistener;
	}


	public NumKeyBoardPopuWindow()
	{

	}

	private void initView()
	{
		one = (Button) mView.findViewById(R.id.one);
		two = (Button) mView.findViewById(R.id.two);
		three = (Button) mView.findViewById(R.id.three);
		four = (Button) mView.findViewById(R.id.four);
		five = (Button) mView.findViewById(R.id.five);
		six = (Button) mView.findViewById(R.id.six);
		seven = (Button) mView.findViewById(R.id.seven);
		eight = (Button) mView.findViewById(R.id.eight);
		nine = (Button) mView.findViewById(R.id.nine);
		zero = (Button) mView.findViewById(R.id.zero);
		delete = (Button) mView.findViewById(R.id.delete);
		delete.setText("清除");
		confirm = (Button) mView.findViewById(R.id.confirm);
		confirm.setText("确定");
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		five.setOnClickListener(this);
		six.setOnClickListener(this);
		seven.setOnClickListener(this);
		eight.setOnClickListener(this);
		nine.setOnClickListener(this);
		zero.setOnClickListener(this);
		delete.setOnClickListener(this);
		confirm.setOnClickListener(this);
	}

	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.one:
			mSetEditTextNum("1");
			break;
		case R.id.two:
			mSetEditTextNum("2");
			break;
		case R.id.three:
			mSetEditTextNum("3");
			break;
		case R.id.four:
			mSetEditTextNum("4");
			break;
		case R.id.five:
			mSetEditTextNum("5");
			break;
		case R.id.six:
			mSetEditTextNum("6");
			break;
		case R.id.seven:
			mSetEditTextNum("7");
			break;
		case R.id.eight:
			mSetEditTextNum("8");
			break;
		case R.id.nine:
			mSetEditTextNum("9");
			break;
		case R.id.zero:
			if (!"0".equals(mSearch.getText().toString()))
			{
				if (!"".equals(mSearch.getText().toString()))
				{

					if ("".equals(mSearch.getText().toString()))
					{
						mSearch.setText("0");
					} else
					{
						String a0 = mSearch.getText().toString();
						long m = Long.parseLong(a0);
						if (m < 10000000000l)
						{
							mSearch.append("0");
						} else
						{
							Toast.makeText(mContext, "超过手机应有位数", Toast.LENGTH_SHORT).show();
						}
					}

				}
			}
			break;

		case R.id.delete:
			mSearch.setText("");
			break;
		case R.id.confirm:
			confirmlistener.confirm();
			this.dismiss();
			break;
		default:
			break;
		}

	}

	private void mSetEditTextNum(String str)
	{
		if (!"0".equals(mSearch.getText().toString()))
		{

			if ("".equals(mSearch.getText().toString()))
			{
				mSearch.setText(str);
			} else
			{
				String a = mSearch.getText().toString();
				long m = Long.parseLong(a);
				if (m < 10000000000l)
				{
					mSearch.append(str);
				} else
				{
					Toast.makeText(mContext, "超过手机应有位数", Toast.LENGTH_SHORT).show();
				}
			}

		} else
		{
			mSearch.setText(str);
		}
	}

	public void setEditText(EditText et)
	{
		mSearch = et;
	}
	
	public static interface OnConfirmListener {
		void confirm();
	}
}
