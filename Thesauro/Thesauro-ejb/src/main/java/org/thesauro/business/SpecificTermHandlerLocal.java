/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.business;

import java.util.List;
import javax.ejb.Local;
import org.thesauro.entity.SpecificTerm;

/**
 *
 * @author Matheus
 */
@Local
public interface SpecificTermHandlerLocal {
    
    public SpecificTerm createSpecificTerm(SpecificTerm term);
    
    public void updateSpecificTerm(SpecificTerm term) throws IllegalArgumentException;
    
    public void deleteSpecificTerm(long id) throws IllegalArgumentException;
    
    public List<SpecificTerm> getAllSpecificTerms();
    
}
