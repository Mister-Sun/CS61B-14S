/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Hw1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import oracle.jrockit.jfr.events.Bits;

/**
 *
 * @author sun
 */
public class Nuke2 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the word: ");
        String output = br.readLine();
        int i = Bits.length(output);
        char[] x = new char[i/2];
        output.getChars(0, 1, x, 0);
        output.getChars(2, i/2, x, 1);
        System.out.println(x);
    }
}
