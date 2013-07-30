/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.dao;

import javax.ejb.Stateless;
import org.thesauro.entity.Thesaurus;

/**
 *
 * @author Matheus
 */
@Stateless
public class ThesaurusDao extends GenericDaoImpl<Thesaurus, Long> implements ThesaurusDaoLocal {

}
