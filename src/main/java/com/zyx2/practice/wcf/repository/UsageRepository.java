package com.zyx2.practice.wcf.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.zyx2.practice.wcf.model.Usage;
import com.zyx2.practice.wcf.model.Usage_;

@ApplicationScoped
public class UsageRepository {
	
	@PersistenceContext(unitName = "wcfPU")
	private EntityManager em;
	
	public Usage find(Long id) {
		return em.find(Usage.class, id);
	}
	
	public List<Usage> findByEmployeeId(Long employeeId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Usage> cq = cb.createQuery(Usage.class);
		Root<Usage> root = cq.from(Usage.class);
		cq.select(root).where(cb.equal(root.get("employeeId"),  employeeId));
		return em.createQuery(cq).getResultList();
	}
	
	// TODO: Delete
	public Stream<Usage> findByEmployeeIdStream(Long employeeId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Usage> cq = cb.createQuery(Usage.class);
		Root<Usage> root = cq.from(Usage.class);
		cq.select(root).where(cb.equal(root.get("employeeId"),  employeeId));
		return em.createQuery(cq).getResultStream();
	}
	
	public List<Usage> findByDate(LocalDate date) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Usage> cq = cb.createQuery(Usage.class);
		Root<Usage> root = cq.from(Usage.class);
		cq.select(root).where(cb.greaterThan(root.get(Usage_.date), date));
		return em.createQuery(cq).getResultList();
	}
	
	public LocalDate maxDate() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Usage> cq = cb.createQuery(Usage.class);
		Root<Usage> root = cq.from(Usage.class);
		cq.select(root).orderBy(cb.desc(root.get(Usage_.date)));
		return em.createQuery(cq).setMaxResults(1).getResultList().get(0).getDate();
	}
	
	public Usage create(Usage usage) {
		em.persist(usage);
		return usage;
	}
	
	public List<Usage> findAll() {
		TypedQuery<Usage> query = em.createQuery("SELECT u FROM Usage u ORDER BY u.employeeName DESC", Usage.class);
		return query.getResultList();
	}
}
