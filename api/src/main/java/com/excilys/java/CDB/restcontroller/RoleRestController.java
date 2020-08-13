package com.excilys.java.CDB.restcontroller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.excilys.java.CDB.DTO.RoleDTO;
import com.excilys.java.CDB.DTO.mapper.RoleMapper;
import com.excilys.java.CDB.exception.RoleException;
import com.excilys.java.CDB.model.Role;
import com.excilys.java.CDB.service.RoleService;
import com.excilys.java.CDB.validator.ValidatorRole;
import com.excilys.java.CDB.validator.ValidatorRoleDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("roles")
public class RoleRestController {
	private DashboardDTO page = new DashboardDTO();
	private RoleService roleService;

	@Autowired
	public RoleRestController(RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping({ "", "/" })
	public ResponseEntity<List<RoleDTO>> allRoles() {
		List<Role> users = roleService.listRoles();

		List<RoleDTO> usersDto = users.stream().map(role -> RoleMapper.mapRoleToDto(role))
				.collect(Collectors.toList());
		
		return new ResponseEntity<List<RoleDTO>>(usersDto, HttpStatus.OK);
	}

	@PostMapping("/page")
	public ResponseEntity<List<RoleDTO>> listRoles(@RequestBody DashboardDTO dashboardDTO) {
		page.setPage(dashboardDTO);
		PageRequest pageReq = PageRequest.of(Integer.parseInt(page.getPageNb()) - 1, Integer.parseInt(page.getLinesNb()),
				roleService.sortBy(page.getColumn(), Boolean.valueOf(page.getAscOrder())));

		Page<Role> users = roleService.listByPage(page.getSearch(), pageReq);
		List<RoleDTO> usersDto = users.stream().map(role -> RoleMapper.mapRoleToDto(role))
				.collect(Collectors.toList());

		return new ResponseEntity<List<RoleDTO>>(usersDto, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RoleDTO> findById(@PathVariable Long id) {
		RoleDTO userDTO = null;
		Optional<Role> role = roleService.findById(id);
		if (role.isPresent()) {
			userDTO = RoleMapper.mapRoleToDto(role.get());
		}
		return new ResponseEntity<RoleDTO>(userDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RoleDTO> deleteById(@PathVariable Long id) {
		roleService.delete(id);
		return new ResponseEntity<RoleDTO>(HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO userDTO) {
		try {
			ValidatorRoleDTO.validate(userDTO);
			Role role = RoleMapper.mapDtoToRole(userDTO);
			ValidatorRole.validate(role);
			roleService.add(role);
		} catch (RoleException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<RoleDTO>(HttpStatus.OK);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<RoleDTO> updateRole(@RequestBody RoleDTO userDTO) {
		try {
			ValidatorRoleDTO.validate(userDTO);
			Role role = RoleMapper.mapDtoToRole(userDTO);
			ValidatorRole.validate(role);
			roleService.edit(role);
		} catch (RoleException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<RoleDTO>(HttpStatus.OK);
	}
	
	@PostMapping("/number")
	public int numberRoles(@RequestBody DashboardDTO dashboardDTO) {
		page.setPage(dashboardDTO);
		return roleService.count(page.getSearch());
	}

}