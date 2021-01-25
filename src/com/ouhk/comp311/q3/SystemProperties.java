package com.ouhk.comp311.q3;

import java.util.Scanner;

public class SystemProperties {

    public static void main(String[] args) {
        System.setProperty("java.security.policy", "SystemProperties.policy");
        System.setSecurityManager(new SecurityManager());

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a property name, or an empty line to quit");

        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if ("".equals(input) || " ".equals(input)) {
                break;
            }

            try {
                String property = System.getProperty(input);
                System.out.println(property);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
