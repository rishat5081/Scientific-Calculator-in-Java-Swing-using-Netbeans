package calculator;

public class Errors extends Exception {

//Primary class constructor without parameters.
public Errors() {
	super();
}

//Constructor which accepts exception message as parameter.
public Errors(String message) {
	super(message);
}
}
