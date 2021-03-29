package com.stackroute.userservice.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.userservice.Controller.UserController;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.service.SecurityTokenGenerator;
import com.stackroute.userservice.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private SecurityTokenGenerator securityTokenGenerator;

	private User user;

	@InjectMocks
	UserController userController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		user = new User("jhon123", "Jhon", "Peter", "123456", new Date());

	}
	
	@Test
	@Ignore
	public void testRegisterUser() throws Exception {
		when(userService.saveUser(user)).thenReturn(true);
		mockMvc.perform(post("/api/v1/userauthservice/register").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(jsonToString(user))).andExpect(status().isCreated()).andDo(print());
		verify (userService, times(1)).saveUser(Mockito.any(User.class));
		verifyNoMoreInteractions(userService);

	
	}

	public void testLoginUser() throws Exception {

		String userId = "Jhon123";
		String password = "123456";
		when(userService.saveUser(user)).thenReturn(true);
		when(userService.findByUsernameAndPassword(userId, password)).thenReturn(user);
		mockMvc.perform(post("/api/v1/userauthservice/login").contentType(MediaType.APPLICATION_JSON).content(jsonToString(user)))
		.andExpect(status().isOk());
			verify (userService, times(1)).findByUsernameAndPassword(userId, password);
			verifyNoMoreInteractions(userService);
	}

	private static String jsonToString(final Object obj) throws JsonProcessingException {

		String result;
		try {

			final ObjectMapper mapper = new ObjectMapper();
			final String jsontContent = mapper.writeValueAsString(obj);
			result = jsontContent;
		} catch (JsonProcessingException ex) {
			result = "Json Processing Error";
		}

		return result;
	}
}
