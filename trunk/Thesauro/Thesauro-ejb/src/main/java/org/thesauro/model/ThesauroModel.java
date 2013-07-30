/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.model;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import org.thesauro.business.GeneralTermHandlerLocal;
import org.thesauro.business.RelatedTermHandlerLocal;
import org.thesauro.business.SpecificTermHandlerLocal;
import org.thesauro.business.ThesaurusHandlerLocal;
import org.thesauro.entity.GeneralTerm;
import org.thesauro.entity.RelatedTerm;
import org.thesauro.entity.SpecificTerm;
import org.thesauro.entity.Thesaurus;
import org.thesauro.util.AttributeCoppier;
import org.thesauro.util.ThesauroLogger;

/**
 *
 * @author Matheus
 */
@Singleton
@LocalBean
public class ThesauroModel {
    
    private Collection<Thesaurus> thesaurusList = new ArrayList<Thesaurus>();
    
    private static final ThesauroLogger LOGGER = ThesauroLogger.getLogger(ThesauroModel.class);
    
    @EJB
    private ThesaurusHandlerLocal thesaurusHandler;
    
    @EJB
    private GeneralTermHandlerLocal generalTermHandler;
    
    @EJB
    private SpecificTermHandlerLocal specificTermHandler;
    
    @EJB
    private RelatedTermHandlerLocal relatedTermHandler;
    
    @PostConstruct
    private void initialize(){
        Collection<Thesaurus> auxList = thesaurusHandler.getAllThesaurus();
        for (Thesaurus thesaurus : auxList) {
            thesaurusHandler.detachObject(thesaurus);
            thesaurusList.add(thesaurus);
        }
    }
    
    public void deleteThersaurus(long id) throws IllegalArgumentException{
        thesaurusHandler.deleteThesaurus(id);
        Collection<Thesaurus> aux = getThesaurusListCopy();
        Thesaurus deletedOne = null;
        for (Thesaurus thesaurus : aux) {
            if(thesaurus.getId()==id){
                deletedOne = thesaurus;
                break;
            }
        }
        removeThesaurusToList(deletedOne);
    }
    
    public void updateThesaurus(Thesaurus updatedOne) throws IllegalArgumentException{
        thesaurusHandler.updateThesaurus(updatedOne);
        Collection<Thesaurus> aux = getThesaurusListCopy();
        for (Thesaurus thesaurus : aux) {
            if(thesaurus.getId()==updatedOne.getId()){
                copyAttributesFromFirstToSecond(updatedOne, thesaurus);
                break;
            }
        }
    }
    
    public Thesaurus getThesaurusBySpecificTermId(long specificTermId){
        Collection<Thesaurus> aux = getThesaurusListCopy();
        for (Thesaurus thesaurus : aux) {
            Collection<GeneralTerm> generalTerms = thesaurus.getGeneralTerms();
            for (GeneralTerm generalTerm : generalTerms) {
                Collection<SpecificTerm> specificTerms = generalTerm.getSpecificTerms();
                for (SpecificTerm specificTerm : specificTerms) {
                    if(specificTerm.getId().longValue()==specificTermId){
                        return thesaurus;
                    }
                }
            }
        }
        return null;
    }
    
    public Thesaurus getThesaurusByGeneralTermId(long generalTermId){
        Collection<Thesaurus> aux = getThesaurusListCopy();
        for (Thesaurus thesaurus : aux) {
            Collection<GeneralTerm> generalTerms = thesaurus.getGeneralTerms();
            for (GeneralTerm generalTerm : generalTerms) {
                if(generalTerm.getId()==generalTermId){
                    Thesaurus ret = new Thesaurus();
                    copyAttributesFromFirstToSecond(thesaurus, ret);
                    return ret;
                }
            }
        }
        return null;
    }
    
    public Thesaurus getThesaurusById(long id){
        Collection<Thesaurus> aux = getThesaurusListCopy();
        for (Thesaurus thesaurus : aux) {
            if(thesaurus.getId().longValue()==id){
                Thesaurus ret = new Thesaurus();
                copyAttributesFromFirstToSecond(thesaurus, ret);
                return ret;
            }
        }
        return null;
    }
    
    public synchronized Collection<Thesaurus> getThesaurusListCopy(){
        return new ArrayList<Thesaurus>(thesaurusList);
    }
    
