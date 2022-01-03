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
@WebServlet(name = "Hit", urlPatterns = {"/jack/move/hit"})
public class Hit extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        ServletContext context = request.getServletContext();
        ArrayList deckOfCards = (ArrayList) context.getAttribute("deckOfCards");
        ArrayList playerHand = (ArrayList) context.getAttribute("playerHand");
        ArrayList dealerHand = (ArrayList) context.getAttribute("dealerHand");
        String userTurn = (String) context.getAttribute("whoseTurn");
        Integer playerFinalCount = (Integer) context.getAttribute("playerFinalCount");
        int gamesPlayed = (int) context.getAttribute("gamesPlayed");

        int playerCount = 0;
        int playerCountAceEleven = 0;

        int dealerCount = 0;
        for (int i = 0; i < playerHand.size(); i++) {
            String card = (String) playerHand.get(i);
            int cardToInt = Integer.parseInt(card.split(" ", 0)[0]);
            if (cardToInt >= 10) {
                playerCount += 10;
                playerCountAceEleven += 10;

            } else if (cardToInt == 1) {
                playerCount++;
                playerCountAceEleven += 11;
            } else {
                playerCount += cardToInt;
                playerCountAceEleven += cardToInt;
            }
        }

        for (int i = 0; i < dealerHand.size(); i++) {
            String card = (String) dealerHand.get(i);
            int cardToInt = Integer.parseInt(card.split(" ", 0)[0]);
            if (cardToInt >= 10) {
                dealerCount += 10;
            } else if (cardToInt == 1 && dealerCount + 11 > 21) {
                dealerCount++;
            } else if (cardToInt == 1 && dealerCount + 11 <= 21) {
                dealerCount += 11;
            } else {
                dealerCount += cardToInt;
            }
        }

        //404 Not Found
        //If there is not
        //active game
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);

        } //400 Bad Request
        //If it is not the user’s
        //turn or user’s hand
        //is already bust
        //(user hand greater
        //than 21)
        else if (playerCount > 21 || !userTurn.equals("user")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            //add a card to player
            playerHand.add(deckOfCards.get(0));
            deckOfCards.remove(0);
            playerCount = 0;
            dealerCount = 0;
            playerCountAceEleven = 0;

            for (int i = 0; i < playerHand.size(); i++) {
                String card = (String) playerHand.get(i);
                int cardToInt = Integer.parseInt(card.split(" ", 0)[0]);
                if (cardToInt >= 10) {
                    playerCount += 10;
                    playerCountAceEleven += 10;

                } else if (cardToInt == 1) {
                    playerCount++;
                    playerCountAceEleven += 11;
                } else {
                    playerCount += cardToInt;
                    playerCountAceEleven += cardToInt;
                }
            }

            for (int i = 0; i < dealerHand.size(); i++) {
                String card = (String) dealerHand.get(i);
                int cardToInt = Integer.parseInt(card.split(" ", 0)[0]);
                if (cardToInt >= 10) {
                    dealerCount += 10;
                } else if (cardToInt == 1 && dealerCount + 11 > 21) {
                    dealerCount++;
                } else if (cardToInt == 1 && dealerCount + 11 <= 21) {
                    dealerCount += 11;
                } else {
                    dealerCount += cardToInt;
                }
            }
            //player count with aces logic
            if (playerCountAceEleven > 21 && playerCount <= 21) {
                playerFinalCount = playerCount;
            } else if (playerCountAceEleven <= 21) {
                playerFinalCount = playerCountAceEleven;
            } else if (playerCount > 21) {
                playerFinalCount = playerCount;
            }
            //update on server
            context.setAttribute("deckOfCards", deckOfCards);
            context.setAttribute("playerHand", playerHand);
            context.setAttribute("playerCount", playerCount);
            context.setAttribute("playerCountAceEleven", playerCountAceEleven);
            context.setAttribute("playerFinalCount", playerFinalCount);

        }

//        processRequest(request, response);
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
