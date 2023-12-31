package com.ontimize.hr.ws.core.rest;

import com.ontimize.hr.api.core.service.*;
import com.ontimize.hr.model.core.dao.*;
import com.ontimize.jee.common.dto.EntityResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ontimize.jee.server.rest.ORestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/registers")
public class RegisterRestController extends ORestController<IRegisterService> {

    @Autowired
    private IRegisterService registerService;
    @Autowired
    private IDevicesService devicesService;
    @Autowired
    private IPlateService plateService;
    @Autowired
    private ITrailerPlateService trailerPlateService;
    @Autowired
    private IDeliveryNoteService deliveryNoteService;

    @Override
    public IRegisterService getService() {
        return this.registerService;
    }

    @RequestMapping(
            value = "/makeRequest",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> makeRequest(@RequestBody Map<String, Object> body) {
        Map<String, Object> mapDevices = new HashMap<>();
        Map<String, Object> mapPlates = new HashMap<>();
        Map<String, Object> mapTrailerPlates = new HashMap<>();
        Map<String, Object> mapDeliveryNotes = new HashMap<>();
        Map<String, Object> mapRegister = new HashMap<>(body);

        boolean is_in = body.get("dev").equals("IN_SCAN_1");
        String attr_id_dev = is_in ? RegisterDao.ATTR_ID_DEV_IN : RegisterDao.ATTR_ID_DEV_OUT;

        mapDevices.put("dev_name", body.get("dev"));
        mapPlates.put("plate_name", body.get("plate"));
        mapTrailerPlates.put("trailer_plate_name", body.get("trailer_plate"));
        mapDeliveryNotes.put("delivery_note", body.get("delivery_note"));
/*        if(body.get("scan_volume").equals("NaN")){
           body.put("scan_volume",null);
        }*/

        if (is_in) {
            mapRegister.put(RegisterDao.ATTR_DATE_IN, Timestamp.valueOf( (String)body.get("date")));
            mapRegister.put(RegisterDao.ATTR_SCAN_VOLUME_IN, body.get("scan_volume"));
        } else {
            mapRegister.put(RegisterDao.ATTR_DATE_OUT, Timestamp.valueOf((String) body.get("date")));
            mapRegister.put(RegisterDao.ATTR_SCAN_VOLUME_OUT, body.get("scan_volume"));
        }

        EntityResult query;
        List<String> attr = new ArrayList<String>();
        attr.add(DevicesDao.ATTR_ID_DEV);

        query = this.devicesService.devicesQuery(mapDevices, attr);
        attr.remove(0);
        if (query.calculateRecordNumber() > 0) {
            mapRegister.put(attr_id_dev, query.getRecordValues(0).get(DevicesDao.ATTR_ID_DEV));
        } else {
            EntityResult resultDevices = this.devicesService.devicesInsert(mapDevices);
            mapRegister.put(attr_id_dev, resultDevices.get(DevicesDao.ATTR_ID_DEV));
        }

        attr.add(PlatesDao.ATTR_ID_PLATE);
        query = this.plateService.platesQuery(mapPlates, attr);
        attr.remove(0);
        if (query.calculateRecordNumber() > 0) {
            mapRegister.put(RegisterDao.ATTR_ID_PLATE, query.getRecordValues(0).get(PlatesDao.ATTR_ID_PLATE));
        } else {
            EntityResult resultPlates = this.plateService.platesInsert(mapPlates);
            mapRegister.put(RegisterDao.ATTR_ID_PLATE, resultPlates.get(PlatesDao.ATTR_ID_PLATE));
        }

        attr.add(TrailerPlatesDao.ATTR_ID_TRAILER_PLATE);
        query = this.trailerPlateService.trailerPlatesQuery(mapTrailerPlates, attr);
        attr.remove(0);
        if (query.calculateRecordNumber() > 0) {
            mapRegister.put(RegisterDao.ATTR_ID_TRAILER_PLATE, query.getRecordValues(0).get(TrailerPlatesDao.ATTR_ID_TRAILER_PLATE));
        } else {
            EntityResult resultTrailerPlates = this.trailerPlateService.trailerPlatesInsert(mapTrailerPlates);
            mapRegister.put(RegisterDao.ATTR_ID_TRAILER_PLATE, resultTrailerPlates.get(TrailerPlatesDao.ATTR_ID_TRAILER_PLATE));
        }

        attr.add(DeliveryNotesDao.ATTR_ID_DELIVERY_NOTE);
        query = this.deliveryNoteService.deliveryNotesQuery(mapDeliveryNotes, attr);
        attr.remove(0);


        if (query.calculateRecordNumber() > 0) {
            mapRegister.put(RegisterDao.ATTR_ID_DELIVERY_NOTE, query.getRecordValues(0).get(DeliveryNotesDao.ATTR_ID_DELIVERY_NOTE));
            /*attr.add(RegisterDao.ATTR_ID);
            Map<String,Object> busquedaDeliveryNote= query.getRecordValues(0);
            query=this.registerService.registerQuery(busquedaDeliveryNote,attr);

            List<String> attr_dev= new ArrayList<>();
            attr_dev.add(RegisterDao.ATTR_ID);
            attr_dev.add(RegisterDao.ATTR_ID_DEV_IN);
            attr_dev.add(RegisterDao.ATTR_ID_DEV_OUT);
            EntityResult query2;

            query2=this.registerService.registerQuery(mapRegister,attr_dev);

            if()
            registerService.registerUpdate(mapRegister,query.getRecordValues(0));*/
        } else {
            EntityResult resultDeliveryNotes = this.deliveryNoteService.deliveryNotesInsert(mapDeliveryNotes);
            mapRegister.put(RegisterDao.ATTR_ID_DELIVERY_NOTE, resultDeliveryNotes.get(DeliveryNotesDao.ATTR_ID_DELIVERY_NOTE));
            //podemos aprovechar la query de los delivery_notes porque van a tener una entrada única en la tabla de registro_camiones
            // registerService.registerInsert(mapRegister);
        }

        mapRegister.remove("date");
        mapRegister.remove("plate");
        mapRegister.remove("delivery_note");
        mapRegister.remove("scan_volume");
        mapRegister.remove("dev");
        mapRegister.remove("trailer_plate");


        attr.add(RegisterDao.ATTR_ID);
        attr.add(RegisterDao.ATTR_ID_DEV_IN);
        attr.add(RegisterDao.ATTR_ID_DEV_OUT);

        if (query.calculateRecordNumber() == 0) {
            registerService.registerInsert(mapRegister);
            return null;
        }
        Map<String, Object> busquedaDeliveryNote = query.getRecordValues(0);
        query = this.registerService.registerQuery(busquedaDeliveryNote, attr);

        for (int i = 0; i < query.calculateRecordNumber(); i++) {
            Object dev_in_value = query.getRecordValues(i).get(RegisterDao.ATTR_ID_DEV_IN);
            Object dev_out_value = query.getRecordValues(i).get(RegisterDao.ATTR_ID_DEV_OUT);

            if (dev_in_value != null && dev_out_value != null) {
                if (i == query.calculateRecordNumber() - 1) {
                    registerService.registerInsert(mapRegister);
                    break;
                }
            } else {
                Map<String, Object> id_update_map = new HashMap<>();
                id_update_map.put(RegisterDao.ATTR_ID, query.getRecordValues(i).get(RegisterDao.ATTR_ID));
                registerService.registerUpdate(mapRegister, id_update_map);
                break;
            }
        }


        return null;
    }
}