package com.poc.process.batch.steps;

import com.poc.process.batch.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    private static final Logger LOG = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(Person person) throws Exception {
        LOG.info("Person: {}", person.toString());

        person.setEmail(person.getEmail().concat("@gmail.com"));

        LOG.info("Person after process: {}", person.toString());

        return person;
    }
}
