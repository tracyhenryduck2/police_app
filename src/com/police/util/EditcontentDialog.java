package com.police.util;





import com.police.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditcontentDialog extends Dialog {

	public EditcontentDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public EditcontentDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public EditcontentDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public static class Builder {
		private Context context;
		private String title;
		private String phone;
		private String name;
		private String content;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private DialogInterface.OnClickListener positiveButtonClickListener;
		private DialogInterface.OnClickListener negativeButtonClickListener;
		private View.OnClickListener clicklistener1;
		private View.OnClickListener clicklistener2;
		private View.OnClickListener clicklistener3;
		private View layout;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setPhone(String phone) {
			this.phone = phone;
			return this;
		}

		public String getPhone(){
			String ds = ((TextView) layout.findViewById(R.id.shouji)).getText().toString();
			return ds;
		}
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		

		public String getName(){
			String ds = ((TextView) layout.findViewById(R.id.name)).getText().toString();
			return ds;
		}

		public Builder setContent(String content) {
			this.content = content;
			return this;
		}

		public String getContent(){
			String ds = ((TextView) layout.findViewById(R.id.content)).getText().toString();
			return ds;
		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param positiveButtonText
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setClickListeners(View.OnClickListener listener1,
				View.OnClickListener listener2,View.OnClickListener listener3) {
			this.clicklistener1 = listener1;
			this.clicklistener2 = listener2;
			this.clicklistener3 = listener3;
			return this;
		}

		public EditcontentDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final EditcontentDialog dialog = new EditcontentDialog(context,R.style.Dialog);
			layout = inflater.inflate(R.layout.editcontent_normal_layout, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the dialog title

			((TextView) layout.findViewById(R.id.title)).setText(title);
			// set the confirm button
			if (positiveButtonText != null) {
				((Button) layout.findViewById(R.id.positiveButton))
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((Button) layout.findViewById(R.id.positiveButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.negativeButton))
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.negativeButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.negativeButton).setVisibility(
						View.GONE);
			}
			
			if(clicklistener1!=null){
				((Button) layout.findViewById(R.id.btn_phone))
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						clicklistener1.onClick(v);
					}
				});
			}
			if(clicklistener2!=null){
				((Button) layout.findViewById(R.id.btn_name))
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						clicklistener2.onClick(v);
					}
				});
			}
			if(clicklistener3!=null){
				((Button) layout.findViewById(R.id.btn_content))
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						clicklistener3.onClick(v);
					}
				});
			}
			
			
			// set the content message
			if (phone != null) {
				((TextView) layout.findViewById(R.id.shouji)).setText(phone);
			}
			if(name != null){
				((TextView) layout.findViewById(R.id.name)).setText(name);
			}
			if(content != null){
				((TextView) layout.findViewById(R.id.content)).setText(content);
			} 
			if (contentView != null) {
				// if no message set
				// add the contentView to the dialog body
				((LinearLayout) layout.findViewById(R.id.content))
						.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.content)).addView(
						contentView, new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
			}
            
			dialog.setCanceledOnTouchOutside(false);
			dialog.setContentView(layout);
			return dialog;
		}

	}
	
}
