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
public class SpecificTerm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(length = 512, unique = true, nullable = false)
    private String term;
    
    @Column(length = 10240)
    private String concept;
    
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<RelatedTerm> relatedTerms = new ArrayList<RelatedTerm>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Collection<RelatedTerm> getRelatedTerms() {
        return relatedTerms;
    }

    public void setRelatedTerms(Collection<RelatedTerm> relatedTerms) {
        this.relatedTerms = relatedTerms;
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
        if (!(object instanceof SpecificTerm)) {
            return false;
        }
        SpecificTerm other = (SpecificTerm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.thesauro.entity.SpecificTerm[ id=" + id + " ]";
    }
    
}
