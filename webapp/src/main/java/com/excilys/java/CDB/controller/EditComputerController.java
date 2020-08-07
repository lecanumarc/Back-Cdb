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
import org.springframework.web.servlet.view.RedirectView;

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
@RequestMapping("/EditComputer")
public class EditComputerController {

	private static Logger logger = LoggerFactory.getLogger(EditComputerController.class);

	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;

	@GetMapping
	public ModelAndView computerInfo(ComputerDTO computerDTO) {
		ModelAndView modelView = new ModelAndView("editComputer");
		Computer computer = new Computer();

		if (computerDTO.getComputerId() != null) {
			computer = computerService.findbyID(Long.parseLong(computerDTO.getComputerId()));
		}

		if (computer != null) {
			computerDTO = ComputerMapper.mapComputerToDTO(computer);
		} else {
			logger.info("The computer does not exist");
		}

		List<Company> companies = companyService.listCompanies();
		List<CompanyDTO> companiesDTO = companies.stream().map(company -> CompanyMapper.mapCompanyToDTO(company)).collect(Collectors.toList());

		modelView.addObject("listCompanies", companiesDTO);
		modelView.addObject("computer", computerDTO);
		return modelView;
	}

	@PostMapping
	public RedirectView addComputer(ComputerDTO computerDTO, CompanyDTO companyDTO) {
		ModelAndView modelView = new ModelAndView("editComputer");
		
		Map<String, String> errors = new HashMap<String, String>();
	
		try {
			ValidatorComputer.validatorName(computerDTO.getComputerName());
			ValidatorComputer.validatorDate(computerDTO.getIntroduced(), computerDTO.getDiscontinued());
		}catch ( ComputerNameException e ) {
            errors.put( "computerName", e.getMessage() );
        }catch ( ComputerDateException e ) {
            errors.put( "discontinued", e.getMessage());
        }
		
		if (errors.isEmpty()) {
			computerDTO.setCompanyDTO(companyDTO);
			Computer computer = ComputerMapper.mapDtoToComputer(computerDTO);
			computerService.updateComputer(computer);
			logger.info("Computer updated with success.");
			return new RedirectView("/webapp/");
		}else {
			logger.info("Impossible to update this computer.");
			
			modelView.addObject("errors", errors );
			return new RedirectView("redirect:/webapp/editComputer?computer=" + computerDTO);
		}
	}
}
