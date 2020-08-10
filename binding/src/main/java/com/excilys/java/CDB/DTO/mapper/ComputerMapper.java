package com.excilys.java.CDB.DTO.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.CDB.DTO.CompanyDTO;
import com.excilys.java.CDB.DTO.ComputerDTO;
import com.excilys.java.CDB.exception.ComputerException;
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
		Computer computer = null;
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		try {
			if(computerDTO != null) {
				Computer.Builder builder =new Computer.Builder();
				if(computerDTO.getComputerId()!=null) {
					builder.setId(Long.valueOf(computerDTO.getComputerId()));
				}
				if(computerDTO.getComputerName()!=null) {
					builder.setName(computerDTO.getComputerName());
				}
				if (!computerDTO.getIntroduced().equals(null) && !computerDTO.getIntroduced().isEmpty()) {
					builder.setIntroduced(LocalDate.parse(computerDTO.getIntroduced(), formatter));
				}
				if (!computerDTO.getDiscontinued().equals(null) && !computerDTO.getDiscontinued().isEmpty()) {
					builder.setDiscontinued(LocalDate.parse(computerDTO.getDiscontinued(), formatter));
				}
				if(computerDTO.getCompanyDTO() != null) {
					Company.Builder companyBuilder = new Company.Builder();
					if(computerDTO.getCompanyDTO().getCompanyId() != null) {
						companyBuilder.setId(Long.valueOf(computerDTO.getCompanyDTO().getCompanyId()));
					}
					if(computerDTO.getCompanyDTO().getCompanyName() != null) {
						companyBuilder.setName(computerDTO.getCompanyDTO().getCompanyName());
					}
					builder.setCompany(companyBuilder.build());
				}
				computer = builder.build();
			} else {
				throw new ComputerException("computerDTO can't be null");
			}
		} catch (ComputerException e) {
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
		ComputerDTO computerDTO = null;
		try {
			if(computer != null) {
				ComputerDTO.Builder builder = new ComputerDTO.Builder();
				if(computer.getId()!=null) {
					builder.setComputerId(computer.getId().toString());
				}
				if(computer.getName()!=null) {
					builder.setComputerName(computer.getName());
				}
				if(computer.getIntroduced()!=null) {
					builder.setIntroduced(computer.getIntroduced().toString());
				}
				if(computer.getDiscontinued()!=null) {
					builder.setDiscontinued(computer.getDiscontinued().toString());
				}
				if(computer.getCompany()!=null && computer.getCompany().getId()!=null){ 
					CompanyDTO companyDTO = new CompanyDTO.Builder()
							.setCompanyId(computer.getCompany().getId().toString())
							.setCompanyName(computer.getCompany().getName())
							.build();
					builder.setCompanyDTO(companyDTO);
				}
				computerDTO = builder.build();
			} else {
				throw new ComputerException("computer can't be null");
			}
		} catch (ComputerException e) {
			logger.error("Error when mapping a ComputerDTO to a Computer",e);
		}
		return computerDTO;
	}

}
