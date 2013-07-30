/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.business;

import java.util.List;
import javax.ejb.Local;
import org.thesauro.entity.GeneralTerm;

/**
 *
 * @author Matheus
 */
@Local
public interface GeneralTermHandlerLocal {
    
    public GeneralTerm createGeneralTerm(GeneralTerm generalTerm);
    
    public List<GeneralTerm> getAllGeneralTerms();
    
    public void updateGeneralTerm(GeneralTerm generalTerm) throws IllegalArgumentException;
    
    public void deleteGeneralTerm(long id) throws IllegalArgumentException;
    
}
