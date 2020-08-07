package com.excilys.java.CDB.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.java.CDB.DTO.CompanyDTO;
import com.excilys.java.CDB.DTO.DashboardDTO;
import com.excilys.java.CDB.DTO.mapper.CompanyMapper;
import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.model.Pagination;
import com.excilys.java.CDB.service.CompanyService;

@RestController
@RequestMapping("companies")
public class CompanyRestController {

	@Autowired
	private CompanyService companyService;

	@GetMapping(value = { "", "/" })
	public List<CompanyDTO> listCompanies() {
		List<Company> companies = companyService.listCompanies();
		return companies.stream().map(company -> CompanyMapper.mapCompanyToDTO(company)).collect(Collectors.toList());
	}
	
	@GetMapping("/page")
	public List<CompanyDTO> listCompanies(@RequestBody DashboardDTO dashboardDTO) {
		Pagination page = new Pagination();
        
        if(dashboardDTO.getLinesNb()!=null) {
			int linesNb= Integer.parseInt(dashboardDTO.getLinesNb());
			page.setLinesPage(linesNb);
		}
        
		int total = companyService.countCompany();
		int nbPages = page.getTotalPages(total);
		
		if(dashboardDTO.getPageNb()!=null) {
			int pageAsked = Integer.parseInt(dashboardDTO.getPageNb());
			if (pageAsked>0 & pageAsked <= nbPages) {
				page.setCurrentPage(pageAsked);
			}
		}
		
		page.setFirstLine(page.calculFirstLine());

		List<Company> companies = companyService.getListPage(page);
		return companies.stream().map(company->CompanyMapper.mapCompanyToDTO(company)).collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public CompanyDTO findById(@PathVariable Long id) {
		CompanyDTO companyDTO = new CompanyDTO();
		if (companyService.existCompany(id)) {
			companyDTO = CompanyMapper.mapCompanyToDTO(companyService.findbyID(id));
		}
		return companyDTO; 
	}
	
	@DeleteMapping("/{id}")
	public HttpStatus deleteById(@PathVariable Long id) {
		if (companyService.existCompany(id)) {
			companyService.deleteCompany(id);
            return HttpStatus.OK;
		}
		else {
			return HttpStatus.NOT_FOUND;
		}
	}
	
}
