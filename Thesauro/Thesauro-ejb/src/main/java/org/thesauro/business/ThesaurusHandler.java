/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.business;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.thesauro.dao.ThesaurusDaoLocal;
import org.thesauro.entity.Thesaurus;
import org.thesauro.util.AttributeCoppier;
import org.thesauro.util.ThesauroLogger;

/**
 *
 * @author Matheus
 */
@Stateless
public class ThesaurusHandler implements ThesaurusHandlerLocal {

    @EJB
    private ThesaurusDaoLocal thesaurusDao;
    
    private ThesauroLogger LOGGER = ThesauroLogger.getLogger(ThesaurusHandler.class);
    
    @Override
    public List<Thesaurus> getAllThesaurus(){
        return thesaurusDao.readAll();
    }
    
    @Override
    public Thesaurus createNewThesaurus(Thesaurus thesaurus){
        return thesaurusDao.create(thesaurus);
    }
       
    @Override
    public void updateThesaurus(Thesaurus updatedOne) throws IllegalArgumentException{
        Thesaurus thesaurusFromDb = thesaurusDao.read(updatedOne.getId());
        if(thesaurusFromDb==null){
            throw new IllegalArgumentException("Can t find thesaurus with id: " + updatedOne.getId());
        }
        copyAttributesFromFirstToSecond(updatedOne, thesaurusFromDb);
        thesaurusDao.update(updatedOne);
    }
    
    @Override
    public void deleteThesaurus(long id) throws IllegalArgumentException{
        Thesaurus thesaurusFromDb = thesaurusDao.read(id);
        if(thesaurusFromDb==null){
            throw new IllegalArgumentException("Can t find thesaurus with id: " + id);
        }
        thesaurusDao.delete(thesaurusFromDb);
    }
    
    @Override
    public void detachObject(Thesaurus thesaurus){
        this.thesaurusDao.detach(thesaurus);
    }
    
    private void copyAttributesFromFirstToSecond(Thesaurus firstOne, Thesaurus secondOne){
        try{
            AttributeCoppier.copyAttributesWithSameName(firstOne, secondOne);
        }
        catch(Exception e){
            LOGGER.error("Error copying attributes", e);
        }
    }

}
