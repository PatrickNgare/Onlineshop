package com.filing.demo.config;

import com.filing.demo.models.Customer;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

public class TransactionValidatingProcessor extends ValidatingItemProcessor<Customer> {
    public TransactionValidatingProcessor(final int limit) {
        super(
                item -> {
                    if (item.getTransactions() >= limit) {
                        throw new ValidationException("Customer has less than " + limit + " transactions");
                    }
                }
        );
        setFilter(true);
    }
}
