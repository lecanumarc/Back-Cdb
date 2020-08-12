package com.excilys.java.CDB.restcontroller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
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

import com.excilys.java.CDB.DTO.ComputerDTO;
import com.excilys.java.CDB.DTO.DashboardDTO;
import com.excilys.java.CDB.DTO.mapper.ComputerMapper;
import com.excilys.java.CDB.exception.ComputerException;
import com.excilys.java.CDB.model.Computer;
import com.excilys.java.CDB.service.ComputerService;
import com.excilys.java.CDB.validator.ValidatorComputer;
import com.excilys.java.CDB.validator.ValidatorComputerDTO;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("computers")
public class ComputerRestController {
	private DashboardDTO page = new DashboardDTO();
	private ComputerService computerService;

	@Autowired
	public ComputerRestController(ComputerService computerService) {
		this.computerService = computerService;
	}
	
	@GetMapping({"", "/"})
	public ResponseEntity<List<ComputerDTO>> allComputers() {
		List<Computer> computers = computerService.listComputers();
		
		List<ComputerDTO> computersDto = computers.stream().map(computer -> ComputerMapper.mapComputerToDTO(computer)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ComputerDTO>>(computersDto, HttpStatus.OK);
	}
	
	@GetMapping("/page")
	public ResponseEntity<List<ComputerDTO>> listComputers(@RequestBody DashboardDTO dashboardDTO) {
		page.setPage(dashboardDTO);
		PageRequest pageReq = PageRequest.of(Integer.parseInt(page.getPageNb()),
				Integer.parseInt(page.getLinesNb()),
				computerService.sortBy(page.getColumn(), Boolean.valueOf(page.getAscOrder())));

		Page<Computer> computers = computerService.listByPage(page.getSearch(), pageReq);
		List<ComputerDTO> computersDto = computers.stream().map(computer -> ComputerMapper.mapComputerToDTO(computer)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ComputerDTO>>(computersDto, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ComputerDTO> findById(@PathVariable Long id) {
		ComputerDTO computerDTO = null;
		Optional<Computer> computer = computerService.findById(id);
		if (computer.isPresent()) {
			computerDTO = ComputerMapper.mapComputerToDTO(computer.get());
		}
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<ComputerDTO>(computerDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ComputerDTO> deleteById(@PathVariable Long id) {
		computerService.delete(id);
		return new ResponseEntity<ComputerDTO>(HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<ComputerDTO>  createComputer(@RequestBody ComputerDTO computerDTO) {
		try {
			ValidatorComputerDTO.validate(computerDTO);
			Computer computer = ComputerMapper.mapDtoToComputer(computerDTO);
			ValidatorComputer.validate(computer);
			computerService.add(computer);
		} catch (ComputerException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ComputerDTO>(HttpStatus.OK);
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<ComputerDTO> updateComputer(@RequestBody ComputerDTO computerDTO) {
		try {
			ValidatorComputerDTO.validate(computerDTO);
			Computer computer = ComputerMapper.mapDtoToComputer(computerDTO);
			ValidatorComputer.validate(computer);
			computerService.edit(computer);
		} catch (ComputerException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ComputerDTO>(HttpStatus.OK);
	}

	private String getToken() {
		
		return "";
	}
}
