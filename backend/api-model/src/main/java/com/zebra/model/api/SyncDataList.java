package com.zebra.model.api;

import java.util.List;

public class SyncDataList extends HateoasResourceList<SyncData> {

    public SyncDataList() {
        super();
    }

    public SyncDataList(List<SyncData> resources, Long total) {
        super(resources, total);
    }

}
