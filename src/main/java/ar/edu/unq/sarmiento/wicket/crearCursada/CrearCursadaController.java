package ar.edu.unq.sarmiento.wicket.crearCursada;

import java.io.Serializable;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ar.edu.unq.sarmiento.hibernate.AlumnoHome;
import ar.edu.unq.sarmiento.hibernate.CarreraHome;
import ar.edu.unq.sarmiento.hibernate.CursadaHome;
import ar.edu.unq.sarmiento.hibernate.MateriaHome;
import ar.edu.unq.sarmiento.modelo.Alumno;
import ar.edu.unq.sarmiento.modelo.Carrera;
import ar.edu.unq.sarmiento.modelo.Cursada;
import ar.edu.unq.sarmiento.modelo.EstadoCursada;
import ar.edu.unq.sarmiento.modelo.Materia;
import ar.edu.unq.sarmiento.modelo.ModelException;
import ar.edu.unq.sarmiento.wicket.utils.EnumUtils;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional
public class CrearCursadaController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CursadaHome cursadaHome;
	
	@Autowired
	private AlumnoHome alumnoHome;
	
	@Autowired
	private MateriaHome materiaHome;

	private int anio;
	
	private Materia materiaElegida;
	
	private EstadoCursada estado = EstadoCursada.CURSANDO;
	
	private int notaFinal;
	
	private Alumno alumno;
	
	@Autowired
	private CarreraHome carreraHome;
	
	public CrearCursadaController() {
		anio = Year.now().getValue();
	}

	public void agregarCursada(Alumno alumno) {
		Cursada cursada = new Cursada();
		cursada.setAnio(this.getAnio());
		cursada.setEstado(this.getEstado());
		this.validarQueMateriaElegidaEsDeCarreraDe(this.getMateriaElegida(),alumno);
		cursada.setMateria(this.getMateriaElegida());
		cursada.setNotaFinal(this.getNotaFinal());
		alumno.addCursada(cursada);
		carreraHome.saveOrUpdate(alumno.getCarrera());
		alumnoHome.saveOrUpdate(alumno);
		cursadaHome.saveOrUpdate(cursada);	
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public Materia getMateriaElegida() {
		return materiaElegida;
	}

	public void setMateriaElegida(Materia materia) {
		this.materiaElegida = materia;
	}

	public EstadoCursada getEstado() {
		return estado;
	}

	public List<EstadoCursada> getEstadosPosibles(){
		return Arrays.asList(EstadoCursada.values());
	}
	public void setEstado(EstadoCursada estado) {
		this.estado = estado;
	}

	public int getNotaFinal() {
		return notaFinal;
	}

	public void setNotaFinal(int notaFinal) {
		this.notaFinal = notaFinal;
	}
	
	public List<Materia> getTodasLasMaterias(){
		return materiaHome.findByCarrera(alumno.getCarrera());
	}
	
	public void validarQueMateriaElegidaEsDeCarreraDe(Materia materiaElegida,Alumno alumno){
		if(!(this.getTodasLasMaterias().contains(materiaElegida))){
			throw new ModelException(" Error, la materia: "+ materiaElegida.getNombre() + " no pertenece a la carrera: "
		+ alumno.getCarrera().getNombre() + ".");
		}	
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	
	
	
}

