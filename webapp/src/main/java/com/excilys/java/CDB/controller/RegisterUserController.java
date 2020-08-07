package com.excilys.java.CDB.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.java.CDB.DTO.UserDTO;
import com.excilys.java.CDB.DTO.mapper.UserMapper;
import com.excilys.java.CDB.model.User;
import com.excilys.java.CDB.service.UserService;

@Controller
@RequestMapping("/RegisterUser")
public class RegisterUserController {

private static Logger logger = LoggerFactory.getLogger(RegisterUserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping
	public ModelAndView userInfo() {
		return new ModelAndView("registerUser");
	}

	@PostMapping
	public RedirectView registerUser(UserDTO userDTO, String passwordConfirm) {
		
		if (!userDTO.getUsername().isEmpty() && !userDTO.getPassword().isEmpty() && userDTO.getPassword().equals(passwordConfirm)) {
            userDTO.setPassword(passwordEncoder.encode(passwordConfirm));
			userDTO.setRole("USER");
			User user = UserMapper.mapDtoToUser(userDTO);
			userService.createUser(user);
		}

		logger.info(userDTO.toString());
		return new RedirectView("/webapp/");
	}

}