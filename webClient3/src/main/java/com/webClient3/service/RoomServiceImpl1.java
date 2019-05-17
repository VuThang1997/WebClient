package com.webClient3.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.webClient3.model.Room;
import com.webClient3.utils.GeneralValue;

@Service
@Qualifier("RoomServiceImpl1")
public class RoomServiceImpl1 implements RoomService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl1.class);
	private RestTemplate restTemplate;
	
	@Autowired
	public RoomServiceImpl1(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	public RoomServiceImpl1() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Room> getAllRoom() {
		LOGGER.info("Begin retrieving all room records ==========================");
		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":" + GeneralValue.SERVER_CORE_PORT
				+ "/rooms/all";

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		try {
			ResponseEntity<List<Room>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Room>>() {
					});
			LOGGER.info("Sending RestTemplate ===================");

			List<Room> listRecords = response.getBody();
			LOGGER.info("list records = " + listRecords.size());
			return listRecords;

		} catch (HttpStatusCodeException e) {
			LOGGER.info("retrieve no record ==========================");
			return null;
		}
	}
}
