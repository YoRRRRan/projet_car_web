package routing;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Plateforme;

import java.io.IOException;
import java.sql.SQLException;

import dataaccess.PlateformeDAO;

/**
 * Servlet implementation class NewPlateformes
 */
public class NewPlateformes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewPlateformes() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Plateforme plateforme = null;
		String message = "";

		String plateformeId = request.getParameter("plateformeId");
		if (plateformeId == null) {
			// Create an empty plateforme so that the page is empty
			plateforme = new Plateforme();
		} else {
			// Get the plateforme from the DAO
			try {
				plateforme = PlateformeDAO.getPlateformebyPlateformeId(Integer.parseInt(plateformeId));
			} catch (Exception e) {
				message = "oops";
			}
		}
		// Put plateforme in the request for the next page
		request.setAttribute("plateforme", plateforme);
		request.setAttribute("message", message);
		getServletContext().getRequestDispatcher("/WEB-INF/newplateforme.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "";
		Plateforme plateforme = (Plateforme) request.getAttribute("plateforme");
		plateforme.setNom(request.getParameter("nom"));
		plateforme.setDescription(request.getParameter("description"));

		try {
			if (plateforme.getPlateformeId() > 0) {
				// We already have a plateforme_Id so do an update
				PlateformeDAO.updatePlateforme(plateforme);
				message = "plateforme updated";
			} else {
				PlateformeDAO.insertPlateforme(plateforme);
				message = "plateforme created";
			}
		} catch (SQLException e) {
			message = "Enter a new plateforme nom.";
		}

		request.setAttribute("message", message);
		request.setAttribute("plateforme", plateforme);
		getServletContext().getRequestDispatcher("/WEB-INF/newplateforme.jsp").forward(request, response);
	}
}