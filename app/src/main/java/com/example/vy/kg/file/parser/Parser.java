package com.example.vy.kg.file.parser;

import com.example.vy.kg.figures.Figure;

import java.util.ArrayList;

/**
 * Created by vy on 2/23/17.
 */

public interface Parser {
    ArrayList<Figure> parse(ArrayList<String> list);
}
