package com.excilys.java.CDB.DTO;

public class DashboardDTO {

	private String search = "";
	private String ascOrder = "true";
	private String column = "id";
	private String pageNb = "1";
	private String linesNb = "20";
	
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}	
	public String getAscOrder() {
		return ascOrder;
	}
	public void setAscOrder(String ascOrder) {
		this.ascOrder = ascOrder;
	}	
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getPageNb() {
		return pageNb;
	}
	public void setPageNb(String pageNb) {
		this.pageNb = pageNb;
	}
	public String getLinesNb() {
		return linesNb;
	}
	public void setLinesNb(String linesNb) {
		this.linesNb = linesNb;
	} 
	
	public void setPage(DashboardDTO dashboardDTO) {
		if(dashboardDTO.getSearch()!=null) {
        	this.setSearch(dashboardDTO.getSearch());
		}
        if(dashboardDTO.getAscOrder()!=null) {
        	this.setAscOrder(dashboardDTO.getAscOrder());
        }
        if(dashboardDTO.getColumn()!=null) {
        	this.setColumn(dashboardDTO.getColumn());
        }
        if(dashboardDTO.getPageNb()!=null) {
        	this.setPageNb(dashboardDTO.getPageNb());
        }
        if(dashboardDTO.getLinesNb()!=null) {
        	this.setLinesNb(dashboardDTO.getLinesNb());
        }
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append(" search :").append(this.search);
		sb.append(", ascOrder :").append(this.ascOrder);
		sb.append(", column :").append(this.column);
		sb.append(", page NUmber :").append(this.pageNb);
		sb.append(", lines Number :").append(this.linesNb);
		return sb.toString();
	}

	
}
