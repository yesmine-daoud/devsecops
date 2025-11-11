package com.example;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        port(9090); // change to any port you want
        get("/", (req, res) -> "Hello from Mini Backend!");
        get("/ping", (req, res) -> "pong");

        // Keep the app running
        awaitInitialization(); // wait for Spark to fully start
        System.out.println("Mini backend is running on port 9090");
    }
}

