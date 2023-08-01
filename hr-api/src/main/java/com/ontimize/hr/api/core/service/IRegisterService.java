package com.ontimize.hr.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;

import java.util.List;
import java.util.Map;

public interface IRegisterService {


    public EntityResult registerQuery(Map<String, Object> keyMap, List<String> attrList) throws OntimizeJEERuntimeException;
    public EntityResult registerQueryDevices(Map<String, Object> keyMap, List<String> attrList) throws OntimizeJEERuntimeException;
    public EntityResult registerQueryPlates(Map<String, Object> keyMap, List<String> attrList) throws OntimizeJEERuntimeException;
    public EntityResult registerQueryTrailerPlates(Map<String, Object> keyMap, List<String> attrList) throws OntimizeJEERuntimeException;
    public EntityResult registerQueryDeliveryNotes(Map<String, Object> keyMap, List<String> attrList) throws OntimizeJEERuntimeException;
    public EntityResult registerInsert(Map<String, Object> attrMap) throws OntimizeJEERuntimeException;

    public EntityResult registerInsertDevices(Map<String, Object> attrMap) throws OntimizeJEERuntimeException;

    public EntityResult registerInsertPlates(Map<String, Object> attrMap) throws OntimizeJEERuntimeException;

    public EntityResult registerInsertTrailerPlates(Map<String, Object> attrMap) throws OntimizeJEERuntimeException;

    public EntityResult registerInsertDeliveryNotes(Map<String, Object> attrMap) throws OntimizeJEERuntimeException;

    public EntityResult registerUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap) throws OntimizeJEERuntimeException;
    public EntityResult registerDelete(Map<String, Object> keyMap) throws OntimizeJEERuntimeException;

    public EntityResult makeRequest(Map<String,Object> body) throws OntimizeJEERuntimeException;
}
