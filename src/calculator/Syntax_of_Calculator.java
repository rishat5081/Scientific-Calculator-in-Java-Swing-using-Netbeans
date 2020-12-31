package calculator;

import java.util.ArrayList;
import java.math.BigDecimal;

public class Syntax_of_Calculator {

    public static final byte open_bracket = 0x00, closing_bracket = 0x01,
            square_root = 0x02, cube_root = 0x03,
            reciprocal = 0x04, squared = 0x05,
            cube = 0x06, power = 0x07,
            factorial = 0x08, sin_x = 0x10,
            cos_x = 0x11, tan_x = 0x12,
            log_x = 0x13, log_n_x = 0x14,
            intagaral = 0x15, negate = 0x16,
            multiply = 0x20, divide = 0x21,
            modulus = 0x22, addition = 0x23, subtraction = 0x24;
    
//Class instance data fields.
    private ArrayList array_list = null;
    private Syntax_of_Calculator syntax_of_Calculator = null;

//A primary constructor is the one that constructs an object and encapsulates other objects inside it
    public Syntax_of_Calculator() {
        this(null);
    }

//Private constructor
    private Syntax_of_Calculator(Syntax_of_Calculator syntax_of_Calculator) {
        this.array_list = new ArrayList();
        this.syntax_of_Calculator = syntax_of_Calculator;
    }

    /* Returns a boolean value hint is this Syntax_of_Calculator is
   embedded within another Syntax_of_Calculator.
     */
    private boolean hassyntax_of_Calculator() {
        return this.syntax_of_Calculator != null;
    }

//Definition of this Syntax_of_Calculator.
    private Syntax_of_Calculator getsyntax_of_Calculator() {
        return this.syntax_of_Calculator;
    }

