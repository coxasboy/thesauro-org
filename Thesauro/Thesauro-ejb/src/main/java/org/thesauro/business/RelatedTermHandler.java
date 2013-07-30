/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.business;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.thesauro.dao.RelatedTermDaoLocal;
import org.thesauro.entity.RelatedTerm;

/**
 *
 * @author Matheus
 */
@Stateless
public class RelatedTermHandler implements RelatedTermHandlerLocal {

    @EJB
    private RelatedTermDaoLocal relatedTermDao;
    
    @Override
    public RelatedTerm createRelatedTerm(RelatedTerm relatedTerm){
        return relatedTermDao.create(relatedTerm);
    }
    
    @Override
    public void updateRelatedTerm(RelatedTerm relatedTerm) throws IllegalArgumentException{
        RelatedTerm aux = relatedTermDao.read(relatedTerm.getId());
        if(aux==null){
            throw new IllegalArgumentException("No related term with this id: " + relatedTerm.getId());
        }
        copyAllValuesFromFirstObjectToSecond(relatedTerm, aux);
        relatedTermDao.update(aux);
    }
    
    @Override
    public void deleteRelatedTerm(long id) throws IllegalArgumentException{
        RelatedTerm aux = relatedTermDao.read(id);
        if(aux==null){
            throw new IllegalArgumentException("No related term with this id: " + id);
        }
        relatedTermDao.delete(aux);
    }
    
    @Override
    public List<RelatedTerm> getAllRelatedTerms(){
        return relatedTermDao.readAll();
    }
    
    private void copyAllValuesFromFirstObjectToSecond(RelatedTerm fromThis, RelatedTerm toThis){
        toThis.setTerm(fromThis.getTerm());
    }

}
