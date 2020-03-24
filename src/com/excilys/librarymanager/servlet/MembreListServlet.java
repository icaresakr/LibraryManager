package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.impl.MembreServiceImpl;

public class MembreListServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        if (action.equals("/membre_list")){
            MembreService membreService = MembreServiceImpl.getInstance();
            try {
                req.setAttribute("membreList", membreService.getList());
            } catch (Exception e) {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/View/membre_list.jsp");
            dispatcher.forward(req, resp);
        }
    }

    
}