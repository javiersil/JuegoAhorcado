/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import jpaController.JugadorJpaController;
import modelo.Jugador;

/**
 *
 * @author cosma_000
 */
@WebServlet(name = "JugadorBD", urlPatterns = {"/JugadorBD"})
public class JugadorBD extends HttpServlet {
 @PersistenceUnit EntityManagerFactory emf;
    @Resource UserTransaction utx;
      private static Jugador jugadores;

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
        response.setContentType("text/html;charset=UTF-8");
     
        try (PrintWriter out = response.getWriter()) {
             HttpSession sesion= request.getSession(); 
       
        emf = Persistence.createEntityManagerFactory("JuegoPU");
            
  JugadorJpaController jugadorC = new JugadorJpaController(utx,emf);
  Jugador jugador  = (Jugador)sesion.getAttribute("jugador");
  String ju =  request.getParameter("nombre").toUpperCase();
  if(jugador==null){
 
  
  
jugadores  =  jugadorC.findJugadorNombre(ju);

if(jugadores != null)
   sesion.setAttribute("jugador",jugadores);
 
else{

            try {
                     
           Jugador j= new Jugador();
             j.setNombre(ju);
                jugadorC.create(j);
                sesion.setAttribute("jugador",j);
            } catch (Exception ex) {
             
            }
}
  }
  
   Jugador jugadorS  = (Jugador)sesion.getAttribute("jugador");  
  //request.getRequestDispatcher("/Juego/mostrar.jsp").forward(request, response);
 
response.sendRedirect("/Juego/mostrar.jsp");
           
            
            /* TODO output your page here. You may use following sample code. */
           /* out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Jugador</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Jugador " + jugadorS.getNombre() + "</h1>");
          
            out.println("<br/><a href='mostrar.jsp'>jugar</a>");
            out.println("</body>");
            out.println("</html>");*/
        }
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
