package calculator;

public class Wrong_Input extends Exception {

//Priimary class constructor without parameters.
public Wrong_Input() {
	super();
}

//Constructor which accepts exception message as parameter.
public Wrong_Input(String message) {
	super(message);
}
}
