/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
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
import jpaController.HistorialJpaController;
import jpaController.PalabrasJpaController;
import jpaController.exceptions.RollbackFailureException;
import modelo.Historial;
import modelo.Jugador;
import modelo.Palabras;

/**
 *
 * @author cosma_000
 */
@WebServlet(name = "Juego", urlPatterns = {"/Juego"})
public class Juego extends HttpServlet {
   @PersistenceUnit EntityManagerFactory emf;
    @Resource UserTransaction utx;
    private static List<Palabras> palabras;
    private static Palabras pal;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   /* private final static String[]
PALABRAS={"UNO","ABCD","OAXACA","HAMACA"};
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
            /* TODO output your page here. You may use following sample code. */
       
HttpSession sesion= request.getSession();

int maxint =0;
String nivel="";
String a = (String) sesion.getAttribute("int");
if(a == null){
 maxint=Integer.parseInt(request.getParameter("intentos"));
 sesion.setAttribute("int",(""+maxint )); 
/* if(maxint == 1){
 nivel = "MUY DIFICIL";
 }else if(maxint == 4){
 nivel = "DIFICIL";
 }
 else if(maxint == 6){
 nivel = "MEDIO";
 }
 else if(maxint == 10){
 nivel = "FACIL";
 }*/
 sesion.setAttribute("nivel",""+maxint); 
}else {
maxint = Integer.parseInt(a);
}


 

 
  
String palabra=(String) sesion.getAttribute("palabra");
String aciertos;
String errados;
Jugador j;
j= (Jugador) sesion.getAttribute("jugador");
if(palabra==null)
{ 
    
  emf = Persistence.createEntityManagerFactory("JuegoPU");
            
  PalabrasJpaController palab = new PalabrasJpaController(utx,emf);
  
  //palabras= palab.findPalabrasEntities();
  palabras= palab.findPalabraTipo(request.getParameter("tipo"));
  Random posi= new Random();
  Palabras t= palabras.get(0);
   for(int i=0 ; i< palabras.size(); i++){
   pal=palabras.get(i);
   if(t.getAdivinada()>= pal.getAdivinada()){
   t=pal;
   }
   }
 // pal=palabras.get(posi.nextInt(palabras.size()));
  palabra=t.getPalabra();
  
    try {
       
          emf = Persistence.createEntityManagerFactory("JuegoPU");            
            PalabrasJpaController palabras = new PalabrasJpaController(utx,emf);            
           pal.setGenerada(pal.getGenerada()+1);
          palabras.edit(pal);
          
    } catch (RollbackFailureException ex) {
        Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
        Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
    }
  
  
  
  if(palabra==null)
palabra = "OAXACA";
  
  
  sesion.setAttribute("pal",pal);
aciertos="";
errados="";
sesion.setAttribute("palabra",palabra);
sesion.setAttribute("aciertos",aciertos);
sesion.setAttribute("errados",errados);
}
else{
aciertos =(String) sesion.getAttribute("aciertos");
errados =(String) sesion.getAttribute("errados");
String letra= request.getParameter("letra");
if(palabra.indexOf(letra)>=0)
aciertos+=letra;
else
errados+=letra;
sesion.setAttribute("aciertos", aciertos);
sesion.setAttribute("errados", errados);
}
// VISTA
PrintWriter out = response.getWriter();
try { out.println("<html>");
out.println("<head>");
out.println("<title>Servlet juego ahorcado </title>");
out.println("</head>");
out.println("<body>");
out.println("<h2>S Juego: " + j.getNombre() + "</h2>");
out.println("<h2>Probando suerte </h2>");
out.println("<p>intentos: "+ (maxint - errados.length()) +"</p>");

boolean terminado = true;
out.println("<h2>");
for(int i=0;i<palabra.length();i++)
{ String letra= palabra.substring(i,i+1);
if(aciertos.indexOf(letra)>=0)
out.println(""+letra);
else
{ out.println(""+"_");
terminado=false;
}
}
out.println("</h2>");

if(maxint>errados.length())
{ out.println("<br/><br/><br/>");
for(char car='A';car<='Z';car++)
{
if(aciertos.indexOf(car)==-1 && errados.indexOf(car)==-1)
out.println("<a href=Juego?letra="+car+">"+car+"</a>");
}
}
else
{ 
    //
    
     pal = (Palabras)sesion.getAttribute("pal");
        //  emf = Persistence.createEntityManagerFactory("JuegoPU");            
            PalabrasJpaController palabras = new PalabrasJpaController(utx,emf);            
           pal.setAdivinada(pal.getAdivinada()+1);
            HistorialJpaController  hs = new HistorialJpaController(utx,emf);
        Historial hsj= new Historial();
        hsj.setIdjugador(j);
        hsj.setNivel(maxint);
        hsj.setIdpalabra(pal);
        hsj.setPuntuacion(0);
       

    try {
         hs.create(hsj);
        palabras.edit(pal);
    } catch (RollbackFailureException ex) {
        Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
        Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        
       
    
    sesion.invalidate();
out.println("<br /><h1> JUEGO TERMINADO </h1>");
out.println("<br /><a href='index.jsp'>regresar</a>");
}
if(terminado)
{ 

    try {
        //
        pal = (Palabras)sesion.getAttribute("pal");
        //  emf = Persistence.createEntityManagerFactory("JuegoPU");            
            PalabrasJpaController palabras = new PalabrasJpaController(utx,emf);            
           pal.setAdivinada(pal.getAdivinada()+1);
        palabras.edit(pal);
        
        
        HistorialJpaController  hs = new HistorialJpaController(utx,emf);
        Historial hsj= new Historial();
        hsj.setIdjugador(j);
        hsj.setNivel(maxint);
        hsj.setIdpalabra(pal);
        hsj.setPuntuacion(maxint-errados.length());
        hs.create(hsj);
        
        
    } catch (RollbackFailureException ex) {
        Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
        Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
    }

out.println("<br/><h1>JUEGO COMPLETO</h1>");
out.println("<br/> <a href='index.jsp'>regresar</a>");
sesion.invalidate();
}
out.println("</body>");
out.println("</html>");
} finally {
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
