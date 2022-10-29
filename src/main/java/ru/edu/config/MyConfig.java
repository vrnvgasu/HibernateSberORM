package ru.edu.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.edu.dao.UserRepository;

@Configuration
public class MyConfig {

	// Бин для работы с Hibernate
	@Bean
	public SessionFactory sessionFactory() {
		SessionFactory sessionFactory = new org.hibernate.cfg.Configuration()
				.configure("hibernate.cfg.xml")
				.buildSessionFactory();
		return sessionFactory;
	}

	// наш кастомный репозиторий для работы с сущностью
	@Bean
	public UserRepository userRepository() {
		return new UserRepository();
	}

}
