/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.transaction.UserTransaction;
import jpaController.PalabrasJpaController;
import modelo.Palabras;

/**
 *
 * @author cosma_000
 */
@WebServlet(name = "AgregarPalabra", urlPatterns = {"/AgregarPalabra"})
public class AgregarPalabra extends HttpServlet {

    @PersistenceUnit EntityManagerFactory emf;
    @Resource UserTransaction utx;
    
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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
       
        try (PrintWriter out = response.getWriter()) {
            
            /* TODO output your page here. You may use following sample code. */
            emf = Persistence.createEntityManagerFactory("JuegoPU");
            String rp=request.getParameter("palabra").toUpperCase();
            String tp=request.getParameter("tipo").toUpperCase();
            PalabrasJpaController palabras = new PalabrasJpaController(utx,emf);
            
            Palabras p= palabras.findPalabraNombre(rp);
            
             out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Palabra</title>");            
            out.println("</head>");
            out.println("<body>");
            
            if( p == null){
                 System.out.println("si");
            Palabras palabra= new Palabras();
            palabra.setTipopal(tp);
              palabra.setAdivinada(0);
              palabra.setGenerada(0);
            palabra.setPalabra(rp);
           
              palabras.create(palabra); 
              
                  out.println("<h3>Palabra agregada: </h3><p>"+palabra.getPalabra()+"</p>");
            }else{
            out.println("<h3>la palabra ya esta agregada: </h3><p>"+p.getPalabra()+"</p>");
            }
                  
             
           
              out.println("<a  href='agregarPal.jsp'>Agregar mas palabras</a>");
               out.println("<a  href='index.jsp'>Ir a inicio</a>");
          
            out.println("</body>");
            out.println("</html>");
                
           
              out.close();
         
          
          
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AgregarPalabra.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AgregarPalabra.class.getName()).log(Level.SEVERE, null, ex);
        }
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
