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

import com.excilys.java.CDB.DTO.CompanyDTO;
import com.excilys.java.CDB.DTO.DashboardDTO;
import com.excilys.java.CDB.DTO.mapper.CompanyMapper;
import com.excilys.java.CDB.exception.CompanyException;
import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.service.CompanyService;
import com.excilys.java.CDB.validator.ValidatorCompany;
import com.excilys.java.CDB.validator.ValidatorCompanyDTO;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("companies")
public class CompanyRestController {

	private DashboardDTO page = new DashboardDTO();
	private CompanyService companyService;

	@Autowired
	public CompanyRestController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping(value = { "", "/" })
	public ResponseEntity<List<CompanyDTO>> listCompanies() {
		List<Company> companies = companyService.listCompanies();

		List<CompanyDTO> companiesDto = companies.stream().map(company -> CompanyMapper.mapCompanyToDTO(company)).collect(Collectors.toList());

		return new ResponseEntity<List<CompanyDTO>>(companiesDto, HttpStatus.OK);
	}

	@GetMapping("/page")
	public ResponseEntity<List<CompanyDTO>> listCompanies(@RequestBody DashboardDTO dashboardDTO) {

		page.setPage(dashboardDTO);

		PageRequest pageReq = PageRequest.of(Integer.parseInt(page.getPageNb()),
				Integer.parseInt(page.getLinesNb()),
				companyService.sortBy(page.getColumn(), Boolean.valueOf(page.getAscOrder())));

		Page<Company> companies = companyService.listByPage(page.getSearch(), pageReq);
		List<CompanyDTO> companiesDto = companies.stream().map(company->CompanyMapper.mapCompanyToDTO(company)).collect(Collectors.toList());

		return new ResponseEntity<List<CompanyDTO>>(companiesDto, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompanyDTO> findById(@PathVariable Long id) {
		CompanyDTO companyDTO = null;
		Optional<Company> company = companyService.findById(id);
		if (company.isPresent()) {
			companyDTO = CompanyMapper.mapCompanyToDTO(company.get());
		}
		return new ResponseEntity<CompanyDTO>(companyDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CompanyDTO> deleteById(@PathVariable Long id) {
		companyService.delete(id);
		return new ResponseEntity<CompanyDTO>(HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<CompanyDTO>  createComputer(@RequestBody CompanyDTO companyDTO) {
		try {
			ValidatorCompanyDTO.validate(companyDTO);
			Company company = CompanyMapper.mapDtoToCompany(companyDTO);
			ValidatorCompany.validate(company);
			companyService.add(company);
		} catch (CompanyException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<CompanyDTO>(HttpStatus.OK);
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<CompanyDTO>  updateCompany(@RequestBody CompanyDTO companyDTO) {
		try {
			ValidatorCompanyDTO.validate(companyDTO);
			Company company = CompanyMapper.mapDtoToCompany(companyDTO);
			ValidatorCompany.validate(company);
			companyService.edit(company);
		} catch (CompanyException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<CompanyDTO>(HttpStatus.OK);
		
	}
}
