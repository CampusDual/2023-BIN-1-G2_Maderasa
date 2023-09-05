package com.ontimize.hr.ws.core.rest;

import com.ontimize.hr.api.core.service.IConfigService;
import com.ontimize.hr.api.core.service.IDeliveryNoteService;
import com.ontimize.jee.server.rest.ORestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigRestService extends ORestController<IConfigService> {

    @Autowired
    private IConfigService configService;

    @Override
    public IConfigService getService() {
        return configService;
    }

}
