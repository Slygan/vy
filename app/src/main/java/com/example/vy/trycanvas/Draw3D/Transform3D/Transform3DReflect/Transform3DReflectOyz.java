package com.example.vy.trycanvas.Draw3D.Transform3D.Transform3DReflect;

import com.example.vy.trycanvas.Draw3D.Transform3D.Transform3D;
import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.List;


public class Transform3DReflectOyz implements Transform3D{
    @Override
    public void transform(List<Point3D> list, Point3D vec, Point3D center) {
        for(Point3D point: list){
            point.x -= center.x;
            point.x *= -1;
            point.x +=center.x;
        }
    }
}
