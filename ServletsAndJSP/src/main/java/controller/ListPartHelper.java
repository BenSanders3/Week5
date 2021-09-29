package controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.ListPart;

/**
 * @author Ben Sanders - bsanders3
 * CIS 175 Fall 2021
 */
public class ListPartHelper {
	
	static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ServletsAndJSP");
	
	public void insertPrice(ListPart li) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(li);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<ListPart> showAllPrices(){
		EntityManager em = emfactory.createEntityManager();
		List<ListPart> allPrices = em.createQuery("SELECT i FROM ListPart i").getResultList();
		return allPrices;
	}
	public void deletePrice(ListPart toDelete) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<ListPart> typedQuery = em.createQuery("select li from ListPart li where li.part = :selectedPart and li.price = :selectedPrice", ListPart.class);
		
		typedQuery.setParameter("selectedPart", toDelete.getPart());
		typedQuery.setParameter("selectedPrice", toDelete.getPrice());
		
		typedQuery.setMaxResults(1);
		
		ListPart result = typedQuery.getSingleResult();
		
		em.remove(result);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * @param idToEdit
	 * @return
	 */
	public ListPart searchForPriceById(int idToEdit) {
		// TODO Auto-generated method stub
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		ListPart found = em.find(ListPart.class, idToEdit);
		em.close();
		return found;
	}

	/**
	 * @param toEdit
	 */
	public void updatePrice(ListPart toEdit) {
		// TODO Auto-generated method stub
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(toEdit);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * @param partName
	 * @return
	 */
	public List<ListPart> serchForPriceByPart(String partName) {
		// TODO Auto-generated method stub
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<ListPart> typedQuery = em.createQuery("select li from ListPart li where li.part = :selectedPart", ListPart.class);
		typedQuery.setParameter("selectedPart", partName);
		
		List<ListPart> foundPrices = typedQuery.getResultList();
		em.close();
		
		return foundPrices;
	}

	/**
	 * @param priceName
	 * @return
	 */
	public List<ListPart> searchForPriceByPrice(String priceName) {
		// TODO Auto-generated method stub
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<ListPart> typedQuery = em.createQuery("select li from ListParts li where li.price = :selectedPrice", ListPart.class);
		typedQuery.setParameter("selectedPrice", priceName);
		
		List<ListPart> foundPrices = typedQuery.getResultList();
		em.close();
		return foundPrices;
	}
	public void cleanUp() {
		emfactory.close();
	}
}
