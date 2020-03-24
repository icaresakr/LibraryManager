package com.excilys.librarymanager.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.impl.EmpruntServiceImpl;

public class EmpruntListServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String action = request.getServletPath();

		if (action.equals("/emprunt_list")) {
			final EmpruntService loanService = EmpruntServiceImpl.getInstance();

			try {
				if (request.getParameter("show") != null && request.getParameter("show").contains("all")) {
					request.setAttribute("loanList", loanService.getList());
					request.setAttribute("show", "all");
				} else {
					request.setAttribute("loanList", loanService.getListCurrent());
					request.setAttribute("show", "current");
				}

			} catch (final Exception e) {
				e.printStackTrace();
			}

			// Submit gathered information th the appropriate .jsp:
			final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
            dispatcher.forward(request, response);

        }
    } 
    
}