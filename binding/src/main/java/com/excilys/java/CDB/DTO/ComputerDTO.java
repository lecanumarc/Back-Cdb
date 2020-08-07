package com.excilys.java.CDB.DTO;

public class ComputerDTO {
	
	private String computerId;
	private String computerName;
	private String introduced; 
	private String discontinued;
	private CompanyDTO companyDTO;

	public String getComputerId() {
		return computerId;
	}

	public void setComputerId(String id) {
		this.computerId = id;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String name) {
		this.computerName = name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}

	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.companyDTO = companyDTO;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append(" id :").append(this.computerId);
		sb.append(", name :").append(this.computerName);
		sb.append(", introduced :").append(this.introduced);
		sb.append(", discontinued :").append(this.discontinued).append(" ");
		sb.append(this.companyDTO);
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((computerId == null) ? 0 : computerId.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((companyDTO == null) ? 0 : companyDTO.hashCode());
		result = prime * result + ((computerName == null) ? 0 : computerName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComputerDTO other = (ComputerDTO) obj;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (computerId == null) {
			if (other.computerId != null)
				return false;
		} else if (!computerId.equals(other.computerId))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (companyDTO == null) {
			if (other.companyDTO != null)
				return false;
		} else if (!companyDTO.equals(other.companyDTO))
			return false;
		if (computerName == null) {
			if (other.computerName != null)
				return false;
		} else if (!computerName.equals(other.computerName))
			return false;
		return true;
	}
	
	public static class Builder {
		private String computerId;
		private String computerName;
		private String introduced; 
		private String discontinued;
		private CompanyDTO companyDTO;

        public Builder setComputerId(String id) {
            this.computerId = id;
            return this;
        }

        public Builder setComputerName(String name) {
            this.computerName = name;
            return this;
        }
        
        public Builder setIntroduced(String introduced) {
            this.introduced = introduced;
            return this;
        }
        
        public Builder setDiscontinued(String discontinued) {
            this.discontinued = discontinued;
            return this;
        }
        
        public Builder setCompanyDTO(CompanyDTO company) {
            this.companyDTO = company;
            return this;
        }

        public ComputerDTO build() {
            ComputerDTO computerDTO = new ComputerDTO();
            computerDTO.computerId = this.computerId;
            computerDTO.computerName = this.computerName;
            computerDTO.introduced = this.introduced;
            computerDTO.discontinued = this.discontinued;
            computerDTO.companyDTO = this.companyDTO;
            return computerDTO;
        }
    }
	
}
