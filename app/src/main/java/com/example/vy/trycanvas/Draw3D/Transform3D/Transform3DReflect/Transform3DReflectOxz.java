package com.example.vy.trycanvas.Draw3D.Transform3D.Transform3DReflect;

import com.example.vy.trycanvas.Draw3D.Transform3D.Transform3D;
import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.List;

public class Transform3DReflectOxz implements Transform3D{
    @Override
    public void transform(List<Point3D> list, Point3D vec, Point3D center) {
        for(Point3D point: list) {
            point.y -= center.y;
            point.y *= -1;
            point.y += center.y;
        }
    }
}
