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

    public static void main(String[] args)
    {
        int t = scanner.nextInt();

        while (t-- > 0) {
            solve();
        }

    }

    private static void solve()
    {
        System.out.println("Ingresa los datos ([eq], tol = [tol], [var] = [inicio])");
        var input = scanner.next();

        try {
            var parts = input.split(",");
            
            generalPartsValidation(parts);

            var eq = new Ecuacion(parts[0]);

            var numPattern = Pattern.compile("(\\d+(\\.\\d+)?)");
            var tolMatcher = numPattern.matcher(parts[1]);
            if (!tolMatcher.find()) {
                throw new Exception("Tolerancia '%s' inválida");
            }
            double tol = Double.parseDouble(tolMatcher.group());

            var variablePattern = Pattern.compile("([a-zA-Z]+)");
            var variableMatcher = variablePattern.matcher(parts[2]);
            if (!variableMatcher.find()) {
                throw new Exception("Valor inicial requerido");
            }

            var inicioMatcher = numPattern.matcher(parts[2]);
            if (!inicioMatcher.find()) {
                throw new Exception("Valor inicial requerido");
            }
            double inicio = Double.parseDouble(inicioMatcher.group());

            var res = eq.NewtonRaphson(tol, inicio);

            System.out.println("res: " + res);
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

}
