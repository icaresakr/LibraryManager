package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;

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
public class EmpruntAddServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        
        if (action.equals("/emprunt_add")){
            MembreService membreService = MembreServiceImpl.getInstance();
            try {
                request.setAttribute("availableMembreList", membreService.getListMembreEmpruntPossible());
            } catch (Exception e) {
                e.printStackTrace();
            }

            LivreService bookService = LivreServiceImpl.getInstance();
            try {
                request.setAttribute("availableBookList", bookService.getListDispo());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Submit gathered information th the appropriate .jsp:
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        EmpruntService loanService = EmpruntServiceImpl.getInstance();

        try {
            if (request.getParameter("idMembre") == null || request.getParameter("idLivre") == null){
                throw new ServletException("Cant add a new loan, some data hasn't been received");
            } else{
                loanService.create(Integer.parseInt(request.getParameter("idMembre")), Integer.parseInt(request.getParameter("idLivre")), LocalDate.now());
						
				request.setAttribute("loanList", loanService.getListCurrent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        MembreService membreService = MembreServiceImpl.getInstance();        
        try {
            request.setAttribute("availableMembreList", membreService.getListMembreEmpruntPossible());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LivreService bookService = LivreServiceImpl.getInstance();     
        try {
            request.setAttribute("availableBookList", bookService.getListDispo());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/emprunt_list");
    }
}