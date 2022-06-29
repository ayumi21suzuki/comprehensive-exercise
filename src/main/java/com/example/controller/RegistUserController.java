package com.example.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Regist;
import com.example.domain.User;
import com.example.form.RegistForm;
import com.example.form.UserForm;
import com.example.service.RegistService;

@Controller
@RequestMapping("/regist")
public class RegistUserController {

	@RequestMapping("")
	public String index() {
		return "regist";

	}

	@Autowired
	private MailSender mailSender;

	@Autowired
	private RegistService registService;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	private RegistForm registForm() {
		return new RegistForm();
	}

	@RequestMapping("/registerConfirm")
	public String registerConfirm(@Validated RegistForm form, BindingResult result) {

		List<Regist> urllList = registService.findByEmail(form.getUser_email());
		List<Regist> userList = registService.findByEmail2(form.getUser_email());

		if (urllList != null) {
			result.rejectValue("user_email", null, "有効期限内のURLが既に発行されています");
		}

		if (userList != null) {
			result.rejectValue("user_email", null, "このメールアドレスは既に登録されています");

		}
		if (result.hasErrors()) {
			return "regist";
		}

		String uniqueUrl = UUID.randomUUID().toString();

		SimpleMailMessage msg = new SimpleMailMessage();

		try {

			msg.setFrom("user2022@gmail.com");
			msg.setTo(form.getUser_email());
			msg.setSubject("ユーザー登録用メール");
			msg.setText("Hogehogeシステム、新規ユーザー登録依頼を受け付けました。\r\n" + "以下のURLから本登録処理を行ってください。\r\n" + "\r\n"
					+ "Hogehogeシステム、ユーザー登録URL\r\n" + "http://localhost:8080/regist/User?key=" + uniqueUrl + "\r\n"
					+ "\r\n" + "※上記URLの有効期限は24時間以内です");

			mailSender.send(msg);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Regist regist2 = new Regist();
		regist2.setUser_email(form.getUser_email());
		regist2.setUnique_url(uniqueUrl);

		System.out.println(regist2);
		registService.insert(regist2);

		return "redirect:/regist/index2";
	}

	@RequestMapping("/index2")
	public String index2() {
		return "send_complete";
	}

	@RequestMapping("/User")
	public String index3(String key) {
		session.setAttribute("key", key);
		return "user";
	}

	@ModelAttribute
	private UserForm userForm() {
		return new UserForm();
	}

	@RequestMapping("/UserComplete")
	public String UserComplete(@Validated UserForm form, BindingResult result) {
		String key = (String) session.getAttribute("key");

		List<Regist> registList = registService.findByEmail3(key);

		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setUser_email(registList.get(0).getUser_email());

		if (!form.getConfirmpassword().isEmpty() && !form.getPassword().isEmpty()
				&& !form.getPassword().equals(form.getConfirmpassword())) {
			result.rejectValue("confirmpassword", "", "パスワードが一致していません");
		}
		if (result.hasErrors()) {
			return "user";

		}
		registService.insert(user);
		registService.update(key);

		return "redirect:/regist/index4";
	}

	@RequestMapping("/index4")
	public String index4() {
		return "registration_complete";
	}

}