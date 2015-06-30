package com.zebra.model.api;

import java.util.List;

public class SyncJobList extends HateoasResourceList<SyncJob> {

    public SyncJobList() {
        super();
    }

    public SyncJobList(List<SyncJob> resources, Long total) {
        super(resources, total);
    }

}
