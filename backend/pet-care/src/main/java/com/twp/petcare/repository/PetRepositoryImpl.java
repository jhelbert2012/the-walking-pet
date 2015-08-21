package com.twp.petcare.repository;

import com.twp.petcare.bean.Owner;
import com.twp.petcare.bean.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by helbert on 8/21/15.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PetRepositoryImpl implements PetRepositoryCustom {

    private final MongoOperations operations;

    private double taxRate = 0.19;

    public Pet getPetFor(Owner owner) {

        AggregationResults<Pet> results = operations.aggregate(newAggregation(Owner.class, //
                match(where("id").is(owner.getId()))//
//                unwind("items"), //
//                project("id", "customerId", "items") //
//                        .andExpression("'$items.price' * '$items.quantity'").as("lineTotal"), //
//                group("id") //
//                        .sum("lineTotal").as("netAmount") //
//                        .addToSet("items").as("items"), //
//                project("id", "items", "netAmount") //
//                        .and("orderId").previousOperation() //
//                        .andExpression("netAmount * [0]", taxRate).as("taxAmount") //
//                        .andExpression("netAmount * (1 + [0])", taxRate).as("totalAmount") //
        ), Pet.class);

        return results.getUniqueMappedResult();
    }
}
