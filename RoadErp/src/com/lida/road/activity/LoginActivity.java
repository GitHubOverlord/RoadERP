package com.lida.road.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;


public class LoginActivity extends MainBaseActivity {
	private String userNameString;
	private String passwordString;
	private EditText userNameEditText,passwordEditText;
	private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       initView();
    }
	private void initView(){
		userNameEditText = (EditText)findViewById(R.id.login_user_name);
		passwordEditText = (EditText)findViewById(R.id.login_password);
		loginBtn = (Button)findViewById(R.id.login_login);
	}
	OnClickListener listener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_login:
				userNameString = userNameEditText.getEditableText().toString();
				passwordString = passwordEditText.getEditableText().toString();
				if (userNameString.equals("") || passwordString.equals("")) {
					SystemUtils.MToast("请输入完整的账号密码", LoginActivity.this);
					return;
				}
				
				break;

			default:
				break;
			}
			
		}
		
	};
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
