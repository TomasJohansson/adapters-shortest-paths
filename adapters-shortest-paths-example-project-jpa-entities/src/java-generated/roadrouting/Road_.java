package roadrouting;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import roadrouting.Road;
import roadrouting.RoadQuality;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Road.class)
public abstract class Road_ {

	public static volatile SingularAttribute<Road, Integer> cityFromId;
	public static volatile SingularAttribute<Road, Integer> roadLength;
	public static volatile SingularAttribute<Road, RoadQuality> roadQuality;
	public static volatile SingularAttribute<Road, Integer> cityToId;
	public static volatile SingularAttribute<Road, Integer> roadKey;
	public static volatile SingularAttribute<Road, String> roadName;

}