    public Thesaurus createNewThesaurus(String name, String description){
        Thesaurus thesaurus = new Thesaurus();
        thesaurus.setName(name);
        thesaurus.setDescription(description);
        thesaurus = thesaurusHandler.createNewThesaurus(thesaurus);
        Thesaurus modelInstance = new Thesaurus();
        copyAttributesFromFirstToSecond(thesaurus, modelInstance);
        addThesaurusToList(modelInstance);
        return modelInstance;
    }
    
    private synchronized void addThesaurusToList(Thesaurus thesaurus){
        this.thesaurusList.add(thesaurus);
    }
    
    private synchronized void removeThesaurusToList(Thesaurus thesaurus){
        this.thesaurusList.remove(thesaurus);
    }
    
    public GeneralTerm createGeneralTermAndAddToThesaurus(long thesaurusId, GeneralTerm newTerm) throws IllegalArgumentException{
        Thesaurus thesaurus = getThesaurusById(thesaurusId);
        if(thesaurus==null){
            throw new IllegalArgumentException("Thesaurus not found for: " + thesaurusId);
        }
        newTerm = this.generalTermHandler.createGeneralTerm(newTerm);
        GeneralTerm modelInstance = new GeneralTerm();
        copyAttributesFromFirstToSecond(newTerm, modelInstance);
        thesaurus.getGeneralTerms().add(modelInstance);
        this.updateThesaurus(thesaurus);
        return modelInstance;
    }
    
    public SpecificTerm createSpecificTermAndAddToGeneralTerm(long generalTermId, SpecificTerm newTerm) throws IllegalArgumentException{
        Thesaurus thesaurus = getThesaurusByGeneralTermId(generalTermId);
        if(thesaurus==null){
            throw new IllegalArgumentException("Thesaurus not found with general term of id: " + generalTermId);
        }
        newTerm = this.specificTermHandler.createSpecificTerm(newTerm);
        SpecificTerm modelInstance = new SpecificTerm();
        copyAttributesFromFirstToSecond(newTerm, modelInstance);
        Collection<GeneralTerm> generalTherms = thesaurus.getGeneralTerms();
        for (GeneralTerm generalTerm : generalTherms) {
            if(generalTerm.getId().longValue()==generalTermId){
                generalTerm.getSpecificTerms().add(modelInstance);
            }
        }
        this.updateThesaurus(thesaurus);
        return modelInstance;
    }
    
    public RelatedTerm createRelatedTermAndAddToSpecificTerm(long specificTermId, RelatedTerm newTerm) throws IllegalArgumentException{
        Thesaurus thesaurus = getThesaurusBySpecificTermId(specificTermId);
        if(thesaurus==null){
            throw new IllegalArgumentException("Thesaurus not found with general term of id: " + specificTermId);
        }
        newTerm = this.relatedTermHandler.createRelatedTerm(newTerm);
        RelatedTerm modelInstance = new RelatedTerm();
        copyAttributesFromFirstToSecond(newTerm, modelInstance);
        Collection<GeneralTerm> generalTherms = thesaurus.getGeneralTerms();
        for (GeneralTerm generalTerm : generalTherms) {
            Collection<SpecificTerm> specificTerms =  generalTerm.getSpecificTerms();
            for (SpecificTerm specificTerm : specificTerms) {
                if(specificTerm.getId()==specificTermId){
                    specificTerm.getRelatedTerms().add(modelInstance);
                }
            }
        }
        this.updateThesaurus(thesaurus);
        return modelInstance;
        
    }
    
    private void copyAttributesFromFirstToSecond(RelatedTerm firstOne, RelatedTerm secondOne){
        try{
            AttributeCoppier.copyAttributesWithSameName(firstOne, secondOne);
        }
        catch(Exception e){
            LOGGER.error("Error copying attributes", e);
        }
    }
    
    private void copyAttributesFromFirstToSecond(SpecificTerm firstOne, SpecificTerm secondOne){
        try{
            AttributeCoppier.copyAttributesWithSameName(firstOne, secondOne);
        }
        catch(Exception e){
            LOGGER.error("Error copying attributes", e);
        }
    }
    
    private void copyAttributesFromFirstToSecond(GeneralTerm firstOne, GeneralTerm secondOne){
        try{
            AttributeCoppier.copyAttributesWithSameName(firstOne, secondOne);
        }
        catch(Exception e){
            LOGGER.error("Error copying attributes", e);
        }
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
