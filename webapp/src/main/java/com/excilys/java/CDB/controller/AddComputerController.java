package com.excilys.java.CDB.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.java.CDB.DTO.CompanyDTO;
import com.excilys.java.CDB.DTO.ComputerDTO;
import com.excilys.java.CDB.DTO.mapper.CompanyMapper;
import com.excilys.java.CDB.DTO.mapper.ComputerMapper;
import com.excilys.java.CDB.exception.ComputerDateException;
import com.excilys.java.CDB.exception.ComputerNameException;
import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.model.Computer;
import com.excilys.java.CDB.service.CompanyService;
import com.excilys.java.CDB.service.ComputerService;
import com.excilys.java.CDB.validator.ValidatorComputer;

@Controller
@RequestMapping("/AddComputer")
public class AddComputerController {

	private static Logger logger = LoggerFactory.getLogger(AddComputerController.class);

	@Autowired
	private CompanyService companyService; 
	@Autowired
	private ComputerService computerService;  
	
	@GetMapping
	public ModelAndView computerInfo() {
		ModelAndView modelView = new ModelAndView("addComputer"); 
		
		List<Company> companies = companyService.listCompanies();
		List<CompanyDTO> companiesDTO = companies.stream().map(company->  CompanyMapper.mapCompanyToDTO(company)).collect(Collectors.toList());
	
		modelView.addObject("listCompanies", companiesDTO);
		return modelView; 
	}
	
	@PostMapping
	public ModelAndView addComputer(ComputerDTO computerDTO, CompanyDTO companyDTO) {
		ModelAndView modelView = new ModelAndView("addComputer");
		
		Map<String, String> errors = new HashMap<String, String>();
		String resultCreation;
	
		try {
			ValidatorComputer.validatorName(computerDTO.getComputerName());
			ValidatorComputer.validatorDate(computerDTO.getIntroduced(), computerDTO.getDiscontinued());
		}catch ( ComputerNameException e ) {
            errors.put( "name", e.getMessage() );
        }catch ( ComputerDateException e ) {
            errors.put( "discontinued", e.getMessage());
        }
		
		if (errors.isEmpty()) {
			computerDTO.setCompanyDTO(companyDTO);
			Computer computer = ComputerMapper.mapDtoToComputer(computerDTO);
			computerService.createComputer(computer);
			resultCreation = "Computer added with success.";
			logger.info("Computer added with success.");
		}else {
			resultCreation = "Impossible to add this computer.";
			logger.info("Impossible to add this computer.");
		}
		
		modelView.addObject("errors", errors );
		modelView.addObject("resultCreation", resultCreation);
		
		return modelView; 
	}
	
}
