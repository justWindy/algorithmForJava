package org.ray.zoj;

import java.util.Scanner;

/**
 * created by ray
 * Date: 21/02/2018
 * Time: 23:22
 */
public class CalDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextInt()) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            System.out.println(a + b);
        }
    }
}
