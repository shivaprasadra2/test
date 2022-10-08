package com.rgrv.rcs.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rgrv.rcs.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}
