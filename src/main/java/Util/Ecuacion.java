/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author rc
 */
public class Ecuacion
{

    public ArrayList<Termino> terminos = null;
    public String variable = null;

    public Ecuacion()
    {

    }

    public Ecuacion(String str) throws Exception
    {
        String[] tokens = str.trim().split(" ");

        terminos = new ArrayList<>();

        boolean nextIsNegative = false;
        for (int i = 0; i < tokens.length; i++) {
            var tk = tokens[i];

            switch (tk) {
            case "+" -> {
                if (i + 1 >= tokens.length) {
                    throw new Exception("[ERROR] Faltan operandos para el operador +");
                }
            }
            case "-" -> {
                if (i + 1 >= tokens.length) {
                    throw new Exception("[ERROR] Faltan operandos para el operador -");
                }
                nextIsNegative = true;
            }
            default -> {
                var termino = new Termino(tk);

                if (variable == null) {
                    if (!termino.constant) variable = termino.literal;
                } else {
                    if (!termino.constant && !termino.literal.equals(variable)) {
                        throw new Exception("[ERROR] Multiples variables detectadas");
                    }
                }
                if (nextIsNegative) {
                    nextIsNegative = false;
                    termino.coef *= -1;
                }
                terminos.add(termino);
            }
            }
        }
    }

    @Override
    public String toString()
    {
        var sb = new StringBuilder();
        int size = terminos.size();

        for (int i = 0; i < size; i++) {
            var t = terminos.get(i);
            sb.append(t.toString());
            if (i < size - 1) {
                sb.append(" + ");
            }
        }

        return sb.toString();
    }

    public Ecuacion diferenciar()
    {
        var res = new Ecuacion();
        var newterms = new ArrayList<Termino>();

        Termino termConstante = null;

        for (Termino termino : terminos) {
            if (termino.constant || termino.coef == 0) continue;

            if (termino.exp == 1) {
                if (termConstante == null) {
                    termConstante = new Termino(termino.coef);
                } else {
                    termConstante.coef += termino.coef;
                }
            } else {
                int coef = termino.coef * termino.exp;
                int exp = termino.exp - 1;
                String literal = termino.literal;
                var term = new Termino(coef, literal, exp);
                newterms.add(term);
            }
        }

        if (termConstante != null) newterms.add(termConstante);

        res.terminos = newterms;
        res.variable = this.variable;

        return res;
    }

    public double evaluar(double x)
    {
        double res = 0;

        for (int i = 0; i < terminos.size(); i++) {
            Termino t = terminos.get(i);
            if (t.constant) {
                res += t.coef;
                continue;
            }
            res += t.coef * (Math.pow(x, t.exp));
        }

        return res;
    }

    public double NewtonRaphson(double tol, double start)
    {
        double x0 = start, x1;
        var fprima = this.diferenciar();
        int iteraciones = 100000;

        while (true) {
            --iteraciones;
            if (iteraciones == 0) break;

            double fp = fprima.evaluar(x0);
            if (fp == 0) {
                // encontramos máximo o mínimo local, continuamos desde otro lado
                var rand = new Random();
                int min = -1000000;
                int max = 1000000;
                x0 = rand.nextInt((max - min) + 1) + min;
                continue;
            }

            double f = this.evaluar(x0);
            if (abs(f) < tol) return x0;

            x1 = x0 - f / fp;
            x0 = x1;
        }

        return Double.NaN;
    }
}
