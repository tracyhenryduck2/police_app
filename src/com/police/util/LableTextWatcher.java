package com.police.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

public class LableTextWatcher implements TextWatcher {

	private View lable = null;

	public LableTextWatcher(View lable) {
		this.lable = lable;
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (lable != null && start == 0) {
			if (count > 0) {
				lable.setVisibility(View.INVISIBLE);
			} else {
				lable.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

}
