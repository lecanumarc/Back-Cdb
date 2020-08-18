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

import com.excilys.java.CDB.DTO.ComputerDTO;
import com.excilys.java.CDB.DTO.DashboardDTO;
import com.excilys.java.CDB.DTO.mapper.ComputerMapper;
import com.excilys.java.CDB.model.Computer;
import com.excilys.java.CDB.model.Pagination;

// https://speakerdeck.com/mickaelbaron/jax-rs-developper-des-services-web-rest-avec-java?slide=76

public class ComputerJersey {
	public static final String URL = "http://localhost:8080/api";

	public static boolean existComputer(Long idC) {
		boolean res = false;
		Client client = ClientBuilder.newClient();
		Response response = client.target(URL).path("/computers/" + idC).request(MediaType.APPLICATION_JSON_TYPE).get();
		if (response.getStatusInfo().equals(Status.OK)) {
			res = true;
		}
		return res;
	}

	public static Computer findbyID(Long idC) {
		Client client = ClientBuilder.newClient();
		ComputerDTO response = client.target(URL).path("/computers/" + idC).request(MediaType.APPLICATION_JSON_TYPE)
				.get(ComputerDTO.class);

		return ComputerMapper.mapDtoToComputer(response);
	}

	public static void createComputer(Computer computerCreate) {
		ComputerDTO computerDTO = ComputerMapper.mapComputerToDTO(computerCreate);
		Client client = ClientBuilder.newClient();
		client.target(URL).path("/computers").request()
				.post(Entity.entity(computerDTO, MediaType.APPLICATION_JSON_TYPE));
	}

	public static void updateComputer(Computer computerUpdate) {
		ComputerDTO computerDTO = ComputerMapper.mapComputerToDTO(computerUpdate);
		Client client = ClientBuilder.newClient();
		client.target(URL).path("/computers").request()
				.put(Entity.entity(computerDTO, MediaType.APPLICATION_JSON_TYPE));
	}

	public static void deleteComputer(Long idComputerDelete) {
		Client client = ClientBuilder.newClient();
		client.target(URL).path("/computers/" + idComputerDelete).request().delete();
	}

	public static int countComputer() {
		DashboardDTO dashboardDTO = new DashboardDTO();
		Client client = ClientBuilder.newClient();
		String response = client.target(URL).path("/computers/number").request()
				.post(Entity.entity(dashboardDTO, MediaType.APPLICATION_JSON_TYPE), String.class);
		return Integer.parseInt(response);
	}

	public static List<Computer> getListPage(Pagination page) {
		DashboardDTO dashboardDTO = new DashboardDTO();
		dashboardDTO.setPageNb(page.getCurrentPage() + "");
		dashboardDTO.setLinesNb(page.getLinesPage() + "");
		Client client = ClientBuilder.newClient();
		List<ComputerDTO> response = client.target(URL).path("/computers/page").request().post(
				Entity.entity(dashboardDTO, MediaType.APPLICATION_JSON_TYPE), new GenericType<List<ComputerDTO>>() {
				});
		return response.stream().map(ComputerMapper::mapDtoToComputer).collect(Collectors.toList());
	}
}
