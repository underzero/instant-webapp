package org.vaadin.appfoundation.persistence.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.vaadin.appfoundation.persistence.data.AbstractPojo;
import org.vaadin.appfoundation.persistence.facade.IFacade;

/**
 * This is a Test Helper
 * 
 * @author Mischa
 *
 */
public class FacadeStub implements IFacade {
	
	private int deleteCallsCounter;
	private int storeCallsCounter;	

    public void close() {
    }

    public void delete(AbstractPojo pojo) {
    	deleteCallsCounter++;
    }
    
    public int getDeleteCallsCounter() {
    	return deleteCallsCounter;
    }
    

    public <A extends AbstractPojo> void deleteAll(Collection<A> pojos) {
    }

    public <A extends AbstractPojo> A find(Class<A> clazz, String id) {
        return null;
    }

    public <A extends AbstractPojo> A find(String queryStr,
            Map<String, Object> parameters) {
        return null;
    }

    public void init(String name) {

    }

    public void kill() {

    }

    public <A extends AbstractPojo> List<A> list(Class<A> clazz) {
        return null;
    }

    public <A extends AbstractPojo> List<A> list(String queryStr,
            Map<String, Object> parameters) {
        return null;
    }

    public <A extends AbstractPojo> void refresh(A pojo) {

    }

    public void store(AbstractPojo pojo) {
    	storeCallsCounter++;
    }
    
    public int getStoreCallsCounter() {
    	return storeCallsCounter;
    }

    public <A extends AbstractPojo> void storeAll(Collection<A> pojos) {

    }

    public Long count(Class<? extends AbstractPojo> c) {
        return 0L;
    }

    public Long count(Class<? extends AbstractPojo> c, String whereClause,
            Map<String, Object> parameters) {
        return 0L;
    }

    public <A extends AbstractPojo> List<A> list(Class<A> clazz,
            int startIndex, int amount) {
        return null;
    }

    public <A extends AbstractPojo> List<A> list(String queryStr,
            Map<String, Object> parameters, int startIndex, int amount) {
        return null;
    }

    public List<?> getFieldValues(Class<? extends AbstractPojo> c,
            String field, String whereConditions, Map<String, Object> parameters) {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return null;
	}

}
