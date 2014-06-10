package hr.zemris.ooup.lab4.renderer;

import hr.zemris.ooup.lab4.util.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by alojzije on 10.6.2014..
 */
public class SVGRendererImpl implements Renderer {
    PrintWriter svgExport;

    public SVGRendererImpl(String fileName) {
        try {
            svgExport = new PrintWriter(fileName+ ".svg");
            svgExport.println("<svg  xmlns=\"http://www.w3.org/2000/svg\"");
            svgExport.println("\txmlns:xlink=\"http://www.w3.org/1999/xlink\">\n");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void drawLine(Point s, Point e) {
        if (svgExport != null) {
            svgExport.print("<line ");
            svgExport.print("x1=\""+ s.getX()+"\" y1=\"" + s.getY() + "\" ");
            svgExport.print("x2=\""+ e.getX()+"\" y2=\"" + e.getY() + "\" ");
            svgExport.print("style=\"stroke:#ff0000;\"/>\n\n");
        }

    }

    @Override
    public void fillPolygon(Point[] points) {
        if (svgExport != null) {
            svgExport.print("<polygon points=\"");
            for (Point p : points)
                svgExport.print(p.getX() + "," + p.getY() + " ");
            svgExport.println("\" style=\"stroke:#ff0000; fill:#0000ff;\"/>\n");
        }
    }

    public void close() {
        svgExport.println("\n</svg>");
        svgExport.close();
    }
}
