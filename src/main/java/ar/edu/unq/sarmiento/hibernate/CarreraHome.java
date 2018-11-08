package ar.edu.unq.sarmiento.hibernate;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.sarmiento.modelo.Carrera;

@Component
@Transactional
public class CarreraHome extends AbstractHome<Carrera> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Carrera findByName(String name) {
		return this.getSession().createQuery("FROM Carrera WHERE nombre = : name",Carrera.class)
				.setParameter("nombre", name)
				.getSingleResult();
	}

	public List<Carrera> ListadoDeCarreras(){
		return this.getSession().createQuery("FROM Carrera" , Carrera.class).list();
	}
}
