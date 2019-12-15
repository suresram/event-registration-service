package com.s2.event.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.s2.event.registration.service.EventRegistrationService;

@Controller
public class EventRegistrationController {

	@Autowired
	private EventRegistrationService eventRegistrationService;

	@RequestMapping(value = "registration/v1/{eventId}", method = RequestMethod.POST)
	@ResponseBody
	public String register(@PathVariable String eventId, @RequestHeader(value = "userId", required = true) String userId,
			@RequestHeader(value = "roles", required = true) String roles) {
		return eventRegistrationService.register(eventId, userId);
	}
}
