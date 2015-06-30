
package com.zebra.das.service;

import com.zebra.das.model.api.Partner;
import java.util.List;

public interface PartnerService {
    public List<Partner> findAll();

    public Partner find(String partnerId);
}
