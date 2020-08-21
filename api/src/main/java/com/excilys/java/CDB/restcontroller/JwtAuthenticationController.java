package com.excilys.java.CDB.restcontroller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.java.CDB.DTO.DashboardDTO;
import com.excilys.java.CDB.DTO.UserDTO;
import com.excilys.java.CDB.DTO.mapper.RoleMapper;
import com.excilys.java.CDB.DTO.mapper.UserMapper;
import com.excilys.java.CDB.exception.UserException;
import com.excilys.java.CDB.jwt.JwtRequest;
import com.excilys.java.CDB.jwt.JwtResponse;
import com.excilys.java.CDB.jwt.JwtTokenUtil;
import com.excilys.java.CDB.jwt.JwtUserDetailsService;
import com.excilys.java.CDB.model.User;
import com.excilys.java.CDB.service.UserService;
import com.excilys.java.CDB.validator.ValidatorUser;
import com.excilys.java.CDB.validator.ValidatorUserDTO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("authenticate")
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
		try{
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		} catch(DisabledException e) {
			return new ResponseEntity<JwtResponse>(HttpStatus.NOT_FOUND);
		}
		catch(BadCredentialsException e) {
			return new ResponseEntity<JwtResponse>(HttpStatus.UNAUTHORIZED);
		}
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return new ResponseEntity<JwtResponse>(new JwtResponse(token) , HttpStatus.OK);
	}

	@PostMapping(value = "/userInfo", consumes = "application/json")
	public ResponseEntity<UserDTO> getUsernameFromToken(@RequestBody Map<String, Object> payload) {
		UserDTO userDTO = new UserDTO();
		String username = jwtTokenUtil.getUsernameFromToken(payload.get("token").toString());
		try {
			User user = userService.findByUsername(username);
			ValidatorUser.validate(user);
			userDTO = UserMapper.mapUserToDto(user);
			ValidatorUserDTO.validate(userDTO);
			userDTO.setPassword("");
		} catch (UserException e) {
			e.printStackTrace();
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/add", consumes = "application/json")
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
	
	@PutMapping(value="/profil" , consumes = "application/json")
	public ResponseEntity<UserDTO> updateSelf(@RequestBody UserDTO userDTO) {
		try {
			ValidatorUserDTO.validate(userDTO);
			if(userDTO.getUserId() != null) {
				Optional<User> foundUser = userService.findById(new Long(userDTO.getUserId()));
				if(foundUser.isPresent()) {
					if(userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
						userDTO.setPassword(foundUser.get().getPassword());
					}
					userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
					if(!foundUser.get().getRole().getName().equals("admin") &&
							!userDTO.getRole().getName().equalsIgnoreCase(foundUser.get().getRole().getName())) {
						return new ResponseEntity<UserDTO>(HttpStatus.UNAUTHORIZED);
					}
				} else {
					return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
				}
			} else {
				throw new UserException("UserDTO id is null !");
			}
			User user = UserMapper.mapDtoToUser(userDTO);
			ValidatorUser.validate(user);
			userService.edit(user);
		} catch (UserException e) {
			e.printStackTrace();
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserDTO>(HttpStatus.CREATED);
	}

	private void authenticate(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new DisabledException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", e);
		}
	}
}