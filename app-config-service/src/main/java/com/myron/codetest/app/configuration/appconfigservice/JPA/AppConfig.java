package com.myron.codetest.app.configuration.appconfigservice.JPA;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="appconfig")
public class AppConfig {
	
	@EmbeddedId
	private AppConfigIdentity appConfigIdentity;
	
	@NotNull
	@Column(name="jsonconfig",columnDefinition="CLOB")
	private String jsonConfig;
	
	@NotNull
	@Column(name = "modifiedtime", updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp modifiedTime;

	protected AppConfig() {
		
	}
	
	public AppConfig(AppConfigIdentity appIdentity, String jsonConfig) {
		super();
		this.appConfigIdentity = appIdentity;
		this.jsonConfig = jsonConfig;
		this.modifiedTime = new Timestamp(System.currentTimeMillis());
	}

	public AppConfigIdentity getAppConfigIdentity() {
		return appConfigIdentity;
	}

	public void setAppConfigIdentity(AppConfigIdentity appIdentity) {
		this.appConfigIdentity = appIdentity;
	}

	public String getJsonConfig() {
		return jsonConfig;
	}

	public void setJsonConfig(String jsonConfig) {
		this.jsonConfig = jsonConfig;
	}

	public Timestamp getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	@Override
	public String toString() {
		return "AppConfig [appIdentity=" + appConfigIdentity + ", jsonConfig=" + jsonConfig + "]";
	}
	
	
}

