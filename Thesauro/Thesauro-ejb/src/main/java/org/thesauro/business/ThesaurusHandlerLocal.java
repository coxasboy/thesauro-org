/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.business;

import java.util.List;
import javax.ejb.Local;
import org.thesauro.entity.Thesaurus;

/**
 *
 * @author Matheus
 */
@Local
public interface ThesaurusHandlerLocal {
    
    public List<Thesaurus> getAllThesaurus();
    
    public Thesaurus createNewThesaurus(Thesaurus thesaurus);
       
    public void updateThesaurus(Thesaurus updatedOne) throws IllegalArgumentException;
    
    public void deleteThesaurus(long id) throws IllegalArgumentException;   
    
    public void detachObject(Thesaurus thesaurus);
    
}
