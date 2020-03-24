package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.impl.MembreServiceImpl;

public class MembreDeleteServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        if (action.equals("/membre_delete")){
            MembreService membreService = MembreServiceImpl.getInstance();
            int id = -1;
            if (req.getParameter("id") != null){    
                id = Integer.parseInt(req.getParameter("id"));
            }

            try {
                if (id != -1){
                    req.setAttribute("membre", membreService.getById(id));
                    req.setAttribute("id", id);
                } else{

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/View/membre_delete.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MembreService membreService = MembreServiceImpl.getInstance();

        try {
            membreService.delete(Integer.parseInt(req.getParameter("id")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/membre_list");
    }
    
}