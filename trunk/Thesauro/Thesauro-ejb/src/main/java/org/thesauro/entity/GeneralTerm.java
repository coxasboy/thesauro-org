/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Matheus
 */
@Entity
public class GeneralTerm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(length = 512, unique = true, nullable = false)
    private String identifier;
    
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<SpecificTerm> specificTerms = new ArrayList<SpecificTerm>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Collection<SpecificTerm> getSpecificTerms() {
        return specificTerms;
    }

    public void setSpecificTerms(Collection<SpecificTerm> specificTerms) {
        this.specificTerms = specificTerms;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeneralTerm)) {
            return false;
        }
        GeneralTerm other = (GeneralTerm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.thesauro.GeneralTerm[ id=" + id + " ]";
    }
    
}
