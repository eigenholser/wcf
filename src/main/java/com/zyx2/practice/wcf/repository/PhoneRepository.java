package com.zyx2.practice.wcf.repository;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.zyx2.practice.wcf.model.Phone;
import com.zyx2.practice.wcf.model.Phone_;
import com.zyx2.practice.wcf.model.Usage;
import com.zyx2.practice.wcf.model.Usage_;

@ApplicationScoped
public class PhoneRepository {
	
	@PersistenceContext(unitName = "wcfPU")
	private EntityManager em;
	
	public Phone find(Long id) {
		return em.find(Phone.class, id);
	}

	public Phone create(Phone phone) {
		em.persist(phone);
		return phone;
	}
	
//	public List<Phone> findAll() {
//		TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p ORDER BY p.employeeName DESC", Phone.class);
//		return query.getResultList();
//	}
	
	public List<Phone> findAllPhones() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Phone> cq = cb.createQuery(Phone.class);
		Root<Phone> phoneRoot = cq.from(Phone.class);
		cq.select(phoneRoot);
		return em.createQuery(cq).getResultList();
	}
	
	public Long getPhoneCount() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Phone.class)));
		return em.createQuery(cq).getSingleResult();
	}
	
	public List<Phone> findPhonesWithUsage(LocalDate date) {
		System.out.println("From date: " + date);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Phone> query = cb.createQuery(Phone.class);
		Root<Phone> phone = query.from(Phone.class);
		Root<Usage> usage = query.from(Usage.class);
		Predicate forUsageDate = cb.greaterThan(usage.get(Usage_.date), date);
		query.select(phone).where(forUsageDate).distinct(true);
		return em.createQuery(query).getResultList();
	}
	
}
