package com.hep.terminalhep;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hep.terminalhep.config.BaseActivity;
import com.hep.terminalhep.data.utils.JsonUtils;
import com.hep.terminalhep.inject.InjectView;
import com.hep.terminalhep.inject.ViewInject;
import com.hep.terminalhep.models.UserLoginModel;
import com.hep.terminalhep.repositories.AccountNmberInformation;
import com.hep.terminalhep.utils.AsyncRunnable;

/**
 * 
 * @ClassName: home_activity
 * @Description: TODO
 * @author zhangj
 * @date 2015年4月28日
 */

public class home_activity extends BaseActivity implements OnClickListener {

	@ViewInject(id = R.id.myText)
	private TextView myText;
	@ViewInject(id = R.id.myButton)
	private Button myButton;

	private Map<String, Object> mm;
	private Context context;

	
	@Override
	protected BaseActivity getThis() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	protected int getResId() {
		// TODO Auto-generated method stub
		return R.layout.home_activity;
	}

	@Override
	protected void afterOnCreate() {
		// TODO Auto-generated method stub
		InjectView.autoInjectAllField(this, this);
		mm = new HashMap<String, Object>();
		mm.put("LoginPwd", "123456");
		mm.put("LoginName", "nidu123456@126.com");
		initView();
	}

	/**
	 * 
	 * initView(这里用一句话描述这个方法的作用)
	 * 
	 * @Title: initView
	 * @Description: TODO
	 * @param 设定文件
	 * @author zhangj
	 * @return void 返回类型
	 * @date 2015年4月29日 下午4:29:37
	 * @throws
	 */
	private void initView() {
		// TODO Auto-generated method stub
		myButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.myButton:
			setData();
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * @Title: setData
	 * @Description: TODO 用户登录 接口交互
	 * @param 设定文件
	 * @author zhangj
	 * @return void 返回类型
	 * @date 2015年4月29日 上午11:51:55
	 * @throws
	 */
	private void setData() {
		// TODO Auto-generated method stub
		new AsyncRunnable<UserLoginModel>() {
			@Override
			protected UserLoginModel doInBackground(Void... params) {
				UserLoginModel my = AccountNmberInformation.UserLogin(
						home_activity.this, mm);
				return my;
			}

			@Override
			protected void onPostExecute(UserLoginModel mm) {
				myText.setText(JsonUtils.ToJson(mm));
			}

			@Override
			protected void onPostError(Exception ex) {
				
			};
		}.execute();
	}

}
