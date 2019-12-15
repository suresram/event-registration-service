package com.s2.event.detail;

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.s2.event.registration.EventRegistrationApplication;
import com.s2.event.registration.model.Registration;
import com.s2.event.registration.repository.EventRegistrationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventRegistrationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class EventRegistrationApplicationTest {

	@LocalServerPort
	private int port;

	@MockBean
	private EventRegistrationRepository eventRegistrationRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testSuccessfulRegistration() throws Exception {
		Mockito.when(eventRegistrationRepository.findByEventIdAndVolunteerId(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(null);
		Mockito.when(eventRegistrationRepository.save(Mockito.any(Registration.class))).thenReturn(new Registration());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", "volunteer1");
		headers.set("roles", "VOLUNTEER");
		ResponseEntity<String> response = restTemplate.exchange(
				new URL("http://localhost:" + port + "/registration/v1/123").toString(), HttpMethod.POST,
				new HttpEntity<>(headers), String.class);
		assertTrue(StringUtils.contains(response.getBody(), "Your registration ID is"));
	}

	@Test
	public void testRegisterationAlreadyExist() throws Exception {
		Mockito.when(eventRegistrationRepository.findByEventIdAndVolunteerId(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(new Registration());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", "volunteer1");
		headers.set("roles", "VOLUNTEER");
		ResponseEntity<String> response = restTemplate.exchange(
				new URL("http://localhost:" + port + "/registration/v1/123").toString(), HttpMethod.POST,
				new HttpEntity<>(headers), String.class);
		assertTrue(StringUtils.contains(response.getBody(), "You have already volunteered for this event"));
	}

}
