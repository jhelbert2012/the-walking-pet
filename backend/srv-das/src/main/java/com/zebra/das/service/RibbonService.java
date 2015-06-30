
package com.zebra.das.service;

import com.zebra.das.model.api.Ribbon;
import java.util.List;

public interface RibbonService {
    public List<Ribbon> findAll();

    public Ribbon find(String ribbonId);
}
