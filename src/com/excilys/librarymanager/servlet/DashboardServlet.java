package com.excilys.librarymanager.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.impl.EmpruntServiceImpl;
import com.excilys.librarymanager.service.impl.LivreServiceImpl;
import com.excilys.librarymanager.service.impl.MembreServiceImpl;

import java.io.IOException;

public class DashboardServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * Function responsible for showing the system dashboard and showing the values
     * of membres, books and loans
     */
    @Override
    protected void doGet (final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String action = request.getServletPath();

        if (action.equals("/dashboard")) {
            final MembreService membreService = MembreServiceImpl.getInstance();
            try {
                request.setAttribute("numberOfMembres", membreService.count());
            } catch (final Exception e) {
                e.printStackTrace();
            }

            final LivreService bookService = LivreServiceImpl.getInstance();
            try {
                request.setAttribute("numberOfBooks", bookService.count());
            } catch (final Exception e) {
                e.printStackTrace();
            }

            final EmpruntService loanService = EmpruntServiceImpl.getInstance();
            try {
                request.setAttribute("numberOfLoans", loanService.count());
                request.setAttribute("currentLoans", loanService.getListCurrent());
            } catch (final Exception e) {
                e.printStackTrace();
            }

            // Submit gathered information th the appropriate .jsp:
            final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
            dispatcher.forward(request, response);
            
        }


    }
    
}