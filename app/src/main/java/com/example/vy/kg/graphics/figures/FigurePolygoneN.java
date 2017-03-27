package com.example.vy.kg.graphics.figures;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.example.vy.kg.Controller;
import com.example.vy.kg.graphics.pixels.MyPixelRect;
import java.util.concurrent.CopyOnWriteArrayList;

public class FigurePolygoneN extends Figure {

    public static int nodesNum = 3;

    public CopyOnWriteArrayList<MyPixelRect> draw(Bitmap bmp, int[] points) {
        int color = Controller.colorLine;
        int color2 = Controller.colorFill;
        int a = Color.alpha(color);
        float r = Color.red(color), g = Color.green(color), b = Color.blue(color);
        float r2 = Color.red(color2), g2 = Color.green(color2), b2 = Color.blue(color2);

        Edge[] edges = new Edge[points.length / 2];
        int k = 0;
        int minY = points[1];
        int maxY = minY;
        for (int i = 0; i < points.length; i += 2) {
            if (points[i + 1] < minY) {
                minY = points[i + 1];
            } else {
                if (points[i + 1] > maxY) {
                    maxY = points[i + 1];
                }
            }

            int j = (i + 2) % points.length;
            if (points[i + 1] > points[j + 1]) {
                edges[k++] = new Edge(points[i], points[i + 1], points[j], points[j + 1]);
            } else {
                edges[k++] = new Edge(points[j], points[j + 1], points[i], points[i + 1]);
            }
        }
        int dy = maxY - minY;
        float dr = (r2 - r) / dy;
        float dg = (g2 - g) / dy;
        float db = (b2 - b) / dy;

        sort(edges);
        int[] xs = new int[points.length];
        int iXs = 0;
        int start = 0, end = 0;
        for (int i = maxY; i >= minY; i--) {
            while (end < edges.length && edges[end].getStartY() >= i) {
                end++;
            }

            iXs = 0;
            for (int j = start; j < end; j++) {
                if (edges[j].isNextY()) {
                    xs[iXs++] = edges[j].getX();
                    edges[j].nextY();
                }
            }
            sort(xs, iXs);

            for (int j = 0; j < iXs; j += 2) {
                for (int h = xs[j]; h <= xs[j + 1]; h++) {
                    //if (checkLimits(bmp, h, i)) {
                    pixels.add(new MyPixelRect(1,h, i, Color.argb(a,(int) r, (int) g, (int) b)));
                    //bmp.setPixel(h, i, Color.argb(a,(int) r, (int) g, (int) b));
                    //}
                }
            }

            r += dr;
            g += dg;
            b += db;
        }
        return pixels;
    }

    private Edge[] sort(Edge[] points) {
        int ii, jj, ll;
        Edge minY, kk;
        for (ii = 0; ii < points.length; ii++) {
            minY = points[ll = ii];
            for (jj = ii + 1; jj < points.length; jj++)
                if ((kk = points[jj]).getStartY() > minY.getStartY()) {
                    ll = jj;
                    minY = kk;
                }
            if (ll != ii) {
                points[ll] = points[ii];
                points[ii] = minY;
            }
        }
        return points;
    }

    private int[] sort(int[] points, int n) {
        int ii, jj, ll, minY, kk;
        for (ii = 0; ii < n; ii++) {
            minY = points[ll = ii];
            for (jj = ii + 1; jj < n; jj++)
                if ((kk = points[jj]) < minY) {
                    ll = jj;
                    minY = kk;
                }
            if (ll != ii) {
                points[ll] = points[ii];
                points[ii] = minY;
            }
        }
        return points;
    }

    public class Edge {
        float x, dx;
        int y, dy;

        public Edge(int x1, int y1, int x2, int y2) {
            y = y1;
            x = x1;
            dy = y1 - y2;
            dx = (x2 - x1) / (float) dy;

        }

        public void nextY() {
            dy--;
            x += dx;
        }

        public boolean isNextY() {
            return dy != 0;
        }

        public int getX() {
            return (int) x;
        }

        public int getStartY() {
            return y;
        }
    }

}
