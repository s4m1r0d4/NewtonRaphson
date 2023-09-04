/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.util.regex.Pattern;

/**
 *
 * @author rc
 */
public class Termino
{

    public int coef;
    public String literal;
    public int exp;
    public boolean constant;

    private static Pattern monomioPattern = Pattern.compile("(-?\\d*)([a-zA-Z]+)(?:\\^(-?\\d+))?");
    private static Pattern constantePattern = Pattern.compile("(-?\\d+)");

    public Termino(int coef, String literal, int exp)
    {
        this.coef = coef;
        this.exp = exp;
        this.literal = literal;
        this.constant = false;
    }

    public Termino(int coef)
    {
        this.coef = coef;
        this.constant = true;
    }

    public Termino(String str) throws Exception
    {
        var monomioMatcher = monomioPattern.matcher(str);
        var constanteMatcher = constantePattern.matcher(str);

        if (monomioMatcher.matches()) {
            int mg = monomioMatcher.groupCount();

            String coefStr = monomioMatcher.group(1);
            String literalStr = monomioMatcher.group(2);
            String expStr = (mg == 3) ? monomioMatcher.group(3) : null;

            this.coef = (coefStr == null || coefStr.isEmpty()) ? 1 : Integer.parseInt(coefStr);
            this.literal = String.valueOf(literalStr);
            this.exp = (expStr == null || expStr.isEmpty()) ? 1 : Integer.parseInt(expStr);
        } else if (constanteMatcher.matches()) {
            var coefStr = constanteMatcher.group();
            this.coef = Integer.parseInt(coefStr);
            this.constant = true;
        } else {
            var msg = String.format("[ERROR] Término invalido '%s'", str);
            throw new Exception(msg);
        }

    }
    
    @Override
    public String toString()
    {      
        if (constant) {
            return String.valueOf(coef);
        }
        var sb = new StringBuilder();
        if (coef != 1) sb.append(coef);
        sb.append(literal);
        if (exp != 1) {
            sb.append('^');
            sb.append(exp);
        }
        return sb.toString();
    }
    
}
