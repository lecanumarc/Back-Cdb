package com.excilys.java.CDB.DTO;

public class DashboardDTO {

	private String search;
	private String order = "id ASC";
	private String pageNb;
	private String linesNb;
	
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append(" search :").append(this.search);
		sb.append(", order :").append(this.order);
		sb.append(", page NUmber :").append(this.pageNb);
		sb.append(", lines Number :").append(this.linesNb);
		return sb.toString();
	}
	
}
