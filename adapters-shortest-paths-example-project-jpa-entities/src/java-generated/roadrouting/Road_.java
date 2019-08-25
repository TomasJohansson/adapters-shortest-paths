package roadrouting;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Road.class)
public abstract class Road_ {

	public static volatile SingularAttribute<Road, Integer> cityFromId;
	public static volatile SingularAttribute<Road, Integer> roadLength;
	public static volatile SingularAttribute<Road, RoadQuality> roadQuality;
	public static volatile SingularAttribute<Road, Integer> cityToId;
	public static volatile SingularAttribute<Road, Integer> roadKey;
	public static volatile SingularAttribute<Road, String> roadName;

	public static final String CITY_FROM_ID = "cityFromId";
	public static final String ROAD_LENGTH = "roadLength";
	public static final String ROAD_QUALITY = "roadQuality";
	public static final String CITY_TO_ID = "cityToId";
	public static final String ROAD_KEY = "roadKey";
	public static final String ROAD_NAME = "roadName";

}

