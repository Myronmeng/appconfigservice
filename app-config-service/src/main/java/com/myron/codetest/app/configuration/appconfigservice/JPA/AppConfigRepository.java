package com.myron.codetest.app.configuration.appconfigservice.JPA;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfigRepository extends JpaRepository<AppConfig, AppConfigIdentity>{

	@Query("select jsonConfig from AppConfig where appConfigIdentity.appCode = ?1 order by jsonConfig desc")
	List<String> findByAppCode(String appCode);
}