    private static boolean operandChecking(Object obje) {
        return obje instanceof BigDecimal;
    }

// Returns a boolean value hint is the pased parametter is an operator.
    private static boolean operatorChecking(Object obje) {
        byte opr = obje instanceof Byte ? (byte) obje : -1;
        return (opr >= open_bracket && opr <= factorial) || (opr >= sin_x && opr <= negate)
                || (opr >= multiply && opr <= subtraction);
    }

// Returns a boolean value hint is the passed parameter is an Syntax_of_Calculator.
    private static boolean definitionChecking(Object obje) {
        return obje instanceof Syntax_of_Calculator;
    }

//returns the factorial of the argument.
    private static BigDecimal factorial(BigDecimal n) {
        BigDecimal retc = BigDecimal.ONE;
        while (n.compareTo(BigDecimal.ONE) > 0) {
            retc = retc.multiply(n);
            n = n.subtract(BigDecimal.ONE);
        }
        return retc;

    }

// A new item onto the internal array_list.
    public Syntax_of_Calculator push(Object... args) {
        for (Object obje : args) {
            this.array_list.add(obje);
        }
        return this;
    }

//the internal stack.
    public boolean havingValues() {
        return array_list.size() > 0;
    }

//Removes the last item from the internal stack.
    public Object getValue() {
        int ind = array_list.size() - 1;
        return (ind >= 0) ? array_list.remove(ind) : null;
    }

//returns the result of this Syntax_of_Calculator.
    public BigDecimal calculateValue()
            throws Errors, Wrong_Input, Calculator_Operators {
        Object obj = null;
        Syntax_of_Calculator sc = this;
        BigDecimal bigDecimal = null, bdd = null;

        for (int i = 0; i < array_list.size(); i++) {
            obj = array_list.get(i);
            if (obj.equals(open_bracket)) {
                if (this.equals(sc)) {
                    sc = new Syntax_of_Calculator(sc);
                    array_list.set(i, sc);
                    continue;
                } else {
                    sc = new Syntax_of_Calculator(sc);
                    sc.getsyntax_of_Calculator().push(sc);
                }
            } else if (obj.equals(closing_bracket)) {
                sc = sc.getsyntax_of_Calculator();
                if (sc == null) {
                    break;
                }
            } else if (this.equals(sc)) {
                if (!(operatorChecking(obj) || definitionChecking(obj))) {
                    array_list.set(i, new BigDecimal(obj.toString()));
                }
                continue;
            } else {
                sc.push(obj);
            }
            array_list.remove(i--);
        }

        if (!this.equals(sc)) {
            throw new Errors("Brackets not Matched");
        }

        for (int i = 0; i < array_list.size(); i++) {
            obj = array_list.get(i);
            if (obj.equals(squared) || obj.equals(cube)) {
                array_list.set(i, new BigDecimal(obj.equals(squared) ? 2 : 3));
                array_list.add(i, power);
                i++;
            }
        }

        for (int i = 0; i < array_list.size(); i++) {
            obj = array_list.get(i);
            if (operatorChecking(obj)) {
                switch ((byte) obj) {
                    case square_root:
                    case cube_root:
                        obj = i + 1 < array_list.size() ? array_list.get(i + 1) : -1;
                        if (obj.equals(square_root) || obj.equals(cube_root)) {
                            continue;
                        } else if (operandChecking(obj)) {
                            bdd = (BigDecimal) obj;
                        } else if (definitionChecking(obj)) {
                            bdd = ((Syntax_of_Calculator) obj).calculateValue();
                        } else {
                            throw new Errors("Operand is missing");
                        }
                        if (bdd.compareTo(BigDecimal.ZERO) < 0) {
                            throw new ArithmeticException("Root of negative no.");
                        }
                        bdd = new BigDecimal(array_list.get(i).equals(square_root)
                                ? Math.sqrt(bdd.doubleValue()) : Math.cbrt(bdd.doubleValue()));
                        array_list.set(i, bdd);
                        array_list.remove(i + 1);
                        i = Math.max(i - 2, -1);
                        break;

                    case power:
                        obj = i + 2 < array_list.size() ? array_list.get(i + 2) : -1;
                        if (obj.equals(power)) {
                            continue;
                        }
                        obj = i > 0 ? array_list.get(i - 1) : -1;
                        if (operandChecking(obj)) {
                            bigDecimal = (BigDecimal) obj;
                        } else if (definitionChecking(obj)) {
                            bigDecimal = ((Syntax_of_Calculator) obj).calculateValue();
                        } else {
                            throw new Errors("Operand is missing");
                        }
                        obj = i + 1 < array_list.size() ? array_list.get(i + 1) : -1;
                        if (operandChecking(obj)) {
                            bdd = (BigDecimal) obj;
                        } else if (definitionChecking(obj)) {
                            bdd = ((Syntax_of_Calculator) obj).calculateValue();
                        } else {
                           throw new Errors("Operand is missing");
                        }
                        if (bdd.compareTo(BigDecimal.ZERO) < 0) {
                            bigDecimal = BigDecimal.ONE.divide(bigDecimal.pow(bdd.abs().intValue()));
                        } else {
                            bigDecimal = bigDecimal.pow(bdd.intValue());
                        }
                        array_list.set(i - 1, bigDecimal);
                        array_list.remove(i);
                        array_list.remove(i);
                        i = Math.max(i - 3, -1);
                        break;

                    case reciprocal:
                        obj = i > 0 ? array_list.get(i - 1) : -1;
                        if (operandChecking(obj)) {
                            bigDecimal = (BigDecimal) obj;
                        } else if (definitionChecking(obj)) {
                            bigDecimal = ((Syntax_of_Calculator) obj).calculateValue();
                        } else {
                            throw new Errors("Operand is missing");
                        }
                        array_list.set(i - 1, BigDecimal.ONE.divide(bigDecimal, 30, BigDecimal.ROUND_DOWN));
                        array_list.remove(i);
                        i -= 1;
                        break;

                    case factorial:
                        obj = i > 0 ? array_list.get(i - 1) : -1;
                        if (operandChecking(obj)) {
                            bigDecimal = (BigDecimal) obj;
                        } else if (definitionChecking(obj)) {
                            bigDecimal = ((Syntax_of_Calculator) obj).calculateValue();
                        } else {
                           throw new Errors("Operand is missing");
                        }
                        array_list.set(i - 1, factorial(bigDecimal.setScale(0, BigDecimal.ROUND_DOWN)));
                        array_list.remove(i);
                        i -= 1;
                        break;
                }
            }
        }

        for (int i = array_list.size() - 1; i >= 0; i--) {
            obj = array_list.get(i);
            if (obj.equals(sin_x) || obj.equals(cos_x) || obj.equals(tan_x)
                    || obj.equals(log_x) || obj.equals(log_n_x) || obj.equals(intagaral)
                    || obj.equals(negate)) {
                obj = i + 1 < array_list.size() ? array_list.get(i + 1) : -1;
                if (operandChecking(obj)) {
                    bdd = (BigDecimal) obj;
                } else if (definitionChecking(obj)) {
                    bdd = ((Syntax_of_Calculator) obj).calculateValue();
                } else {
                    throw new Errors("Operand is missing");
                }
                switch ((byte) array_list.get(i)) {
                    case sin_x:
                        bdd = new BigDecimal(Math.sin(Math.toRadians(bdd.doubleValue())));
                        break;
                    case cos_x:
                        bdd = new BigDecimal(Math.cos(Math.toRadians(bdd.doubleValue())));
                        break;
                    case tan_x:
                        if (bdd.compareTo(new BigDecimal(90)) == 0) {
                            throw new ArithmeticException("Tangent 90");
                        }
                        bdd = new BigDecimal(Math.tan(Math.toRadians(bdd.doubleValue())));
                        break;
                    case log_x:
                        bdd = new BigDecimal(Math.log10(bdd.doubleValue()));
                        break;
                    case log_n_x:
                        bdd = new BigDecimal(Math.log(bdd.doubleValue()));
                        break;
                    case intagaral:
                        bdd = bdd.setScale(0, BigDecimal.ROUND_DOWN);
                        break;
                    case negate:
                        bdd = bdd.negate();
                        break;
                    default:
                        continue;
                }
                if (bdd.scale() > 15) {
                    bdd = bdd.setScale(15, BigDecimal.ROUND_HALF_EVEN);
                }
                array_list.set(i, bdd);
                array_list.remove(i + 1);
            }
        }

        for (int b = 0; b < 2; b++) {
            for (int i = 0; i < array_list.size(); i++) {
                obj = array_list.get(i);
                if (b == 0 && (obj.equals(multiply) || obj.equals(divide) || obj.equals(modulus))
                        || b == 1 && (obj.equals(addition) || obj.equals(subtraction))) {
                    obj = i > 0 ? array_list.get(i - 1) : -1;
                    if (operandChecking(obj)) {
                        bigDecimal = (BigDecimal) obj;
                    } else if (definitionChecking(obj)) {
                        bigDecimal = ((Syntax_of_Calculator) obj).calculateValue();
                    } else {
                        throw new Errors("Operand is missing");
                    }
                    obj = i + 1 < array_list.size() ? array_list.get(i + 1) : -1;
                    if (operandChecking(obj)) {
                        bdd = (BigDecimal) obj;
                    } else if (definitionChecking(obj)) {
                        bdd = ((Syntax_of_Calculator) obj).calculateValue();
                    } else {
                        throw new Errors("Operand is missing");
                    }
                    switch ((byte) array_list.get(i)) {
                        case multiply:
                            bigDecimal = bigDecimal.multiply(bdd);
                            break;
                        case divide:
                            if (bdd.compareTo(BigDecimal.ZERO) == 0) {
                                throw new ArithmeticException("Division by zero");
                            }
                            bigDecimal = bigDecimal.divide(bdd, 30, BigDecimal.ROUND_DOWN);
                            break;
                        case modulus:
                            bigDecimal = bigDecimal.remainder(bdd);
                            break;
                        case addition:
                            bigDecimal = bigDecimal.add(bdd);
                            break;
                        case subtraction:
                            bigDecimal = bigDecimal.subtract(bdd);
                            break;
                    }
                    array_list.set(i - 1, bigDecimal);
                    array_list.remove(i);
                    array_list.remove(i);
                    i -= 1;
                } else if (definitionChecking(obj)) {
                    array_list.set(i, bdd = ((Syntax_of_Calculator) obj).calculateValue());
                    obj = i > 0 ? array_list.get(i - 1) : -1;
                    if (operandChecking(obj)) {
                        array_list.set(i - 1, bdd = bdd.multiply((BigDecimal) obj));
                        array_list.remove(i);
                        i -= 1;
                    }
                    obj = i + 1 < array_list.size() ? array_list.get(i + 1) : -1;
                    if (operandChecking(obj)) {
                        array_list.set(i, bdd.multiply((BigDecimal) obj));
                        array_list.remove(i + 1);
                    }
                }
            }
        }

        while (array_list.size() > 1) {
            obj = array_list.get(0);
            if (definitionChecking(obj)) {
                bigDecimal = ((Syntax_of_Calculator) obj).calculateValue();
            } else if (operandChecking(obj)) {
                bigDecimal = (BigDecimal) obj;
            } else {
                throw new Calculator_Operators();
            }
            obj = array_list.get(1);
            if (definitionChecking(obj)) {
                bdd = ((Syntax_of_Calculator) obj).calculateValue();
            } else if (operandChecking(obj)) {
                bdd = (BigDecimal) obj;
            } else {
                throw new Calculator_Operators();
            }

            array_list.set(0, bigDecimal.multiply(bdd));
            array_list.remove(1);
        }

        if (array_list.size() == 0) {
            throw new Errors("Empty "
                    + (this.hassyntax_of_Calculator() ? "brackets" : "Definition"));
        } else if (definitionChecking(array_list.get(0))) {
            array_list.set(0, ((Syntax_of_Calculator) array_list.get(0)).calculateValue());
        }

        bigDecimal = (BigDecimal) array_list.get(0);
        if (bigDecimal.scale() > 30) {
            bigDecimal = bigDecimal.setScale(30, BigDecimal.ROUND_HALF_EVEN);
        }
        return bigDecimal.stripTrailingZeros();
    }

//the string representation of this Syntax_of_Calculator.
    public String toString() {
        String ret = "";
        Object obj = null;

        for (int i = 0; i < array_list.size(); i++) {
            obj = array_list.get(i);
            if (obj.equals(open_bracket)) {
                ret += "(";
            } else if (obj.equals(closing_bracket)) {
                ret += ")";
            } else if (definitionChecking(obj)) {
                ret += "[" + obj + "]";
            } else if (obj.equals(square_root)) {
                ret += "\u221A";
            } else if (obj.equals(cube_root)) {
                ret += "\u221B";
            } else if (obj.equals(reciprocal)) {
                ret += "\u02C9\u00B9";
            } else if (obj.equals(squared)) {
                ret += "\u00B2";
            } else if (obj.equals(cube)) {
                ret += "\u00B3";
            } else if (obj.equals(power)) {
                ret += " ^ ";
            } else if (obj.equals(factorial)) {
                ret += "!";
            } else if (obj.equals(sin_x)) {
                ret += " sin";
            } else if (obj.equals(cos_x)) {
                ret += " cos";
            } else if (obj.equals(tan_x)) {
                ret += " tan";
            } else if (obj.equals(log_x)) {
                ret += " log";
            } else if (obj.equals(log_n_x)) {
                ret += " ln";
            } else if (obj.equals(intagaral)) {
                ret += "\u222B";
            } else if (obj.equals(negate)) {
                ret += "-";
            } else if (obj.equals(multiply)) {
                ret += " \u00D7 ";
            } else if (obj.equals(divide)) {
                ret += " \u00F7 ";
            } else if (obj.equals(modulus)) {
                ret += " mod ";
            } else if (obj.equals(addition)) {
                ret += " \u002B ";
            } else if (obj.equals(subtraction)) {
                ret += " - ";
            } else if (i > 0 && (array_list.get(i - 1).equals(square_root)
                    || array_list.get(i - 1).equals(cube_root) || array_list.get(i - 1).equals(negate))) {
                ret += obj;
            } else {
                ret += " " + obj;
            }
        }

        ret = ret.replaceAll("\\s\\s+", " ");
        ret = ret.replaceAll("\\(\\s+", "(");
        return ret.trim();
    }
}
