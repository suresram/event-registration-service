package com.s2.event.registration.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s2.event.registration.model.Registration;
import com.s2.event.registration.repository.EventRegistrationRepository;

@Service
public class EventRegistrationService {

	@Autowired
	private EventRegistrationRepository eventRegistrationRepository;

	public String register(String eventId, String userId) {
		if (eventRegistrationRepository.findByEventIdAndVolunteerId(eventId, userId) != null) {
			return "You have already volunteered for this event";
		} else {
			Registration registration = new Registration();
			registration.setCreatedDate(new Date());
			registration.setEventId(eventId);
			registration.setVolunteerId(userId);
			registration = eventRegistrationRepository.save(registration);
			return "Your registration ID is " + registration.getId();
		}
	}
}
