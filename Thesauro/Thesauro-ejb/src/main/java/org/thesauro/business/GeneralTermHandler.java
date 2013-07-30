/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.business;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.thesauro.dao.GeneralTermDaoLocal;
import org.thesauro.entity.GeneralTerm;
import org.thesauro.util.AttributeCoppier;
import org.thesauro.util.ThesauroLogger;

/**
 *
 * @author Matheus
 */
@Stateless
public class GeneralTermHandler implements GeneralTermHandlerLocal {

    @EJB            
    private GeneralTermDaoLocal generalTermDao;
    
    private ThesauroLogger LOGGER = ThesauroLogger.getLogger(GeneralTermHandler.class);
    
    @Override
    public GeneralTerm createGeneralTerm(GeneralTerm generalTerm){
        return generalTermDao.create(generalTerm);
    }
    
    @Override
    public List<GeneralTerm> getAllGeneralTerms(){
        return generalTermDao.readAll();
    }
    
    @Override
    public void updateGeneralTerm(GeneralTerm generalTerm) throws IllegalArgumentException{
        GeneralTerm generalTermFromDb = generalTermDao.read(generalTerm.getId());
        if(generalTermFromDb==null){
            throw new IllegalArgumentException("General term not found for id: " + generalTerm.getId());
        }
        copyValuesFromFirstObjectToTheSecond(generalTerm, generalTermFromDb);
        generalTermDao.update(generalTerm);        
    }
    
    @Override
    public void deleteGeneralTerm(long id) throws IllegalArgumentException{
        GeneralTerm generalTermFromDb = generalTermDao.read(id);
        if(generalTermFromDb==null){
            throw new IllegalArgumentException("General term not found for id: " + id);
        }
        generalTermDao.delete(generalTermFromDb);
    }        
    
    private void copyValuesFromFirstObjectToTheSecond(GeneralTerm firstOne, GeneralTerm secondOne){
        try{
            AttributeCoppier.copyAttributesWithSameName(firstOne, secondOne);
        }
        catch(Exception e){
            LOGGER.error("Error copying values",e);
        }
    }
    

}
