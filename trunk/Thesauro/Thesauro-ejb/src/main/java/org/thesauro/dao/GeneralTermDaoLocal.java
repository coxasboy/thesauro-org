/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.dao;

import javax.ejb.Local;
import org.thesauro.entity.GeneralTerm;

/**
 *
 * @author Matheus
 */
@Local
public interface GeneralTermDaoLocal extends GenericDao<GeneralTerm, Long>{
    
}
