package com.twp.petcare.repository;

import com.twp.petcare.bean.Owner;
import com.twp.petcare.bean.Pet;

/**
 * Created by helbert on 8/21/15.
 */
public interface PetRepositoryCustom {
    Pet getPetFor(Owner order);
}
