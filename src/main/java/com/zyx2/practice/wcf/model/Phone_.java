package com.zyx2.practice.wcf.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Phone.class)
public abstract class Phone_ {

	public static volatile SingularAttribute<Phone, String> employeeName;
	public static volatile SingularAttribute<Phone, LocalDate> purchaseDate;
	public static volatile SingularAttribute<Phone, Long> employeeId;
	public static volatile SingularAttribute<Phone, String> model;

	public static final String EMPLOYEE_NAME = "employeeName";
	public static final String PURCHASE_DATE = "purchaseDate";
	public static final String EMPLOYEE_ID = "employeeId";
	public static final String MODEL = "model";

}

