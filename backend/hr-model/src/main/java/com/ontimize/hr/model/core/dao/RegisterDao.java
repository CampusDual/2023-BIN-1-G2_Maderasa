package com.ontimize.hr.model.core.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;

@Repository("RegisterDao")
@Lazy
@ConfigurationFile(configurationFile = "dao/RegisterDao.xml", configurationFilePlaceholder = "dao/placeholders.properties")
public class RegisterDao extends OntimizeJdbcDaoSupport {

    public static final String ATTR_ID = "id";

    public static final String ATTR_ID_DEV = "id_dev";

    public static final String ATTR_DATE = "date";

    public static final String ATTR_SCAN_VOLUME = "scan_volume";

    public static final String ATTR_CALCULATED_VOLUME = "calculated_volume";

    public static final String ATTR_ID_PLATE = "id_plate";

    public static final String ATTR_HEIGHT = "height";

    public static final String ATTR_WIDTH = "width";

    public static final String ATTR_LENGTH = "length";

    public static final String ATTR_ID_TRAILER_PLATE = "id_trailer_plate";

    public static final String ATTR_ID_DELIVERY_NOTE = "id_delivery_note";
    public static final String QUERY_COMPLETED = "completed";
    public static final String QUERY_RESUME_DELIVERY = "resume_delivery";
}