/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.ws;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import org.thesauro.entity.Thesaurus;
import org.thesauro.model.ThesauroModel;

/**
 *
 * @author Matheus
 */
@WebService(serviceName = "ThresuroWs")
@Stateless()
public class ThresuroWs {
    
    @EJB
    private ThesauroModel thesauroModel;
    
    @WebMethod(operationName = "createThesaurus")
    public long createThesaurus(@WebParam(name = "thesaurusName") String thesaurusName, @WebParam(name = "thesaurusDescription") String description) {
        Thesaurus thesaurus = thesauroModel.createNewThesaurus(thesaurusName, description);
        return thesaurus.getId();
    }
        
    @WebMethod(operationName = "updateThesaurus")
    public void updateThesaurus(@WebParam(name = "thesaurusId") long thesaurusId, @WebParam(name = "thesaurusName") String thesaurusName, @WebParam(name = "thesaurusDescription") String description) throws IllegalArgumentException{
        Thesaurus thesaurus = thesauroModel.getThesaurusById(thesaurusId);
        if(thesaurus==null){
            throw new IllegalArgumentException("Can t find thesaurus with id: " + thesaurusId);
        }       
        thesaurus.setName(thesaurusName);
        thesaurus.setDescription(description);
        thesauroModel.updateThesaurus(thesaurus);        
    }
    
    @WebMethod(operationName = "deleteThesaurus")
    public void deleteThesaurus(@WebParam(name = "thesaurusId") long thesaurusId) throws IllegalArgumentException{
        Thesaurus thesaurus = thesauroModel.getThesaurusById(thesaurusId);
        if(thesaurus==null){
            throw new IllegalArgumentException("Can t find thesaurus with id: " + thesaurusId);
        }
        thesauroModel.deleteThersaurus(thesaurusId);
    }
    
    @WebMethod(operationName = "getThesaurusById")
    public Thesaurus getThesaurusById(@WebParam(name = "thesaurusId") long id){
        return thesauroModel.getThesaurusById(id);
    }
        
    
}
