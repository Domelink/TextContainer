package com.gmail.l.domelink;

import com.gmail.l.domelink.Cars;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Main {
    final static Class<?> cls = Cars.class;

    public static void main(String[] args) throws IOException, IllegalAccessException {

        Cars cars = new Cars();

        // get data from file
        Map<String, String> carsData = readFile();

        // set data to object 'cars'
        fillCarsWithData(cars, carsData);

        // modify cars data
        modifyCars(cars);

        recCarsDataToFile(cars);
    }

    private static void recCarsDataToFile(Cars cars)
            throws IOException, IllegalAccessException
    {
        FileWriter writer = new FileWriter("d://Cars.txt", true);
        Field[] carsFields = cls.getDeclaredFields();
        for (Field field : carsFields) {
            if (field.isAnnotationPresent(SaveAnnotation.class)) {
                field.setAccessible(true);
                writer.write(field.getName() + "-" + field.get(cars) + ";");
            }
        }
        writer.close();

        System.out.println("\n file 'cars.txt' was generated.");
    }

    private static void modifyCars(Cars cars) {
        cars.setBrand("Ferrari");
        cars.setModel("Enzo");
        cars.setHorsePower(650);

        System.out.println("\nModified car");
        System.out.println("Brand: " + cars.getBrand());
        System.out.println("Model: " + cars.getModel());
        System.out.println("HorsePower: " + cars.getHorsePower());
    }

    private static void fillCarsWithData(Cars cars, Map<String, String> carsData)
            throws IllegalAccessException {

        Field[] carsFields = cls.getDeclaredFields();
        for (Field field : carsFields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(SaveAnnotation.class)) {
                if (field.getType().equals(String.class)) {
                    field.set(cars, carsData.get(field.getName()));
                } else {
                    field.set(cars, Integer.parseInt(carsData.get(field.getName())));
                }
            }
        }

        System.out.println("new car");
        System.out.println("Brand: " + cars.getBrand());
        System.out.println("Model: " + cars.getModel());
        System.out.println("HorsePower: " + cars.getHorsePower());
    }


    private static Map<String, String> readFile() throws IOException {
        Map<String, String> data = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader("d://Cars_in.txt"));

        String text = reader.readLine();
        System.out.println("\ndata from file: " + text + "\n");
        if (text.length() == 0) {
            throw new IOException("File is Empty");
        }

        String[] pairs = text.split(";");
        for (String pair : pairs) {
            String[] field = pair.split("-");

            data.put(field[0], field[1]);
        }

        return data;
    }
}
