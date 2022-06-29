package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RegistForm {
	@NotBlank(message = "メールアドレスを入力して下さい")
	@Email(message = "メールアドレスの形式が不正です")
	private String user_email;
	
	private String unique_url;

	private String regist_date;

	private String regist_user;

	private String update_date;

	private String update_user;

	private Integer del_flg;

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUnique_url() {
		return unique_url;
	}

	public void setUnique_url(String unique_url) {
		this.unique_url = unique_url;
	}

	public String getRegist_date() {
		return regist_date;
	}

	public void setRegist_date(String regist_date) {
		this.regist_date = regist_date;
	}

	public String getRegist_user() {
		return regist_user;
	}

	public void setRegist_user(String regist_user) {
		this.regist_user = regist_user;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public Integer getDel_flg() {
		return del_flg;
	}

	public void setDel_flg(Integer del_flg) {
		this.del_flg = del_flg;
	}

	@Override
	public String toString() {
		return "RegistForm [user_email=" + user_email + ", unique_url=" + unique_url + ", regist_date=" + regist_date
				+ ", regist_user=" + regist_user + ", update_date=" + update_date + ", update_user=" + update_user
				+ ", del_flg=" + del_flg + "]";
	}

}