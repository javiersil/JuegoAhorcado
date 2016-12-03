<%-- 
    Document   : mostrar
    Created on : 16/11/2016, 04:06:29 PM
    Author     : cosma_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% int maxint = 3; %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
     <h1>Bienvenido </h1>
<h2>Te gustaria jugar?</h2>


<form method="get" action="Juego">
    <label>Intentos</label>
<!-- <input type="text" name="intentos" /> -->
<select name="intentos">
    <option value="10">Facil</option>
     <option value="6">Medio</option>
      <option value="4">Dificil</option>
       <option value="1">Muy dificil</option>
</select>
 <label>Tipo a generar</label>
<select name="tipo">
                                <option value="ADJETIVO">ADJETIVO</option>
                                <option value="ACCION">ACCION</option>
                                <option value="COSA">COSA</option>
                    </select>
<input type="submit" value="si" />
<a href="index.jsp" type="submit">No</a>

</form>
   
 



    </body>
</html>
