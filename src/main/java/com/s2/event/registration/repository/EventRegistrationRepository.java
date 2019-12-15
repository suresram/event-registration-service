package com.s2.event.registration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.s2.event.registration.model.Registration;

public interface EventRegistrationRepository extends MongoRepository<Registration, String> {

	Registration findByEventIdAndVolunteerId(String eventId, String volunteerId);

}