package com.zebra.rest.mapper;

import com.zebra.das.model.api.SyncData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SyncDataMapper extends Mapper<Map<String,String>, List<com.zebra.das.model.api.SyncData>> {

    @Override
    public Map<String, String> map(List<SyncData> syncDataList){
        Map<String,String> result = new HashMap<>();
        for (SyncData data : syncDataList) {
            result.put(data.getSyncJob().getDmar() + "_" + data.getSyncDataPK().getLocalId(), data.getSyncDataPK().getRemoteId());
        }
        
        return result;
    }

}
