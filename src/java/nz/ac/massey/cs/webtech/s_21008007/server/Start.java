/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_21008007.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
@WebServlet(name = "Start", urlPatterns = {"/jack/start"})
public class Start extends HttpServlet {

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
        HttpSession session = request.getSession();
        // spade S hearts H clubs C diamonds D
        ArrayList deckOfCards = new ArrayList<>();
        ArrayList playerHand = new ArrayList<>();
        ArrayList dealerHand = new ArrayList<>();
        String userTurn = "user";
        String winner = "none";
        int playerCount = 0;
        int playerCountAceEleven = 0;

        int dealerCount = 0;
        int dealerCountAceEleven = 0;
        int playerFinalCount = 0;
        int dealerFinalCount = 0;

        //create deck of 52 cards
        for (int i = 1; i < 14; i++) {
            deckOfCards.add(Integer.toString(i) + " S");
            deckOfCards.add(Integer.toString(i) + " H");
            deckOfCards.add(Integer.toString(i) + " C");
            deckOfCards.add(Integer.toString(i) + " D");
        }
        //shuffle deck
        Collections.shuffle(deckOfCards);

        //deal cards to player and dealer
        playerHand.add(deckOfCards.get(0));
        playerHand.add(deckOfCards.get(1));
        dealerHand.add(deckOfCards.get(2));
        dealerHand.add(deckOfCards.get(3));

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
                dealerCountAceEleven += 10;

            } else if (cardToInt == 1) {
                dealerCount++;
                dealerCountAceEleven += 11;
            } else {
                dealerCount += cardToInt;
                dealerCountAceEleven += cardToInt;

            }
        }
        //player count with aces logic
        if (playerCountAceEleven > 21 && playerCount <= 21) {
            playerFinalCount = playerCount;
        } else if (playerCountAceEleven <= 21) {
            playerFinalCount = playerCountAceEleven;
        }
        //dealer count with aces logic
        if (dealerCountAceEleven > 21 && dealerCount <= 21) {
            dealerFinalCount = dealerCount;
        } else if (dealerCountAceEleven <= 21) {
            dealerFinalCount = dealerCountAceEleven;
        }
        //remove those cards that were dealt from main deck
        for (int i = 0; i < 4; i++) {
            deckOfCards.remove(0);
        }

        //add to context
        response.setContentType("application/json;charset=UTF-8");
        ServletContext context = getServletContext();
        int newGame = 0;
        Integer gamesPlayed = (Integer) context.getAttribute("gamesPlayed");

        if (gamesPlayed == null) {
            context.setAttribute("gamesPlayed", newGame);
            context.setAttribute("userWin", 0);

        }
//        else {
//            gamesPlayed++;
//            context.setAttribute("gamesPlayed", gamesPlayed);
//        }

        context.setAttribute("deckOfCards", deckOfCards);
        context.setAttribute("playerHand", playerHand);
        context.setAttribute("dealerHand", dealerHand);
        context.setAttribute("playerCount", playerCount);
        context.setAttribute("dealerCount", dealerCount);
        context.setAttribute("dealerCountAceEleven", dealerCountAceEleven);
        context.setAttribute("playerCountAceEleven", playerCountAceEleven);
        context.setAttribute("playerFinalCount", playerFinalCount);
        context.setAttribute("dealerFinalCount", dealerFinalCount);
        context.setAttribute("whoseTurn", userTurn);
        context.setAttribute("winner", winner);

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
