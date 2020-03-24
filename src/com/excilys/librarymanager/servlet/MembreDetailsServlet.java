package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.model.Abonnement;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.impl.EmpruntServiceImpl;
import com.excilys.librarymanager.service.impl.MembreServiceImpl;

public class MembreDetailsServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        if (action.equals("/membre_details")){
            MembreService membreService = MembreServiceImpl.getInstance();
            EmpruntService loanService = EmpruntServiceImpl.getInstance();

            try {
                req.setAttribute("membre", membreService.getById(Integer.parseInt(req.getParameter("id"))));
            } catch (Exception e) {
                new ServletException("Cant get the chosen membre", e);
            }

            try {
                req.setAttribute("currentByMembre", loanService.getListCurrentByMembre(Integer.parseInt(req.getParameter("id"))));
            } catch (Exception e) {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/View/membre_details.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MembreService membreService = MembreServiceImpl.getInstance();
        EmpruntService loanService = EmpruntServiceImpl.getInstance();

        try {
            if (req.getParameter("prenom") == "" || req.getParameter("prenom") == null || req.getParameter("nom") == "" || req.getParameter("nom") == null){
                throw new ServletException("First or Last names are empties!");
            } else{
                Membre adding = membreService.getById(Integer.parseInt(req.getParameter("id")));
                adding.setPrenom(req.getParameter("prenom"));
                adding.setNom(req.getParameter("nom"));
                adding.setEmail(req.getParameter("email"));
                adding.setTel(req.getParameter("telephone"));
                if (req.getParameter("abonnement").equals("BASIC")){
                    adding.setAbo(Abonnement.BASIC);
                } else if (req.getParameter("abonnement").equals("PREMIUM")){
                    adding.setAbo(Abonnement.PREMIUM);
                } else if (req.getParameter("abonnement").equals("VIP")){
                    adding.setAbo(Abonnement.VIP);
                } 
                membreService.update(adding);
                req.setAttribute("id", adding.getId());
                req.setAttribute("currentByMembre", loanService.getListCurrentByMembre(adding.getId()));
                
                
                resp.sendRedirect(req.getContextPath() + "/membre_details?id=" + adding.getId());
            }
        } catch (Exception e) {
            new ServletException("Error in sending update!", e);
            req.setAttribute("errorMessage", "Empty Parameter");
        }

    }
    
}