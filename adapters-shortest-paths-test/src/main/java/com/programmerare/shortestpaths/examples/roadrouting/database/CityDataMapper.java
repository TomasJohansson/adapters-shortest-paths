package com.programmerare.shortestpaths.examples.roadrouting.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.programmerare.shortestpaths.examples.roadrouting.City;
import com.programmerare.shortestpaths.examples.roadrouting.City_; // JPA metomodel class. It is generated ! 

/**
 * @author Tomas Johansson
 */
public final class CityDataMapper extends BaseDataMapper<City, Integer>  {

	public CityDataMapper(final EntityManager em) {
		super(em, City.class);
	}
	
	public City getByCityName(final String cityName) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<City> cq = cb.createQuery(City.class);
		
		final Root<City> root = cq.from(City.class);
		
		// Note that the below used class "City_ is code generated and part of the JPA meta model 
		cq.where(cb.like(root.get(City_.cityName), cityName));
		
		final List<City> resultList = em.createQuery(cq).getResultList();
		if(resultList.size() == 0) {
			return null;
		}
		if(resultList.size() > 1) {
			throw new RuntimeException("There should not be more than one city with the same name " + cityName);
		}		
		return resultList.get(0); 
	}	
}