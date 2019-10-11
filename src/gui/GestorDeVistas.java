package gui;

import java.util.Stack;

import gui.vistas.*;

public class GestorDeVistas {
	private static Stack<Vista> vistas;
	
	private GestorDeVistas() {
		
	}
	
	public static void main(String[] args) {
		vistas = new Stack<>();
		vistas.push(new VistaIngresar());
	}
	
	public static void agregarVista(Vista v) {
		if(!vistas.isEmpty())
			vistas.peek().minimizarVista();
		vistas.push(v);

		if(vistas.size() > 1){
			v.activateBackButton();
		}
	}
	
	public static void retroceder() {
		if(!vistas.isEmpty())
			vistas.pop().finalizarVista();
		
		if(!vistas.isEmpty())
			vistas.peek().abrirVista();
	}

	public static void finalizar(){
		while(!vistas.isEmpty()){
			vistas.pop().finalizarVista();
		}
	}
}
