/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_21008007.server;

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
 * @author BabyK
 */
@WebServlet(name = "Won", urlPatterns = {"/jack/won"})
public class Won extends HttpServlet {

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
        processRequest(request, response);
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
//        processRequest(request, response);
        response.setContentType("text/plain;charset=UTF-8");
        //set context and grab variables from context
        ServletContext context = request.getServletContext();
        ArrayList deckOfCards = (ArrayList) context.getAttribute("deckOfCards");
        ArrayList playerHand = (ArrayList) context.getAttribute("playerHand");
        ArrayList dealerHand = (ArrayList) context.getAttribute("dealerHand");
        String whoseTurn = (String) context.getAttribute("whoseTurn");
        String winner = (String) context.getAttribute("winner");
        Integer playerCount = (Integer) context.getAttribute("playerCount");
        Integer dealerCount = (Integer) context.getAttribute("dealerCount");
        Integer dealerCountAceEleven = (Integer) context.getAttribute("dealerCountAceEleven");
        Integer playerCountAceEleven = (Integer) context.getAttribute("playerCountAceEleven");
        Integer playerFinalCount = (Integer) context.getAttribute("playerFinalCount");
        Integer dealerFinalCount = (Integer) context.getAttribute("dealerFinalCount");

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);

        }

        //winner logic, and set in context
        //lose
        if (playerFinalCount > 21) {
            winner = "computer";
            context.setAttribute("winner", winner);

        } //draw
        else if (playerFinalCount.equals(dealerFinalCount) && playerFinalCount <= 21) {
            winner = "draw";
            context.setAttribute("winner", winner);
        } //win
        else if (playerFinalCount <= 21 && playerFinalCount > dealerFinalCount) {
            winner = "user";
            Integer userWin = (Integer) context.getAttribute("userWin");
            userWin++;
            context.setAttribute("winner", winner);
            context.setAttribute("userWin", userWin);

        } //lose
        else if (dealerFinalCount <= 21 && dealerFinalCount > playerFinalCount) {
            winner = "computer";
            context.setAttribute("winner", winner);
        } else if (dealerFinalCount > 21) {
            winner = "user";
            Integer userWin = (Integer) context.getAttribute("userWin");
            userWin += 1;
            context.setAttribute("winner", winner);
            context.setAttribute("userWin", userWin);
        }
        int newGame = 0;
        Integer gamesPlayed = (Integer) context.getAttribute("gamesPlayed");

        if (gamesPlayed == null) {
            context.setAttribute("gamesPlayed", newGame);
            context.setAttribute("userWin", 0);

        } else {
            gamesPlayed++;
            context.setAttribute("gamesPlayed", gamesPlayed);
        }
        request.getRequestDispatcher("/blackjack.jsp").forward(request, response);

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
