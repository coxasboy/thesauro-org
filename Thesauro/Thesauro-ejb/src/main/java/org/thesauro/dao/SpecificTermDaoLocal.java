/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.dao;

import javax.ejb.Local;
import org.thesauro.entity.SpecificTerm;

/**
 *
 * @author Matheus
 */
@Local
public interface SpecificTermDaoLocal extends GenericDao<SpecificTerm, Long>{
    
}
