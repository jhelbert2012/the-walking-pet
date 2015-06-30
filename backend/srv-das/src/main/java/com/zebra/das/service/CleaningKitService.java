
package com.zebra.das.service;

import com.zebra.das.model.api.CleaningKit;
import java.util.List;

public interface CleaningKitService {
    public List<CleaningKit> findAll();

    public CleaningKit find(String cleaningkitId);
}
