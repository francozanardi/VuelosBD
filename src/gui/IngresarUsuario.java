package gui;

public abstract class IngresarUsuario {
	private String nombreOpcion;
	
	public IngresarUsuario(String opcion) {
		this.nombreOpcion = opcion;
	}
	
	@Override
	public String toString() {
		return nombreOpcion;
	}
	
	public abstract void crearGUI();
}
