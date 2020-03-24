package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.impl.LivreServiceImpl;


public class LivreListServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        if (action.equals("/livre_list")){
            LivreService bookService = LivreServiceImpl.getInstance();
            try {
                req.setAttribute("bookList", bookService.getList());
            } catch (Exception e) {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/View/livre_list.jsp");
            dispatcher.forward(req, resp);
        }
    }

    
}