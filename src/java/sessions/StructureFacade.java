/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Structure;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ngnawen
 */
@Stateless
public class StructureFacade extends AbstractFacade<Structure> {
    @PersistenceContext(unitName = "GPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StructureFacade() {
        super(Structure.class);
    }
    
}
