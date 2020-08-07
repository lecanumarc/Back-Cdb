package com.excilys.java.CDB.DTO;

public class CompanyDTO {
	
	private String companyId; 
	private String companyName;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String id) {
		this.companyId = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String name) {
		this.companyName = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append(" id :").append(this.companyId);
		sb.append(", name :").append(this.companyName).append("\n");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
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
		CompanyDTO other = (CompanyDTO) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		return true;
	}
	
    public static class Builder {
        private String companyId;
        private String companyName;

        public Builder setCompanyId(String id) {
            this.companyId = id;
            return this;
        }

        public Builder setCompanyName(String name) {
            this.companyName = name;
            return this;
        }

        public CompanyDTO build() {
            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.companyId = this.companyId;
            companyDTO.companyName = this.companyName;
            return companyDTO;
        }
    }

}
