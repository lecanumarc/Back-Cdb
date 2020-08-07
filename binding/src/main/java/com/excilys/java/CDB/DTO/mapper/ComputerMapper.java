package com.excilys.java.CDB.DTO.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.CDB.DTO.CompanyDTO;
import com.excilys.java.CDB.DTO.ComputerDTO;
import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.model.Computer;

/**
 * Mapping an object to a computer
 * @author ninonV
 *
 */

public class ComputerMapper {
	
	private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);
	
	/**
	 * Convert a ComputerDTO to a Computer
	 * @param computerDTO
	 * @return computer
	 */
	public static Computer mapDtoToComputer(ComputerDTO computerDTO){
		Computer computer = new Computer();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		try {
			Long id = null;
			if(computerDTO.getComputerId()!=null) {
				id=Long.valueOf(computerDTO.getComputerId());
			}
			String name = computerDTO.getComputerName();
	    	LocalDate introduced = null;
	    	if (!computerDTO.getIntroduced().equals(null) && !computerDTO.getIntroduced().isEmpty()) {
	    		introduced = LocalDate.parse(computerDTO.getIntroduced(), formatter);
	    	}
	    	LocalDate discontinued = null; 
	    	if (!computerDTO.getDiscontinued().equals(null) && !computerDTO.getDiscontinued().isEmpty()) {
	    		discontinued = LocalDate.parse(computerDTO.getDiscontinued(), formatter);
	    	}
	    	
	    	Long company_id = Long.valueOf(computerDTO.getCompanyDTO().getCompanyId());
	    	String company_name = computerDTO.getCompanyDTO().getCompanyName();
	    	
	    	computer.setId(id);
	    	computer.setName(name);
			computer.setIntroduced(introduced);
			computer.setDiscontinued(discontinued);
	    	computer.setCompany(new Company.Builder().setId(company_id).setName(company_name).build());
	    	
		} catch (Exception e) {
			logger.error("Error when mapping a ComputerDTO to a Computer",e);
		}
		return computer;
	}
	

	/**
	 * Convert a Computer to a ComputerDTO
	 * @param computer
	 * @return computerDTO
	 */
	public static ComputerDTO mapComputerToDTO(Computer computer){
		ComputerDTO computerDTO = new ComputerDTO();
		if(computer.getId()!=null) {
			computerDTO.setComputerId(computer.getId().toString());
		}
		if(computer.getName()!=null) {
			computerDTO.setComputerName(computer.getName());
		}
		if(computer.getIntroduced()!=null) {
			computerDTO.setIntroduced(computer.getIntroduced().toString());
		}
		if(computer.getDiscontinued()!=null) {
			computerDTO.setDiscontinued(computer.getDiscontinued().toString());
		}
		if(computer.getCompany()!=null && computer.getCompany().getId()!=null){ 
			CompanyDTO companyDTO = new CompanyDTO.Builder()
					.setCompanyId(computer.getCompany().getId().toString())
					.setCompanyName(computer.getCompany().getName())
					.build();
			computerDTO.setCompanyDTO(companyDTO);
		}
		return computerDTO;
	}

}
