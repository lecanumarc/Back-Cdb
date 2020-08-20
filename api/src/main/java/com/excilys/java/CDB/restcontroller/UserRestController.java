package com.excilys.java.CDB.restcontroller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.java.CDB.DTO.DashboardDTO;
import com.excilys.java.CDB.DTO.UserDTO;
import com.excilys.java.CDB.DTO.mapper.UserMapper;
import com.excilys.java.CDB.exception.UserException;
import com.excilys.java.CDB.model.User;
import com.excilys.java.CDB.service.UserService;
import com.excilys.java.CDB.validator.ValidatorUser;
import com.excilys.java.CDB.validator.ValidatorUserDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("users")
public class UserRestController {
	private DashboardDTO page = new DashboardDTO();
	private UserService userService;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserRestController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping({ "", "/" })
	public ResponseEntity<List<UserDTO>> allUsers() {
		List<User> users = userService.listUsers();

		List<UserDTO> usersDto = users.stream().map(user -> UserMapper.mapUserToDto(user))
				.collect(Collectors.toList());
		
		return new ResponseEntity<List<UserDTO>>(usersDto, HttpStatus.OK);
	}

	@PostMapping("/page")
	public ResponseEntity<List<UserDTO>> listUsers(@RequestBody DashboardDTO dashboardDTO) {
		page.setPage(dashboardDTO);
		PageRequest pageReq = PageRequest.of(Integer.parseInt(page.getPageNb()) - 1, Integer.parseInt(page.getLinesNb()),
				userService.sortBy(page.getColumn(), Boolean.valueOf(page.getAscOrder())));

		Page<User> users = userService.listByPage(page.getSearch(), pageReq);
		List<UserDTO> usersDto = users.stream().map(user -> UserMapper.mapUserToDto(user))
				.collect(Collectors.toList());

		return new ResponseEntity<List<UserDTO>>(usersDto, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO userDTO = null;
		Optional<User> user = userService.findById(id);
		if (user.isPresent()) {
			userDTO = UserMapper.mapUserToDto(user.get());
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		}
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<UserDTO> deleteById(@PathVariable Long id) {
		if(userService.delete(id)) {
			return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
		try {
			ValidatorUserDTO.validate(userDTO);
			if(userDTO.getRole().getName().contentEquals("admin")) {
				throw new UserException("Cannot create new user as admin !");
			}
			userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			User user = UserMapper.mapDtoToUser(userDTO);
			ValidatorUser.validate(user);
			userService.add(user);
		} catch (UserException e) {
			e.printStackTrace();
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserDTO>(HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
		try {
			ValidatorUserDTO.validate(userDTO);
			User user = UserMapper.mapDtoToUser(userDTO);
			ValidatorUser.validate(user);
			userService.edit(user);
		} catch (UserException e) {
			e.printStackTrace();
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserDTO>(HttpStatus.CREATED);
	}
	
	@PostMapping("/number")
	public int numberUsers(@RequestBody DashboardDTO dashboardDTO) {
		page.setPage(dashboardDTO);
		return userService.count(page.getSearch());
	}

}