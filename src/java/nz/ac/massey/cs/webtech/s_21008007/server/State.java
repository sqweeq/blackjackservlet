/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_21008007.server;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static java.util.Collections.list;
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
 * @author BabyK
 */
@WebServlet(name = "State", urlPatterns = {"/jack/state"})
public class State extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        ServletContext context = request.getServletContext();
        ArrayList deckOfCards = (ArrayList) context.getAttribute("deckOfCards");
        ArrayList playerHand = (ArrayList) context.getAttribute("playerHand");
        ArrayList dealerHand = (ArrayList) context.getAttribute("dealerHand");
        Integer gamesPlayed = (Integer) context.getAttribute("gamesPlayed");
        String whoseTurn = (String) context.getAttribute("whoseTurn");
        String winner = (String) context.getAttribute("winner");
        Integer playerCount = (Integer) context.getAttribute("playerCount");
        Integer dealerCount = (Integer) context.getAttribute("dealerCount");
        Integer dealerCountAceEleven = (Integer) context.getAttribute("dealerCountAceEleven");
        Integer playerCountAceEleven = (Integer) context.getAttribute("playerCountAceEleven");
        Integer playerFinalCount = (Integer) context.getAttribute("playerFinalCount");
        Integer dealerFinalCount = (Integer) context.getAttribute("dealerFinalCount");
        Integer userWin = (Integer) context.getAttribute("userWin");
        Integer winPercentage = (Integer) context.getAttribute("winPercentage");

        request.setAttribute("userWin", userWin);
        request.setAttribute("winPercentage", winPercentage);
        request.setAttribute("gamesPlayed", gamesPlayed);
        request.setAttribute("deckOfCards", deckOfCards);
        request.setAttribute("playerHand", playerHand);
        request.setAttribute("dealerHand", dealerHand);
        request.setAttribute("whoseTurn", whoseTurn);
        request.setAttribute("winner", winner);
        request.setAttribute("playerCount", playerCount);
        request.setAttribute("dealerCount", dealerCount);
        request.setAttribute("dealerCountAceEleven", dealerCountAceEleven);
        request.setAttribute("playerCountAceEleven", playerCountAceEleven);
        request.setAttribute("playerFinalCount", playerFinalCount);
        request.setAttribute("dealerFinalCount", dealerFinalCount);
        Gson gson = new Gson();
        ArrayList jsonToSend = new ArrayList<>();
        jsonToSend.add(deckOfCards);
        jsonToSend.add(playerHand);
        jsonToSend.add(dealerHand);
        jsonToSend.add(playerFinalCount);
        jsonToSend.add(dealerFinalCount);
        jsonToSend.add(whoseTurn);
        jsonToSend.add(winner);
        jsonToSend.add(gamesPlayed);
        jsonToSend.add(userWin);

//        String jsonCards = gson.toJson(deckOfCards);
//        String jsonPlayer = gson.toJson(playerHand);
//        String jsonDealer = gson.toJson(dealerHand);
        String jsonData = gson.toJson(jsonToSend);

        PrintWriter out = response.getWriter();
        PrintWriter out1 = null;

        try {
//            out.println(jsonCards);
//            out.println(jsonPlayer);
//            out.println(jsonDealer);
            out.println(jsonData);

            String realPath = context.getRealPath(request.getContextPath());
            out1 = new PrintWriter(new FileWriter(realPath + "/stats.json"));
            out1.write(jsonData);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(realPath + "stats.json"));

        } finally {
            out.close();
        }
        request.getRequestDispatcher("/blackjack.jsp").forward(request, response);
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
