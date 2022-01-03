/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_21008007.server;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Brandon
 */
@WebServlet(name = "Stats", urlPatterns = {"/jack/stats"})
public class Stats extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        ServletContext context = request.getServletContext();
//        response.setContentType("text/plain;charset=UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            PrintWriter out = response.getWriter();
            try {
                Integer gamesPlayed = (Integer) context.getAttribute("gamesPlayed");
                Integer userWin = (Integer) context.getAttribute("userWin");
                float winningPercentage = userWin * 100f / gamesPlayed;

                out.println(Integer.toString(gamesPlayed) + ' ' + Integer.toString(userWin) + ' ' + Float.toString(winningPercentage));
//                out.println(userWin);
//                out.println(winningPercentage);

            } finally {
                out.close();
            }
        }
//        Integer gamesPlayed = (Integer) context.getAttribute("gamesPlayed");
//        Integer userWin;
//        if (gamesPlayed > 0) {
//            userWin = (Integer) context.getAttribute("userWin");
//            int winPercentage = userWin / gamesPlayed;
//            context.setAttribute("winPercentage", winPercentage);
//            PrintWriter out = response.getWriter();
//            out.println(winPercentage);
//        }
        // Actual logic goes here.
//        String winPercentage = Integer.toString(userWin / gamesPlayed) + "%";
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
