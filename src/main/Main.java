package main;

import view.View;
import controller.Controller;

public class Main {
	public static final String TITLE = "Carcassone";

	@SuppressWarnings("unused")
	public static void main (String[] args) {
		Controller controller = new Controller();
		View display = new View(controller);  
	}
}

