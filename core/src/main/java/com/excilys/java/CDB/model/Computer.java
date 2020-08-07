package com.excilys.java.CDB.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class representing a computer
 * @author ninonV
 *
 */

@Entity
@Table(name = "computer")
public class Computer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) throws Exception {
		if (introduced != null && this.discontinued != null && introduced.isAfter(this.discontinued)) {
			throw new Exception("Introduced date is after discontinued date");
		}
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) throws Exception {
		if (discontinued != null && this.introduced != null && discontinued.isBefore(this.introduced)) {
			throw new Exception("Discontinued date is before introduced date");
		}
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append(" id :").append(this.id);
		sb.append(", name :").append(this.name);
		sb.append(", introduced :").append(this.introduced);
		sb.append(", discontinued :").append(this.discontinued).append(" ");
		sb.append(this.company);
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Computer other = (Computer) obj;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	  public static class Builder {
			private Long id;
			private String name;
			private LocalDate introduced; 
			private LocalDate discontinued;
			private Company company;

	        public Builder setId(Long id) {
	            this.id = id;
	            return this;
	        }

	        public Builder setName(String name) {
	            this.name = name;
	            return this;
	        }
	        
	        public Builder setIntroduced(LocalDate introduced) {
	            this.introduced = introduced;
	            return this;
	        }
	        
	        public Builder setDiscontinued(LocalDate discontinued) {
	            this.discontinued = discontinued;
	            return this;
	        }
	        
	        public Builder setCompany(Company company) {
	            this.company = company;
	            return this;
	        }

	        public Computer build() {
	            Computer computer = new Computer();
	            computer.id = this.id;
	            computer.name = this.name;
	            computer.introduced = this.introduced;
	            computer.discontinued = this.discontinued;
	            computer.company = this.company;
	            return computer;
	        }
	    }
	
}

