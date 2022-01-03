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
 * @author Brandon
 */
@WebServlet(name = "Stand", urlPatterns = {"/jack/move/stand"})
public class Stand extends HttpServlet {

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

        response.setContentType("text/html;charset=UTF-8");
        ServletContext context = request.getServletContext();

        ArrayList deckOfCards = (ArrayList) context.getAttribute("deckOfCards");
        ArrayList playerHand = (ArrayList) context.getAttribute("playerHand");
        ArrayList dealerHand = (ArrayList) context.getAttribute("dealerHand");
        Integer dealerFinalCount = (Integer) context.getAttribute("dealerFinalCount");

        String userTurn = "dealer";
        int gamesPlayed = (int) context.getAttribute("gamesPlayed");

        int playerCount = 0;
        int dealerCount = 0;
        int dealerCountAceEleven = 0;

        for (int i = 0; i < playerHand.size(); i++) {
            String card = (String) playerHand.get(i);
            int cardToInt = Integer.parseInt(card.split(" ", 0)[0]);
            if (cardToInt >= 10) {
                playerCount += 10;
            } else {
                playerCount += cardToInt;
            }

        }

        for (int i = 0; i < dealerHand.size(); i++) {
            String card = (String) dealerHand.get(i);
            int cardToInt = Integer.parseInt(card.split(" ", 0)[0]);
            if (cardToInt >= 10) {
                dealerCount += 10;
                dealerCountAceEleven += 10;

            } else if (cardToInt == 1) {
                dealerCount++;
                dealerCountAceEleven += 11;
            } else {
                dealerCount += cardToInt;
                dealerCountAceEleven += cardToInt;

            }
        }

        while (dealerCount <= 17 && playerCount <= 21) {
            if (dealerCountAceEleven > 17 && dealerCountAceEleven <= 21) {
                break;
            }

            dealerHand.add(deckOfCards.get(0));
            deckOfCards.remove(0);
            dealerCount = 0;
            dealerCountAceEleven = 0;
            for (int i = 0; i < dealerHand.size(); i++) {
                String card = (String) dealerHand.get(i);
                int cardToInt = Integer.parseInt(card.split(" ", 0)[0]);
                if (cardToInt >= 10) {
                    dealerCount += 10;
                    dealerCountAceEleven += 10;

                } else if (cardToInt == 1) {
                    dealerCount++;
                    dealerCountAceEleven += 11;
                } else {
                    dealerCount += cardToInt;
                    dealerCountAceEleven += cardToInt;
                }
            }
        }
        //404 Not Found
        //If there is not
        //active game
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);

        }

        //dealer count with aces logic
        if (dealerCountAceEleven > 21 && dealerCount <= 21) {
            dealerFinalCount = dealerCount;
        } else if (dealerCountAceEleven <= 21) {
            dealerFinalCount = dealerCountAceEleven;
        } else if (dealerCount > 21) {
            dealerFinalCount = dealerCount;
        }
        //update on server
        context.setAttribute("deckOfCards", deckOfCards);
        context.setAttribute("playerHand", playerHand);
        context.setAttribute("dealerHand", dealerHand);
        context.setAttribute("whoseTurn", userTurn);
        context.setAttribute("dealerCount", dealerCount);
        context.setAttribute("dealerCountAceEleven", dealerCountAceEleven);
        context.setAttribute("dealerFinalCount", dealerFinalCount);

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
