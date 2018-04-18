package com.myron.codetest.app.configuration.appconfigservice.rest;


import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myron.codetest.app.configuration.appconfigservice.Exception.AppConfigNotFoundException;
import com.myron.codetest.app.configuration.appconfigservice.JPA.AppConfig;
import com.myron.codetest.app.configuration.appconfigservice.JPA.AppConfigIdentity;
import com.myron.codetest.app.configuration.appconfigservice.JPA.AppConfigRepository;

@RestController
public class AppConfigController {
	
	@Autowired
	private AppConfigRepository appConfigRepository; 
	
	//GET /api/{appCode}/config – return list of available versions in JSON sorted by last modified date in descending order
	@GetMapping("/api/{appCode}/config")
	public List<JsonNode> retrieveAllConfigsByAppCode(@PathVariable String appCode) {
		List<String> listString = appConfigRepository.findByAppCode(appCode);
		ObjectMapper mapper = new ObjectMapper();
		List<JsonNode> listJson= new ArrayList<JsonNode>();
		listString.stream().forEach(p->{
			try {
				//convert string to jasonnode to avoid the backslash return in service response
				JsonNode actualObj = mapper.readTree(p);
			    listJson.add(actualObj);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return listJson;
	}
	
	//GET /api/{appCode}/config/{version} – return JSON document for specified appCode and version
	@GetMapping("/api/{appCode}/config/{version}")
	public String retrieveConfig(@PathVariable String appCode, @PathVariable String version) {
		Optional<AppConfig> appConfig = appConfigRepository.findById(new AppConfigIdentity(appCode, version));

		if (!appConfig.isPresent())
			throw new AppConfigNotFoundException("AppCode-" + appCode + ", Version-" + version);
		
		return appConfig.get().getJsonConfig();
	}

	//POST /api/{appCode}/config/{version} – add new or update existing JSON document for specified appCode and version. JSON document should be included in the request body
	@PostMapping("api/{appCode}/config/{version}")
	public ResponseEntity<Object> createUser(@PathVariable String appCode, @PathVariable String version, @RequestBody String jsonConfig) {
		AppConfig savedAppConfig = appConfigRepository.save( new AppConfig(new AppConfigIdentity(appCode, version), jsonConfig));

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(savedAppConfig.getAppConfigIdentity().getAppCode(), savedAppConfig.getAppConfigIdentity().getVersion())
				.toUri();

		return ResponseEntity.created(location).build();

	}
}
