/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.Dao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Users;

/**
 *
 * @author admin
 */
public class Login extends HttpServlet {
    private Dao dao;
    public void init(){
        dao = new Dao();
    }
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Login</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //processRequest(request, response);
        request.getRequestDispatcher("/Views/Login.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //processRequest(request, response);
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null || password == null
            || email.isEmpty() || password.isEmpty()) {
        request.setAttribute("error", "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
        request.getRequestDispatcher("/Views/Login.jsp")
               .forward(request, response);
        return;
    }


        Users u = dao.login(email, password);
         if (u == null) {
        request.setAttribute("error","Sai tài khoản hoặc mật khẩu. Vui lòng kiểm tra lại!");
        request.getRequestDispatcher("/Views/Login.jsp").forward(request, response);
        return;
    }

    String role = u.getRole();

    if (role == null) {
        request.setAttribute("error", "Tài khoản chưa được phân quyền");
        request.getRequestDispatcher("/Views/Login.jsp").forward(request, response);
        return;
    }

    HttpSession session = request.getSession();
    session.setAttribute("u", u);

    if ("ADMIN".equalsIgnoreCase(role)) {
        response.sendRedirect(request.getContextPath() + "/Admin");
    } else if ("STUDENT".equalsIgnoreCase(role)) {
        response.sendRedirect(request.getContextPath() + "/Student");
    } else if ("TUTOR".equalsIgnoreCase(role)) {
        response.sendRedirect(request.getContextPath() + "/Tutor");
    } else {
        session.removeAttribute("u");
        request.setAttribute("error", "Role trong database không hợp lệ: " + role);
        request.getRequestDispatcher("/Views/Login.jsp").forward(request, response);
    }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
