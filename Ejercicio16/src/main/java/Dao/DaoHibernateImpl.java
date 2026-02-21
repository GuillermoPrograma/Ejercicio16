package Dao;

import java.util.List;

import Model.Alumno;
import Model.Grupo;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import HibernateUtil.HibernateUtil;
public class DaoHibernateImpl implements Dao {
	private static final Logger logger = LogManager.getLogger(DaoHibernateImpl.class);

	@Override
	public int guardarAlumnoBd(Alumno a) {
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			session.beginTransaction();
			
			session.persist(a);
			
			session.getTransaction().commit();
			return 1;
		}
		catch (Exception e) 
		{
			logger.error("error al guardar el alumno ",e.getMessage());
			return -1;
		}
	}

	@Override
	public int guardarGrupo(Grupo g) {
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			session.beginTransaction();
			
			session.persist(g);
			
			session.getTransaction().commit();
			return 1;
		}
		catch (Exception e) 
		{
			logger.error("error al guardar el grupo ",e.getMessage());
			return -1;
		}
	}

	@Override
	public int añadirAlumnoAGrupoPorNia(int nia, int id) {
		Transaction tx = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			tx = session.beginTransaction();
			Alumno a = session.get(Alumno.class, nia);
			Grupo g = session.get(Grupo.class, id);
			if(a == null || g == null)
			{
				return -1;
			}
			
			a.setGrupo(g);
			session.merge(a);
			tx.commit();
			return 1;
		}catch(Exception e) 
		{
			if(tx != null) tx.rollback();
			logger.error("error al añadir alumnoAGrupo",e.getMessage());
			return -1;
			
		}
	}

	@Override
	public int eliminarAlumnoPorApellido(String apellido) {
		Transaction tx = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			tx = session.beginTransaction();
			 int filas = session.createMutationQuery(
		                "delete from Alumno a where a.apellidos like :apellido")
		                .setParameter("apellido", "%" + apellido + "%")
		                .executeUpdate();

		        tx.commit();
		        return filas;
		}catch(Exception e) 
		{if(tx != null) tx.rollback();
			logger.error("Error al eliminar alumno por apellido",  e.getMessage());
			return -1;
		}
		
	}

	@Override
	public int eliminarAlumnoPorNia(int nia) {
		Transaction tx = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			tx = session.beginTransaction();
			Alumno a =session.get(Alumno.class, nia);
			if(a  == null) 
			{
				return -1;
			}
			session.remove(a);
			tx.commit();
			return 1;
		}catch(Exception e) 
		{if(tx != null) tx.rollback();
			logger.error("Error al eliminar el Alumno por Nia",  e.getMessage());
			return -1;
		}
		
	}

	@Override
	public int modificarNombreAPartirDeNiaBD(String nombre, int nia) {
		Transaction tx = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			tx = session.beginTransaction();
			Alumno a = session.get(Alumno.class, nia);
			if(a == null) 
			{
				return -1;
			}
			a.setNombre(nombre);
			session.merge(a);
			tx.commit();
			return 1;
		}catch(Exception e) 
		{if(tx != null) tx.rollback();
			logger.error("Error al Modificar nombre del alumno", e.getMessage());
			return -1;
		}
		
	}

	@Override
	public void insertarListaGrupo(List<Grupo> muestrolista) {
		Transaction tx = null;
		try(Session sesion = HibernateUtil.getSessionFactory().openSession())
		{
			tx = sesion.beginTransaction();
			for(Grupo g : muestrolista) 
			{
				sesion.merge(g);
			}
			tx.commit();
			
		}catch(Exception e) 
		{
			 if (tx != null) tx.rollback();
			logger.error("error al insertar lista grupos", e.getMessage());
		}
		
	}

	@Override
	public List<Alumno> mostrarAlumnos() {
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			String hql = "from Alumno";
			List<Alumno>cargarAlumnos = session.createQuery(hql,Alumno.class).getResultList();
			return cargarAlumnos;
		}catch(Exception e) 
		{
			logger.error("Error al mostrar lista de alumnos", e.getMessage());
			return null;
		}
	
	}

	@Override
	public List<Alumno> mostrarAlumnosPorGrupo(int num) {
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			String hql = "from Alumno where grupo.id = :num";
			List<Alumno>cargarAlumnos = session.createQuery(hql,Alumno.class).setParameter("num", num).getResultList();
			return cargarAlumnos;
		}catch(Exception e) 
		{
			logger.error("Error al mostrar lista de alumnos", e.getMessage());
			return null;
		}
	}

	@Override
	public List<Alumno> mostrarUnAlumnoNia(int num) {
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			String hql = "from Alumno where nia = :num";
			List<Alumno>cargarAlumnos = session.createQuery(hql,Alumno.class).setParameter("num", num).getResultList();
			return cargarAlumnos;
		}catch(Exception e) 
		{
			logger.error("Error al mostrar el alumno", e.getMessage());
			return null;
		}
	}

}
