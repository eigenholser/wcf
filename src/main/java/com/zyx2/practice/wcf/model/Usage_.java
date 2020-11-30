package com.zyx2.practice.wcf.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usage.class)
public abstract class Usage_ {

	public static volatile SingularAttribute<Usage, LocalDate> date;
	public static volatile SingularAttribute<Usage, Float> totalData;
	public static volatile SingularAttribute<Usage, Integer> totalMinutes;
	public static volatile SingularAttribute<Usage, Long> employeeId;
	public static volatile SingularAttribute<Usage, Long> id;

	public static final String DATE = "date";
	public static final String TOTAL_DATA = "totalData";
	public static final String TOTAL_MINUTES = "totalMinutes";
	public static final String EMPLOYEE_ID = "employeeId";
	public static final String ID = "id";

}

