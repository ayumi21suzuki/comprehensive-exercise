package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import com.example.util.XlsDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})
class RegistUserControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("登録用URLリクエスト画面の表示")
	void testIndex() throws Exception {
		mockMvc.perform(get("/regist"))
		.andExpect(view().name("regist"))
		.andReturn();
	}

	@Test
	@DisplayName("送信完了画面の表示")
	@DatabaseSetup("classpath:ensyu_01.xlsx")
	void testRegisterConfirm_01() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/regist/registerConfirm")
				.param("user_email", "aaa.test@gmail.com"))
				.andExpect(view().name("redirect:/regist/index2"))
				.andReturn();

		MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
		String user_email = (String) session.getAttribute("user_email");
		assertNull(user_email);
	}

	@Test
	@DisplayName("メールアドレス登録済みのため登録用URLリクエスト画面に戻る")
	@DatabaseSetup("classpath:ensyu_02.xlsx")
	void testRegisterConfirm_02() throws Exception {
//		MvcResult mvcResult = 
				mockMvc.perform(post("/regist/registerConfirm")
				.param("user_email", "bbb.test@gmail.com")).andExpect(view().name("regist"))
				.andExpect(model().hasErrors()).andExpect(model().attributeHasErrors("registForm"))
				.andReturn();

//		BindingResult bindingResult = (BindingResult) mvcResult.getModelAndView().getModel().get(BindingResult.MODEL_KEY_PREFIX + "registForm");
//		String message = bindingResult.getFieldError().getDefaultMessage();
//		assertEquals("このメールアドレスは既に登録されています", message);
	}

	@Test
	@DisplayName("有効期限内のURLが存在するため登録用URLリクエスト画面に戻る")
	@DatabaseSetup("classpath:ensyu_03.xlsx")
	void testRegisterConfirm_03() throws Exception {
//		MvcResult mvcResult = 
				mockMvc.perform(post("/regist/registerConfirm")
				.param("unique_url", "dd42a4c9-c3ff-44bf-92cb-e48eaa1bc265"))
				.andExpect(view().name("regist"))
				.andReturn();

		
//		BindingResult bindingResult = (BindingResult) mvcResult.getModelAndView().getModel().get(BindingResult.MODEL_KEY_PREFIX + "registForm");
//		String message = bindingResult.getFieldError().getDefaultMessage();
//		assertEquals("有効期限内のURLが既に発行されています",  message);
	}

	@Test
	@DisplayName("送信完了画面の表示(リダイレクト)")
	void testIndex2() throws Exception {
		mockMvc.perform(get("/regist/index2"))
		.andExpect(view().name("send_complete"))
		.andReturn();
	}

	@Test
	@DisplayName("URLからユーザー登録画面を表示")
	void testIndex3() throws Exception {
		mockMvc.perform(get("/regist/User"))
		.andExpect(view().name("user"))
		.andReturn();
	}

	@Test
	@DisplayName("ユーザー登録完了画面の表示")
	@DatabaseSetup(value ="classpath:ensyu_04.xlsx")
	void testUserComplete_01() throws Exception {
		mockMvc.perform(post("/regist/UserComplete")
				.sessionAttr("key","dd42a4c9-c3ff-44bf-92cb-e48eaa1bc265")
				.param("name", "テスト")
				.param("ruby", "てすと")
				.param("zipcode", "111-1111")
				.param("address", "テスト住所")
				.param("telephone", "000-0000-0000")
				.param("password", "passpass")
				.param("confirmpassword", "passpass"))
				.andExpect(view().name("redirect:/regist/index4"))
				.andReturn();
	}

	@Test
	@DisplayName("パスワードが一致せず、登録失敗のためユーザー登録画面に戻る")
	@DatabaseSetup(value ="classpath:ensyu_05.xlsx")
	void testUserComplete_02() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/regist/UserComplete")
				.sessionAttr("key","dd42a4c9-c3ff-44bf-92cb-e48eaa1bc265")
				.param("name", "テスト")
				.param("ruby", "てすと")
				.param("zipcode", "111-1111")
				.param("address", "テスト住所")
				.param("telephone", "000-0000-0000")
				.param("password", "morimori")
				.param("confirmpassword", "rimorimo"))
				.andExpect(view().name("user"))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasErrors("userForm"))
				.andReturn();

		BindingResult bindResult = (BindingResult) mvcResult.getModelAndView().getModel()
				.get(BindingResult.MODEL_KEY_PREFIX + "userForm");
		String message = bindResult.getFieldError().getDefaultMessage();
		assertEquals("パスワードが一致していません", message);

	}

	@Test
	@DisplayName("ユーザー登録完了画面の表示(リダイレクト)")
	void testIndex4() throws Exception {
		mockMvc.perform(get("/regist/index4"))
		.andExpect(view().name("registration_complete"))
		.andReturn();
	}

}
