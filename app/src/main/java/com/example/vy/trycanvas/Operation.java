package com.example.vy.trycanvas;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.MotionEvent;

import com.example.vy.trycanvas.file.FileReadBMP;
import com.example.vy.trycanvas.file.FileWriter;
import com.example.vy.trycanvas.graphics.Drawer;
import com.example.vy.trycanvas.graphics.pixels.Point;

import java.util.ArrayList;

public class Operation {

    private ArrayList<Point> points;
    private ArrayList<Point> motion;//пока только в безье используется
    private int x, y;

    public Operation() {
        points = new ArrayList<>();
        motion = new ArrayList<>();
        x = -1;
    }

    public void operationClear() {
        points.clear();
        motion.clear();
        x = -1;
        y = -1;
    }

    public void operationPen(MotionEvent event, int color, int size, Bitmap bitmap) {
        if (event.getAction() != MotionEvent.ACTION_UP) {
            Drawer.getPixel((int) event.getX(), (int) event.getY(), color, size, bitmap);
        }
    }

    public void operationLine(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getLine(x, y, (int) event.getX(), (int) event.getY(), color, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getLine(x, y, (int) event.getX(), (int) event.getY(), color, bitmapMotion);
            }
        }
    }

    public void operationRound(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            int xs2 = (int) event.getX() - x;
            xs2 *= xs2;
            int ys2 = (int) event.getY() - y;
            ys2 *= ys2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getRound(x, y, (int) Math.sqrt(Math.abs(xs2 + ys2)), color, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getRound(x, y, (int) Math.sqrt(Math.abs(xs2 + ys2)), color, bitmapMotion);
            }
        }
    }

    public void operationEllipse(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getEllipse(x, y,
                        (int) event.getX(), (int) event.getY(), color, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getEllipse(x, y, (int) event.getX(), (int) event.getY(), color, bitmapMotion);
            }
        }
    }

    public void operationEllipseFill(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, int colorFill, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getEllipse(x, y, (int) event.getX(), (int) event.getY(), color, bitmap));
                Drawer.fillFigureZatrav(((int) event.getX() - x) / 2 + x, ((int) event.getY() - y) / 2 + y, colorFill, bitmap);
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getEllipse(x, y, (int) event.getX(), (int) event.getY(), color, bitmapMotion);
            }
        }
    }

    public void operationRect(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getRectangle(x, y, (int) event.getX(), (int) event.getY(), color, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getRectangle(x, y, (int) event.getX(), (int) event.getY(), color, bitmapMotion);
            }
        }
    }

    public void operationBezier(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (points.size() == 0) {
            points.add(new Point((int) event.getX(), (int) event.getY()));
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (points.size() == 1) {
                    points.add(new Point((int) event.getX(), (int) event.getY()));
                } else {// добавляем в предпоследнюю
                    points.add(points.size() - 1, new Point((int) event.getX(), (int) event.getY()));
                }

                bitmapMotion.eraseColor(Color.WHITE);
                field.getFigures().add(Drawer.getBezierLine(parseArrayList(points), color, bitmapMotion));
            } else { //анимация
                motion = new ArrayList<>(points);
                motion.add(points.size() - 1, new Point((int) event.getX(), (int) event.getY()));
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getBezierLine(parseArrayList(motion), color, bitmapMotion);
                motion.clear();

            }
        }
    }

    public void operationErmit(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (points.size() < 3) {
                points.add(new Point((int) event.getX(), (int) event.getY()));
            } else {
                points.add(new Point((int) event.getX(), (int) event.getY()));
                field.getFigures().add(Drawer.getErmit(parseArrayList(points).clone(), bitmap, color));
                points.clear();
            }
        }
    }

    public void operationNURBS(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (points.size() < 3) {
                points.add(new Point((int) event.getX(), (int) event.getY()));
            } else {
                points.add(new Point((int) event.getX(), (int) event.getY()));
                field.getFigures().add(Drawer.getNURBS(parseArrayList(points).clone(), bitmap, color));
                points.clear();
            }
        }
    }

    public void operationBSpline(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (points.size() < 3) {
                points.add(new Point((int) event.getX(), (int) event.getY()));
            } else {
                points.add(new Point((int) event.getX(), (int) event.getY()));
                field.getFigures().add(Drawer.getBSpline(parseArrayList(points).clone(), bitmap, color));
                points.clear();
            }
        }
    }

    public void operationSave(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        FileWriter fl = new FileWriter();
        fl.writeBMP24("test",bitmap);
    }

    public void operationOpen(Field field) {
        FileReadBMP frb = new FileReadBMP();
        field.setBitmap(frb.readBMP24("test"));
    }

    public void operationPolygon3(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, int colorFill, Field field) {
        if (points.size() == 0) {
            points.add(new Point((int) event.getX(), (int) event.getY()));
        } else {
            if (points.size() == 1) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    points.add(new Point((int) event.getX(), (int) event.getY()));
                    field.getFigures().add(Drawer.getLine(points.get(0).getX(), points.get(0).getY(), points.get(1).getX(), points.get(1).getY(), color, bitmap));
                } else {
                    bitmapMotion.eraseColor(Color.WHITE);
                    Drawer.getLine(points.get(0).getX(), points.get(0).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                }
            } else {
                if (points.size() == 2) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        field.getFigures().add(Drawer.getPolygon3(points.get(0).getX(), points.get(1).getX(), (int) event.getX(),
                                points.get(0).getY(), points.get(1).getY(), (int) event.getY(),
                                color, colorFill, bitmap));
                        points.clear();
                    } else {
                        bitmapMotion.eraseColor(Color.WHITE);
                        Drawer.getPolygon3(points.get(0).getX(), points.get(1).getX(), (int) event.getX(),
                                points.get(0).getY(), points.get(1).getY(), (int) event.getY(),
                                color, colorFill, bitmapMotion);
                    }
                }
            }
        }
    }

    public void operationTriangle(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getTriangle(x, y, (int) event.getX(), (int) event.getY(), color, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getTriangle(x, y, (int) event.getX(), (int) event.getY(), color, bitmapMotion);
            }
        }
    }

    public void operationTriangleFill(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, int colorFill, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getTriangle(x, y, (int) event.getX(), (int) event.getY(), colorFill, bitmap));
                Drawer.fillFigureZatrav(((int) event.getX() - x) / 2 + x, ((int) event.getY() - y) / 2 + y, colorFill, bitmap);
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getTriangle(x, y, (int) event.getX(), (int) event.getY(), colorFill, bitmapMotion);
            }
        }
    }

    public void operationZatrav(Bitmap bitmap, MotionEvent event, int colorFill) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawer.fillFigureZatrav((int) event.getX(), (int) event.getY(), colorFill, bitmap);
        }
    }

    public void operationFillRect(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, int colorFill, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getFillRectangle(x, y, (int) event.getX(), (int) event.getY(), color, colorFill, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                field.getFigures().add(Drawer.getFillRectangle(x, y, (int) event.getX(), (int) event.getY(), color, colorFill, bitmapMotion));
            }
        }
    }

    public void operationFillPolygonNGrad(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, int colorFill, Field field, int numNodes) {
        if (points.size() == 0) {
            points.add(new Point((int) event.getX(), (int) event.getY()));
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                points.add(new Point((int) event.getX(), (int) event.getY()));
                if (points.size() < numNodes) {
                    for (int i = 0; i < points.size() - 1; i++) {
                        Drawer.getLine(points.get(i).getX(), points.get(i).getY(), points.get(i + 1).getX(), points.get(i + 1).getY(), color, bitmap);
                    }
                } else {
                    int[] pts = parseArrayList(points);
                    field.getFigures().add(Drawer.getFillPolygonNGrad(pts, bitmap, color, colorFill));
                    points.clear();
                }

            } else {
                if (points.size() != numNodes - 1) {
                    bitmapMotion.eraseColor(Color.WHITE);
                    for (int i = 0; i < points.size() - 1; i++) {
                        Drawer.getLine(points.get(i).getX(), points.get(i).getY(), points.get(i + 1).getX(), points.get(i + 1).getY(), color, bitmapMotion);
                    }
                    Drawer.getLine(points.get(points.size() - 1).getX(), points.get(points.size() - 1).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                } else {
                    bitmapMotion.eraseColor(Color.WHITE);
                    for (int i = 0; i < points.size() - 1; i++) {
                        Drawer.getLine(points.get(i).getX(), points.get(i).getY(), points.get(i + 1).getX(), points.get(i + 1).getY(), color, bitmapMotion);
                    }
                    Drawer.getLine(points.get(points.size() - 1).getX(), points.get(points.size() - 1).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                    Drawer.getLine(points.get(0).getX(), points.get(0).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                }


            }

        }
    }

    public void operationFillPolygonNStatic(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, int colorFill, Field field, int numNodes) {
        if (points.size() == 0) {
            points.add(new Point((int) event.getX(), (int) event.getY()));
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                points.add(new Point((int) event.getX(), (int) event.getY()));
                if (points.size() < numNodes) {
                    for (int i = 0; i < points.size() - 1; i++) {
                        Drawer.getLine(points.get(i).getX(), points.get(i).getY(), points.get(i + 1).getX(), points.get(i + 1).getY(), color, bitmap);
                    }
                } else {
                    int[] pts = parseArrayList(points);
                    field.getFigures().add(Drawer.getFillPolygonNStatic(pts, bitmap, color, colorFill));
                    points.clear();
                }

            } else {
                if (points.size() != numNodes - 1) {
                    bitmapMotion.eraseColor(Color.WHITE);
                    for (int i = 0; i < points.size() - 1; i++) {
                        Drawer.getLine(points.get(i).getX(), points.get(i).getY(), points.get(i + 1).getX(), points.get(i + 1).getY(), color, bitmapMotion);
                    }
                    Drawer.getLine(points.get(points.size() - 1).getX(), points.get(points.size() - 1).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                } else {
                    bitmapMotion.eraseColor(Color.WHITE);
                    for (int i = 0; i < points.size() - 1; i++) {
                        Drawer.getLine(points.get(i).getX(), points.get(i).getY(), points.get(i + 1).getX(), points.get(i + 1).getY(), color, bitmapMotion);
                    }
                    Drawer.getLine(points.get(points.size() - 1).getX(), points.get(points.size() - 1).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                    Drawer.getLine(points.get(0).getX(), points.get(0).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                }


            }

        }
    }


    public int[] parseArrayList(ArrayList<Point> list) {
        int n = list.size() * 2;
        int[] pts = new int[n];

        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            pts[count] = list.get(i).getX();
            count++;
            pts[count] = list.get(i).getY();
            count++;
        }
        return pts;
    }

}
