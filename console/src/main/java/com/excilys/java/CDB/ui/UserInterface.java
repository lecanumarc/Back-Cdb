
package com.excilys.java.CDB.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.excilys.java.CDB.jersey.CompanyJersey;
import com.excilys.java.CDB.jersey.ComputerJersey;
import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.model.Computer;
import com.excilys.java.CDB.model.Pagination;

/**
 * Class doing the relation with the user
 * 
 * @author ninonV
 **/

@Component
public class UserInterface {

	/**
	 * Main menu that calls the different functions
	 */

	public void start() {

		boolean quit = false;
		while (!quit) {
			try {
				System.out.println("Features : ");
				System.out.println("\t 1- List computers");
				System.out.println("\t 2- List companies");
				System.out.println("\t 3- Show computer details");
				System.out.println("\t 4- Create a computer");
				System.out.println("\t 5- Update a computer");
				System.out.println("\t 6- Delete a computer");
				System.out.println("\t 7- Delete a company");
				System.out.println("\t 8- Quit");

				System.out.println("What do you want to do ? ");
				Scanner sc = new Scanner(System.in);
				String choice = sc.nextLine();
				int choiceInt = Integer.parseInt(choice);

				switch (choiceInt) {
				case 1:
					pagesComputers();
					break;

				case 2:
					pagesCompanies();
					break;

				case 3:
					Long idC = getID();
					if (ComputerJersey.existComputer(idC)) {
						Computer computerID = ComputerJersey.findbyID(idC);
						System.out.println(computerID);
					} else {
						System.out.println("This computer does not exist");
					}
					break;

				case 4:
					System.out.println("What is the computer that you want to create : ");
					Computer computerCreate = infoComputer();
					ComputerJersey.createComputer(computerCreate);
					System.out.println("Computer created with success");
					break;

				case 5:
					System.out.println("Which computer do you want to update: ");
					Long idComputer = getID();
					if (ComputerJersey.existComputer(idComputer)) {
						Computer computerIdUpdate = ComputerJersey.findbyID(idComputer);
						System.out.println(computerIdUpdate);
						System.out.println("Complete the new informations : ");
						Computer computerUpdate = infoComputer();
						// TO DO : use builder
						// computerUpdate.setId(idComputer);
						ComputerJersey.updateComputer(computerUpdate);
						System.out.println("Computer updated with success");
					} else {
						System.out.println("This computer does not exist");
					}
					break;

				case 6:
					System.out.println("Which computer do you want to delete: ");
					Long idComputerDelete = getID();
					if (ComputerJersey.existComputer(idComputerDelete)) {
						Computer computerIdDelete = ComputerJersey.findbyID(idComputerDelete);
						System.out.println(computerIdDelete);
						ComputerJersey.deleteComputer(idComputerDelete);
						System.out.println("Computer deleted with success");
					} else {
						System.out.println("This computer does not exist");
					}
					break;
				case 7:
					System.out.println("Which company do you want to delete: ");
					Long idCompanyDelete = getID();
					if (CompanyJersey.existCompany(idCompanyDelete)) {
						Company companyIdDelete = CompanyJersey.findbyID(idCompanyDelete);
						System.out.println(companyIdDelete);
						CompanyJersey.deleteCompany(idCompanyDelete);
						System.out.println("Company ans computers of the company deleted with success");

					} else {
						System.out.println("This company does not exist");
					}
					break;
				case 8:
					quit = true;
					break;
				default:
					System.out.println("Select a choice between 1 and 7");
				}
			} catch (java.lang.NumberFormatException e) {
				System.err.println("Enter a number between 1 and 7");
			}

		}
	}

	/**
	 * Get the id of the computer
	 * 
	 * @return Long id
	 */
	public Long getID() {

		System.out.println("ID : ");
		Scanner sc = new Scanner(System.in);
		String idC = sc.nextLine();
		Long id = null;

		try {
			id = Long.valueOf(idC);
		} catch (java.lang.NumberFormatException e) {
			System.err.println("Enter a number");
		}
		return id;
	}

