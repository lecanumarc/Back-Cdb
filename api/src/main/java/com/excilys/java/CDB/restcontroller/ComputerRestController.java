package com.excilys.java.CDB.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.java.CDB.DTO.CompanyDTO;
import com.excilys.java.CDB.DTO.ComputerDTO;
import com.excilys.java.CDB.DTO.DashboardDTO;
import com.excilys.java.CDB.DTO.mapper.ComputerMapper;
import com.excilys.java.CDB.exception.ComputerDateException;
import com.excilys.java.CDB.exception.ComputerNameException;
import com.excilys.java.CDB.model.Computer;
import com.excilys.java.CDB.model.Pagination;
import com.excilys.java.CDB.service.ComputerService;
import com.excilys.java.CDB.validator.ValidatorComputer;

@RestController
@RequestMapping("computers")
public class ComputerRestController {

	@Autowired
	private ComputerService computerService;

	@GetMapping({"", "/"})
	public List<ComputerDTO> allComputers() {
		List<Computer> computers = computerService.listComputers();
		return computers.stream().map(computer -> ComputerMapper.mapComputerToDTO(computer)).collect(Collectors.toList());
	}
	
	@GetMapping("/page")
	public List<ComputerDTO> listComputers(@RequestBody DashboardDTO dashboardDTO) {
		Pagination page = new Pagination();
        
        if(dashboardDTO.getLinesNb()!=null) {
			int linesNb= Integer.parseInt(dashboardDTO.getLinesNb());
			page.setLinesPage(linesNb);
		}
        
		int total = computerService.countComputer(dashboardDTO.getSearch());
		int nbPages = page.getTotalPages(total);
		
		if(dashboardDTO.getPageNb()!=null) {
			int pageAsked = Integer.parseInt(dashboardDTO.getPageNb());
			if (pageAsked>0 & pageAsked <= nbPages) {
				page.setCurrentPage(pageAsked);
			}
		}
		
		page.setFirstLine(page.calculFirstLine());

		List<Computer> computers = computerService.getListPage(page,dashboardDTO.getSearch(),dashboardDTO.getOrder());
		return computers.stream().map(computer->ComputerMapper.mapComputerToDTO(computer)).collect(Collectors.toList());
		
	}
	
	@GetMapping("/number")
	public int numberComputers(@RequestBody DashboardDTO dashboardDTO) {
		return computerService.countComputer(dashboardDTO.getSearch());
	}

	@GetMapping("/{id}")
	public ComputerDTO findById(@PathVariable Long id) {
		ComputerDTO computerDTO = new ComputerDTO();
		if (computerService.existComputer(id)) {
			computerDTO = ComputerMapper.mapComputerToDTO(computerService.findbyID(id));
		}
		return computerDTO; 
	}
	
	@DeleteMapping("/{id}")
	public HttpStatus deleteById(@PathVariable Long id) {
		if (computerService.existComputer(id)) {
			computerService.deleteComputer(id);
            return HttpStatus.OK;
		}
		else {
			return HttpStatus.NOT_FOUND;
		}
	}
	
	@PostMapping({ "/" })
	public HttpStatus createComputer(@RequestBody ComputerDTO computerDTO) {
		try {
			ValidatorComputer.validatorName(computerDTO.getComputerName());
			ValidatorComputer.validatorDate(computerDTO.getIntroduced(), computerDTO.getDiscontinued());
			computerService.createComputer(ComputerMapper.mapDtoToComputer(computerDTO));
		}catch ( ComputerNameException | ComputerDateException  e ) {
			return HttpStatus.BAD_REQUEST;
		}
		return HttpStatus.CREATED;
	}
	
	@PutMapping("/")
	public HttpStatus updateComputer(@RequestBody ComputerDTO computerDTO) {
		try {
			ValidatorComputer.validatorName(computerDTO.getComputerName());
			ValidatorComputer.validatorDate(computerDTO.getIntroduced(), computerDTO.getDiscontinued());
		}catch ( ComputerNameException | ComputerDateException  e ) {
			return HttpStatus.BAD_REQUEST;
		}
		
		Computer computer = ComputerMapper.mapDtoToComputer(computerDTO);
		if(computerService.existComputer(computer.getId())) {
			computerService.updateComputer(computer);
			return HttpStatus.ACCEPTED;
		}else {
			return HttpStatus.NOT_FOUND;
		}
		
	}
	

}
