package calculator;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Main {

    /* 
 * A static data field is a field that applies to an entire class of objects.
     */
    public static final String basic = "Basic",
            advance = "Advanced", placement = "Placement",
            mc_text = "MC", mr_text = "MR",
            ms_text = "MS", m_addition = "M+",
            m_subtraction = "M-", BSPC = "\u2190",
            clent_option = "CE", clear_screen = " C ",
            clear_Screen = null, equalSign = "\u003D",
            bracketOpening = "(", bracketClosing = ")",
            bin_text = "Bin", oct_text = "Oct",
            decimal_text = "Dec", hexadeimal_text = "Hex",
            cos_x_text = "cos", sin_x_text = "sin",
            tan_x_text = "tan", log_x_text = "log",
            logN_x_text = "ln", reciprocal_text = "1/x",
            exponential_text = "Exp", negative_text = "\u00b1",
            INTX = "int", PWTX = "10\u02E3",
            F2E = "F-E", SRT = "\u221a",
            CRT = "\u221bx", SQR = "x\u00b2",
            CUB = "x\u00b3", FCT = "n!",
            PRCT = "%", DMS = "dms",
            power_text = "x\u02B8", point_text = ".",
            zero = "0", one = "1",
            two = "2", three = "3",
            four = "4", five = "5",
            six = "6", seven = "7",
            eight = "8", nine = "9",
            A_text = "A", B_text = "B",
            C_text = "C", D_text = "D",
            E_text = "E", F_text = "F",
            multiply = "\u00D7", divide = "\u00F7",
            modulus = "mod", addition = "\u002B",
            subtractionn = "\u2212", PIX = "\u03C0",
            aveerage = "\u03BC", SUM = "\u2211",
            LST = "lst", clearr_screeen = "clr";

    private Syntax_of_Calculator[] calculators = null;
    private String valueTakeen = null, pScText = null,
            nTyppp = null, lastKey = null;

    private boolean screenClear_status = false, grDig = false, hhErrr = false;

    ArrayList number_sets = null;
    public ArrayList<String> results_of_num = null;

// A primary constructor is the one that constructs an object and encapsulates other objects inside it. 
    public Main() {
        calculators = new Syntax_of_Calculator[2];
        number_sets = new ArrayList();
        for (int i = 0; i < calculators.length; i++) {
            calculators[i] = new Syntax_of_Calculator();
        }
        nTyppp = decimal_text;
        valueTakeen = "0";
        initFields();

        results_of_num = new ArrayList<String>();
    }

//  Reinitializes different fields.
    private void initFields() {
        clear();
        pScText = "0";
        screenClear_status = true;
        hhErrr = false;
        lastKey = null;
    }

//Returns a boolean value announce if this calculator has a gathered memory value
    public boolean hasvalueTakeen() {
        return valueTakeen != "0";
    }

//Returns a boolean value announce if the parameter is a digit.
    private boolean isDigit(String val) {
        return val == zero || val == one || val == two || val == three
                || val == four || val == five || val == six || val == seven
                || val == eight || val == nine || val == A_text || val == B_text
                || val == C_text || val == D_text || val == E_text || val == F_text;
    }

//Returns the number of items in the number set.
    public int getSetSize() {
        return number_sets.size();
    }

//Returs the text for the other screen
    public String getSecScreenText() {
        //
        if (lastKey == equalSign) {
            //JOptionPane.showMessageDialog(null, calculators[1] +  "= "+pScText,"Result",JOptionPane.INFORMATION_MESSAGE);
            results_of_num.add(calculators[1] + "= " + pScText);
        }
        return calculators[1] + (lastKey == equalSign ? " =" : "");
    }

//Returnns the text for the main screen.
    public String getpScText() {
        if (!hhErrr && grDig) {
            if (nTyppp == bin_text || nTyppp == hexadeimal_text) {
                return groupText(pScText, 4, " ");
            } else if (nTyppp == oct_text) {
                return groupText(pScText, 3, " ");
            } else {
                return groupText(pScText, 3, ",");
            }
        }
        return pScText;
    }

//Convert a deciimal value to radicaal value.
    private static String dec2rad(String str, String mode) {
        if (mode == bin_text) {
            return dec2rad(str, new BigDecimal(2));
        } else if (mode == oct_text) {
            return dec2rad(str, new BigDecimal(8));
        } else if (mode == hexadeimal_text) {
            return dec2rad(str, new BigDecimal(16));
        }
        return str;
    }

//Converts Bigdecimal
    private static String dec2rad(String str, BigDecimal rad) {
        BigDecimal bd = new BigDecimal(str);
        String ret = "";
        int rem = 0;
        bd = bd.setScale(0, BigDecimal.ROUND_DOWN);
        while (bd.compareTo(BigDecimal.ZERO) > 0) {
            rem = bd.remainder(rad).intValue();
            if (rem >= 0 && rem <= 9) {
                ret = (char) (rem + '0') + ret;
            } else if (rem >= 10) {
                ret = (char) (rem + 'A' - 10) + ret;
            }
            bd = bd.divide(rad, 0, BigDecimal.ROUND_DOWN);
        }
        return ret == "" ? "0" : ret;
    }

//Converts a raddical value to Bigdecimal.
    private static String rad2dec(String str, BigDecimal rad) {
        BigDecimal bd = new BigDecimal(0);
        int chr = 0;

        for (int i = str.length() - 1, p = 0; i >= 0; i--, p++) {
            chr = Character.toUpperCase(str.charAt(i));
            if (chr >= '0' && chr <= '9') {
                bd = bd.add(rad.pow(p).multiply(new BigDecimal(chr - '0')));
            } else if (chr >= 'A' && chr <= 'Z') {
                bd = bd.add(rad.pow(p).multiply(new BigDecimal(chr - 'A' + 10)));
            } else {
                p--;	// Ignore other characters as if they weren't there ;)
            }
        }

        return bd.toPlainString();
    }

//Convert a radical value to decimal.
    private static String rad2dec(String str, String mode) {
        if (mode == bin_text) {
            return rad2dec(str, new BigDecimal(2));
        } else if (mode == oct_text) {
            return rad2dec(str, new BigDecimal(8));
        } else if (mode == hexadeimal_text) {
            return rad2dec(str, new BigDecimal(16));
        }
        return str;
    }

//Returns a arranged text.
    private static String groupText(String str, int cnt, String sep) {
        String ret = "";
        int i = 0, a = 0, z = str.lastIndexOf(".");
        if (z <= 0) {
            z = str.length();
        } else {
            ret = str.substring(z);
        }
        if (str.length() > 0 && str.charAt(0) == '-') {
            a++;
        }

        for (i = z - cnt; i > a; i -= cnt) {
            ret = sep + str.substring(i, i + cnt) + ret;
        }

        return str.substring(0, i + cnt) + ret;
    }

//Force a new item in the Syntax_of_Calculator stack.
    private void push(Object obj) {
        calculators[1].push(obj);
        if (obj instanceof String) {
            calculators[0].push(rad2dec((String) obj, nTyppp));
        } else {
            calculators[0].push(obj);
        }
    }

//Clears all items 
    private void clear() {
        for (int i = 0; i < calculators.length; i++) {
            while (calculators[i].havingValues()) {
                calculators[i].getValue();
            }
        }
    }

//Tone
    private static void beep() {
        java.awt.Toolkit.getDefaultToolkit().beep();
    }
// zeros from a decimal string.

    private String stripZeros(String s) {
        if (s.indexOf(".") >= 0) {
            while (s.length() > 1 && (s.endsWith("0") || s.endsWith("."))) {
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }

//error message .
    private void throwError(String msg) {
        pScText = msg;
        hhErrr = true;
        beep();
    }

//An item offf the Syntax_of_Calculator stack.
    private void pop() {
        calculators[0].getValue();
        calculators[1].getValue();
    }

//input key 
    public void inputKey(String key) {
        if (hhErrr) {
            if (key == clear_screen || key == placement) {
            } else if (key == hexadeimal_text || key == decimal_text || key == oct_text || key == bin_text) {
                initFields();
            } else {
                beep();
                return;
            }
        } else if (lastKey == equalSign && key != BSPC) {
            clear();
        }

        switch (key) {
            case placement:
                grDig = !grDig;
                if (hhErrr) {
                    return;
                }
                break;
            case bin_text:
            case oct_text:
            case decimal_text:
            case hexadeimal_text:
                pScText = dec2rad(rad2dec(pScText, nTyppp), key);
                nTyppp = key;
                screenClear_status = true;
                break;
            case mc_text:
                valueTakeen = "0";
                screenClear_status = true;
                break;
            case mr_text:
                if (valueTakeen != "0") {
                    pScText = dec2rad(valueTakeen, nTyppp);
                } else {
                    pScText = "0";
                    lastKey = zero;
                    return;
                }
                screenClear_status = true;
                break;
            case ms_text  :
                valueTakeen = rad2dec(pScText, nTyppp);
                screenClear_status = true;
                break;
            case m_addition:
                valueTakeen = new BigDecimal(rad2dec(pScText, nTyppp)).add(
                        new BigDecimal(valueTakeen)).toPlainString();
                screenClear_status = true;
                break;
            case m_subtraction:
                valueTakeen = new BigDecimal(rad2dec(valueTakeen, nTyppp)).subtract(
                        new BigDecimal(pScText)).toPlainString();
                screenClear_status = true;
                break;
            case BSPC:
                if (screenClear_status) {
                    beep();
                    return;
                } else if (pScText.length() > 1) {
                    pScText = pScText.substring(0, pScText.length() - 1);
                } else if (pScText != "0") {
                    pScText = "0";
                } else if (calculators[0].havingValues()) {
                    pop();
                } else {
                    beep();
                }
                break;
            case clent_option:
                pScText = "0";
                break;
            case clear_screen:
                this.initFields();
                break;
            case aveerage:
                if (number_sets.size() > 0) {
                    BigDecimal bd = BigDecimal.ZERO;
                    for (int i = 0; i < number_sets.size(); i++) {
                        bd = bd.add((BigDecimal) number_sets.get(i));
                    }
                    pScText = dec2rad(bd.divide(new BigDecimal(number_sets.size()), 32,
                            BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toPlainString(), nTyppp);
                } else {
                    throwError("Invalid Operation: Empty set");
                }
                screenClear_status = true;
                break;
            case SUM:
                BigDecimal bd = BigDecimal.ZERO;
                for (int i = 0; i < number_sets.size(); i++) {
                    bd = bd.add((BigDecimal) number_sets.get(i));
                }
                pScText = dec2rad(bd.toPlainString(), nTyppp);
                screenClear_status = true;
                if (pScText == "0") {
                    lastKey = zero;
                    return;
                }
                break;
            case LST:
                if (pScText != "0") {
                    number_sets.add(new BigDecimal(rad2dec(pScText, nTyppp)));
                } else {
                    beep();
                }
                screenClear_status = true;
                break;
            case clearr_screeen:
                number_sets.clear();
                break;
            case zero:
                if (screenClear_status) {
                    pScText = "0";
                    screenClear_status = false;
                } else if (pScText != "0") {
                    pScText += key;
                } else if (lastKey == zero) {
                    beep();
                }
                break;
            case one:
            case two:
            case three:
            case four:
            case five:
            case six:
            case seven:
            case eight:
            case nine:
            case A_text:
            case B_text:
            case C_text:
            case D_text:
            case E_text:
            case F_text:
                if (screenClear_status || pScText == zero) {
                    pScText = key;
                    screenClear_status = false;
                } else {
                    pScText += key;
                }
                break;
            case point_text:
                if (screenClear_status || pScText == zero) {
                    pScText = zero + point_text;
                    screenClear_status = false;
                } else if (pScText.indexOf(point_text) < 0) {
                    pScText += key;
                } else {
                    beep();
                }
                break;
            case bracketOpening:
            case bracketClosing:
            case SRT:
            case CRT:
            case reciprocal_text:
            case SQR:
            case CUB:
            case FCT:
            case sin_x_text:
            case cos_x_text:
            case tan_x_text:
            case log_x_text:
            case logN_x_text:
            case INTX:
            case negative_text:
            case power_text:
            case multiply:
            case divide:
            case modulus:
            case addition:
            case subtractionn:
                if (pScText != "0" || isDigit(lastKey)) {
                    push(pScText);
                }
                if (key.equals(bracketOpening)) {
                    push(Syntax_of_Calculator.open_bracket);
                } else if (key.equals(bracketClosing)) {
                    push(Syntax_of_Calculator.closing_bracket);
                } else if (key.equals(SRT)) {
                    push(Syntax_of_Calculator.square_root);
                } else if (key.equals(CRT)) {
                    push(Syntax_of_Calculator.cube_root);
                } else if (key.equals(reciprocal_text)) {
                    push(Syntax_of_Calculator.reciprocal);
                } else if (key.equals(SQR)) {
                    push(Syntax_of_Calculator.squared);
                } else if (key.equals(CUB)) {
                    push(Syntax_of_Calculator.cube);
                } else if (key.equals(FCT)) {
                    push(Syntax_of_Calculator.factorial);
                } else if (key.equals(sin_x_text)) {
                    push(Syntax_of_Calculator.sin_x);
                } else if (key.equals(cos_x_text)) {
                    push(Syntax_of_Calculator.cos_x);
                } else if (key.equals(tan_x_text)) {
                    push(Syntax_of_Calculator.tan_x);
                } else if (key.equals(log_x_text)) {
                    push(Syntax_of_Calculator.log_x);
                } else if (key.equals(logN_x_text)) {
                    push(Syntax_of_Calculator.log_n_x);
                } else if (key.equals(INTX)) {
                    push(Syntax_of_Calculator.intagaral);
                } else if (key.equals(negative_text)) {
                    push(Syntax_of_Calculator.negate);
                } else if (key.equals(power_text)) {
                    push(Syntax_of_Calculator.power);
                } else if (key.equals(multiply)) {
                    push(Syntax_of_Calculator.multiply);
                } else if (key.equals(divide)) {
                    push(Syntax_of_Calculator.divide);
                } else if (key.equals(modulus)) {
                    push(Syntax_of_Calculator.modulus);
                } else if (key.equals(addition)) {
                    push(Syntax_of_Calculator.addition);
                } else if (key.equals(subtractionn)) {
                    push(Syntax_of_Calculator.subtraction);
                }
                pScText = "0";
                screenClear_status = false;
                break;
            case equalSign:
                if (pScText != "0" || isDigit(lastKey) || !calculators[0].havingValues()) {
                    push(pScText);
                }
                try {
                    pScText = stripZeros(calculators[0].calculateValue().toPlainString());
                    if (nTyppp == bin_text) {
                        pScText = dec2rad(pScText, bin_text);
                    } else if (nTyppp == oct_text) {
                        pScText = dec2rad(pScText, oct_text);
                    } else if (nTyppp == hexadeimal_text) {
                        pScText = dec2rad(pScText, hexadeimal_text);
                    }
                    screenClear_status = true;
                } catch (Errors e) {
                    throwError("Syntax Error: " + e.getMessage());
                } catch (Wrong_Input e) {
                    throwError("Input Error: " + e.getMessage());
                } catch (Calculator_Operators e) {
                    throwError("Unknown Operator: " + e.getMessage());
                } catch (ArithmeticException e) {
                    throwError("Math Error: " + e.getMessage());
                } catch (Exception e) {
                    throwError("Application Error: " + e.getMessage());
                }

                break;
        }

        lastKey = key;
    }
}
