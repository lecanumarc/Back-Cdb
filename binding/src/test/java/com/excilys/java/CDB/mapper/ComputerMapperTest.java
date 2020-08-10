package com.excilys.java.CDB.mapper;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.excilys.java.CDB.DTO.CompanyDTO;
import com.excilys.java.CDB.DTO.ComputerDTO;
import com.excilys.java.CDB.DTO.mapper.CompanyMapper;
import com.excilys.java.CDB.DTO.mapper.ComputerMapper;
import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.model.Computer;

public class ComputerMapperTest {


	@Test
	public void validMapDtoToComputer() {
		CompanyDTO companyDTO = new CompanyDTO.Builder()
				.setCompanyId("1")
				.setCompanyName("Apple")
				.build();
		ComputerDTO computerDTO = new ComputerDTO.Builder()
				.setComputerId("1")
				.setComputerName("MacBook Pro 15.4 inch")
				.setIntroduced(LocalDate.now().toString())
				.setDiscontinued(LocalDate.now().plusYears((long) 1).toString())
				.setCompanyDTO(companyDTO)
				.build();
		Computer computer = ComputerMapper.mapDtoToComputer(computerDTO);
		Company company = new Company.Builder()
				.setId(new Long(1))
				.setName("Apple")
				.build();
		assertEquals("computer's id is incorrect", new Long(1), computer.getId());
		assertEquals("computer's name is incorrect", "MacBook Pro 15.4 inch", computer.getName());
		assertEquals("computer's introduced date is incorrect", LocalDate.now(), computer.getIntroduced());
		assertEquals("computer's discontinued date is incorrect", LocalDate.now().plusYears(1), computer.getDiscontinued());
		assertEquals("computer's company is incorrect", company, computer.getCompany());
	}

	@Test()
	public void mapDtoToComputerWithNullDto() {
		ComputerDTO computerDTO =null;
		Computer computer = ComputerMapper.mapDtoToComputer(computerDTO);
		assertEquals("computer should be null", null, computer);
	}

	@Test()
	public void validMapComputerToDTO() {
		Company company = new Company.Builder()
				.setId( new Long(1))
				.setName("Apple")
				.build();
		
		CompanyDTO companyDTO= CompanyMapper.mapCompanyToDTO(company);
		
		Computer computer = new Computer.Builder()
									.setId( new Long(1))
									.setName("MacBook Pro 15.4 inch")
									.setIntroduced(LocalDate.now())
									.setDiscontinued(LocalDate.now().plusYears(1))
									.setCompany(company)
									.build();
		
		ComputerDTO computerDTO = ComputerMapper.mapComputerToDTO(computer);
		
		assertEquals("computerDTO id is incorrect", "1", computerDTO.getComputerId());
		assertEquals("computerDTO name is incorrect", "MacBook Pro 15.4 inch", computerDTO.getComputerName());
		assertEquals("computerDTO introduced date is incorrect", LocalDate.now().toString(), computerDTO.getIntroduced());
		assertEquals("computerDTO discontinued date is incorrect", LocalDate.now().plusYears(1).toString(), computerDTO.getDiscontinued());
		assertEquals("computerDTO company is incorrect", companyDTO, computerDTO.getCompanyDTO());
	}
	
}
