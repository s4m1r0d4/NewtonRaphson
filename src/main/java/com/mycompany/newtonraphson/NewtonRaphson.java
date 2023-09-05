/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.newtonraphson;

import Util.Ecuacion;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author rc
 */
public class NewtonRaphson
{

    public static Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    public static Pattern numPattern = Pattern.compile("(-?\\d+(\\.\\d+)?)");

    public static void main(String[] args)
    {
        int t = scanner.nextInt();

        while (t-- > 0) {
            solve();
        }

    }

    private static void solve()
    {
//        System.out.println("\nIngresa los datos ([eq], tol = [tol], [var] = [inicio])");
        var input = scanner.next();

        try {
            var parts = input.split(",");
            generalPartsValidation(parts);
            var eq = new Ecuacion(parts[0]);
            double tol = parseTolerance(parts[1]);
            double inicio = parseInicio(parts[2]);
            var res = eq.NewtonRaphson(tol, inicio);
            if (res == Double.NaN) {
                System.out.println("No se encontró solución real");
            } else {
                System.out.println("res: " + res);
            }
        } catch (Exception ex) {
            Logger.getLogger(NewtonRaphson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void generalPartsValidation(String[] parts) throws Exception
    {
        if (parts.length != 3) {
            throw new Exception("Se esperaban 3 datos");
        }

        if (parts[0].isBlank()) {
            throw new Exception("Equación requerida");
        }
        if (parts[1].isBlank()) {
            throw new Exception("Tolerancia requerida");
        }
        if (parts[2].isBlank()) {
            throw new Exception("Valor inicial requerido");
        }
    }

    private static double parseTolerance(String part) throws Exception
    {
        var tolMatcher = numPattern.matcher(part);
        if (!tolMatcher.find()) {
            var msg = String.format("Tolerancia '%s' inválida", part);
            throw new Exception(msg);
        }
        double tol = Double.parseDouble(tolMatcher.group());
        if (tol <= 0) {
            throw new Exception("La tolerancia debe ser mayor a 0");
        }
        return tol;
    }

    private static double parseInicio(String part) throws Exception
    {
        var variablePattern = Pattern.compile("([a-zA-Z]+)");
        var variableMatcher = variablePattern.matcher(part);
        if (!variableMatcher.find()) {
            throw new Exception("Valor inicial requerido");
        }

        var inicioMatcher = numPattern.matcher(part);
        if (!inicioMatcher.find()) {
            throw new Exception("Valor inicial requerido");
        }
        double inicio = Double.parseDouble(inicioMatcher.group());
        return inicio;
    }

}
