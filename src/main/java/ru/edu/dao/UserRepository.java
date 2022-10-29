package ru.edu.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepository {

	// для Hibernate. Определили файл в MyConfig
	private SessionFactory sessionFactory;

	@Autowired // DI через сеттер
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<UserEntity> getAll() {
		try(Session session = sessionFactory.openSession()) {
			// HQL с условием
			Query<UserEntity> query = session
					.createQuery("from UserEntity e where e.id='1'", UserEntity.class);

//			Query<UserEntity> query = session.createQuery("from UserEntity", UserEntity.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new RuntimeException("Failed to .getAll", e);
		}
	}

	public UserEntity getById(String id) {
		// автоматом сделает session.close(); тк Session extends Closeable
		try(Session session = sessionFactory.openSession()) {
			// сущность внутри сессии попала в persistence context
			UserEntity user = session.get(UserEntity.class, id);
			return user;
		} catch (Exception e) {
			throw new RuntimeException("Failed to .getById id=" + id, e);
		}
	}

	public UserEntity create(UserEntity user) {
		Transaction transaction = null;
		try(Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.persist(user);
			transaction.commit();

			return user;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}

			throw new RuntimeException("Failed to .create user=" + user, e);
		}
	}

	public UserEntity update(UserEntity user) {
		Transaction transaction = null;
		try(Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.merge(user);
			transaction.commit();

			return user;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}

			throw new RuntimeException("Failed to .update user=" + user, e);
		}
	}

	public UserEntity deleteById(String id) {
		Transaction transaction = null;
		try(Session session = sessionFactory.openSession()) {
			UserEntity user = session.get(UserEntity.class, id);

			if (user == null) {
				throw new RuntimeException("User not found");
			}

			transaction = session.beginTransaction();
			session.remove(user);
			transaction.commit();

			return user;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}

			throw new RuntimeException("Failed to .deleteById id=" + id, e);
		}
	}

}
