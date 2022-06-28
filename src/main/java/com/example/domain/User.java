package com.example.domain;

public class User {
	private String name;

	private String ruby;

	private String user_email;

	private String zipcode;

	private String address;

	private String telephone;

	private String password;

	private String Confirmpassword;

	private String regist_date;

	private String regist_user;

	private String update_date;

	private String update_user;

	private Integer del_flg;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRuby() {
		return ruby;
	}

	public void setRuby(String ruby) {
		this.ruby = ruby;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return Confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		Confirmpassword = confirmpassword;
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
		return "User [name=" + name + ", ruby=" + ruby + ", user_email=" + user_email + ", zipcode=" + zipcode
				+ ", address=" + address + ", telephone=" + telephone + ", password=" + password + ", Confirmpassword="
				+ Confirmpassword + ", regist_date=" + regist_date + ", regist_user=" + regist_user + ", update_date="
				+ update_date + ", update_user=" + update_user + ", del_flg=" + del_flg + "]";
	}

}