package com.example.marksmanobserver;

public class BModel {
    private static Model model;
    public static Model getModel() {
        if (model == null) { model = new Model(); }
        return model;
    }
}