	/**
	 * Create a new computer or enter the new informations IF the conditions are
	 * checked
	 * 
	 * @return Computer
	 */
	public Computer infoComputer() {
		Scanner sc = new Scanner(System.in);
		Boolean conditionsOK = false;
		String name = null;
		String intro = "";
		LocalDate introduced = null;
		String discon = "";
		LocalDate discontinued = null;
		String id = "";
		Long idCompany = null;
		Company company = null;
		Computer computer = null;
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

		while (!conditionsOK) {
			System.out.println("Name : ");
			name = sc.nextLine();

			System.out.println("Date of introduction (YYYY-MM-DD) : ");
			intro = sc.nextLine();
			if (intro.length() > 0) {
				try {
					introduced = LocalDate.parse(intro, formatter);
				} catch (java.time.format.DateTimeParseException e) {
					System.err.println("Enter the following format YYYY-MM-DD");
				}
			}

			System.out.println("Date of discontinuation (YYYY-MM-DD) : ");
			discon = sc.nextLine();
			if (discon.length() > 0) {
				try {
					discontinued = LocalDate.parse(discon, formatter);
				} catch (java.time.format.DateTimeParseException e) {
					System.err.println("Enter the following format YYYY-MM-DD");
				}
			}

			System.out.println("ID of the company : ");
			try {
				id = sc.nextLine();
				if (id.length() > 0) {
					idCompany = Long.valueOf(id);
					company = CompanyJersey.findbyID(idCompany);
				}
			} catch (java.lang.NumberFormatException e) {
				System.err.println("Enter a number");
				company = null;
			}

			computer = new Computer.Builder().setName(name).setIntroduced(introduced).setDiscontinued(discontinued)
					.setCompany(company).build();
			System.out.println(computer.getCompany());
			conditionsOK = allowCreation(computer);
		}
		return computer;
	}

	/**
	 * Method that show the pages related to the computers
	 */

	public void pagesComputers() {
		Pagination page = new Pagination();
		int total = ComputerJersey.countComputer();
		int nbPages = page.getTotalPages(total);
		boolean quitPage = false;

		while (!quitPage) {
			List<Computer> computers = ComputerJersey.getListPage(page);
			System.out.println(computers);
			quitPage = menuPage(page, nbPages);
		}
	}

	/**
	 * Method that show the pages related to the companies
	 */

	public void pagesCompanies() {
		Pagination page = new Pagination();

		int total = CompanyJersey.countCompany();
		int nbPages = page.getTotalPages(total);
		boolean quitPage = false;

		while (!quitPage) {
			List<Company> companies = CompanyJersey.getListPage(page);
			System.out.println(companies);
			quitPage = menuPage(page, nbPages);
		}
	}

	/**
	 * Show the menu of a page and calls the function of the pages
	 * 
	 * @param Pagination page, int nbPages
	 * @return boolean
	 */
	public boolean menuPage(Pagination page, int nbPages) {
		boolean quitPage = false;

		System.out.println("Page " + page.getCurrentPage() + "/" + nbPages);
		System.out.println("n : Next page   -   p : Previous page  -  s : Select page   -   q : Quit");

		try {
			Scanner sc = new Scanner(System.in);
			String choice = sc.nextLine();

			switch (choice) {
			case "n":
				if (page.getCurrentPage() == nbPages) {
					System.out.println("\n No more page ! \n");
				} else {
					page.nextPage();
				}
				break;

			case "p":
				if (page.getCurrentPage() == 1) {
					System.out.println("\n No previous page ! \n");
				} else {
					page.previousPage();
				}
				break;

			case "s":
				System.out.println("Number of the page : ");
				String nb = sc.nextLine();
				int newPage = page.getCurrentPage();
				try {
					newPage = Integer.parseInt(nb);
					if (newPage > 0 && newPage <= nbPages) {
						page.setCurrentPage(newPage);
						page.setFirstLine(page.getLinesPage() * (page.getCurrentPage() - 1) + 1);
					} else {
						System.out.println("Number incorrect");
					}
				} catch (java.lang.NumberFormatException e) {
					System.err.println("Enter a number");
				}
				break;

			case "q":
				quitPage = true;
				break;
			default:
				System.out.println("Enter one of the previous instructions");
			}
		} catch (java.lang.NumberFormatException e) {
			System.err.println("Enter a letter or a page followed by a number");
		}
		return quitPage;
	}

	public boolean allowCreation(Computer computer) {
		boolean creationAuthorized = true;
		if (computer.getName().length() == 0) {
			System.err.println("The name is mandatory");
			creationAuthorized = false;
		} else if (computer.getIntroduced() != null && computer.getDiscontinued() != null
				&& computer.getDiscontinued().isBefore(computer.getIntroduced())) {
			System.err.println("Introduced date must be before discontinued date");
			creationAuthorized = false;
		}
		if (computer.getCompany().getId() != 0) {
			if (!CompanyJersey.existCompany(computer.getCompany().getId())) {
				System.err.println("The company should exist");
				creationAuthorized = false;
			}
		}
		return creationAuthorized;
	}

}
