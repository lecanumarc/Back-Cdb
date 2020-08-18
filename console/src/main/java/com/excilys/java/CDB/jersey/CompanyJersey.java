package com.excilys.java.CDB.jersey;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.excilys.java.CDB.DTO.CompanyDTO;
import com.excilys.java.CDB.DTO.DashboardDTO;
import com.excilys.java.CDB.DTO.mapper.CompanyMapper;
import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.model.Pagination;

public class CompanyJersey {
	public static final String URL = "http://localhost:8080/api";

	public static Company findbyID(Long idCompany) {
		Client client = ClientBuilder.newClient();
		CompanyDTO response = client.target(URL).path("/companies/" + idCompany)
				.request(MediaType.APPLICATION_JSON_TYPE).get(CompanyDTO.class);

		return CompanyMapper.mapDtoToCompany(response);
	}

	public static boolean existCompany(Long idCompany) {
		boolean res = false;
		Client client = ClientBuilder.newClient();
		Response response = client.target(URL).path("/companies/" + idCompany).request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		if (response.getStatusInfo().equals(Status.OK)) {
			res = true;
		}
		return res;
	}

	public static int countCompany() {
		DashboardDTO dashboardDTO = new DashboardDTO();
		Client client = ClientBuilder.newClient();
		String response = client.target(URL).path("/companies/number").request()
				.post(Entity.entity(dashboardDTO, MediaType.APPLICATION_JSON_TYPE), String.class);
		return Integer.parseInt(response);
	}

	public static List<Company> getListPage(Pagination page) {
		DashboardDTO dashboardDTO = new DashboardDTO();
		dashboardDTO.setPageNb(page.getCurrentPage() + "");
		dashboardDTO.setLinesNb(page.getLinesPage() + "");
		Client client = ClientBuilder.newClient();
		List<CompanyDTO> response = client.target(URL).path("/companies/page").request().post(
				Entity.entity(dashboardDTO, MediaType.APPLICATION_JSON_TYPE), new GenericType<List<CompanyDTO>>() {
				});
		return response.stream().map(CompanyMapper::mapDtoToCompany).collect(Collectors.toList());
	}

	public static void deleteCompany(Long idCompanyDelete) {
		Client client = ClientBuilder.newClient();
		client.target(URL).path("/companies/" + idCompanyDelete).request().delete();
	}

}
