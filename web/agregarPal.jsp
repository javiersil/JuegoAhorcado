<%-- 
    Document   : agregarPal
    Created on : 22/11/2016, 09:48:03 AM
    Author     : cosma_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Agregar</h1>
        <form action="AgregarPalabra">            
            
        
        
        <table>
            <tr>
                
                <td>Palabra</td>
            </tr>
            <tr>
                <td><input type="text" name="palabra"></td>
                <td><select name="tipo">
                                <option value="ADJETIVO">ADJETIVO</option>
                                <option value="ACCION">ACCION</option>
                                <option value="COSA">COSA</option>
                    </select></td>
                <td><input type="submit" name="aceptar" value="Aceptar"></td>
            </tr>
        </table>
            
            </form>
    </body>
</html>
