package com.assessment.project.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.assessment.project.entity.Transaction;

@Component
public class TransactionItemProcessor implements ItemProcessor<Transaction,Transaction> {

    //private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");

    @Override
    public Transaction process(Transaction item) throws Exception {
        return item;
    }
}
