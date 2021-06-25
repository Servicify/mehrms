package org.servicify.mehrms.service;

import org.servicify.mehrms.mapper.PoliticsstatusMapper;
import org.servicify.mehrms.model.Politicsstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class PoliticsstatusService {
    @Autowired
    PoliticsstatusMapper politicsstatusMapper;
    public List<Politicsstatus> getAllPoliticsstatus() {
        return  politicsstatusMapper.getAllPoliticsstatus();
    }
}
