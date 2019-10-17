package debug.gui;

import org.graphstream.algorithm.BellmanFord;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.algorithm.measure.ChartMeasure;
import org.graphstream.algorithm.measure.ChartSeries1DMeasure;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSourceDGS;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.swingViewer.basicRenderer.SwingBasicGraphRenderer;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.io.*;
import java.util.Random;

public class GraphStreamMain {




    public static void main(String[] args) throws ChartMeasure.PlotException, IOException {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");



        Graph graph2 = new DefaultGraph("Bellman-Ford Test");



        //readFromFile(graph2);
        graph2.setAttribute("ui.stylesheet", "node {text-size:20px; text-offset:-20px,-20px;} edge { stroke-color:white; stroke-width:2px;  text-background-color:white; text-size:15px; }");

        //Node b = graph2.getNode("B");
        //b.setAttribute("ui.style", "fill-color:green; size:10px;");


        Generator gen = new GridGenerator();

        gen.addSink(graph2);
        gen.begin();

        for(int i=0; i<4; i++) {
            gen.nextEvents();
        }
        gen.end();

        graph2.getNodeIterator().forEachRemaining(n -> {
            n.addAttribute("label",n.getId());
        });

        graph2.getEdgeSet().forEach(e -> {
            e.setAttribute("label", e.getId());
        });

        graph2.display(false);
        Random random = new Random();
        while(true){

            graph2.getEdgeSet().forEach(e->e.setAttribute("ui.style", "fill-color:green; size:4px;"));
            for(int i = 5+random.nextInt(10); i > 0; --i) {
                graph2.getEdge(random.nextInt(graph2.getEdgeCount())).setAttribute("ui.style", "fill-color:rgb(255, 0,0); size:4px;");
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void readFromFile(Graph graph) throws IOException {
        try(FileInputStream fi = new FileInputStream(new File("debug.gui/map/testMap.dgs")); BufferedInputStream br = new BufferedInputStream(fi)){
            FileSourceDGS source = new FileSourceDGS();
            source.addSink(graph);
            source.readAll(br);
        }
    }
}
