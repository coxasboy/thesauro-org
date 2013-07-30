/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.business;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.thesauro.dao.SpecificTermDaoLocal;
import org.thesauro.entity.SpecificTerm;
import org.thesauro.util.AttributeCoppier;
import org.thesauro.util.ThesauroLogger;

/**
 *
 * @author Matheus
 */
@Stateless
public class SpecificTermHandler implements SpecificTermHandlerLocal {

    @EJB
    private SpecificTermDaoLocal specificDaoTerm;
    
    private static final ThesauroLogger LOGGER = ThesauroLogger.getLogger(SpecificTermHandler.class);
            
    @Override
    public SpecificTerm createSpecificTerm(SpecificTerm term){
        return specificDaoTerm.create(term);
    }
    
    @Override
    public void updateSpecificTerm(SpecificTerm term) throws IllegalArgumentException{
        SpecificTerm specificTerm = specificDaoTerm.read(term.getId());
        if(specificTerm==null){
            throw new IllegalArgumentException("Can t find specific term for: " + term.getId());
        }
        copyValuesFromFirstInstanceToSecond(term, specificTerm);
        specificDaoTerm.update(specificTerm);
    }
    
    @Override
    public void deleteSpecificTerm(long id) throws IllegalArgumentException{
        SpecificTerm specificTerm = specificDaoTerm.read(id);
        if(specificTerm==null){
            throw new IllegalArgumentException("Can t find specific term for: " + id);
        }
        specificDaoTerm.delete(specificTerm);                
    }
    
    @Override
    public List<SpecificTerm> getAllSpecificTerms(){
        return specificDaoTerm.readAll();
    }
    
    private void copyValuesFromFirstInstanceToSecond(SpecificTerm firstOne, SpecificTerm secondOne){
        try{
            AttributeCoppier.copyAttributesWithSameName(firstOne, secondOne);
        }
        catch(Exception e){
            LOGGER.error("Error copying terms",e);
        }
    }

}
