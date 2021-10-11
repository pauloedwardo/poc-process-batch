package com.poc.process.batch.steps;

import com.poc.process.batch.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class PersonWriter implements ItemWriter<Person> {

    private static final Logger LOG = LoggerFactory.getLogger(PersonWriter.class);

    @Override
    public void write(List<? extends Person> items) throws Exception {
        LOG.info("Saving...");
    }
}
