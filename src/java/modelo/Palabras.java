/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cosma_000
 */
@Entity
@Table(name = "PALABRAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Palabras.findAll", query = "SELECT p FROM Palabras p"),
    @NamedQuery(name = "Palabras.findByIdpal", query = "SELECT p FROM Palabras p WHERE p.idpal = :idpal"),
    @NamedQuery(name = "Palabras.findByAdivinada", query = "SELECT p FROM Palabras p WHERE p.adivinada = :adivinada"),
    @NamedQuery(name = "Palabras.findByGenerada", query = "SELECT p FROM Palabras p WHERE p.generada = :generada"),
    @NamedQuery(name = "Palabras.findByPalabra", query = "SELECT p FROM Palabras p WHERE p.palabra = :palabra"),
    @NamedQuery(name = "Palabras.findByTipopal", query = "SELECT p FROM Palabras p WHERE p.tipopal = :tipopal")})
public class Palabras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDPAL")
    private Integer idpal;
    @Column(name = "ADIVINADA")
    private Integer adivinada;
    @Column(name = "GENERADA")
    private Integer generada;
    @Size(max = 255)
    @Column(name = "PALABRA")
    private String palabra;
    @Size(max = 50)
    @Column(name = "TIPOPAL")
    private String tipopal;

    public Palabras() {
    }

    public Palabras(Integer idpal) {
        this.idpal = idpal;
    }

    public Integer getIdpal() {
        return idpal;
    }

    public void setIdpal(Integer idpal) {
        this.idpal = idpal;
    }

    public Integer getAdivinada() {
        return adivinada;
    }

    public void setAdivinada(Integer adivinada) {
        this.adivinada = adivinada;
    }

    public Integer getGenerada() {
        return generada;
    }

    public void setGenerada(Integer generada) {
        this.generada = generada;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getTipopal() {
        return tipopal;
    }

    public void setTipopal(String tipopal) {
        this.tipopal = tipopal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpal != null ? idpal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Palabras)) {
            return false;
        }
        Palabras other = (Palabras) object;
        if ((this.idpal == null && other.idpal != null) || (this.idpal != null && !this.idpal.equals(other.idpal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Palabras[ idpal=" + idpal + " ]";
    }
    
}
