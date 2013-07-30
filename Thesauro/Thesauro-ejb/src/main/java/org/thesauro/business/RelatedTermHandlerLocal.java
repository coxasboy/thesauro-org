/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.business;

import java.util.List;
import javax.ejb.Local;
import org.thesauro.entity.RelatedTerm;

/**
 *
 * @author Matheus
 */
@Local
public interface RelatedTermHandlerLocal {
    
    public RelatedTerm createRelatedTerm(RelatedTerm relatedTerm);
    
    public void updateRelatedTerm(RelatedTerm relatedTerm) throws IllegalArgumentException;
    
    public void deleteRelatedTerm(long id) throws IllegalArgumentException;
    
    public List<RelatedTerm> getAllRelatedTerms();
    
}
