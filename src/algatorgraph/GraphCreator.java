package algatorgraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.jgrapht.Graph;
import org.jgrapht.generate.BarabasiAlbertForestGenerator;
import org.jgrapht.generate.BarabasiAlbertGraphGenerator;
import org.jgrapht.generate.CompleteBipartiteGraphGenerator;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.generate.DirectedScaleFreeGraphGenerator;
import org.jgrapht.generate.EmptyGraphGenerator;
import org.jgrapht.generate.GeneralizedPetersenGraphGenerator;
import org.jgrapht.generate.GnmRandomBipartiteGraphGenerator;
import org.jgrapht.generate.GnmRandomGraphGenerator;
import org.jgrapht.generate.GnpRandomBipartiteGraphGenerator;
import org.jgrapht.generate.GnpRandomGraphGenerator;
import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.generate.GridGraphGenerator;
import org.jgrapht.generate.HyperCubeGraphGenerator;
import org.jgrapht.generate.KleinbergSmallWorldGraphGenerator;
import org.jgrapht.generate.LinearGraphGenerator;
import org.jgrapht.generate.LinearizedChordDiagramGraphGenerator;
import org.jgrapht.generate.NamedGraphGenerator;
import org.jgrapht.generate.PlantedPartitionGraphGenerator;
import org.jgrapht.generate.PruferTreeGenerator;
import org.jgrapht.generate.RandomRegularGraphGenerator;
import org.jgrapht.generate.RingGraphGenerator;
import org.jgrapht.generate.ScaleFreeGraphGenerator;
import org.jgrapht.generate.SimpleWeightedBipartiteGraphMatrixGenerator;
import org.jgrapht.generate.StarGraphGenerator;
import org.jgrapht.generate.WattsStrogatzGraphGenerator;
import org.jgrapht.generate.WheelGraphGenerator;
import org.jgrapht.generate.WindmillGraphsGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.jgrapht.graph.Pseudograph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedPseudograph;
import org.jgrapht.nio.graph6.Graph6Sparse6Importer;
import org.jgrapht.nio.graphml.GraphMLImporter;
import org.jgrapht.util.SupplierUtil;
import java.nio.file.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;

/**
 * GraphCreator is a utility class for generating and importing various types of graphs
 * using the JGraphT library and other classes and methods, that produce JGraphT graphs.
 * It supports generating predefined graph types (named graphs, random graphs, structured graphs), importing graphs from various file formats,
 * creating visual representations of graphs and creating the generation of the html website showing all possible graph generations. 
 * 
 * The class serves as a unified interface for graph creation and manipulation in the ALGator system.
 */
public class GraphCreator {
	public static void main(String[] args) throws IOException {
		GraphCreator.GraphWebsiteGenerator.createWebsite();
	}
	/**
	 *  Static variable to store folder path for import operations
	 */
	static String folderPath;
	/**
     * Enumeration of all supported graph types for generation.
     * Categorized into two groups:
     * - Simple named graphs
     * - Parameterized graphs (Graphs that require parameters to be generated.)
     */
	enum GraphTypes{
	    //SIMPLE GRAPHS
	    BIDIAKIS_CUBE,
	    BLANUSA_FIRST_SNARK,
	    BLANUSA_SECOND_SNARK,
	    BRINKMANN,
	    BUCKY_BALL,
	    BULL,
	    BUTTERFLY,
	    CHVATAL,
	    CLAW,
	    CLEBSCH,
	    COXETER,
	    DESARGUES,
	    DIAMOND,
	    DODECAHEDRON,
	    DOUBLE_STAR_SNARK,
	    DOYLE,
	    DURER,
	    ELLINGHAM_HORTON_54,
	    ELLINGHAM_HORTON_78,
	    ERRERA,
	    FOLKMAN,
	    FRANKLIN,
	    FRUCHT,
	    GEM,
	    GOLDNER_HARARY,
	    GOSSET,
	    GROTZSCH,
	    HEAWOOD,
	    HERSCHEL,
	    HOFFMAN,
	    HOUSE,
	    KITTELL,
	    KLEIN_3_REGULAR,
	    KLEIN_7_REGULAR,
	    KRACKHARDT_KITE,
	    MOBIUS_KANTOR,
	    MOSER_SPINDLE,
	    NAURU,
	    PAPPUS,
	    PETERSEN,
	    POUSSIN,
	    SCHLAFLI,
	    THOMSEN,
	    TIETZE,
	    TUTTE,
	    ZACHARY_KARATE_CLUB,
	    // --- PARAMETERIZED GENERATORS ---
	    BARABASI_ALBERT_FOREST,
	    BARABASI_ALBERT_GRAPH,
	    BARBELL,
	    BROOM,
	    COMPLETE_BIPARTITE,
	    COMPLETE,
	    CROWN,
	    DIRECTED_SCALE_FREE,
	    EMPTY,
	    FAN,
	    GENERALIZED_PETERSEN,
	    GNM_RANDOM_BIPARTITE,
	    GNM_RANDOM_GRAPH,
	    GNP_RANDOM_BIPARTITE,
	    GNP_RANDOM_GRAPH,
	    GRID,
	    HYPERCUBE,
	    KLEINBERG_SMALL_WORLD,
	    LADDER,
	    LINEAR,
	    LINEARIZED_CHORD_DIAGRAM,
	    PLANTED_PARTITION,
	    PRISM,
	    PRUFER_TREE,
	    RANDOM_REGULAR,
	    RING,
	    SCALE_FREE,
	    SIMPLE_WEIGHTED_BIPARTITE_MATRIX,
	    SIMPLE_WEIGHTED_GRAPH_MATRIX,
	    STAR,
	    SUNLET,
	    WATTS_STROGATZ,
	    WHEEL,
	    WINDMILL
	}
	
	static class GraphDescription {
	    public String description;
	    public int n;
	    public int m;

	    public GraphDescription(String description, int n, int m) {
	        this.description = description;
	        this.n = n;
	        this.m = m;
	    }

	    @Override
	    public String toString() {
	        return String.format("Description: %s, vertices (n): %d, edges (m): %d", description, n, m);
	    }
	}
	
	/**
     * Returns a list of all files (graphs) in a specific collection.
     */
    public static String[] getGraphNames(String folderName, String collectionName) {
        File dir = new File(folderName, collectionName);
        if (!dir.exists() || !dir.isDirectory()) {
            return new String[0];
        }
        // Filtriramo, da ne vrnemo log datotek
        return dir.list((f, name) -> !name.equals("import_log.txt"));
    }
    
    /**
     * Returns a random graph from the selected collection.
     */
    public static Graph getRandomGraph(String folderName, String collectionName) {
        String[] names = getGraphNames(folderName, collectionName);
        if (names.length == 0) return null;

        String randomName = names[new Random().nextInt(names.length)];
        File file = new File(new File(folderName, collectionName), randomName);
        
        return importGraphFromFile(file);
    }
    
    /**
     * Returns a description of the graph (name, number of vertices n, number of edges m).
     */
    public static GraphDescription getGraphDescription(String folderName, String collectionName, String graphName) {
        File file = new File(new File(folderName, collectionName), graphName);
        Graph graph = importGraphFromFile(file);
        
        if (graph == null) return null;
        
        String d = "collection: " + collectionName + " , name: " + graphName; 
	    int n = graph.vertexSet().size();
	    int m = graph.edgeSet().size();
        return new GraphDescription(d, n, m);
    }
    
    /**
     * Imports a single graph from a file.
     * Automatically detects format based on extension and handles critical errors like OutOfMemory.
     * * @param file The file to be parsed.
     * @return A Graph object or null if parsing fails
     */
    public static Graph importGraphFromFile(File file) {
        try {
            String name = file.getName().toLowerCase();
            
            // 1. Prepoznavanje formatov
            if (name.endsWith(".graphml")) return parseGraphML(file);
            if (name.endsWith(".lst"))     return parseLst(file);
            if (name.endsWith(".g6") || name.endsWith(".s6")) return parseG6(file);
            if (name.endsWith(".edges") || name.endsWith(".mtx")) return parseNetRepo(file);
            if (name.endsWith(".mat") || name.endsWith(".net") || name.endsWith(".paj")) return parsePajek(file);
            if (name.endsWith(".txt") || name.endsWith(".csv")) return parseStanford(file);
            if (name.endsWith(".edgelist")) return parseMalNetEdgeList(file);
            if (name.endsWith(".graph"))   return parseDimacs10(file);
            
            // Poseben primer za TUDataset (če pokličemo za posamezno mapo, vrne le prvi graf)
            if (file.isDirectory() && new File(file, file.getName() + "_A.txt").exists()) {
                List<Graph<String, DefaultWeightedEdge>> dataset = parseTUDataset(file);
                return (dataset != null && !dataset.isEmpty()) ? dataset.get(0) : null;
            }

            return parseUnknownFile(file);

        } catch (OutOfMemoryError e) {
            System.err.println("OutOfMemoryError processing file: " + file.getName());
            System.gc(); // Poskusimo sprostiti spomin
            return null;
        } catch (Exception e) {
            System.err.println("Error processing file: " + file.getName());
            return null;
        }
    }
	
	/**
     * Public method used to import graphs from a folder containing various graph file formats.
     * It automatically detects file formats, uses appropriate parsers, returns a list of generated graphs
     * and creates a import_log.txt file containing a log of import successes and failures.
     * 
     * Usage example:
     * List&lt;Graph&gt; importedGraphs = GraphCreator.importGraphsFromFolder("path_to_folder");
     * 
     * @param folderPath Path to directory containing graph files
     * @return List of imported graphs as JGraphT Graph objects
     */
	public static List<Graph> importGraphsFromFolder(String folderPath) {
	    List<Graph> graphs = new ArrayList<>();
	    
	    File folder = new File(folderPath);
	    File[] files = folder.listFiles();
	    if (files == null) return graphs;
	    File logFile = new File(folder,"import_log.txt");
	    
	    try (PrintWriter log = new PrintWriter(new FileWriter(logFile,false))) {
	        for (File file : files) {
	            if (file.equals(logFile)) continue;
	            Graph graph = null;
	            List<Graph<String, DefaultWeightedEdge>> graphsDataset = null;
	            boolean success = false;
	            try {
	                if (file.getName().endsWith(".graphml")) {graph = parseGraphML(file);} 
	                else if (file.getName().endsWith(".lst")) {graph = parseLst(file);} 
	                else if (file.getName().endsWith(".g6") || file.getName().endsWith(".s6")) {graph = parseG6(file);} 
	                else if (file.getName().endsWith(".edges") || file.getName().endsWith(".mtx")) {graph = parseNetRepo(file);} 
	                else if (file.getName().endsWith(".mat") || file.getName().endsWith(".net") || file.getName().endsWith(".paj")) {graph = parsePajek(file);} 
	                else if (file.isDirectory() && new File(file, file.getName() + "_A.txt").exists() && new File(file, file.getName() + "_graph_indicator.txt").exists()) {
	                    graphsDataset = parseTUDataset(file);
	                    graphs.addAll(graphsDataset); 
	                    success = !graphsDataset.isEmpty();
	                    System.out.println(graphsDataset.size());
	                } else if (file.getName().endsWith(".txt") || file.getName().endsWith(".csv")) {graph = parseStanford(file);} 
	                else if (file.getName().endsWith(".edgelist")) {graph = parseMalNetEdgeList(file);} 
	                else if (file.getName().endsWith(".graph")) {graph = parseDimacs10(file);} 
	                else {graph = parseUnknownFile(file);}

	                if (graph == null && graphsDataset == null) {
	                    System.out.println("Graph was not added to the list");
	                } else if (graph != null) {
	                    graphs.add(graph);
	                    success = true;
	                    System.out.println(1);
	                }
	            } catch (OutOfMemoryError e) {
	                System.err.println("OutOfMemoryError processing file: " + file.getName());
	                log.println("Out Of Memory Error processing file: " + file.getName());
	                log.flush();
	                e.printStackTrace();
	                graphs.clear();
	                System.gc();
	                continue;
	            } catch (Exception e) {
	                System.out.println("Error processing file: " + file.getName());
	                e.printStackTrace();
	                continue;
	            }
	            
	            log.println(file.getName() + " " + (success ? 1 : 0));
	            log.flush();
	        }
	    } catch (IOException e) {
	        System.out.println("Error writing log file.");
	        e.printStackTrace();
	        return graphs;
	    }
	    
	    System.out.println("Import log written to: " + logFile.getAbsolutePath());
	    return graphs;
	}

	/**
     * Parses a GraphML format file and creates a weighted graph.
     * GraphML is an XML-based format for graph representation.
     * 
     * @param file .graphml file to parse
     * @return A weighted graph or null if parsing fails
     */
	private static Graph<String, DefaultWeightedEdge> parseGraphML(File file) {
	    boolean directed = true;
	    String weightAttr = null;

	    try {
	        List<String> lines = Files.readAllLines(file.toPath());
	        List<String> cleanLines = new ArrayList<>();

	        for (String line : lines) {
	            String lower = line.toLowerCase();
	            if (lower.contains("<!doctype")) continue;
	            if (lower.contains("edgedefault=\"undirected\"")) directed = false;
	            if (lower.contains("for=\"edge\"") && (lower.contains("attr.type=\"double\"") || lower.contains("attr.type=\"float\"") || lower.contains("attr.type=\"int\""))) {
	                java.util.regex.Matcher m = java.util.regex.Pattern.compile("attr\\.name=\"([^\"]+)\"").matcher(line);
	                if (m.find()) weightAttr = m.group(1);
	                else {
	                    m = java.util.regex.Pattern.compile("key id=\"([^\"]+)\"").matcher(line);
	                    if (m.find()) weightAttr = m.group(1);
	                }
	            }
	            cleanLines.add(line);
	        }
	        String sanitizedXml = String.join("\n", cleanLines);
	        Reader reader = new StringReader(sanitizedXml);

	        Graph<String, DefaultWeightedEdge> graph = directed
	                ? new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class)
	                : new WeightedPseudograph<>(DefaultWeightedEdge.class);

	        GraphMLImporter<String, DefaultWeightedEdge> importer = new GraphMLImporter<>();
	        importer.setSchemaValidation(false);
	        importer.setVertexFactory(id -> id);

	        if (weightAttr != null) {
	            importer.setEdgeWeightAttributeName(weightAttr);
	        } else {
	            importer.addEdgeAttributeConsumer((pair, attr) -> {
	                DefaultWeightedEdge e = pair.getFirst();
	                if (!graph.containsEdge(e)) return;
	                graph.setEdgeWeight(e, 1.0);
	            });
	        }
	        importer.importGraph(graph, reader);

	        return graph;

	    } catch (Exception e) {
	        System.out.println("Error parsing GraphML file: " + file.getName());
	        e.printStackTrace();
	        return null;
	    }
	}
	
	/**
     * Parses a .lst format file and creates a graph.
     * 
     * @param file .lst file to parse
     * @return A graph or null if parsing fails
     */
	private static Graph<String, DefaultEdge> parseLst(File file) {
	    Graph<String, DefaultEdge> graph = new Pseudograph<>(DefaultEdge.class);

	    List<String> lines;
	    try {
	        lines = Files.readAllLines(file.toPath());
	    } catch (IOException e) {
	        System.out.println("Error importing lst file: " + file.getName());
	        e.printStackTrace();
	        return null;
	    }

	    for (String line : lines) {
	        line = line.trim();
	        if (line.isEmpty()) continue;

	        String[] parts = line.split(":");
	        if (parts.length != 2) continue;

	        String src = parts[0].trim();
	        graph.addVertex(src);

	        String[] neighbors = parts[1].trim().split("\\s+");
	        for (String tgt : neighbors) {
	            graph.addVertex(tgt);
	            graph.addEdge(src, tgt);
	        }
	    }

	    return graph;
	}
	
	/**
     * Parses a .g6 or .s6 format file (graph6/sparse6 format) and creates a graph.
     * 
     * @param file .g6/.s6 file to parse
     * @return A graph or null if parsing fails
     */
	public static Graph<String, DefaultEdge> parseG6(File file){
	    Graph<String, DefaultEdge> graph = new Pseudograph<>(SupplierUtil.createStringSupplier(),SupplierUtil.createDefaultEdgeSupplier(),false);
	    Graph6Sparse6Importer<String, DefaultEdge> importer = new Graph6Sparse6Importer<>();

	    try (FileReader fr = new FileReader(file)) {
	        importer.importGraph(graph, fr);
	    }
	    catch(IOException e) {
	    	System.out.println("Error importing .g6/.s6 file: " + file.getName());
	        e.printStackTrace();
	        return null;
	    }

	    return graph;
	}

	/**
     * Parses network repository format files (.edges, .mtx) and creates a graph.
     * 
     * @param file .edges/.mtx file to parse
     * @return A weighted graph or null if parsing fails
     */
	private static Graph<String, DefaultWeightedEdge> parseNetRepo(File file) {

	    boolean directed = false;
	    boolean weighted = false;

	    Graph<String, DefaultWeightedEdge> graph = null;

	    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

	        String line;
	        int lineIndex = 0;

	        while ((line = br.readLine()) != null) {

	            line = line.trim();
	            if (line.isEmpty()) continue;

	            if (lineIndex < 2 && line.startsWith("%")) {

	                String header = line.toLowerCase();

	                if (header.contains("asym") || header.contains("general")) directed = true;
	                if (header.contains("sym") || header.contains("symmetric")) directed = false;

	                if (header.contains("weighted") || header.contains("real")) weighted = true;
	                if (header.contains("unweighted") || header.contains("pattern")) weighted = false;

	                lineIndex++;
	                continue;
	            }

	            if (graph == null) {
	                graph = directed
	                        ? new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class)
	                        : new WeightedPseudograph<>(DefaultWeightedEdge.class);
	            }

	            if (line.startsWith("%") || line.startsWith("#")) continue;

	            String[] parts = line.split("[,\\s]+");
	            if (parts.length < 2) continue;

	            String src = parts[0];
	            String dst = parts[1];

	            double weightValue = 1.0;

	            if (parts.length >= 3) {
	                weighted = true;
	                try {
	                    weightValue = Double.parseDouble(parts[2]);
	                } catch (NumberFormatException ignore) {}
	            }

	            graph.addVertex(src);
	            graph.addVertex(dst);

	            DefaultWeightedEdge edge = graph.addEdge(src, dst);
	            if (edge != null) graph.setEdgeWeight(edge, weightValue);

	            lineIndex++;
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }

	    return graph;
	}

	/**
     * Parses Pajek format files (.net, .mat, .paj) and creates a graph.
     * 
     * @param file .net/.mat/.paj format file to parse
     * @return A weighted graph or null if parsing fails
     */
	private static Graph<String, DefaultWeightedEdge> parsePajek(File file) {
	    boolean directed = false;
	    boolean weighted = false;
	    int edgeType = -1; // 0 = edges, 1 = arcs, 2 = matrix

	    List<String> lines = new ArrayList<>();

	    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            line = line.trim();
	            if (line.isEmpty() || line.startsWith("%")) continue;
	            lines.add(line);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }

	    int verticesCount = 0;
	    Map<Integer, String> vertexLabels = new HashMap<>();

	    int i = 0;
	    for (; i < lines.size(); i++) {
	        String l = lines.get(i).toLowerCase();

	        if (l.startsWith("*vertices")) {
	            String[] parts = l.split("\\s+");
	            if (parts.length >= 2) {
	                try {
	                    verticesCount = Integer.parseInt(parts[1]);
	                } catch (NumberFormatException ignored) {}
	            }

	            int j = i + 1;
	            for (; j < lines.size(); j++) {
	                String vline = lines.get(j);
	                if (vline.startsWith("*")) break;

	                String[] vp = vline.trim().split("\\s+", 2);
	                if (vp.length >= 1) {
	                    try {
	                        int id = Integer.parseInt(vp[0]);
	                        String label = (vp.length == 2)
	                                ? vp[1].replaceAll("^\"|\"$", "")
	                                : String.valueOf(id);
	                        vertexLabels.put(id, label);
	                    } catch (NumberFormatException ignored) {}
	                }
	            }
	            i = j - 1;
	        }

	        if (l.startsWith("*edges")) {
	            directed = false;
	            edgeType = 0;
	            i++;
	            break;
	        }
	        if (l.startsWith("*arcs")) {
	            directed = true;
	            edgeType = 1;
	            i++;
	            break;
	        }
	        if (l.startsWith("*matrix")) {
	            directed = false;
	            edgeType = 2;
	            i++;
	            break;
	        }
	    }

	    Graph<String, DefaultWeightedEdge> graph =
	            directed
	                    ? new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class)
	                    : new WeightedPseudograph<>(DefaultWeightedEdge.class);

	    for (Map.Entry<Integer, String> entry : vertexLabels.entrySet()) {
	        Integer id = entry.getKey();
	        String label = entry.getValue();
	        String vertexName = id.toString().equals(label) ? label : id + ":" + label;
	        graph.addVertex(vertexName);
	    }

	    switch (edgeType) {
	        case 0: // *Edges (undirected)
	        case 1: // *Arcs (directed)
	            for (; i < lines.size(); i++) {
	                String line = lines.get(i);
	                if (line.startsWith("*")) break;
	                if (line.isEmpty()) continue;

	                String[] parts = line.split("[,\\s]+");
	                if (parts.length < 2) continue;

	                String src = vertexLabels.getOrDefault(Integer.parseInt(parts[0]), parts[0]);
	                String dst = vertexLabels.getOrDefault(Integer.parseInt(parts[1]), parts[1]);
	                double weight = 1.0;

	                if (parts.length >= 3) {
	                    weighted = true;
	                    try {
	                        weight = Double.parseDouble(parts[2]);
	                    } catch (NumberFormatException ignored) {}
	                }

	                graph.addVertex(src);
	                graph.addVertex(dst);
	                DefaultWeightedEdge e = graph.addEdge(src, dst);
	                if (e != null) graph.setEdgeWeight(e, weight);
	            }
	            break;

	        case 2: // *Matrix (adjacency matrix)
	            int row = 0;
	            for (int j = i; j < lines.size(); j++) {
	                String line = lines.get(j);
	                if (line.startsWith("*")) break;

	                String[] nums = line.trim().split("\\s+");
	                for (int col = 0; col < nums.length; col++) {
	                    try {
	                        double val = Double.parseDouble(nums[col]);
	                        if (val != 0.0) {
	                            String src = vertexLabels.getOrDefault(row + 1, String.valueOf(row + 1));
	                            String dst = vertexLabels.getOrDefault(col + 1, String.valueOf(col + 1));
	                            graph.addVertex(src);
	                            graph.addVertex(dst);
	                            DefaultWeightedEdge e = graph.addEdge(src, dst);
	                            if (e != null) graph.setEdgeWeight(e, val);
	                        }
	                    } catch (NumberFormatException ignored) {}
	                }
	                row++;
	            }
	            break;
	    }

	    return graph;
	}

	/**
     * Parses Stanford/SNAP format files (.txt, .csv) and creates a graph.
     * 
     * @param file .txt/.csv format file to parse
     * @return A weighted graph or null if parsing fails
     */
	private static Graph<String, DefaultWeightedEdge> parseStanford(File file) {
	    boolean directed = false;
	    boolean decidedDirection = false;

	    Graph<String, DefaultWeightedEdge> graph = null;

	    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            line = line.trim();
	            if (line.isEmpty()) continue;

	            if (line.startsWith("#") || line.startsWith("%")) {
	                String lower = line.toLowerCase();
	                if (!decidedDirection && lower.contains("directed")) {
	                    directed = true;
	                    decidedDirection = true;
	                } else if (!decidedDirection && lower.contains("undirected")) {
	                    directed = false;
	                    decidedDirection = true;
	                }
	                continue;
	            }
	            
	            String[] parts = line.split("[,\\s]+");
	            if (parts.length < 2) continue;

	            int offset = 0;
	            if (parts[0].matches("[a-zA-Z]\\d+")) {
	                offset = 1;
	            }

	            if (parts.length < offset + 2) continue;

	            String src = parts[offset];
	            String dst = parts[offset + 1];

	            if (graph == null) {
	                graph = directed
	                    ? new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class)
	                    : new WeightedPseudograph<>(DefaultWeightedEdge.class);
	            }

	            String weightStr = null;
	            if (parts.length > offset + 2) {
	                weightStr = String.join(" ", Arrays.copyOfRange(parts, offset + 2, parts.length));
	            }

	            double weight = 1.0;
	            boolean numeric = false;
	            Object attr = null;

	            if (weightStr != null) {
	                try {
	                    weight = Double.parseDouble(weightStr);
	                    numeric = true;
	                    attr = weight;
	                } catch (NumberFormatException e) {
	                    attr = weightStr;
	                }
	            }

	            graph.addVertex(src);
	            graph.addVertex(dst);
	            DefaultWeightedEdge e = graph.addEdge(src, dst);

	            if (e != null) {
	                graph.setEdgeWeight(e, numeric ? weight : 1.0);
	            }
	        }

	    } catch (IOException e) {
	        System.out.println("Error parsing Stanford/SNAP file: " + file.getName());
	        e.printStackTrace();
	        return null;
	    }

	    return graph;
	}

	/**
     * Parses TU Dataset format files (Can return multiple graphs in a List).
     * 
     * @param folder The folder containing TU dataset files (must contain at least _A.txt 
     * and _graph_indicator.txt files for succesfull parsing)
     * @return List of weighted graphs parsed from the dataset
     */
	private static List<Graph<String, DefaultWeightedEdge>> parseTUDataset(File folder) {

	    List<Graph<String, DefaultWeightedEdge>> graphs = new ArrayList<>();

	    // Core files
	    File AFile = new File(folder, folder.getName() + "_A.txt");
	    File indicatorFile = new File(folder, folder.getName() + "_graph_indicator.txt");

	    // Optional files
	    File graphLabelsFile = new File(folder, folder.getName() + "_graph_labels.txt");
	    File nodeLabelsFile = new File(folder, folder.getName() + "_node_labels.txt");
	    File nodeAttributesFile = new File(folder, folder.getName() + "_node_attributes.txt");
	    File edgeLabelsFile = new File(folder, folder.getName() + "_edge_labels.txt");

	    if (!AFile.exists() || !indicatorFile.exists()) {
	        System.out.println("Missing core TU files in: " + folder.getName());
	        return graphs;
	    }

	    try {

	        List<Integer> graphIndicator =
	                Files.readAllLines(indicatorFile.toPath())
	                        .stream()
	                        .map(Integer::parseInt)
	                        .collect(Collectors.toList());

	        int numNodes = graphIndicator.size();
	        int numGraphs = Collections.max(graphIndicator);

	        List<Graph<String, DefaultWeightedEdge>> gList = new ArrayList<>(numGraphs);
	        for (int i = 0; i < numGraphs; i++) {
	            gList.add(new WeightedPseudograph<>(DefaultWeightedEdge.class));
	        }

	        List<String> nodeLabels = null;
	        if (nodeLabelsFile.exists()) {
	            nodeLabels = Files.readAllLines(nodeLabelsFile.toPath());
	        }

	        List<String> nodeAttributes = null;
	        if (nodeAttributesFile.exists()) {
	            nodeAttributes = Files.readAllLines(nodeAttributesFile.toPath());
	        }

	        List<String> edgeLabels = null;
	        if (edgeLabelsFile.exists()) {
	            edgeLabels = Files.readAllLines(edgeLabelsFile.toPath());
	        }

	        try (BufferedReader br = new BufferedReader(new FileReader(AFile))) {
	            String line;
	            int edgeIndex = 0;

	            while ((line = br.readLine()) != null) {
	                line = line.trim();
	                if (line.isEmpty()) continue;

	                String[] p = line.split("[,\\s]+");
	                if (p.length < 2) continue;

	                int src = Integer.parseInt(p[0]);
	                int dst = Integer.parseInt(p[1]);

	                int gID_src = graphIndicator.get(src - 1);
	                int gID_dst = graphIndicator.get(dst - 1);

	                // Skip edges that cross between graphs
	                if (gID_src != gID_dst) {
	                    edgeIndex++;
	                    continue;
	                }

	                Graph<String, DefaultWeightedEdge> g = gList.get(gID_src - 1);

	                String v1 = String.valueOf(src);
	                String v2 = String.valueOf(dst);

	                g.addVertex(v1);
	                g.addVertex(v2);

	                DefaultWeightedEdge e = g.addEdge(v1, v2);

	                if (e != null) {
	                	// default weight
	                    double w = 1.0;

	                    if (edgeLabels != null && edgeIndex < edgeLabels.size()) {
	                        String wStr = edgeLabels.get(edgeIndex).trim();
	                        try {
	                            w = Double.parseDouble(wStr);
	                        } catch (Exception ignore) {
	                            w = 1.0;
	                        }
	                    }

	                    g.setEdgeWeight(e, w);
	                }

	                edgeIndex++;
	            }
	        }

	        graphs.addAll(gList);

	    } catch (Exception e) {
	        System.out.println("Error parsing TUDataset: " + folder.getName());
	        e.printStackTrace();
	    }

	    return graphs;
	}

	/**
     * Parses MalNet .edgelist format files and creates a graph.
     * 
     * @param file .edgelist file to parse
     * @return A weighted graph or null if parsing fails
     */
	private static Graph<String, DefaultWeightedEdge> parseMalNetEdgeList(File file) {
	    Graph<String, DefaultWeightedEdge> graph = new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class);

	    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            line = line.trim();
	            if (line.isEmpty() || line.startsWith("#")) continue;

	            int commentIndex = line.indexOf('#');
	            if (commentIndex >= 0) line = line.substring(0, commentIndex).trim();
	            if (line.isEmpty()) continue;

	            String[] parts = line.split("\\s+");
	            if (parts.length < 2) continue;

	            String src = parts[0];
	            String dst = parts[1];

	            double weight = 1.0;
	            if (parts.length >= 3) {
	                try {
	                    weight = Double.parseDouble(parts[2]);
	                } catch (NumberFormatException ignored) {
	                    weight = 1.0;
	                }
	            }

	            graph.addVertex(src);
	            graph.addVertex(dst);

	            DefaultWeightedEdge e = graph.addEdge(src, dst);
	            if (e != null) graph.setEdgeWeight(e, weight);
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading MalNet edge list file: " + file.getName());
	        e.printStackTrace();
	        return null;
	    }

	    return graph;
	}

	/**
     * Parses SuiteSparse Matrix Collection format files and creates a graph.
     * 
     * @param file .graph format file to parse
     * @return A weighted graph or null if parsing fails
     */
	private static Graph<String, DefaultWeightedEdge> parseDimacs10(File file) {
	    boolean directed = false;
	    boolean mentionsMatrix = false;

	    List<String> lines = new ArrayList<>();
	    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            line = line.trim();
	            if (line.isEmpty()) continue;

	            if (line.startsWith("#") || line.startsWith("%")) {
	                String lower = line.toLowerCase();
	                if (lower.contains("directed")) directed = true;
	                if (lower.contains("matrix")) mentionsMatrix = true;
	                continue;
	            }
	            lines.add(line);
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading file: " + file.getName());
	        e.printStackTrace();
	        return null;
	    }

	    Graph<String, DefaultWeightedEdge> graph =
	            directed
	                    ? new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class)
	                    : new WeightedPseudograph<>(DefaultWeightedEdge.class);

	    int nodeIndex = 1;
	    boolean skipHeader = mentionsMatrix || (lines.size() > 0 && lines.get(0).split("\\s+").length >= 2);

	    for (int i = 0; i < lines.size(); i++) {
	        String line = lines.get(i);
	        if (line.isEmpty() || line.startsWith("#") || line.startsWith("%")) continue;

	        if (i == 0 && skipHeader) continue;

	        String[] parts = line.split("\\s+");
	        if (parts.length < 2) continue;

	        String src = parts[0];
	        String dst = parts[1];
	        double weight = 1.0;

	        if (parts.length >= 3) {
	            String weightToken = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
	            try {
	                weight = weightToken.contains(".") ? Double.parseDouble(weightToken) : Integer.parseInt(weightToken);
	            } catch (NumberFormatException ignored) {
	                weight = 1.0;
	            }
	        }

	        graph.addVertex(src);
	        graph.addVertex(dst);

	        DefaultWeightedEdge e = graph.addEdge(src, dst);
	        if (e != null) graph.setEdgeWeight(e, weight);
	    }

	    return graph;
	}

	/**
     * Generic parser for unknown file formats.
     * Attempts to parse as either edge list or adjacency matrix.
     * 
     * @param file The unknown format file to parse
     * @return A weighted graph if parsing succeeds, otherwise null
     */
	private static Graph<String, DefaultWeightedEdge> parseUnknownFile(File file) {
	    Graph<String, DefaultWeightedEdge> graph = new WeightedPseudograph<>(DefaultWeightedEdge.class);

	    List<String> lines;
	    try {
	        lines = Files.readAllLines(file.toPath());
	    } catch (IOException e) {
	        System.out.println("Error reading file: " + file.getName());
	        e.printStackTrace();
	        return null;
	    }

	    List<String> realLines = new ArrayList<>();
	    for (String line : lines) {
	        line = line.trim();
	        if (!line.isEmpty() && !line.startsWith("#") && !line.startsWith("%")) {
	            realLines.add(line);
	        }
	    }

	    if (realLines.isEmpty()) return graph;

	    //edge list if every line has 2 or 3 parts, else adjacency matrix
	    boolean treatAsEdgeList = true;
	    for (String line : realLines) {
	        String[] parts = line.split("[,\\s]+");
	        if (parts.length < 2 || parts.length > 3) {
	            treatAsEdgeList = false;
	            break;
	        }
	    }

	    if (treatAsEdgeList) {
	        for (String line : realLines) {
	            String[] parts = line.split("[,\\s]+");
	            if (parts.length < 2) continue;

	            String src = parts[0];
	            String dst = parts[1];
	            double weight = (parts.length == 3) ? parseDoubleSafe(parts[2], 1.0) : 1.0;

	            graph.addVertex(src);
	            graph.addVertex(dst);

	            DefaultWeightedEdge e = graph.addEdge(src, dst);
	            if (e != null) graph.setEdgeWeight(e, weight);
	        }
	    } else {
	        for (int row = 0; row < realLines.size(); row++) {
	            String[] nums = realLines.get(row).split("[,\\s]+");
	            String src = String.valueOf(row + 1);
	            graph.addVertex(src);

	            for (int col = 0; col < nums.length; col++) {
	                double val = parseDoubleSafe(nums[col], 0.0);
	                if (val != 0.0) {
	                    String dst = String.valueOf(col + 1);
	                    graph.addVertex(dst);

	                    DefaultWeightedEdge e = graph.addEdge(src, dst);
	                    if (e != null) graph.setEdgeWeight(e, val);
	                }
	            }
	        }
	    }

	    return graph;
	}
	
	 /**
     * Double parsing with default value.
     * 
     * @param s The string to parse as double
     * @param defaultVal Default value if parsing fails
     * @return Parsed double or default value
     */
	private static double parseDoubleSafe(String s, double defaultVal) {
	    try {
	        return Double.parseDouble(s);
	    } catch (NumberFormatException e) {
	        return defaultVal;
	    }
	}
	
	/**
     * Generates a JGraphT graph instance, based on the given arguments.
     * 
     * The graph type is determined by the first element in the {@code args} array, which must
     * match one of the enumerated {@link GraphTypes} values. Subsequent elements are interpreted
     * as type-specific parameters (e.g., number of vertices, edges, probabilities, etc.).
     * 
     * Supported graph types include both named graphs (Petersen, Karate Club, etc.) and
     * parameterized generators (Complete, Path, Scale-Free, etc.).
     * 
     * Example usage 1: Complete graph
     * String[] argsComplete = {"COMPLETE", "5"};
     * Graph&lt;Integer, DefaultEdge&gt; completeGraph = generateGraph(argsComplete);
     *
     * Example usage 2: Directed scale-free graph
     * String[] argsDirected = {"DIRECTED_SCALE_FREE", "0.3", "0.5", "0.1", "0.1", "50", "20"};
     * Graph&lt;Integer, DefaultEdge&gt; directedScaleFreeGraph = generateGraph(argsDirected);
     *
     * @param args Arguments specifying the graph type and its parameters:
     *             <ul>
     *             <li>[0] - Graph type (from {@link GraphTypes} enum)</li>
     *             <li>[1..n] - Type-specific parameters ({@link getGraphDescription} for details)</li>
     *             </ul>
     * @return A {@link Graph} of type {@code Graph<Integer, DefaultEdge>} representing the generated graph,
     *         or {@code null} if the arguments are invalid.
     */
	public static Graph<Integer,DefaultWeightedEdge> generateGraph(String []args){

		if (args.length == 0) {
			System.out.println("Please enter at least one argument. Vnesite vsaj en argument.");
			return null;
		}

		String arg0Upper = args[0].toUpperCase();
		GraphTypes graphType = GraphTypes.valueOf(arg0Upper);

		Graph<Integer, DefaultWeightedEdge> graph = null;

		try {
		
			switch(graphType) {
			    //non-parameterized named graphs
			    case BIDIAKIS_CUBE:
			    	graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer,DefaultWeightedEdge>().generateBidiakisCubeGraph(graph);
			        break;
			    
			    case BLANUSA_FIRST_SNARK:
			    	graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer,DefaultWeightedEdge>().generateBlanusaFirstSnarkGraph(graph);
			        break;
		
			    case BLANUSA_SECOND_SNARK:
			    	graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer,DefaultWeightedEdge>().generateBlanusaSecondSnarkGraph(graph);
			        break;
		
			    case BRINKMANN:
			    	graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer,DefaultWeightedEdge>().generateBrinkmannGraph(graph);
			        break;
		
			    case BUCKY_BALL:
			    	graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer,DefaultWeightedEdge>().generateBuckyBallGraph(graph);
			        break;
			    
			    case BULL:
			    	graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer,DefaultWeightedEdge>().generateBullGraph(graph);
			        break;
			    
			    case BUTTERFLY:
			    	graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer,DefaultWeightedEdge>().generateButterflyGraph(graph);
			        break;
		
			    case CHVATAL:
			    	graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer,DefaultWeightedEdge>().generateChvatalGraph(graph);
			        break;
			    
			    case CLAW:
			    	graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer,DefaultWeightedEdge>().generateClawGraph(graph);
			        break;
			    
			    case CLEBSCH:
			    	graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer,DefaultWeightedEdge>().generateClebschGraph(graph);
			        break;
		
			    case COXETER:
			    	graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer,DefaultWeightedEdge>().generateCoxeterGraph(graph);
			        break;
		
			    case DESARGUES:
			    	graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer,DefaultWeightedEdge>().generateDesarguesGraph(graph);
			        break;
		
			    case DIAMOND:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateDiamondGraph(graph);
			        break;
		
			    case DODECAHEDRON:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateDodecahedronGraph(graph);
			        break;
		
			    case DOUBLE_STAR_SNARK:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateDoubleStarSnarkGraph(graph);
			        break;
		
			    case DOYLE:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateDoyleGraph(graph);
			        break;
		
			    case DURER:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateDürerGraph(graph);
			        break;
		
			    case ELLINGHAM_HORTON_54:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateEllinghamHorton54Graph(graph);
			        break;
		
			    case ELLINGHAM_HORTON_78:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateEllinghamHorton78Graph(graph);
			        break;
		
			    case ERRERA:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateErreraGraph(graph);
			        break;
		
			    case FOLKMAN:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateFolkmanGraph(graph);
			        break;
		
			    case FRANKLIN:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateFranklinGraph(graph);
			        break;
		
			    case FRUCHT:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateFruchtGraph(graph);
			        break;
			        
			    case GEM:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        GemGraphGenerator gemGen = new GemGraphGenerator();
			        gemGen.generateGraph(graph, null);
			        break;
		
			    case GOLDNER_HARARY:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateGoldnerHararyGraph(graph);
			        break;
		
			    case GOSSET:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateGossetGraph(graph);
			        break;
		
			    case GROTZSCH:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateGrötzschGraph(graph);
			        break;
		
			    case HEAWOOD:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateHeawoodGraph(graph);
			        break;
		
			    case HERSCHEL:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateHerschelGraph(graph);
			        break;
		
			    case HOFFMAN:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateHoffmanGraph(graph);
			        break;
			        
			    case HOUSE:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        HouseGraphGenerator houseGen = new HouseGraphGenerator();
			        houseGen.generateGraph(graph, null);
			        break;
		
			    case KITTELL:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateKittellGraph(graph);
			        break;
		
			    case KLEIN_3_REGULAR:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateKlein3RegularGraph(graph);
			        break;
		
			    case KLEIN_7_REGULAR:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateKlein7RegularGraph(graph);
			        break;
		
			    case KRACKHARDT_KITE:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateKrackhardtKiteGraph(graph);
			        break;
		
			    case MOBIUS_KANTOR:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateMöbiusKantorGraph(graph);
			        break;
		
			    case MOSER_SPINDLE:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateMoserSpindleGraph(graph);
			        break;
		
			    case NAURU:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateNauruGraph(graph);
			        break;
		
			    case PAPPUS:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generatePappusGraph(graph);
			        break;
		
			    case PETERSEN:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generatePetersenGraph(graph);
			        break;
		
			    case POUSSIN:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generatePoussinGraph(graph);
			        break;
		
			    case SCHLAFLI:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateSchläfliGraph(graph);
			        break;
		
			    case THOMSEN:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateThomsenGraph(graph);
			        break;
		
			    case TIETZE:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateTietzeGraph(graph);
			        break;
		
			    case TUTTE:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateTutteGraph(graph);
			        break;
		
			    case ZACHARY_KARATE_CLUB:
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        new NamedGraphGenerator<Integer, DefaultWeightedEdge>().generateZacharyKarateClubGraph(graph);
			        break;
		
			    // Parameterized generators
			    case BARABASI_ALBERT_FOREST:
			        int numTrees = Integer.parseInt(args[1]);
			        int numVerticesBarabasiAlbertForest = Integer.parseInt(args[2]);
			        long seedForest = args.length > 3 ? Long.parseLong(args[3]) : System.currentTimeMillis();
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        BarabasiAlbertForestGenerator<Integer, DefaultWeightedEdge> barabasiAlbertForestGenerator = 
			            new BarabasiAlbertForestGenerator<>(numTrees, numVerticesBarabasiAlbertForest, seedForest);
			        barabasiAlbertForestGenerator.generateGraph(graph, null);
			        break;
		
			    case BARABASI_ALBERT_GRAPH:
			        int initialVertices = Integer.parseInt(args[1]);
			        int edgesPerStep = Integer.parseInt(args[2]);
			        int finalVertices = Integer.parseInt(args[3]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        BarabasiAlbertGraphGenerator<Integer, DefaultWeightedEdge> barabasiAlbertGenerator;
			        if (args.length > 4) {
			            long seed = Long.parseLong(args[4]);
			            barabasiAlbertGenerator = new BarabasiAlbertGraphGenerator<>(initialVertices, edgesPerStep, finalVertices, new Random(seed));
			        } else {
			            barabasiAlbertGenerator = new BarabasiAlbertGraphGenerator<>(initialVertices, edgesPerStep, finalVertices);
			        }
			        barabasiAlbertGenerator.generateGraph(graph, null);
			        break;
			        
			    case BARBELL:
			        int size1 = Integer.parseInt(args[1]);
			        int size2 = Integer.parseInt(args[2]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        BarbellGraphGenerator barbellGen = new BarbellGraphGenerator(size1, size2);
			        barbellGen.generateGraph(graph, null);
			        break;
			        
			    case BROOM:
			        int pathLength = Integer.parseInt(args[1]);
			        int numLeaves = Integer.parseInt(args[2]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        BroomGraphGenerator broomGen = new BroomGraphGenerator(pathLength, numLeaves);
			        broomGen.generateGraph(graph, null);
			        break;
			    
			    case COMPLETE_BIPARTITE:
			        int set1SizeComplete = Integer.parseInt(args[1]);
			        int set2SizeComplete = Integer.parseInt(args[2]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        CompleteBipartiteGraphGenerator<Integer, DefaultWeightedEdge> completeBipartiteGenerator = 
			            new CompleteBipartiteGraphGenerator<>(set1SizeComplete, set2SizeComplete);
			        completeBipartiteGenerator.generateGraph(graph, null);
			        break;
		
			    case COMPLETE:
			        int numVerticesComplete = Integer.parseInt(args[1]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        CompleteGraphGenerator<Integer, DefaultWeightedEdge> completeGenerator = 
			            new CompleteGraphGenerator<>(numVerticesComplete);
			        completeGenerator.generateGraph(graph, null);
			        break;
		
			    case CROWN:
			        int crownSize = Integer.parseInt(args[1]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        CrownGraphGenerator crownGen = new CrownGraphGenerator(crownSize);
			        crownGen.generateGraph(graph, null);
			        break;
			        
			    case DIRECTED_SCALE_FREE:
			    	graph = new DirectedPseudograph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier(),true);
			        // Parameter order: targetNodes (int), targetEdges (int), then the doubles
			        int targetNodes = Integer.parseInt(args[1]);    
			        int targetEdges = Integer.parseInt(args[2]);      
			        float alpha = Float.parseFloat(args[3]);     
			        float gamma = Float.parseFloat(args[4]);     
			        float deltaIn = Float.parseFloat(args[5]);   
			        float deltaOut = Float.parseFloat(args[6]);    
			        DirectedScaleFreeGraphGenerator<Integer, DefaultWeightedEdge> directedScaleFreeGenerator = new DirectedScaleFreeGraphGenerator<Integer, DefaultWeightedEdge>(alpha, gamma, deltaIn, deltaOut, targetEdges, targetNodes);
			        directedScaleFreeGenerator.generateGraph(graph, null);
			        break;
			        
			    case EMPTY:
			        int numVerticesEmpty = Integer.parseInt(args[1]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        EmptyGraphGenerator<Integer, DefaultWeightedEdge> emptyGenerator = 
			            new EmptyGraphGenerator<>(numVerticesEmpty);
			        emptyGenerator.generateGraph(graph, null);
			        break;
			        
			    case FAN:
			        int pathLenFan = Integer.parseInt(args[1]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        FanGraphGenerator fanGen = new FanGraphGenerator(pathLenFan);
			        fanGen.generateGraph(graph, null);
			        break;
			        
			    case GENERALIZED_PETERSEN:
			        int n = Integer.parseInt(args[1]);
			        int k = Integer.parseInt(args[2]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        GeneralizedPetersenGraphGenerator<Integer, DefaultWeightedEdge> generalizedPetersenGenerator = 
			            new GeneralizedPetersenGraphGenerator<>(n, k);
			        generalizedPetersenGenerator.generateGraph(graph, null);
			        break;
			        
			    case GNM_RANDOM_BIPARTITE:
			        int set1Size = Integer.parseInt(args[1]);
			        int set2Size = Integer.parseInt(args[2]);
			        int numEdgesBipartite = Integer.parseInt(args[3]);
			        long seedBipartite = args.length > 4 ? Long.parseLong(args[4]) : System.currentTimeMillis();
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        GnmRandomBipartiteGraphGenerator<Integer, DefaultWeightedEdge> gnmBipartiteGenerator = 
			            new GnmRandomBipartiteGraphGenerator<>(set1Size, set2Size, numEdgesBipartite, seedBipartite);
			        gnmBipartiteGenerator.generateGraph(graph, null);
			        break;
		
			    case GNM_RANDOM_GRAPH:
			        int numVerticesGnm = Integer.parseInt(args[1]);
			        int numEdgesGnm = Integer.parseInt(args[2]);
			        long seedGnm = args.length > 3 ? Long.parseLong(args[3]) : System.currentTimeMillis();
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        GnmRandomGraphGenerator<Integer, DefaultWeightedEdge> gnmGenerator = 
			            new GnmRandomGraphGenerator<>(numVerticesGnm, numEdgesGnm, seedGnm);
			        gnmGenerator.generateGraph(graph, null);
			        break;
		
			    case GNP_RANDOM_BIPARTITE:
			        int set1SizeGnp = Integer.parseInt(args[1]);
			        int set2SizeGnp = Integer.parseInt(args[2]);
			        double edgeProbabilityGnp = Double.parseDouble(args[3]);
			        long seedGnpBipartite = args.length > 4 ? Long.parseLong(args[4]) : System.currentTimeMillis();
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        GnpRandomBipartiteGraphGenerator<Integer, DefaultWeightedEdge> gnpBipartiteGenerator = 
			            new GnpRandomBipartiteGraphGenerator<>(set1SizeGnp, set2SizeGnp, edgeProbabilityGnp, seedGnpBipartite);
			        gnpBipartiteGenerator.generateGraph(graph, null);
			        break;
		
			    case GNP_RANDOM_GRAPH:
			        int numVerticesGnp = Integer.parseInt(args[1]);
			        double edgeProbability = Double.parseDouble(args[2]);
			        long seedGnp = args.length > 3 ? Long.parseLong(args[3]) : System.currentTimeMillis();
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        GnpRandomGraphGenerator<Integer, DefaultWeightedEdge> gnpGenerator = 
			            new GnpRandomGraphGenerator<>(numVerticesGnp, edgeProbability, seedGnp);
			        gnpGenerator.generateGraph(graph, null);
			        break;
		
			    case GRID:
			        int rows = Integer.parseInt(args[1]);
			        int cols = Integer.parseInt(args[2]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        GridGraphGenerator<Integer, DefaultWeightedEdge> gridGenerator = 
			            new GridGraphGenerator<>(rows, cols);
			        gridGenerator.generateGraph(graph, null);
			        break;
		
			    case HYPERCUBE:
			        int dimensions = Integer.parseInt(args[1]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        HyperCubeGraphGenerator<Integer, DefaultWeightedEdge> hypercubeGenerator = 
			            new HyperCubeGraphGenerator<>(dimensions);
			        hypercubeGenerator.generateGraph(graph, null);
			        break;
		
			    case KLEINBERG_SMALL_WORLD:
			    	int gs = Integer.parseInt(args[1]); // grid size
			    	int p = Integer.parseInt(args[2]); // local edges per vertex
			    	int q = Integer.parseInt(args[3]); // long-range edges per vertex
			    	int r = Integer.parseInt(args[4]); // clustering exponent
			    	long seedKSW = args.length > 5 ? Long.parseLong(args[5]) : System.currentTimeMillis();
		
			    	graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			    	KleinbergSmallWorldGraphGenerator<Integer, DefaultWeightedEdge> kleinbergGenerator = 
			    	    new KleinbergSmallWorldGraphGenerator<>(gs, p, q, r, seedKSW);
			    	kleinbergGenerator.generateGraph(graph, null);
			    	break;
			    	
			    case LADDER:
			        int length = Integer.parseInt(args[1]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        LadderGraphGenerator ladderGen = new LadderGraphGenerator(length);
			        ladderGen.generateGraph(graph, null);
			        break;
		
			    case LINEAR:
			        int numVerticesLinear = Integer.parseInt(args[1]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        LinearGraphGenerator<Integer, DefaultWeightedEdge> linearGenerator = 
			            new LinearGraphGenerator<>(numVerticesLinear);
			        linearGenerator.generateGraph(graph, null);
			        break;
			    
			    case LINEARIZED_CHORD_DIAGRAM:
			        int numVerticesLCD = Integer.parseInt(args[1]);
			        int numEdgesLCD = Integer.parseInt(args[2]);
			        long seedLCD = args.length > 3 ? Long.parseLong(args[3]) : System.currentTimeMillis();
			        
			        graph = new DirectedPseudograph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier(),true);
			        LinearizedChordDiagramGraphGenerator<Integer, DefaultWeightedEdge> lcdGenerator = 
			            new LinearizedChordDiagramGraphGenerator<>(numVerticesLCD, numEdgesLCD, seedLCD);
			        lcdGenerator.generateGraph(graph, null);
			        break;
			        
			    case PLANTED_PARTITION:
			        int numBlocks = Integer.parseInt(args[1]);
			        int blockSize = Integer.parseInt(args[2]);
			        double intraProbability = Double.parseDouble(args[3]);
			        double interProbability = Double.parseDouble(args[4]);
			        long seedPP = args.length > 5 ? Long.parseLong(args[5]) : System.currentTimeMillis();
			        
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        PlantedPartitionGraphGenerator<Integer, DefaultWeightedEdge> plantedPartitionGenerator = 
			            new PlantedPartitionGraphGenerator<>(numBlocks, blockSize, intraProbability, interProbability, seedPP);
			        plantedPartitionGenerator.generateGraph(graph, null);
			        break;

			    case PRISM:
			        int nc = Integer.parseInt(args[1]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        PrismGraphGenerator prismGen = new PrismGraphGenerator(nc);
			        prismGen.generateGraph(graph, null);
			        break;
		
			    case PRUFER_TREE:
			        int numVerticesPrufer = Integer.parseInt(args[1]);
			        long seedPrufer = args.length > 2 ? Long.parseLong(args[2]) : System.currentTimeMillis();
			        
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        PruferTreeGenerator<Integer, DefaultWeightedEdge> pruferGenerator = 
			            new PruferTreeGenerator<>(numVerticesPrufer, seedPrufer);
			        pruferGenerator.generateGraph(graph, null);
			        break;
			        
			    case RANDOM_REGULAR:
			        int numVerticesRandomRegular = Integer.parseInt(args[1]);
			        int degreeRandomRegular = Integer.parseInt(args[2]);
			        long seedRR = args.length > 3 ? Long.parseLong(args[3]) : System.currentTimeMillis();
			        
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        RandomRegularGraphGenerator<Integer, DefaultWeightedEdge> randomRegularGenerator = 
			            new RandomRegularGraphGenerator<>(numVerticesRandomRegular, degreeRandomRegular, seedRR);
			        randomRegularGenerator.generateGraph(graph, null);
			        break;
			        
			    case RING:
			        int numVerticesRing = Integer.parseInt(args[1]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        RingGraphGenerator<Integer, DefaultWeightedEdge> ringGenerator = 
			            new RingGraphGenerator<>(numVerticesRing);
			        ringGenerator.generateGraph(graph, null);
			        break;
			        
			    case SCALE_FREE:
			    	int numVerticesScaleFree = Integer.parseInt(args[1]);
			    	long seedSF = args.length > 2 ? Long.parseLong(args[2]) : System.currentTimeMillis();
		
			    	graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			    	ScaleFreeGraphGenerator<Integer, DefaultWeightedEdge> scaleFreeGenerator = 
			    	    new ScaleFreeGraphGenerator<>(numVerticesScaleFree, seedSF);
			    	scaleFreeGenerator.generateGraph(graph, null);
		
			    case SIMPLE_WEIGHTED_BIPARTITE_MATRIX:
			    	if (args.length < 4) {
			            System.out.println("Please provide partitionA, partitionB, and a weight matrix.");
			            break;
			        }
			        List<Integer> partitionA = Arrays.stream(args[1].split(","))
			                                         .map(Integer::parseInt)
			                                         .collect(Collectors.toList());
			        List<Integer> partitionB = Arrays.stream(args[2].split(","))
			                                         .map(Integer::parseInt)
			                                         .collect(Collectors.toList());
			        String[] weightRows = args[3].split(";");
			        double[][] weights = new double[partitionA.size()][partitionB.size()];
			        for (int i = 0; i < weightRows.length; i++) {
			            String[] row = weightRows[i].split(",");
			            for (int j = 0; j < row.length; j++) {
			                weights[i][j] = Double.parseDouble(row[j].trim());
			            }
			        }
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        
			        SimpleWeightedBipartiteGraphMatrixGenerator<Integer, DefaultWeightedEdge> swbGenerator =
			            new SimpleWeightedBipartiteGraphMatrixGenerator<>();
			        swbGenerator.first(partitionA)
			                    .second(partitionB)
			                    .weights(weights)
			                    .generateGraph(graph, null);
			        
			        System.out.println(graph.toString());
			        break;
		
			    case SIMPLE_WEIGHTED_GRAPH_MATRIX:
			        if (args.length < 3) {
			            System.out.println("Please provide vertices and a weight matrix.");
			            break;
			        }
			        List<Integer> vertices = Arrays.stream(args[1].split(","))
			            .map(Integer::parseInt)
			            .collect(Collectors.toList());
			        String[] matrixRows = args[2].split(";");
			        double[][] matrixWeights = new double[vertices.size()][vertices.size()];
			        for (int i = 0; i < matrixRows.length; i++) {
			            String[] row = matrixRows[i].split(",");
			            for (int j = 0; j < row.length; j++) {
			                matrixWeights[i][j] = Double.parseDouble(row[j].trim());
			            }
			        }
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        
			        WeightedGraphMatrixGenerator wgmGenerator = new WeightedGraphMatrixGenerator(vertices,matrixWeights);
			        wgmGenerator.generateGraph(graph);
			        
			        System.out.println(graph.toString());
			        for (DefaultWeightedEdge e : graph.edgeSet()) {
			            System.out.println(
			                graph.getEdgeSource(e) + " -- " +
			                graph.getEdgeTarget(e) + "  weight=" +
			                graph.getEdgeWeight(e)
			            );
			        }

			        break;
		
			    case STAR:
			        int numVerticesStar = Integer.parseInt(args[1]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        StarGraphGenerator<Integer, DefaultWeightedEdge> starGenerator = 
			            new StarGraphGenerator<>(numVerticesStar);
			        starGenerator.generateGraph(graph, null);
			        
			        for (DefaultWeightedEdge e : graph.edgeSet()) {
			            System.out.println(
			                graph.getEdgeSource(e) + " -- " +
			                graph.getEdgeTarget(e) + "  weight=" +
			                graph.getEdgeWeight(e)
			            );
			        }

			        break;
			    
			    case SUNLET:
			        int cycleSizeSunlet = Integer.parseInt(args[1]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        SunletGraphGenerator<Integer, DefaultWeightedEdge> sunletGen =
			                new SunletGraphGenerator<>(cycleSizeSunlet);
			        sunletGen.generateGraph(graph, null);
			        break;
	
			    case WATTS_STROGATZ:
			        int numVerticesWattsStrogatz = Integer.parseInt(args[1]);
			        int numNearestNeighbors = Integer.parseInt(args[2]);
			        double rewiringProbability = Double.parseDouble(args[3]);
			        long seedWS = args.length > 4 ? Long.parseLong(args[4]) : System.currentTimeMillis();
			        
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        WattsStrogatzGraphGenerator<Integer, DefaultWeightedEdge> wattsStrogatzGenerator = 
			            new WattsStrogatzGraphGenerator<>(numVerticesWattsStrogatz, numNearestNeighbors, rewiringProbability, seedWS);
			        wattsStrogatzGenerator.generateGraph(graph, null);
			        break;
		
			    case WHEEL:
			        int numVerticesWheel = Integer.parseInt(args[1]);
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        WheelGraphGenerator<Integer, DefaultWeightedEdge> wheelGenerator = 
			            new WheelGraphGenerator<>(numVerticesWheel);
			        wheelGenerator.generateGraph(graph, null);
			        break;
		
			    case WINDMILL:
			        int numBlades = Integer.parseInt(args[1]);
			        int bladeSize = Integer.parseInt(args[2]);
			        WindmillGraphsGenerator.Mode mode = WindmillGraphsGenerator.Mode.WINDMILL;
			        
			        graph = new SimpleWeightedGraph<>(SupplierUtil.createIntegerSupplier(),SupplierUtil.createDefaultWeightedEdgeSupplier());
			        WindmillGraphsGenerator<Integer, DefaultWeightedEdge> windmillGenerator = 
			            new WindmillGraphsGenerator<>(mode, numBlades, bladeSize);
			        windmillGenerator.generateGraph(graph, null);
			        break;
		
			    default:
			        System.out.println("Invalid graph type.");
			        return null;
			}
		} catch (NumberFormatException e) {
	        System.err.println("Invalid number format for graph '" + graphType + "'");
	        System.err.println("Error: " + e.getMessage());
	    } catch (IllegalArgumentException e) {
	        System.err.println("Invalid argument for graph '" + graphType + "'");
	        System.err.println("Error: " + e.getMessage());
	    } catch (NullPointerException e) {
	    	System.err.println("NullPointer error generating graph '" + graphType + "': " + e.getMessage());
	    }
		catch (Exception e) {
	        System.err.println("Unexpected error generating graph '" + graphType + "': " + e.getMessage());
	    }
		return graph;
	}
	
	/**
     * Provides descriptive information about each graph type we can generate with the generateGraph method.
     * 
     * Example usage:
     * String description = getGraphDescription("BIDIAKIS_CUBE");
     * System.out.println(description);
     * Output: "Bidakis cube graph - a 3-regular graph with 12 vertices and 18 edges. Parameters: []"
     * 
     * @param graphName Name of the graph type (must match enum values)
     * @return User-friendly descriptions with parameter explanations
     */
	public static String getGraphDescription(String graphName) {
	    switch (graphName) {
	    
	    // Non-parameterized named graphs
	    case "BIDIAKIS_CUBE":
	        return "Bidakis cube graph - a 3-regular graph with 12 vertices and 18 edges. Parameters: []";
	    
	    case "BLANUSA_FIRST_SNARK":
	        return "Blanusa's first snark graph - a snark with 18 vertices and 27 edges. Parameters: []";
	    
	    case "BLANUSA_SECOND_SNARK":
	        return "Blanusa's second snark graph - a snark with 18 vertices and 27 edges. Parameters: []";
	    
	    case "BRINKMANN":
	        return "Brinkmann graph - a symmetric cubic graph with 21 vertices and 31 edges. Parameters: []";
	    
	    case "BUCKY_BALL":
	        return "Bucky ball graph - the graph of a truncated icosahedron (C60 fullerene) with 60 vertices and 90 edges. Parameters: []";
	    
	    case "BULL":
	        return "Bull graph - a triangle with two pendant vertices (5 vertices, 5 edges). Parameters: []";
	    
	    case "BUTTERFLY":
	        return "Butterfly graph - also known as the bowtie graph (5 vertices, 6 edges). Parameters: []";
	    
	    case "CHVATAL":
	        return "Chvatal graph - a triangle-free 4-regular graph with 12 vertices and 24 edges. Parameters: []";
	    
	    case "CLAW":
	        return "Claw graph (K1,3) - a star graph with one central vertex and three leaves (4 vertices, 3 edges). Parameters: []";
	    
	    case "CLEBSCH":
	        return "Clebsch graph - a strongly regular graph with 16 vertices and 40 edges. Parameters: []";
	    
	    case "COXETER":
	        return "Coxeter graph - a non-Hamiltonian 3-regular graph with 28 vertices and 42 edges. Parameters: []";
	    
	    case "DESARGUES":
	        return "Desargues graph - a distance-transitive cubic graph with 20 vertices and 30 edges. Parameters: []";
	    
	    case "DIAMOND":
	        return "Diamond graph - a complete graph K4 with one edge removed (4 vertices, 5 edges). Parameters: []";
	    
	    case "DODECAHEDRON":
	        return "Dodecahedron graph - the graph of a regular dodecahedron (20 vertices, 30 edges). Parameters: []";
	    
	    case "DOUBLE_STAR_SNARK":
	        return "Double star snark graph - a snark with 30 vertices and 45 edges. Parameters: []";
	    
	    case "DOYLE":
	        return "Doyle graph - a symmetric cubic graph with 27 vertices and 40 edges. Parameters: []";
	    
	    case "DURER":
	        return "Dürer graph - a polyhedral graph with 12 vertices and 18 edges. Parameters: []";
	    
	    case "ELLINGHAM_HORTON_54":
	        return "Ellingham-Horton 54 graph - a bipartite cubic graph with 54 vertices and 81 edges. Parameters: []";
	    
	    case "ELLINGHAM_HORTON_78":
	        return "Ellingham-Horton 78 graph - a bipartite cubic graph with 78 vertices and 117 edges. Parameters: []";
	    
	    case "ERRERA":
	        return "Errera graph - a semi-symmetric cubic graph with 17 vertices and 27 edges. Parameters: []";
	    
	    case "FOLKMAN":
	        return "Folkman graph - a semi-symmetric regular graph with 20 vertices and 40 edges. Parameters: []";
	    
	    case "FRANKLIN":
	        return "Franklin graph - a 3-regular graph with 12 vertices and 18 edges. Parameters: []";
	    
	    case "FRUCHT":
	        return "Frucht graph - a 3-regular graph with 12 vertices and 18 edges with no non-trivial symmetries. Parameters: []";
	    
	    case "GEM":
	        return "Gem graph - diamond graph with an extra vertex attached. Parameters: [] - " +
	               "Fixed graph with 5 vertices, 4 forming a diamond, 1 extra vertex connected to one vertex of the diamond";
	        
	    case "GOLDNER_HARARY":
	        return "Goldner-Harary graph - a maximal planar graph with 11 vertices and 27 edges. Parameters: []";
	    
	    case "GOSSET":
	        return "Gosset graph - a graph related to the E8 root system with 56 vertices and 756 edges. Parameters: []";
	    
	    case "GROTZSCH":
	        return "Grötzsch graph - a triangle-free graph with 11 vertices and 20 edges and chromatic number 4. Parameters: []";
	    
	    case "HEAWOOD":
	        return "Heawood graph - the unique (3,6)-cage graph with 14 vertices and 21 edges. Parameters: []";
	    
	    case "HERSCHEL":
	        return "Herschel graph - the smallest non-Hamiltonian polyhedral graph (11 vertices, 18 edges). Parameters: []";
	    
	    case "HOFFMAN":
	        return "Hoffman graph - a 4-regular graph with 16 vertices and 32 edges. Parameters: []";
	    
	    case "HOUSE":
	        return "House graph - pentagon with a triangle on top. Parameters: [] - " +
	               "Fixed graph with 5 vertices forming a pentagon and a roof triangle";
	        
	    case "KITTELL":
	        return "Kittell graph - a graph with 23 vertices and 63 edges. Parameters: []";
	    
	    case "KLEIN_3_REGULAR":
	        return "Klein 3-regular graph - a cubic graph with 56 vertices and 84 edges. Parameters: []";
	    
	    case "KLEIN_7_REGULAR":
	        return "Klein 7-regular graph - a 7-regular graph with 24 vertices and 84 edges. Parameters: []";
	    
	    case "KRACKHARDT_KITE":
	        return "Krackhardt kite graph - a social network example with 10 vertices and 18 edges. Parameters: []";
	    
	    case "MOBIUS_KANTOR":
	        return "Möbius-Kantor graph - a symmetric bipartite cubic graph with 16 vertices and 24 edges. Parameters: []";
	    
	    case "MOSER_SPINDLE":
	        return "Moser spindle graph - a unit distance graph with 7 vertices and 11 edges. Parameters: []";
	    
	    case "NAURU":
	        return "Nauru graph - a symmetric bipartite cubic graph with 24 vertices and 36 edges. Parameters: []";
	    
	    case "PAPPUS":
	        return "Pappus graph - a 3-regular graph with 18 vertices and 27 edges. Parameters: []";
	    
	    case "PETERSEN":
	        return "Petersen graph - a well-known symmetric cubic graph with 10 vertices and 15 edges. Parameters: []";
	    
	    case "POUSSIN":
	        return "Poussin graph - a graph with 15 vertices and 39 edges. Parameters: []";
	    
	    case "SCHLAFLI":
	        return "Schläfli graph - a strongly regular graph with 27 vertices and 216 edges. Parameters: []";
	    
	    case "THOMSEN":
	        return "Thomsen graph (K3,3) - the utility graph, a complete bipartite graph (6 vertices, 9 edges). Parameters: []";
	    
	    case "TIETZE":
	        return "Tietze graph - a cubic graph with 12 vertices and 18 edges. Parameters: []";
	    
	    case "TUTTE":
	        return "Tutte graph - the smallest cubic graph without a Hamiltonian cycle (46 vertices, 69 edges). Parameters: []";
	    
	    case "ZACHARY_KARATE_CLUB":
	        return "Zachary's karate club graph - a social network of a university karate club (34 vertices, 78 edges). Parameters: []";
	    // Parameterized generators
	    case "BARABASI_ALBERT_FOREST":
	        return "Barabasi-Albert forest generator. Parameters: [numTrees: int, numVertices: int, seed: long] - " +
	               "numTrees: number of trees in the forest, " +
	               "numVertices: total number of vertices in the forest, " +
	               "seed: random seed for reproducible generation";
	    
	    case "BARABASI_ALBERT_GRAPH":
	        return "Barabasi-Albert scale-free network model. Parameters: [initialVertices: int, edgesPerStep: int, finalVertices: int, seed (optional): long] - " +
	               "initialVertices: number of initial vertices in the seed graph, " +
	               "edgesPerStep: number of edges each new vertex will attach to existing vertices, " +
	               "finalVertices: total number of vertices in the final graph, " +
	               "seed (optional): random seed for reproducible generation";

	    case "BARBELL":
	        return "Barbell graph - two complete graphs connected by a bridge. Parameters: [size1: int, size2: int] - " +
	               "size1: number of vertices in the first complete graph, size2: number in the second";
    
	    case "BROOM":
	        return "Broom graph - path with multiple leaves at one end. Parameters: [pathLength: int, numLeaves: int] - " +
	               "pathLength: number of vertices in the path, numLeaves: number of leaf vertices attached to one end";

	    case "COMPLETE_BIPARTITE":
	        return "Complete bipartite graph. Parameters: [set1Size: int, set2Size: int] - " +
	               "set1Size: number of vertices in the first partition, " +
	               "set2Size: number of vertices in the second partition, " +
	               "every vertex in set1 is connected to every vertex in set2";
	    
	    case "COMPLETE":
	        return "Complete graph (clique). Parameters: [numVertices: int] - " +
	               "numVertices: number of vertices where every pair of distinct vertices is connected";
	    
	    case "CROWN":
	        return "Crown graph - bipartite graph with a crown pattern. Parameters: [size: int] - " +
	               "size: number of vertices in each partition forming a crown structure";
	    
	    case "DIRECTED_SCALE_FREE":
	        return "Directed scale-free graph. Parameters: [alpha: float, gamma: float, deltaIn: float, deltaOut: float, targetEdges: int, targetNodes: int, seed: long (optional)] - " +
	               "alpha: probability of adding a new vertex, " +
	               "gamma: probability of adding an edge between two existing vertices, " +
	               "deltaIn: bias for in-degree in attachment probability, " +
	               "deltaOut: bias for out-degree in attachment probability, " +
	               "targetEdges: target number of edges to generate, " +
	               "targetNodes: target number of vertices to generate, ";

	    case "EMPTY":
	        return "Empty graph (edgeless graph). Parameters: [numVertices: int] - " +
	               "numVertices: number of vertices with no edges between them";
	    
	    case "FAN":
	        return "Fan graph - a single vertex joined to a path. Parameters: [pathLength: int] - " +
	               "pathLength: number of vertices in the path";
	        
	    case "GENERALIZED_PETERSEN":
	        return "Generalized Petersen graph. Parameters: [n: int, k: int] - " +
	               "n: number of vertices in the outer and inner cycles, " +
	               "k: step size for inner cycle connections (must satisfy 1 <= k <= n/2)";
	    
	    case "GNM_RANDOM_BIPARTITE":
	        return "G(n,m) random bipartite graph. Parameters: [set1Size: int, set2Size: int, numEdges: int, seed: long] - " +
	               "set1Size: number of vertices in the first partition, " +
	               "set2Size: number of vertices in the second partition, " +
	               "numEdges: exact number of edges between partitions, " +
	               "seed: random seed for reproducible generation";
	    
	    case "GNM_RANDOM_GRAPH":
	        return "G(n,m) random graph (Erdős–Rényi model). Parameters: [numVertices: int, numEdges: int, seed: long] - " +
	               "numVertices: number of vertices in the graph, " +
	               "numEdges: exact number of edges to generate uniformly at random, " +
	               "seed: random seed for reproducible generation";
	    
	    case "GNP_RANDOM_BIPARTITE":
	        return "G(n,p) random bipartite graph. Parameters: [set1Size: int, set2Size: int, edgeProbability: double, seed: long] - " +
	               "set1Size: number of vertices in the first partition, " +
	               "set2Size: number of vertices in the second partition, " +
	               "edgeProbability: probability of connection between partitions (0.0 to 1.0), " +
	               "seed: random seed for reproducible generation";
	    
	    case "GNP_RANDOM_GRAPH":
	        return "G(n,p) random graph (Erdős–Rényi model). Parameters: [numVertices: int, edgeProbability: double, seed: long] - " +
	               "numVertices: number of vertices in the graph, " +
	               "edgeProbability: probability that any two vertices are connected (0.0 to 1.0), " +
	               "seed: random seed for reproducible generation";
	    
	    case "GRID":
	        return "Grid graph. Parameters: [rows: int, columns: int] - " +
	               "rows: number of rows in the grid, " +
	               "columns: number of columns in the grid, " +
	               "creates a bidirectional grid with rows*columns vertices";
	    
	    case "HYPERCUBE":
	        return "Hypercube graph. Parameters: [dimensions: int] - " +
	               "dimensions: dimension of the hypercube (2^dimensions vertices), " +
	               "each vertex represents a binary string and edges connect strings differing by one bit";
	    
	    case "KLEINBERG_SMALL_WORLD":
	        return "Kleinberg small-world network. Parameters: [gridSize: int, localEdges: int, longRangeEdges: int, clusteringExponent: int, seed: long] - " +
	               "gridSize: size of the base grid (n x n), " +
	               "localEdges: number of local edges per vertex, " +
	               "longRangeEdges: number of long-range edges per vertex, " +
	               "clusteringExponent: exponent for distance-based connection probability, " +
	               "seed: random seed for reproducible generation";

	    case "LADDER":
	        return "Ladder graph - two parallel paths connected by edges (rungs). Parameters: [length: int] - " +
	               "length: number of rungs (columns) forming a 2xlength grid";
    
	    case "LINEAR":
	        return "Linear graph (path graph). Parameters: [numVertices: int] - " +
	               "numVertices: number of vertices arranged in a linear path, " +
	               "creates a graph with vertices connected in a straight line";
	    
	    case "LINEARIZED_CHORD_DIAGRAM":
	        return "Linearized chord diagram model. Parameters: [numVertices: int, numEdges: int, seed: long] - " +
	               "numVertices: number of vertices in the graph, " +
	               "numEdges: number of edges to generate, " +
	               "seed: random seed for reproducible generation";
	    
	    case "PLANTED_PARTITION":
	        return "Planted partition graph (stochastic block model). Parameters: [numBlocks: int, blockSize: int, intraProbability: double, interProbability: double, seed: long] - " +
	               "numBlocks: number of communities/blocks, " +
	               "blockSize: number of vertices in each block, " +
	               "intraProbability: probability of edges within blocks, " +
	               "interProbability: probability of edges between blocks, " +
	               "seed: random seed for reproducible generation";
	    
	    case "PRISM":
	        return "Prism graph - two n-cycles connected by matching edges. Parameters: [n: int] - " +
	               "n: size of each cycle (triangular prism for n=3)";

	    case "PRUFER_TREE":
	        return "Random tree generator using Prüfer sequences. Parameters: [pruferSequence: int[]] OR [numVertices: int, seed: long] - " +
	               "pruferSequence: Prüfer sequence defining the tree structure, " +
	               "OR numVertices: number of vertices for random tree, seed: random seed";
	    
	    case "RANDOM_REGULAR":
	        return "Random regular graph. Parameters: [numVertices: int, degree: int, seed: long] - " +
	               "numVertices: number of vertices in the graph, " +
	               "degree: uniform degree for every vertex (must be even for odd numVertices), " +
	               "seed: random seed for reproducible generation";
	    
	    case "RING":
	        return "Ring graph (cycle graph). Parameters: [numVertices: int] - " +
	               "numVertices: number of vertices arranged in a cycle, " +
	               "each vertex connected to two neighbors forming a closed loop";
	        
	    case "SCALE_FREE":
	        return "Scale-free graph (preferential attachment model). Parameters: [numVertices: int, seed: long] - " +
	               "numVertices: total number of vertices in the graph, " +
	               "seed: random seed for reproducible generation (optional)";

	    case "SIMPLE_WEIGHTED_BIPARTITE_MATRIX":
	        return "Simple weighted bipartite graph from matrix. Parameters: [partitionA: String, partitionB: String, weights: String] - " +
	               "partitionA: comma-separated integers for the first partition (e.g., \"1,2,3\"), " +
	               "partitionB: comma-separated integers for the second partition (e.g., \"4,5\"), " +
	               "weights: semicolon-separated rows of comma-separated doubles defining the weight matrix, " +
	               "where each row corresponds to a vertex in partitionA and each column to a vertex in partitionB " +
	               "(e.g., partitionA=\"1,2,3\", partitionB=\"4,5\", weights=\"1.0,2.0;3.0,4.0;5.0,6.0\")";

	    case "SIMPLE_WEIGHTED_GRAPH_MATRIX":
	        return "Simple weighted graph from matrix. Parameters: [vertices: String, weights: String] - " +
	               "vertices: comma-separated integers for all graph vertices (e.g., \"1,2,3\"), " +
	               "weights: semicolon-separated rows of comma-separated doubles representing a square weight matrix, " +
	               "where weights[i][j] is the weight of the edge from vertex i to vertex j " +
	               "(e.g., vertices=\"1,2,3\", weights=\"0.0,1.0,2.0;1.0,0.0,3.0;2.0,3.0,0.0\")";

	    case "STAR":
	        return "Star graph. Parameters: [numVertices: int] - " +
	               "numVertices: total number of vertices (one center connected to all others), " +
	               "creates K1,(n-1) with one central vertex and n-1 leaves";
	    
	    case "SUNLET":
	        return "Sunlet graph - cycle with pendant vertices attached to each cycle vertex. Parameters: [cycleSize: int] - " +
	               "cycleSize: number of vertices in the central cycle, each with a leaf vertex attached";
	        
	    case "WATTS_STROGATZ":
	        return "Watts-Strogatz small-world network. Parameters: [numVertices: int, numNeighbors: int, rewiringProbability: double, seed: long] - " +
	               "numVertices: number of vertices in the graph, " +
	               "numNeighbors: number of nearest neighbors to connect in the ring lattice, " +
	               "rewiringProbability: probability of rewiring each edge (0.0 to 1.0), " +
	               "seed: random seed for reproducible generation";
	    
	    case "WHEEL":
	        return "Wheel graph. Parameters: [numVertices: int] - " +
	               "numVertices: total number of vertices (cycle plus central vertex), " +
	               "creates a cycle of n-1 vertices with a central vertex connected to all cycle vertices";
	    
	    case "WINDMILL":
	        return "Windmill graph (friendship graph). Parameters: [numBlades: int, bladeSize: int, mode: Mode] - " +
	               "numBlades: number of complete graphs sharing a common vertex, " +
	               "bladeSize: number of vertices in each complete graph, " +
	               "mode: WINDMILL (complete graphs) or DUTCHWINDMILL (cycle graphs)";
	    
	    default:
	        return "Graph description not available for the specified graph name or this type of graph can't be generated by this method.";
	    }
	}
	
	/**
     * Inner class for generating HTML documentation website showcasing all available graphs.
     * Creates visual representations of each graph type and organizes them in a web page.
     */
	public static class GraphWebsiteGenerator {
		/** Array of graph names and descriptions for graphs without parameters */
	    private static final String[][] GRAPHS_WITHOUT_PARAMS = {
	        {"BIDIAKIS_CUBE", "Bidiakis cube graph - a 3-regular graph with 12 vertices and 18 edges. Parameters: []"},
	        {"BLANUSA_FIRST_SNARK", "Blanusa's first snark graph - a snark with 18 vertices and 27 edges. Parameters: []"},
	        {"BLANUSA_SECOND_SNARK", "Blanusa's second snark graph - a snark with 18 vertices and 27 edges. Parameters: []"},
	        {"BRINKMANN", "Brinkmann graph - a symmetric cubic graph with 21 vertices and 31 edges. Parameters: []"},
	        {"BUCKY_BALL", "Bucky ball graph - the graph of a truncated icosahedron (C60 fullerene) with 60 vertices and 90 edges. Parameters: []"},
	        {"BULL", "Bull graph - a triangle with two pendant vertices (5 vertices, 5 edges). Parameters: []"},
	        {"BUTTERFLY", "Butterfly graph - also known as the bowtie graph (5 vertices, 6 edges). Parameters: []"},
	        {"CHVATAL", "Chvatal graph - a triangle-free 4-regular graph with 12 vertices and 24 edges. Parameters: []"},
	        {"CLAW", "Claw graph (K1,3) - a star graph with one central vertex and three leaves (4 vertices, 3 edges). Parameters: []"},
	        {"CLEBSCH", "Clebsch graph - a strongly regular graph with 16 vertices and 40 edges. Parameters: []"},
	        {"COXETER", "Coxeter graph - a non-Hamiltonian 3-regular graph with 28 vertices and 42 edges. Parameters: []"},
	        {"DESARGUES", "Desargues graph - a distance-transitive cubic graph with 20 vertices and 30 edges. Parameters: []"},
	        {"DIAMOND", "Diamond graph - a complete graph K4 with one edge removed (4 vertices, 5 edges). Parameters: []"},
	        {"DODECAHEDRON", "Dodecahedron graph - the graph of a regular dodecahedron (20 vertices, 30 edges). Parameters: []"},
	        {"DOUBLE_STAR_SNARK", "Double star snark graph - a snark with 30 vertices and 45 edges. Parameters: []"},
	        {"DOYLE", "Doyle graph - a symmetric cubic graph with 27 vertices and 40 edges. Parameters: []"},
	        {"DURER", "Dürer graph - a polyhedral graph with 12 vertices and 18 edges. Parameters: []"},
	        {"ELLINGHAM_HORTON_54", "Ellingham-Horton 54 graph - a bipartite cubic graph with 54 vertices and 81 edges. Parameters: []"},
	        {"ELLINGHAM_HORTON_78", "Ellingham-Horton 78 graph - a bipartite cubic graph with 78 vertices and 117 edges. Parameters: []"},
	        {"ERRERA", "Errera graph - a semi-symmetric cubic graph with 17 vertices and 27 edges. Parameters: []"},
	        {"FOLKMAN", "Folkman graph - a semi-symmetric regular graph with 20 vertices and 40 edges. Parameters: []"},
	        {"FRANKLIN", "Franklin graph - a 3-regular graph with 12 vertices and 18 edges. Parameters: []"},
	        {"FRUCHT", "Frucht graph - a 3-regular graph with 12 vertices and 18 edges with no non-trivial symmetries. Parameters: []"},
	        {"GEM", "Gem graph - diamond graph with an extra vertex attached. Parameters: []"},
	        {"GOLDNER_HARARY", "Goldner-Harary graph - a maximal planar graph with 11 vertices and 27 edges. Parameters: []"},
	        {"GOSSET", "Gosset graph - a graph related to the E8 root system with 56 vertices and 756 edges. Parameters: []"},
	        {"GROTZSCH", "Grötzsch graph - a triangle-free graph with 11 vertices and 20 edges and chromatic number 4. Parameters: []"},
	        {"HEAWOOD", "Heawood graph - the unique (3,6)-cage graph with 14 vertices and 21 edges. Parameters: []"},
	        {"HERSCHEL", "Herschel graph - the smallest non-Hamiltonian polyhedral graph (11 vertices, 18 edges). Parameters: []"},
	        {"HOFFMAN", "Hoffman graph - a 4-regular graph with 16 vertices and 32 edges. Parameters: []"},
	        {"HOUSE", "House graph - pentagon with a triangle on top. Parameters: []"},
	        {"KITTELL", "Kittell graph - a graph with 23 vertices and 63 edges. Parameters: []"},
	        {"KLEIN_3_REGULAR", "Klein 3-regular graph - a cubic graph with 56 vertices and 84 edges. Parameters: []"},
	        {"KLEIN_7_REGULAR", "Klein 7-regular graph - a 7-regular graph with 24 vertices and 84 edges. Parameters: []"},
	        {"KRACKHARDT_KITE", "Krackhardt kite graph - a social network example with 10 vertices and 18 edges. Parameters: []"},
	        {"MOBIUS_KANTOR", "Möbius-Kantor graph - a symmetric bipartite cubic graph with 16 vertices and 24 edges. Parameters: []"},
	        {"MOSER_SPINDLE", "Moser spindle graph - a unit distance graph with 7 vertices and 11 edges. Parameters: []"},
	        {"NAURU", "Nauru graph - a symmetric bipartite cubic graph with 24 vertices and 36 edges. Parameters: []"},
	        {"PAPPUS", "Pappus graph - a 3-regular graph with 18 vertices and 27 edges. Parameters: []"},
	        {"PETERSEN", "Petersen graph - a well-known symmetric cubic graph with 10 vertices and 15 edges. Parameters: []"},
	        {"POUSSIN", "Poussin graph - a graph with 15 vertices and 39 edges. Parameters: []"},
	        {"SCHLAFLI", "Schläfli graph - a strongly regular graph with 27 vertices and 216 edges. Parameters: []"},
	        {"THOMSEN", "Thomsen graph (K3,3) - the utility graph, a complete bipartite graph (6 vertices, 9 edges). Parameters: []"},
	        {"TIETZE", "Tietze graph - a cubic graph with 12 vertices and 18 edges. Parameters: []"},
	        {"TUTTE", "Tutte graph - the smallest cubic graph without a Hamiltonian cycle (46 vertices, 69 edges). Parameters: []"},
	        {"ZACHARY_KARATE_CLUB", "Zachary's karate club graph - a social network of a university karate club (34 vertices, 78 edges). Parameters: []"}
	    };
	    /** Array of graph names and descriptions for graphs with parameters */
	    public static final String[][] GRAPHS_WITH_PARAMS = {
	        {"BARABASI_ALBERT_FOREST", "Barabasi-Albert forest generator. Parameters: [numTrees: int, numVertices: int, seed: long]"},
	        {"BARABASI_ALBERT_GRAPH", "Barabasi-Albert scale-free network model. Parameters: [initialVertices: int, edgesPerStep: int, finalVertices: int, seed: long]"},
	        {"BARBELL", "Barbell graph - two complete graphs connected by a bridge. Parameters: [size1: int, size2: int]"},
	        {"BROOM", "Broom graph - path with multiple leaves at one end. Parameters: [pathLength: int, numLeaves: int]"},
	        {"COMPLETE_BIPARTITE", "Complete bipartite graph. Parameters: [set1Size: int, set2Size: int]"},
	        {"COMPLETE", "Complete graph (clique). Parameters: [numVertices: int]"},
	        {"CROWN", "Crown graph - bipartite graph with a crown pattern. Parameters: [size: int]"},
	        {"DIRECTED_SCALE_FREE", "Directed scale-free graph. Parameters: [alpha: float, gamma: float, deltaIn: float, deltaOut: float, targetEdges: int, targetNodes: int, seed: long]"},
	        {"EMPTY", "Empty graph (edgeless graph). Parameters: [numVertices: int]"},
	        {"FAN", "Fan graph - a single vertex joined to a path. Parameters: [pathLength: int]"},
	        {"GENERALIZED_PETERSEN", "Generalized Petersen graph. Parameters: [n: int, k: int]"},
	        {"GNM_RANDOM_BIPARTITE", "G(n,m) random bipartite graph. Parameters: [set1Size: int, set2Size: int, numEdges: int, seed: long]"},
	        {"GNM_RANDOM_GRAPH", "G(n,m) random graph (Erdős–Rényi model). Parameters: [numVertices: int, numEdges: int, seed: long]"},
	        {"GNP_RANDOM_BIPARTITE", "G(n,p) random bipartite graph. Parameters: [set1Size: int, set2Size: int, edgeProbability: double, seed: long]"},
	        {"GNP_RANDOM_GRAPH", "G(n,p) random graph (Erdős–Rényi model). Parameters: [numVertices: int, edgeProbability: double, seed: long]"},
	        {"GRID", "Grid graph. Parameters: [rows: int, columns: int]"},
	        {"HYPERCUBE", "Hypercube graph. Parameters: [dimensions: int]"},
	        {"KLEINBERG_SMALL_WORLD", "Kleinberg small-world network. Parameters: [gridSize: int, localEdges: int, longRangeEdges: int, clusteringExponent: int, seed: long]"},
	        {"LADDER", "Ladder graph - two parallel paths connected by edges (rungs). Parameters: [length: int]"},
	        {"LINEAR", "Linear graph (path graph). Parameters: [numVertices: int]"},
	        {"LINEARIZED_CHORD_DIAGRAM", "Linearized chord diagram model. Parameters: [numVertices: int, numEdges: int, seed: long]"},
	        {"PLANTED_PARTITION", "Planted partition graph (stochastic block model). Parameters: [numBlocks: int, blockSize: int, intraProbability: double, interProbability: double, seed: long]"},
	        {"PRISM", "Prism graph - two n-cycles connected by matching edges. Parameters: [n: int]"},
	        {"PRUFER_TREE", "Random tree generator using Prüfer sequences. Parameters: [numVertices: int, seed: long]"},
	        {"RANDOM_REGULAR", "Random regular graph. Parameters: [numVertices: int, degree: int, seed: long]"},
	        {"RING", "Ring graph (cycle graph). Parameters: [numVertices: int]"},
	        {"SCALE_FREE", "Scale-free graph (preferential attachment model). Parameters: [numVertices: int, seed: long]"},
	        {"SIMPLE_WEIGHTED_BIPARTITE_MATRIX", "Simple weighted bipartite graph from matrix. Parameters: [weights: double[][]]"},
	        {"SIMPLE_WEIGHTED_GRAPH_MATRIX", "Simple weighted graph from matrix. Parameters: [weights: double[][]]"},
	        {"STAR", "Star graph. Parameters: [numVertices: int]"},
	        {"SUNLET", "Sunlet graph - cycle with pendant vertices attached to each cycle vertex. Parameters: [cycleSize: int]"},
	        {"WATTS_STROGATZ", "Watts-Strogatz small-world network. Parameters: [numVertices: int, numNeighbors: int, rewiringProbability: double, seed: long]"},
	        {"WHEEL", "Wheel graph. Parameters: [numVertices: int]"},
	        {"WINDMILL", "Windmill graph (friendship graph). Parameters: [numBlades: int, bladeSize: int, mode: Mode]"}
	    };
	    
	    private static final String OUTPUT_DIR = "graph_images";
	    private static final int IMAGE_SIZE = 400;
	    private static final int NODE_RADIUS = 15;
	    private static final Color NODE_COLOR = new Color(52, 152, 219);
	    private static final Color EDGE_COLOR = new Color(149, 165, 166);
	    private static final Color BACKGROUND_COLOR = Color.WHITE;
	    private static final Color TEXT_COLOR = Color.BLACK;
	    private static Map<String, String[]> graphArgs = new HashMap<>();
	    
	    /**
        * Initializes default arguments for all graph types.
        * These are used when generating example visualizations.
        */
	    static void initializeGraphArgs() {
	        // Non-parameterized graphs
	        graphArgs.put("BIDIAKIS_CUBE", new String[]{"BIDIAKIS_CUBE"});
	        graphArgs.put("BLANUSA_FIRST_SNARK", new String[]{"BLANUSA_FIRST_SNARK"});
	        graphArgs.put("BLANUSA_SECOND_SNARK", new String[]{"BLANUSA_SECOND_SNARK"});
	        graphArgs.put("BRINKMANN", new String[]{"BRINKMANN"});
	        graphArgs.put("BUCKY_BALL", new String[]{"BUCKY_BALL"});
	        graphArgs.put("BULL", new String[]{"BULL"});
	        graphArgs.put("BUTTERFLY", new String[]{"BUTTERFLY"});
	        graphArgs.put("CHVATAL", new String[]{"CHVATAL"});
	        graphArgs.put("CLAW", new String[]{"CLAW"});
	        graphArgs.put("CLEBSCH", new String[]{"CLEBSCH"});
	        graphArgs.put("COXETER", new String[]{"COXETER"});
	        graphArgs.put("DESARGUES", new String[]{"DESARGUES"});
	        graphArgs.put("DIAMOND", new String[]{"DIAMOND"});
	        graphArgs.put("DODECAHEDRON", new String[]{"DODECAHEDRON"});
	        graphArgs.put("DOUBLE_STAR_SNARK", new String[]{"DOUBLE_STAR_SNARK"});
	        graphArgs.put("DOYLE", new String[]{"DOYLE"});
	        graphArgs.put("DURER", new String[]{"DURER"});
	        graphArgs.put("ELLINGHAM_HORTON_54", new String[]{"ELLINGHAM_HORTON_54"});
	        graphArgs.put("ELLINGHAM_HORTON_78", new String[]{"ELLINGHAM_HORTON_78"});
	        graphArgs.put("ERRERA", new String[]{"ERRERA"});
	        graphArgs.put("FOLKMAN", new String[]{"FOLKMAN"});
	        graphArgs.put("FRANKLIN", new String[]{"FRANKLIN"});
	        graphArgs.put("FRUCHT", new String[]{"FRUCHT"});
	        graphArgs.put("GEM", new String[]{"GEM"});
	        graphArgs.put("GOLDNER_HARARY", new String[]{"GOLDNER_HARARY"});
	        graphArgs.put("GOSSET", new String[]{"GOSSET"});
	        graphArgs.put("GROTZSCH", new String[]{"GROTZSCH"});
	        graphArgs.put("HEAWOOD", new String[]{"HEAWOOD"});
	        graphArgs.put("HERSCHEL", new String[]{"HERSCHEL"});
	        graphArgs.put("HOFFMAN", new String[]{"HOFFMAN"});
	        graphArgs.put("HOUSE", new String[]{"HOUSE"});
	        graphArgs.put("KITTELL", new String[]{"KITTELL"});
	        graphArgs.put("KLEIN_3_REGULAR", new String[]{"KLEIN_3_REGULAR"});
	        graphArgs.put("KLEIN_7_REGULAR", new String[]{"KLEIN_7_REGULAR"});
	        graphArgs.put("KRACKHARDT_KITE", new String[]{"KRACKHARDT_KITE"});
	        graphArgs.put("MOBIUS_KANTOR", new String[]{"MOBIUS_KANTOR"});
	        graphArgs.put("MOSER_SPINDLE", new String[]{"MOSER_SPINDLE"});
	        graphArgs.put("NAURU", new String[]{"NAURU"});
	        graphArgs.put("PAPPUS", new String[]{"PAPPUS"});
	        graphArgs.put("PETERSEN", new String[]{"PETERSEN"});
	        graphArgs.put("POUSSIN", new String[]{"POUSSIN"});
	        graphArgs.put("SCHLAFLI", new String[]{"SCHLAFLI"});
	        graphArgs.put("THOMSEN", new String[]{"THOMSEN"});
	        graphArgs.put("TIETZE", new String[]{"TIETZE"});
	        graphArgs.put("TUTTE", new String[]{"TUTTE"});
	        graphArgs.put("ZACHARY_KARATE_CLUB", new String[]{"ZACHARY_KARATE_CLUB"});
	        // Parameterized graphs
	        graphArgs.put("BARABASI_ALBERT_FOREST", new String[]{"BARABASI_ALBERT_FOREST", "3", "20", "42"});
	        graphArgs.put("BARABASI_ALBERT_GRAPH", new String[]{"BARABASI_ALBERT_GRAPH", "10", "2", "20"});
	        graphArgs.put("BARBELL", new String[]{"BARBELL", "4", "3"});
	        graphArgs.put("BROOM", new String[]{"BROOM", "5", "3"});
	        graphArgs.put("COMPLETE_BIPARTITE", new String[]{"COMPLETE_BIPARTITE", "5", "15"});
	        graphArgs.put("COMPLETE", new String[]{"COMPLETE", "20"});
	        graphArgs.put("CROWN", new String[]{"CROWN", "5"});
	        graphArgs.put("DIRECTED_SCALE_FREE", new String[]{"DIRECTED_SCALE_FREE", "20", "30", "0.3", "0.3", "0.4", "0.1"});
	        graphArgs.put("EMPTY", new String[]{"EMPTY", "20"});
	        graphArgs.put("FAN", new String[]{"FAN", "4"});
	        graphArgs.put("GENERALIZED_PETERSEN", new String[]{"GENERALIZED_PETERSEN", "10", "3"});
	        graphArgs.put("GNM_RANDOM_BIPARTITE", new String[]{"GNM_RANDOM_BIPARTITE", "10", "10", "25", "42"});
	        graphArgs.put("GNM_RANDOM_GRAPH", new String[]{"GNM_RANDOM_GRAPH", "50", "100", "42"});
	        graphArgs.put("GNP_RANDOM_BIPARTITE", new String[]{"GNP_RANDOM_BIPARTITE", "25", "35", "0.3", "42"});
	        graphArgs.put("GNP_RANDOM_GRAPH", new String[]{"GNP_RANDOM_GRAPH", "40", "0.2", "42"});
	        graphArgs.put("GRID", new String[]{"GRID", "3", "4"});
	        graphArgs.put("HYPERCUBE", new String[]{"HYPERCUBE", "5"});
	        graphArgs.put("KLEINBERG_SMALL_WORLD", new String[]{"KLEINBERG_SMALL_WORLD", "10", "2", "3", "42"});
	        graphArgs.put("LADDER", new String[]{"LADDER", "5"});
	        graphArgs.put("LINEAR", new String[]{"LINEAR", "20"});
	        graphArgs.put("LINEARIZED_CHORD_DIAGRAM", new String[]{"LINEARIZED_CHORD_DIAGRAM", "40", "60", "42"});
	        graphArgs.put("PLANTED_PARTITION", new String[]{"PLANTED_PARTITION", "3", "5", "0.8", "0.1", "42"});
	        graphArgs.put("PRISM", new String[]{"PRISM", "4"});
	        graphArgs.put("PRUFER_TREE", new String[]{"PRUFER_TREE", "15", "42"});
	        graphArgs.put("RANDOM_REGULAR", new String[]{"RANDOM_REGULAR", "30", "4", "42"});
	        graphArgs.put("RING", new String[]{"RING", "25"});
	        graphArgs.put("SCALE_FREE", new String[]{"SCALE_FREE", "10", "423"});
	        graphArgs.put("SIMPLE_WEIGHTED_BIPARTITE_MATRIX", new String[]{"SIMPLE_WEIGHTED_BIPARTITE_MATRIX", "1,2,3", "4,5", "1.0,2.0;3.0,4.0;5.0,6.0"});
	        graphArgs.put("SIMPLE_WEIGHTED_GRAPH_MATRIX", new String[]{"SIMPLE_WEIGHTED_GRAPH_MATRIX", "1,2,3", "0.0,1.0,2.0;1.0,0.0,3.0;2.0,3.0,0.0"});
	        graphArgs.put("STAR", new String[]{"STAR", "15"});
	        graphArgs.put("SUNLET", new String[]{"SUNLET", "5"});
	        graphArgs.put("WATTS_STROGATZ", new String[]{"WATTS_STROGATZ", "30", "4", "0.1", "42"});
	        graphArgs.put("WHEEL", new String[]{"WHEEL", "20"});
	        graphArgs.put("WINDMILL", new String[]{"WINDMILL", "5", "4", "WINDMILL"});
	    }
	    
	    /**
         * Main method to create the complete HTML website with graph visualizations.
         * 
         * @throws IOException if file creation fails
         */
	    public static void createWebsite() throws IOException {
	        Files.createDirectories(Paths.get(OUTPUT_DIR));
	        initializeGraphArgs();
	        generateGraphImages();
	        
	        String htmlContent = generateHTML();
	        
	        try (PrintWriter writer = new PrintWriter("AlGatorGraph_graphs.html", "UTF-8")) {
	            writer.write(htmlContent);
	        }
	        
	        System.out.println("\n Created HTML: AlGatorGraph_graphs.html");
	        System.out.println(" Images directory: " + OUTPUT_DIR);
	    }
	    
	    /**
         * Generates PNG images for all graph types.
         */
	    private static void generateGraphImages() {
	        System.out.println("Generating graph images");
	        int total = GRAPHS_WITHOUT_PARAMS.length + GRAPHS_WITH_PARAMS.length;
	        int processed = 0;
	        
	        for (String[] graphInfo : GRAPHS_WITHOUT_PARAMS) {
	            String name = graphInfo[0];
	            System.out.print("[" + (++processed) + "/" + total + "] " + name + ": ");
	            try {
	                generateGraphImage(name);
	                System.out.println("Y");
	            } catch (Exception e) {
	                System.out.println("N Error: " + e.getMessage());
	            }
	        }
	        
	        for (String[] graphInfo : GRAPHS_WITH_PARAMS) {
	            String name = graphInfo[0];
	            System.out.print("[" + (++processed) + "/" + total + "] " + name + ": ");
	            try {
	                generateGraphImage(name);
	                System.out.println("Y");
	            } catch (Exception e) {
	                System.out.println("N Error: " + e.getMessage());
	            }
	        }
	    }
	    
	    /**
         * Generates a PNG image for a specific graph type.
         * 
         * @param graphName Name of the graph type to generate
         * @throws IOException if image saving fails
         */
	    private static void generateGraphImage(String graphName) throws IOException {
	        String[] args = graphArgs.get(graphName);
	        if (args == null) {
	            throw new IllegalArgumentException("No arguments defined for graph: " + graphName);
	        }
	        
	        Graph<Integer, DefaultWeightedEdge> graph = generateGraph(args);
	        if (graph == null) {
	            throw new IllegalStateException("Failed to generate graph: " + graphName);
	        }
	        
	        String filename = OUTPUT_DIR + "/" + graphName.toLowerCase() + ".png";
	        renderGraphToImage(graph, graphName, filename);
	    }
	    
	    /**
         * Renders a graph to a PNG image file.
         * 
         * @param graph The graph to render
         * @param graphName Name of the graph type (for labeling)
         * @param filename Output filename for the PNG image
         * @throws IOException if image saving fails
         */
	    private static void renderGraphToImage(Graph<Integer, DefaultWeightedEdge> graph, String graphName, String filename) throws IOException {
	        int width = IMAGE_SIZE;
	        int height = IMAGE_SIZE;
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2d = image.createGraphics();
	        
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	        
	        g2d.setColor(BACKGROUND_COLOR);
	        g2d.fillRect(0, 0, width, height);
	        
	        Map<Integer, Point2D> positions = calculateVertexPositions(graph, width, height);
	        
	        g2d.setColor(EDGE_COLOR);
	        g2d.setStroke(new BasicStroke(2.0f));
	        
	        for (DefaultWeightedEdge edge : graph.edgeSet()) {
	            Integer source = graph.getEdgeSource(edge);
	            Integer target = graph.getEdgeTarget(edge);
	            
	            Point2D sourcePos = positions.get(source);
	            Point2D targetPos = positions.get(target);
	            
	            if (sourcePos != null && targetPos != null) {
	                g2d.drawLine(
	                    (int) sourcePos.getX(), (int) sourcePos.getY(),
	                    (int) targetPos.getX(), (int) targetPos.getY()
	                );
	                
	                if(graphName == "SIMPLE_WEIGHTED_GRAPH_MATRIX" || graphName == "SIMPLE_WEIGHTED_BIPARTITE_MATRIX") {
		                double w = graph.getEdgeWeight(edge);
		                String label = String.valueOf(w);
		
		                double midX = (sourcePos.getX() + targetPos.getX()) / 2.0;
		                double midY = (sourcePos.getY() + targetPos.getY()) / 2.0;
		
		                g2d.setColor(Color.BLACK);
		                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
		                g2d.drawString(label, (int) midX + 4, (int) midY - 4);
		
		                g2d.setColor(EDGE_COLOR);
	                }
	            }
	        }
	        
	        g2d.setColor(NODE_COLOR);
	        for (Point2D pos : positions.values()) {
	            int x = (int) pos.getX() - NODE_RADIUS;
	            int y = (int) pos.getY() - NODE_RADIUS;
	            g2d.fillOval(x, y, NODE_RADIUS * 2, NODE_RADIUS * 2);
	            
	            g2d.setColor(Color.BLACK);
	            g2d.setStroke(new BasicStroke(1.5f));
	            g2d.drawOval(x, y, NODE_RADIUS * 2, NODE_RADIUS * 2);
	            g2d.setColor(NODE_COLOR);
	        }
	        
	        g2d.setColor(TEXT_COLOR);
	        g2d.setFont(new Font("Arial", Font.BOLD, 14));
	        FontMetrics metrics = g2d.getFontMetrics();
	        String displayName = graphName.replace('_', ' ');
	        int textX = (width - metrics.stringWidth(displayName)) / 2;
	        g2d.drawString(displayName, textX, height - 10);
	        
	        String stats = "V: " + graph.vertexSet().size() + ", E: " + graph.edgeSet().size();
	        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
	        metrics = g2d.getFontMetrics();
	        int statsX = (width - metrics.stringWidth(stats)) / 2;
	        g2d.drawString(stats, statsX, height - 25);
	        
	        g2d.dispose();
	        
	        File outputFile = new File(filename);
	        ImageIO.write(image, "PNG", outputFile);
	    }
	    
	    /**
         * Calculates vertex positions for graph visualization in a circular layout.
         * 
         * @param graph The graph to layout
         * @param width Image width
         * @param height Image height
         * @return Map from vertex IDs to their positions
         */
	    private static Map<Integer, Point2D> calculateVertexPositions(Graph<Integer, DefaultWeightedEdge> graph, int width, int height) {
	        Map<Integer, Point2D> positions = new HashMap<>();
	        List<Integer> vertices = new ArrayList<>(graph.vertexSet());
	        int vertexCount = vertices.size();
	        
	        if (vertexCount == 0) {
	            return positions;
	        }
	        
	        double centerX = width / 2.0;
	        double centerY = height / 2.0;
	        double radius = Math.min(width, height) * 0.35; // Use 35% of the smaller dimension
	        
	        for (int i = 0; i < vertexCount; i++) {
	            double angle = 2 * Math.PI * i / vertexCount;
	            double x = centerX + radius * Math.cos(angle);
	            double y = centerY + radius * Math.sin(angle);
	            positions.put(vertices.get(i), new Point2D.Double(x, y));
	        }
	        return positions;
	    }
	    
	    /** Generates the full HTML document */
	    private static String generateHTML() {
	        StringBuilder html = new StringBuilder();
	        
	        html.append("<!DOCTYPE html>\n");
	        html.append("<html lang=\"en\">\n");
	        html.append("<head>\n");
	        html.append("    <meta charset=\"UTF-8\">\n");
	        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
	        html.append("    <title>ALGator Graph Generator Library</title>\n");
	        html.append("    <style>\n");
	        html.append("        * {\n");
	        html.append("            margin: 0;\n");
	        html.append("            padding: 0;\n");
	        html.append("            box-sizing: border-box;\n");
	        html.append("        }\n");
	        html.append("        body {\n");
	        html.append("            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n");
	        html.append("            line-height: 1.6;\n");
	        html.append("            color: #333;\n");
	        html.append("            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);\n");
	        html.append("            padding: 20px;\n");
	        html.append("        }\n");
	        html.append("        .container {\n");
	        html.append("            max-width: 1400px;\n");
	        html.append("            margin: 0 auto;\n");
	        html.append("        }\n");
	        html.append("        header {\n");
	        html.append("            text-align: center;\n");
	        html.append("            margin-bottom: 40px;\n");
	        html.append("            padding: 30px;\n");
	        html.append("            background: white;\n");
	        html.append("            border-radius: 15px;\n");
	        html.append("            box-shadow: 0 10px 30px rgba(0,0,0,0.1);\n");
	        html.append("        }\n");
	        html.append("        h1 {\n");
	        html.append("            font-size: 2.8em;\n");
	        html.append("            color: #2c3e50;\n");
	        html.append("            margin-bottom: 10px;\n");
	        html.append("            background: linear-gradient(90deg, #3498db, #2ecc71);\n");
	        html.append("            -webkit-background-clip: text;\n");
	        html.append("            background-clip: text;\n");
	        html.append("            color: transparent;\n");
	        html.append("        }\n");
	        html.append("        .subtitle {\n");
	        html.append("            font-size: 1.2em;\n");
	        html.append("            color: #7f8c8d;\n");
	        html.append("            margin-bottom: 20px;\n");
	        html.append("        }\n");
	        html.append("        .category-section {\n");
	        html.append("            margin-bottom: 40px;\n");
	        html.append("            background: white;\n");
	        html.append("            border-radius: 15px;\n");
	        html.append("            overflow: hidden;\n");
	        html.append("            box-shadow: 0 10px 30px rgba(0,0,0,0.1);\n");
	        html.append("        }\n");
	        html.append("        .category-header {\n");
	        html.append("            background: linear-gradient(135deg, #3498db, #2980b9);\n");
	        html.append("            color: white;\n");
	        html.append("            padding: 20px 30px;\n");
	        html.append("            margin-bottom: 0;\n");
	        html.append("        }\n");
	        html.append("        .category-header h2 {\n");
	        html.append("            font-size: 1.8em;\n");
	        html.append("            display: flex;\n");
	        html.append("            align-items: center;\n");
	        html.append("            gap: 10px;\n");
	        html.append("        }\n");
	        html.append("        .category-count {\n");
	        html.append("            background: rgba(255,255,255,0.2);\n");
	        html.append("            padding: 2px 10px;\n");
	        html.append("            border-radius: 12px;\n");
	        html.append("            font-size: 0.8em;\n");
	        html.append("        }\n");
	        html.append("        .graph-grid {\n");
	        html.append("            display: grid;\n");
	        html.append("            grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));\n");
	        html.append("            gap: 20px;\n");
	        html.append("            padding: 20px;\n");
	        html.append("        }\n");
	        html.append("        .graph-card {\n");
	        html.append("            background: #f8f9fa;\n");
	        html.append("            border-radius: 10px;\n");
	        html.append("            overflow: hidden;\n");
	        html.append("            box-shadow: 0 3px 10px rgba(0,0,0,0.1);\n");
	        html.append("            transition: transform 0.3s ease, box-shadow 0.3s ease;\n");
	        html.append("        }\n");
	        html.append("        .graph-card:hover {\n");
	        html.append("            transform: translateY(-5px);\n");
	        html.append("            box-shadow: 0 5px 20px rgba(0,0,0,0.15);\n");
	        html.append("        }\n");
	        html.append("        .graph-image-container {\n");
	        html.append("            height: 180px;\n");
	        html.append("            display: flex;\n");
	        html.append("            align-items: center;\n");
	        html.append("            justify-content: center;\n");
	        html.append("            background: white;\n");
	        html.append("            padding: 10px;\n");
	        html.append("        }\n");
	        html.append("        .graph-image {\n");
	        html.append("            max-width: 90%;\n");
	        html.append("            max-height: 90%;\n");
	        html.append("            object-fit: contain;\n");
	        html.append("        }\n");
	        html.append("        .graph-content {\n");
	        html.append("            padding: 15px;\n");
	        html.append("        }\n");
	        html.append("        .graph-name {\n");
	        html.append("            font-size: 1.2em;\n");
	        html.append("            font-weight: 600;\n");
	        html.append("            color: #2c3e50;\n");
	        html.append("            margin-bottom: 8px;\n");
	        html.append("        }\n");
	        html.append("        .graph-description {\n");
	        html.append("            font-size: 0.95em;\n");
	        html.append("            line-height: 1.4;\n");
	        html.append("            color: #555;\n");
	        html.append("            margin-bottom: 10px;\n");
	        html.append("        }\n");
	        html.append("        .parameter-badge {\n");
	        html.append("            display: inline-block;\n");
	        html.append("            padding: 3px 10px;\n");
	        html.append("            background: #e74c3c;\n");
	        html.append("            color: white;\n");
	        html.append("            border-radius: 12px;\n");
	        html.append("            font-size: 0.8em;\n");
	        html.append("            font-weight: bold;\n");
	        html.append("            margin-top: 5px;\n");
	        html.append("        }\n");
	        html.append("        .no-param-badge {\n");
	        html.append("            background: #2ecc71;\n");
	        html.append("        }\n");
	        html.append("        .parameters {\n");
	        html.append("            background: #ecf0f1;\n");
	        html.append("            padding: 8px 12px;\n");
	        html.append("            border-radius: 6px;\n");
	        html.append("            font-family: 'Consolas', 'Monaco', monospace;\n");
	        html.append("            font-size: 0.85em;\n");
	        html.append("            margin-top: 8px;\n");
	        html.append("            line-height: 1.4;\n");
	        html.append("        }\n");
	        html.append("        .docs-section {\n");
	        html.append("            margin-bottom: 40px;\n");
	        html.append("            background: white;\n");
	        html.append("            border-radius: 15px;\n");
	        html.append("            overflow: hidden;\n");
	        html.append("            box-shadow: 0 10px 30px rgba(0,0,0,0.1);\n");
	        html.append("        }\n");
	        html.append("        .docs-header {\n");
	        html.append("            background: linear-gradient(135deg, #e74c3c, #c0392b);\n");
	        html.append("            color: white;\n");
	        html.append("            padding: 20px 30px;\n");
	        html.append("        }\n");
	        html.append("        .docs-header h2 {\n");
	        html.append("            font-size: 1.8em;\n");
	        html.append("        }\n");
	        html.append("        .docs-content {\n");
	        html.append("            padding: 30px;\n");
	        html.append("        }\n");
	        html.append("        .code-block {\n");
	        html.append("            background: #2c3e50;\n");
	        html.append("            color: #ecf0f1;\n");
	        html.append("            padding: 15px;\n");
	        html.append("            border-radius: 8px;\n");
	        html.append("            overflow-x: auto;\n");
	        html.append("            font-family: 'Consolas', 'Monaco', monospace;\n");
	        html.append("            font-size: 0.9em;\n");
	        html.append("            margin: 15px 0;\n");
	        html.append("            line-height: 1.5;\n");
	        html.append("        }\n");
	        html.append("        .code-block pre {\n");
	        html.append("            margin: 0;\n");
	        html.append("            white-space: pre-wrap;\n");
	        html.append("            word-wrap: break-word;\n");
	        html.append("        }\n");
	        html.append("        .example-title {\n");
	        html.append("            font-size: 1.3em;\n");
	        html.append("            font-weight: 600;\n");
	        html.append("            color: #2c3e50;\n");
	        html.append("            margin-top: 25px;\n");
	        html.append("            margin-bottom: 10px;\n");
	        html.append("            border-left: 4px solid #3498db;\n");
	        html.append("            padding-left: 15px;\n");
	        html.append("        }\n");
	        html.append("        .note {\n");
	        html.append("            background: #ecf0f1;\n");
	        html.append("            border-left: 4px solid #3498db;\n");
	        html.append("            padding: 12px;\n");
	        html.append("            margin: 15px 0;\n");
	        html.append("            border-radius: 5px;\n");
	        html.append("        }\n");
	        html.append("        footer {\n");
	        html.append("            text-align: center;\n");
	        html.append("            margin-top: 40px;\n");
	        html.append("            padding: 15px;\n");
	        html.append("            color: #7f8c8d;\n");
	        html.append("            font-size: 0.9em;\n");
	        html.append("            background: white;\n");
	        html.append("            border-radius: 12px;\n");
	        html.append("            box-shadow: 0 3px 10px rgba(0,0,0,0.1);\n");
	        html.append("        }\n");
	        html.append("        @media (max-width: 768px) {\n");
	        html.append("            .graph-grid {\n");
	        html.append("                grid-template-columns: 1fr;\n");
	        html.append("            }\n");
	        html.append("            h1 {\n");
	        html.append("                font-size: 2em;\n");
	        html.append("            }\n");
	        html.append("        }\n");
	        html.append("    </style>\n");
	        html.append("</head>\n");
	        html.append("<body>\n");
	        html.append("    <div class=\"container\">\n");
	        html.append("        <header>\n");
	        html.append("            <h1>ALGator Graph Generator Library</h1>\n");
	        html.append("            <p class=\"subtitle\">Complete documentation and gallery of graph generation capabilities</p>\n");
	        html.append("        </header>\n");
	        
	        // Graph Gallery Sections
	        html.append(generateCategorySection("no-params", " Graphs Without Parameters", 
	                     GRAPHS_WITHOUT_PARAMS.length, GRAPHS_WITHOUT_PARAMS, false));
	        html.append(generateCategorySection("with-params", " Graphs With Parameters", 
	                     GRAPHS_WITH_PARAMS.length, GRAPHS_WITH_PARAMS, true));
	        
	        // Documentation Section 1: Creating Graphs with ALGatorGraph Library
	        html.append(generateALGatorGraphDocumentation());
	        
	        // Documentation Section 2: Generating Graphs with GraphCreator
	        html.append(generateGraphCreatorDocumentation());
	        
	        // Documentation Section 3: Importing Graphs from Files
	        html.append(generateImportDocumentation());
	        
	        html.append("    </div>\n");
	        
	        html.append("    <script>\n");
	        html.append("        // Add click handler to graph cards\n");
	        html.append("        document.addEventListener('DOMContentLoaded', function() {\n");
	        html.append("            const cards = document.querySelectorAll('.graph-card');\n");
	        html.append("            cards.forEach(card => {\n");
	        html.append("                card.addEventListener('click', function() {\n");
	        html.append("                    const name = this.querySelector('.graph-name').textContent;\n");
	        html.append("                    console.log('Selected graph:', name);\n");
	        html.append("                });\n");
	        html.append("            });\n");
	        html.append("            \n");
	        html.append("            // Add smooth scrolling for category headers\n");
	        html.append("            const categoryHeaders = document.querySelectorAll('.category-header');\n");
	        html.append("            categoryHeaders.forEach(header => {\n");
	        html.append("                header.addEventListener('click', function() {\n");
	        html.append("                    this.scrollIntoView({ behavior: 'smooth' });\n");
	        html.append("                });\n");
	        html.append("            });\n");
	        html.append("        });\n");
	        html.append("    </script>\n");
	        html.append("</body>\n");
	        html.append("</html>\n");
	        
	        return html.toString();
	    }

	    /**
	     * Generates the documentation section for creating graphs with ALGatorGraph library
	     */
	    private static String generateALGatorGraphDocumentation() {
	        StringBuilder html = new StringBuilder();
	        
	        html.append("        <div class=\"docs-section\">\n");
	        html.append("            <div class=\"docs-header\">\n");
	        html.append("                <h2>Creating Graphs with ALGatorGraph Library</h2>\n");
	        html.append("            </div>\n");
	        html.append("            <div class=\"docs-content\">\n");
	        html.append("                <p>The ALGatorGraph library provides two main graph classes: <strong>Graph</strong> (undirected) and <strong>DGraph</strong> (directed). Both support generic vertex and edge types with generic weights.</p>\n");
	        
	        // Example 1: Basic undirected graph
	        html.append("                <div class=\"example-title\">Example 1: Creating a Simple Undirected Graph</div>\n");
	        html.append("                <div class=\"code-block\">\n");
	        html.append("                    <pre>\n");
	        html.append("// Create an undirected graph with String vertices and Integer weights\n");
	        html.append("Graph&lt;String, Integer&gt; cityGraph = new Graph&lt;&gt;();\n");
	        html.append("\n");
	        html.append("// Add vertices (cities)\n");
	        html.append("cityGraph.addVertex(\"New York\");\n");
	        html.append("cityGraph.addVertex(\"Boston\");\n");
	        html.append("cityGraph.addVertex(\"Washington DC\");\n");
	        html.append("\n");
	        html.append("// Create edges with weights\n");
	        html.append("Edge&lt;String, Integer&gt; edge1 = new Edge&lt;&gt;(\"New York\", \"Boston\", 215);\n");
	        html.append("Edge&lt;String, Integer&gt; edge2 = new Edge&lt;&gt;(\"New York\", \"Washington DC\", 225);\n");
	        html.append("Edge&lt;String, Integer&gt; edge3 = new Edge&lt;&gt;(\"Boston\", \"Washington DC\", 440);\n");
	        html.append("\n");
	        html.append("// Add edges to the graph\n");
	        html.append("cityGraph.addEdge(\"New York\", \"Boston\", edge1);\n");
	        html.append("cityGraph.addEdge(\"New York\", \"Washington DC\", edge2);\n");
	        html.append("cityGraph.addEdge(\"Boston\", \"Washington DC\", edge3);\n");
	        html.append("\n");
	        html.append("// Get all vertices and edges\n");
	        html.append("Set&lt;String&gt; cities = cityGraph.vertexSet();\n");
	        html.append("Set&lt;Edge&lt;String, Integer&gt;&gt; routes = cityGraph.edgeSet();\n");
	        html.append("System.out.println(cityGraph);\n");
	        html.append("                    </pre>\n");
	        html.append("                </div>\n");
	        
	        // Example 2: Creating a directed graph
	        html.append("                <div class=\"example-title\">Example 2: Creating a Directed Graph</div>\n");
	        html.append("                <div class=\"code-block\">\n");
	        html.append("                    <pre>\n");
	        html.append("// Create a directed graph\n");
	        html.append("DGraph&lt;String, Double&gt; webGraph = new DGraph&lt;&gt;();\n");
	        html.append("\n");
	        html.append("// Add web pages as vertices\n");
	        html.append("webGraph.addVertex(\"Page A\");\n");
	        html.append("webGraph.addVertex(\"Page B\");\n");
	        html.append("webGraph.addVertex(\"Page C\");\n");
	        html.append("\n");
	        html.append("// Add directed edges (hyperlinks) with weights (importance scores)\n");
	        html.append("DEdge&lt;String, Double&gt; link1 = new DEdge&lt;&gt;(\"Page A\", \"Page B\", 0.85);\n");
	        html.append("DEdge&lt;String, Double&gt; link2 = new DEdge&lt;&gt;(\"Page A\", \"Page C\", 0.45);\n");
	        html.append("DEdge&lt;String, Double&gt; link3 = new DEdge&lt;&gt;(\"Page B\", \"Page C\", 0.92);\n");
	        html.append("\n");
	        html.append("webGraph.addEdge(\"Page A\", \"Page B\", link1);\n");
	        html.append("webGraph.addEdge(\"Page A\", \"Page C\", link2);\n");
	        html.append("webGraph.addEdge(\"Page B\", \"Page C\", link3);\n");
	        html.append("\n");
	        html.append("// Check outgoing links from Page A\n");
	        html.append("Set&lt;DEdge&lt;String, Double&gt;&gt; outLinks = webGraph.outgoingEdgesOf(\"Page A\");\n");
	        html.append("System.out.println(\"Outgoing links from Page A: \" + outLinks);\n");
	        html.append("                    </pre>\n");
	        html.append("                </div>\n");
	        
	     // Example 3: Building a graph from scratch with vertices numbered 0..n-1
	        html.append("                <div class=\"example-title\">Example 3: Building a Graph \"From Scratch\" (Simple Unweighted)</div>\n");
	        html.append("                <div class=\"code-block\">\n");
	        html.append("                    <pre>\n");
	        html.append("// Create an unweighted graph\n");
	        html.append("Graph&lt;Integer, Object&gt; myGraph = new Graph&lt;&gt;();\n");
	        html.append("\n");
	        html.append("// Add vertices 0 through 4\n");
	        html.append("for (int i = 0; i &lt; 5; i++) {\n");
	        html.append("    myGraph.addVertex(i);\n");
	        html.append("}\n");
	        html.append("\n");
	        html.append("// Add unweighted edges using the simple addEdge method\n");
	        html.append("// (creates edges with null weights automatically)\n");
	        html.append("myGraph.addEdge(0, 1);\n");
	        html.append("myGraph.addEdge(0, 2);\n");
	        html.append("myGraph.addEdge(1, 2);\n");
	        html.append("myGraph.addEdge(1, 3);\n");
	        html.append("myGraph.addEdge(2, 4);\n");
	        html.append("myGraph.addEdge(3, 4);\n");
	        html.append("\n");
	        html.append("// Display graph information\n");
	        html.append("System.out.println(\"Number of vertices: \" + myGraph.vertexSet().size());\n");
	        html.append("System.out.println(\"Number of edges: \" + myGraph.edgeSet().size());\n");
	        html.append("                    </pre>\n");
	        html.append("                </div>\n");

	        html.append("            </div>\n");
	        html.append("        </div>\n");
	        
	        return html.toString();
	    }

	    /**
	     * Generates the documentation section for generating graphs with GraphCreator
	     */
	    private static String generateGraphCreatorDocumentation() {
	        StringBuilder html = new StringBuilder();
	        
	        html.append("        <div class=\"docs-section\">\n");
	        html.append("            <div class=\"docs-header\">\n");
	        html.append("                <h2>Generating Graphs with GraphCreator</h2>\n");
	        html.append("            </div>\n");
	        html.append("            <div class=\"docs-content\">\n");
	        html.append("                <p>The <strong>GraphCreator</strong> class provides a unified interface for generating various types of graphs using the <code>generateGraph()</code> method. Graph types are specified using the <code>GraphTypes</code> enum.</p>\n");
	        
	        // Example 1: Parameterless graph
	        html.append("                <div class=\"example-title\">Example 1: Generating a Graph Without Parameters</div>\n");
	        html.append("                <div class=\"code-block\">\n");
	        html.append("                    <pre>\n");
	        html.append("// Generate the Petersen graph (no parameters needed)\n");
	        html.append("String[] args = {\"PETERSEN\"};\n");
	        html.append("Graph&lt;Integer, DefaultWeightedEdge&gt; petersenGraph = GraphCreator.generateGraph(args);\n");
	        html.append("\n");
	        html.append("System.out.println(\"Petersen Graph:\");\n");
	        html.append("System.out.println(\"  Vertices: \" + petersenGraph.vertexSet().size());  // Output: 10\n");
	        html.append("System.out.println(\"  Edges: \" + petersenGraph.edgeSet().size());     // Output: 15\n");
	        html.append("                    </pre>\n");
	        html.append("                </div>\n");
	        
	        // Example 2: Complete graph with parameters
	        html.append("                <div class=\"example-title\">Example 2: Generating a Complete Graph (K5)</div>\n");
	        html.append("                <div class=\"code-block\">\n");
	        html.append("                    <pre>\n");
	        html.append("// Generate a complete graph with 5 vertices\n");
	        html.append("String[] args = {\"COMPLETE\", \"5\"};\n");
	        html.append("Graph&lt;Integer, DefaultWeightedEdge&gt; completeGraph = GraphCreator.generateGraph(args);\n");
	        html.append("\n");
	        html.append("System.out.println(\"Complete Graph K5:\");\n");
	        html.append("System.out.println(\"  Vertices: \" + completeGraph.vertexSet().size());  // Output: 5\n");
	        html.append("System.out.println(\"  Edges: \" + completeGraph.edgeSet().size());     // Output: 10\n");
	        html.append("                    </pre>\n");
	        html.append("                </div>\n");
	        
	        // Example 3: Random graph
	        html.append("                <div class=\"example-title\">Example 3: Generating a Random Graph (GNP)</div>\n");
	        html.append("                <div class=\"code-block\">\n");
	        html.append("                    <pre>\n");
	        html.append("// Generate a random graph with 20 vertices and edge probability 0.3\n");
	        html.append("String[] args = {\"GNP_RANDOM_GRAPH\", \"20\", \"0.3\", \"12345\"};\n");
	        html.append("Graph&lt;Integer, DefaultWeightedEdge&gt; randomGraph = GraphCreator.generateGraph(args);\n");
	        html.append("\n");
	        html.append("System.out.println(\"Random Graph (GNP):\");\n");
	        html.append("System.out.println(\"  Vertices: \" + randomGraph.vertexSet().size());\n");
	        html.append("System.out.println(\"  Edges: \" + randomGraph.edgeSet().size());\n");
	        html.append("                    </pre>\n");
	        html.append("                </div>\n");
	        
	        // Example 4: Grid graph
	        html.append("                <div class=\"example-title\">Example 4: Generating a Grid Graph</div>\n");
	        html.append("                <div class=\"code-block\">\n");
	        html.append("                    <pre>\n");
	        html.append("// Generate a 4x5 grid graph\n");
	        html.append("String[] args = {\"GRID\", \"4\", \"5\"};\n");
	        html.append("Graph&lt;Integer, DefaultWeightedEdge&gt; gridGraph = GraphCreator.generateGraph(args);\n");
	        html.append("\n");
	        html.append("System.out.println(\"Grid Graph (4x5):\");\n");
	        html.append("System.out.println(\"  Vertices: \" + gridGraph.vertexSet().size());  // Output: 20\n");
	        html.append("System.out.println(\"  Edges: \" + gridGraph.edgeSet().size());     // Output: 31\n");
	        html.append("                    </pre>\n");
	        html.append("                </div>\n");
	        
	        // Example 5: Scale-free network
	        html.append("                <div class=\"example-title\">Example 5: Generating a Scale-Free Network</div>\n");
	        html.append("                <div class=\"code-block\">\n");
	        html.append("                    <pre>\n");
	        html.append("// Generate a scale-free network with 100 vertices\n");
	        html.append("String[] args = {\"SCALE_FREE\", \"100\", \"42\"};\n");
	        html.append("Graph&lt;Integer, DefaultWeightedEdge&gt; scaleFreeGraph = GraphCreator.generateGraph(args);\n");
	        html.append("\n");
	        html.append("System.out.println(\"Scale-Free Network:\");\n");
	        html.append("System.out.println(\"  Vertices: \" + scaleFreeGraph.vertexSet().size());\n");
	        html.append("System.out.println(\"  Edges: \" + scaleFreeGraph.edgeSet().size());\n");
	        html.append("                    </pre>\n");
	        html.append("                </div>\n");
	        
	        html.append("                <div class=\"note\">\n");
	        html.append("                    <strong>Note:</strong> The <code>GraphTypes</code> enum includes over 80 different graph types, ranging from classic named graphs (Petersen, Chvátal, Karate Club) to parameterized generators (Complete, Grid, Watts-Strogatz, etc.). Check the gallery above for the complete list!\n");
	        html.append("                </div>\n");
	        
	        html.append("            </div>\n");
	        html.append("        </div>\n");
	        
	        return html.toString();
	    }

	    /**
	     * Generates the documentation section for importing graphs from files
	     */
	    private static String generateImportDocumentation() {
	        StringBuilder html = new StringBuilder();
	        
	        html.append("        <div class=\"docs-section\">\n");
			html.append("            <div class=\"docs-header\">\n");
			html.append("                <h2>Importing Graphs from Files</h2>\n");
			html.append("            </div>\n");
			html.append("            <div class=\"docs-content\">\n");
			html.append("                <p>The <code>GraphCreator</code> class automatically detects formats based on file extensions. Supported formats and their sources include:</p>\n");
			html.append("                \n");
			html.append("                <ul class=\"format-list\" style=\"line-height: 1.6; margin-left: 40px;\">\n");
			html.append("                    <li><strong>GraphML</strong> (<code>.graphml</code>): <a href=\"https://visdunneright.github.io/gd_benchmark_sets/\">GD Benchmark Sets</a></li>\n");
			html.append("                    <li><strong>Pajek</strong> (<code>.net, .paj, .mat</code>): <a href=\"http://vlado.fmf.uni-lj.si/pub/networks/data/\">Pajek Datasets</a></li>\n");
			html.append("                    <li><strong>Network Repo</strong> (<code>.edges, .mtx</code>): <a href=\"https://networkrepository.com\">Network Repository</a></li>\n");
			html.append("                    <li><strong>Stanford SNAP</strong> (<code>.txt, .csv</code>): <a href=\"https://snap.stanford.edu/data/\">SNAP Repository</a></li>\n");
			html.append("                    <li><strong>TU Dataset</strong> (Folder): <a href=\"https://chrsmrrs.github.io/datasets/docs/datasets/\">TU Dortmund</a></li>\n");
			html.append("                    <li><strong>DIMACS10</strong> (<code>.graph</code>): <a href=\"https://sparse.tamu.edu/DIMACS10\">DIMACS Archive</a></li>\n");
			html.append("                    <li><strong>LST</strong> (<code>.lst</code>): <a href=\"https://houseofgraphs.org\">House of Graphs</a></li>\n");
			html.append("                    <li><strong>MalNet</strong> (<code>.edgelist</code>): <a href=\"https://www.mal-net.org/\">MalNet Project</a></li>\n");
			html.append("                </ul>\n");
	        
	        // Example 1: Import a single graph
	        html.append("                <div class=\"example-title\">Example 1: Importing a Single Graph from a File</div>\n");
	        html.append("                <div class=\"code-block\">\n");
	        html.append("                    <pre>\n");
	        html.append("// Import a single graph from a file (format is auto-detected)\n");
	        html.append("File graphFile = new File(\"path/to/mygraph.graphml\");\n");
	        html.append("Graph importedGraph = GraphCreator.importGraphFromFile(graphFile);\n");
	        html.append("\n");
	        html.append("if (importedGraph != null) {\n");
	        html.append("    System.out.println(\"Successfully imported graph:\");\n");
	        html.append("    System.out.println(\"  Vertices: \" + importedGraph.vertexSet().size());\n");
	        html.append("    System.out.println(\"  Edges: \" + importedGraph.edgeSet().size());\n");
	        html.append("} else {\n");
	        html.append("    System.out.println(\"Failed to import graph\");\n");
	        html.append("}\n");
	        html.append("                    </pre>\n");
	        html.append("                </div>\n");
	        
	        // Example 2: Import multiple graphs from folder
	        html.append("                <div class=\"example-title\">Example 2: Importing All Graphs from a Folder</div>\n");
	        html.append("                <div class=\"code-block\">\n");
	        html.append("                    <pre>\n");
	        html.append("// Import all graphs from a folder (creates import_log.txt)\n");
	        html.append("String folderPath = \"path/to/graphs/folder\";\n");
	        html.append("List&lt;Graph&gt; graphCollection = GraphCreator.importGraphsFromFolder(folderPath);\n");
	        html.append("\n");
	        html.append("System.out.println(\"Imported \" + graphCollection.size() + \" graphs successfully\");\n");
	        html.append("\n");
	        html.append("// Display information about each imported graph\n");
	        html.append("for (int i = 0; i &lt; graphCollection.size(); i++) {\n");
	        html.append("    Graph g = graphCollection.get(i);\n");
	        html.append("    System.out.println(\"Graph \" + i + \": \" + g.vertexSet().size() + \" vertices, \" + g.edgeSet().size() + \" edges\");\n");
	        html.append("}\n");
	        html.append("                    </pre>\n");
	        html.append("                </div>\n");
	        
	        // Example 3: Working with collections
	        html.append("                <div class=\"example-title\">Example 3: Working with Graph Collections</div>\n");
	        html.append("                <div class=\"code-block\">\n");
	        html.append("                    <pre>\n");
	        html.append("// Get all graph names in a collection\n");
	        html.append("String folderPath = \"path/to/graphs\";\n");
	        html.append("String collectionName = \"my_collection\";\n");
	        html.append("String[] graphNames = GraphCreator.getGraphNames(folderPath, collectionName);\n");
	        html.append("\n");
	        html.append("System.out.println(\"Graphs in collection: \" + Arrays.toString(graphNames));\n");
	        html.append("\n");
	        html.append("// Get a random graph from the collection\n");
	        html.append("Graph randomGraph = GraphCreator.getRandomGraph(folderPath, collectionName);\n");
	        html.append("\n");
	        html.append("// Get description of a specific graph\n");
	        html.append("GraphCreator.GraphDescription desc = GraphCreator.getGraphDescription(folderPath, collectionName, graphNames[0]);\n");
	        html.append("System.out.println(desc);\n");
	        html.append("                    </pre>\n");
	        html.append("                </div>\n");
	        
	        // Example 4: Error handling
	        html.append("                <div class=\"example-title\">Example 4: Handling Import Errors</div>\n");
	        html.append("                <div class=\"code-block\">\n");
	        html.append("                    <pre>\n");
	        html.append("// The import process handles errors gracefully\n");
	        html.append("// - OutOfMemoryError: System attempts to empty memory and continue importing the rest of the graphs\n");
	        html.append("// - Parse errors: Skips problematic files and logs them in a log file inside the graph folder\n");
	        html.append("// - Log file: import_log.txt is created with success/failure status for each graph\n");
	        html.append("\n");
	        html.append("List&lt;Graph&gt; graphs = GraphCreator.importGraphsFromFolder(\"path/to/folder\");\n");
	        html.append("\n");
	        html.append("// Check the log file for details on any failures\n");
	        html.append("File logFile = new File(\"path/to/folder/import_log.txt\");\n");
	        html.append("// Format: filename success(1/0)\n");
	        html.append("                    </pre>\n");
	        html.append("                </div>\n");
	        
	        html.append("            </div>\n");
	        html.append("        </div>\n");
	        
	        return html.toString();
	    }
	    
	    /** Generates a category on the HTML document */
	    private static String generateCategorySection(String id, String title, int count, 
	                                                  String[][] graphs, boolean hasParams) {
	        StringBuilder section = new StringBuilder();
	        section.append("        <div class=\"category-section\" id=\"").append(id).append("\">\n");
	        section.append("            <div class=\"category-header\">\n");
	        section.append("                <h2>").append(title).append(" <span class=\"category-count\">")
	               .append(count).append(" graphs</span></h2>\n");
	        section.append("            </div>\n");
	        section.append("            <div class=\"graph-grid\">\n");
	        
	        for (String[] graphInfo : graphs) {
	            section.append(generateGraphCard(graphInfo[0], graphInfo[1], hasParams));
	        }
	        
	        section.append("            </div>\n");
	        section.append("        </div>\n");
	        return section.toString();
	    }
	    
	    /** Generates a graph card */
	    private static String generateGraphCard(String name, String description, boolean hasParams) {
	        String imagePath = OUTPUT_DIR + "/" + name.toLowerCase() + ".png";
	        
	        StringBuilder card = new StringBuilder();
	        card.append("        <div class=\"graph-card\">\n");
	        card.append("            <div class=\"graph-image-container\">\n");
	        card.append("                <img src=\"").append(imagePath).append("\" alt=\"").append(name).append("\" class=\"graph-image\">\n");
	        card.append("            </div>\n");
	        card.append("            <div class=\"graph-content\">\n");
	        card.append("                <div class=\"graph-name\">").append(formatGraphName(name)).append("</div>\n");
	        card.append("                <div class=\"graph-description\">").append(description).append("</div>\n");
	        
	        if (hasParams) {
	            String[] args = graphArgs.get(name);
	            if (args != null && args.length > 1) {
	                card.append("                <div class=\"parameters\">\n");
	                card.append("                    Example Args: ").append(String.join(", ", args)).append("\n");
	                card.append("                </div>\n");
	            }
	            card.append("                <div class=\"parameter-badge\">Parameterized</div>\n");
	        } else {
	            card.append("                <div class=\"parameter-badge no-param-badge\">No Parameters</div>\n");
	        }
	        
	        card.append("            </div>\n");
	        card.append("        </div>\n");
	        
	        return card.toString();
	    }
	    
	    /** Renaming helper function */
	    private static String formatGraphName(String name) {
	        return name.replace('_', ' ');
	    }
	}
		
	/**
     * Custom generator for House graph (pentagon with a triangle on top).
     */	
	private static class HouseGraphGenerator implements GraphGenerator<Integer, DefaultWeightedEdge, Integer> {
		
	    @Override
	    public void generateGraph(Graph<Integer, DefaultWeightedEdge> graph, Map<String, Integer> resultMap) {
	        int v0 = 0;
	        int v1 = 1;
	        int v2 = 2;
	        int v3 = 3;
	        int v4 = 4;

	        graph.addVertex(v0);
	        graph.addVertex(v1);
	        graph.addVertex(v2);
	        graph.addVertex(v3);
	        graph.addVertex(v4);

	        graph.addEdge(v0, v1);
	        graph.addEdge(v1, v2);
	        graph.addEdge(v2, v3);
	        graph.addEdge(v3, v0);

	        graph.addEdge(v0, v4);
	        graph.addEdge(v3, v4);

	        if (resultMap != null) {
	            resultMap.put("v0", v0);
	            resultMap.put("v1", v1);
	            resultMap.put("v2", v2);
	            resultMap.put("v3", v3);
	            resultMap.put("v4", v4);
	        }
	    }
	}
	/**
     * Custom generator for Gem graph (diamond with an extra vertex).
     */
	private static class GemGraphGenerator implements GraphGenerator<Integer, DefaultWeightedEdge, Integer> {

	    @Override
	    public void generateGraph(Graph<Integer, DefaultWeightedEdge> graph, Map<String, Integer> resultMap) {
	        int v0 = 0;
	        int v1 = 1;
	        int v2 = 2;
	        int v3 = 3;
	        int v4 = 4;

	        graph.addVertex(v0);
	        graph.addVertex(v1);
	        graph.addVertex(v2);
	        graph.addVertex(v3);
	        graph.addVertex(v4);

	        graph.addEdge(v0, v1);
	        graph.addEdge(v1, v2);
	        graph.addEdge(v2, v3);
	        
	        graph.addEdge(v0, v4);
	        graph.addEdge(v1, v4);
	        graph.addEdge(v2, v4);
	        graph.addEdge(v3, v4);

	        if (resultMap != null) {
	            resultMap.put("v0", v0);
	            resultMap.put("v1", v1);
	            resultMap.put("v2", v2);
	            resultMap.put("v3", v3);
	            resultMap.put("v4", v4);
	        }
	    }
	}
	/**
     * Custom generator for Barbell graph (two complete graphs connected by a bridge).
     */
	private static class BarbellGraphGenerator implements GraphGenerator<Integer, DefaultWeightedEdge, Integer> {

	    private final int size1; // size of complete graphs
	    private final int size2; // number of vertices in the connecting path

	    /**
	     * Creates a barbell graph generator.
	     * 
	     * @param size1 size of the first complete graph
	     * @param size2 size of the second complete graph
	     */
	    public BarbellGraphGenerator(int size1, int size2) {
	        this.size1 = size1;
	        this.size2 = size2;
	    }

	    @Override
	    public void generateGraph(Graph<Integer, DefaultWeightedEdge> graph, Map<String, Integer> resultMap) {
	        int vertexCounter = 0;

	        List<Integer> firstClique = new ArrayList<>();
	        for (int i = 0; i < size1; i++) {
	            int v = vertexCounter++;
	            graph.addVertex(v);
	            firstClique.add(v);
	        }
	        for (int i = 0; i < firstClique.size(); i++) {
	            for (int j = i + 1; j < firstClique.size(); j++) {
	                graph.addEdge(firstClique.get(i), firstClique.get(j));
	            }
	        }

	        List<Integer> secondClique = new ArrayList<>();
	        for (int i = 0; i < size1; i++) {
	            int v = vertexCounter++;
	            graph.addVertex(v);
	            secondClique.add(v);
	        }
	        for (int i = 0; i < secondClique.size(); i++) {
	            for (int j = i + 1; j < secondClique.size(); j++) {
	                graph.addEdge(secondClique.get(i), secondClique.get(j));
	            }
	        }

	        List<Integer> bridge = new ArrayList<>();
	        for (int i = 0; i < size2; i++) {
	            int v = vertexCounter++;
	            graph.addVertex(v);
	            bridge.add(v);
	        }

	        graph.addEdge(firstClique.get(firstClique.size() - 1), bridge.get(0));

	        for (int i = 0; i < bridge.size() - 1; i++) {
	            graph.addEdge(bridge.get(i), bridge.get(i + 1));
	        }

	        graph.addEdge(bridge.get(bridge.size() - 1), secondClique.get(0));

	        if (resultMap != null) {
	            resultMap.put("firstCliqueStart", firstClique.get(0));
	            resultMap.put("firstCliqueEnd", firstClique.get(firstClique.size() - 1));
	            resultMap.put("secondCliqueStart", secondClique.get(0));
	            resultMap.put("secondCliqueEnd", secondClique.get(secondClique.size() - 1));
	        }
	    }
	}
	/**
     * Custom generator for Broom graph (path with multiple leaves at one end).
     */
	private static class BroomGraphGenerator implements GraphGenerator<Integer, DefaultWeightedEdge, Integer> {

	    private final int pathLength; // length of the main path
	    private final int numLeaves;  // number of leaves attached at one end

	    /**
	     * Creates a barbell graph generator.
	     * 
	     * @param pathLength size of the path
	     * @param numLeaves number of leaves at the end
	     */
	    public BroomGraphGenerator(int pathLength, int numLeaves) {
	        this.pathLength = pathLength;
	        this.numLeaves = numLeaves;
	    }

	    @Override
	    public void generateGraph(Graph<Integer, DefaultWeightedEdge> graph, Map<String, Integer> resultMap) {
	        int vertexCounter = 0;

	        List<Integer> pathVertices = new ArrayList<>();
	        for (int i = 0; i < pathLength; i++) {
	            int v = vertexCounter++;
	            graph.addVertex(v);
	            pathVertices.add(v);
	            if (i > 0) {
	                graph.addEdge(pathVertices.get(i - 1), v);
	            }
	        }

	        // Attach leaves to the first vertex of the path
	        int root = pathVertices.get(0);
	        for (int i = 0; i < numLeaves; i++) {
	            int leaf = vertexCounter++;
	            graph.addVertex(leaf);
	            graph.addEdge(root, leaf);
	        }
	        if (resultMap != null) {
	            resultMap.put("root", root);
	            resultMap.put("pathStart", pathVertices.get(0));
	            resultMap.put("pathEnd", pathVertices.get(pathVertices.size() - 1));
	        }
	    }
	}
	/**
     * Custom generator for Crown graph (bipartite graph with crown pattern).
     */
	private static class CrownGraphGenerator implements GraphGenerator<Integer, DefaultWeightedEdge, Integer> {

	    private final int size;
	    
	    /**
	     * Creates a crown graph generator.
	     * A crown graph is a bipartite graph with a "crown" pattern.
	     * 
	     * @param size number of vertex pairs in the crown
	     */
	    public CrownGraphGenerator(int size) {
	        this.size = size;
	    }

	    @Override
	    public void generateGraph(Graph<Integer, DefaultWeightedEdge> graph, Map<String, Integer> resultMap) {
	        List<Integer> topPartition = new ArrayList<>();
	        List<Integer> bottomPartition = new ArrayList<>();
	        int vertexCounter = 0;

	        for (int i = 0; i < size; i++) {
	            int top = vertexCounter++;
	            int bottom = vertexCounter++;
	            graph.addVertex(top);
	            graph.addVertex(bottom);
	            topPartition.add(top);
	            bottomPartition.add(bottom);
	        }

	        for (int i = 0; i < size; i++) {
	            int top = topPartition.get(i);
	            int bottom = bottomPartition.get((i + 1) % size);
	            graph.addEdge(top, bottom);
	        }

	        if (resultMap != null) {
	            resultMap.put("topStart", topPartition.get(0));
	            resultMap.put("bottomStart", bottomPartition.get(0));
	        }
	    }
	}
	/**
     * Custom generator for Fan graph (single vertex joined to a path).
     */
	private static class FanGraphGenerator implements GraphGenerator<Integer, DefaultWeightedEdge, Integer> {

	    private final int pathLength; // length of the path to attach to the hub
	    
	    /**
	     * Creates a fan graph generator.
	     * A fan graph consists of a single vertex (hub) connected to all vertices of a path.
	     * 
	     * @param pathLength length of the path attached to the hub
	     */
	    public FanGraphGenerator(int pathLength) {
	        this.pathLength = pathLength;
	    }

	    @Override
	    public void generateGraph(Graph<Integer, DefaultWeightedEdge> graph, Map<String, Integer> resultMap) {
	        if (pathLength < 1) return;

	        int vertexCounter = 0;

	        int hub = vertexCounter++;
	        graph.addVertex(hub);

	        // Create the path vertices
	        List<Integer> pathVertices = new ArrayList<>();
	        for (int i = 0; i < pathLength; i++) {
	            int v = vertexCounter++;
	            graph.addVertex(v);
	            pathVertices.add(v);

	            if (i > 0) {
	                graph.addEdge(pathVertices.get(i - 1), v);
	            }

	            // Connect each path vertex to the hub
	            graph.addEdge(hub, v);
	        }

	        if (resultMap != null) {
	            resultMap.put("hub", hub);
	            resultMap.put("firstPath", pathVertices.get(0));
	        }
	    }
	}
	/**
     * Custom generator for Ladder graph (two parallel paths connected by edges).
     */
	private static class LadderGraphGenerator implements GraphGenerator<Integer, DefaultWeightedEdge, Integer> {

	    private final int length;
	    
	    /**
	     * Creates a ladder graph generator.
	     * A ladder graph consists of two parallel paths of equal length 
	     * connected by "rungs" (edges between corresponding vertices).
	     * 
	     * @param length number of rungs in the ladder (creates 2×(length+1) vertices)
	     */
	    public LadderGraphGenerator(int length) {
	        this.length = length;
	    }

	    @Override
	    public void generateGraph(Graph<Integer, DefaultWeightedEdge> graph, Map<String, Integer> resultMap) {
	        if (length < 1) return;

	        int vertexCounter = 0;

	        List<Integer> top = new ArrayList<>();

	        List<Integer> bottom = new ArrayList<>();

	        for (int i = 0; i < length + 1; i++) {
	            int vTop = vertexCounter++;
	            int vBottom = vertexCounter++;

	            graph.addVertex(vTop);
	            graph.addVertex(vBottom);

	            top.add(vTop);
	            bottom.add(vBottom);

	            if (i > 0) {
	                graph.addEdge(top.get(i - 1), vTop);
	            }

	            if (i > 0) {
	                graph.addEdge(bottom.get(i - 1), vBottom);
	            }

	            graph.addEdge(vTop, vBottom);
	        }

	        if (resultMap != null) {
	            resultMap.put("topStart", top.get(0));
	            resultMap.put("bottomStart", bottom.get(0));
	        }
	    }
	}
	/**
     * Custom generator for Prism graph (two n-cycles connected by matching edges).
     */
	private static class PrismGraphGenerator implements GraphGenerator<Integer, DefaultWeightedEdge, Integer> {

	    private final int n; // number of vertices in each cycle
	    
	    /**
	     * Creates a prism graph generator.
	     * A prism graph consists of two n-cycles connected by matching edges,
	     * forming a 3D prism shape in graph form.
	     * 
	     * @param n number of vertices in each cycle (creates 2×n vertices total)
	     */
	    public PrismGraphGenerator(int n) {
	        this.n = n;
	    }

	    @Override
	    public void generateGraph(Graph<Integer, DefaultWeightedEdge> graph, Map<String, Integer> resultMap) {
	        if (n < 3) return;

	        int vertexCounter = 0;

	        List<Integer> top = new ArrayList<>();
	        List<Integer> bottom = new ArrayList<>();

	        for (int i = 0; i < n; i++) {
	            int vTop = vertexCounter++;
	            int vBottom = vertexCounter++;

	            graph.addVertex(vTop);
	            graph.addVertex(vBottom);

	            top.add(vTop);
	            bottom.add(vBottom);
	        }

	        for (int i = 0; i < n; i++) {
	            int v1 = top.get(i);
	            int v2 = top.get((i + 1) % n);
	            graph.addEdge(v1, v2);
	        }

	        for (int i = 0; i < n; i++) {
	            int v1 = bottom.get(i);
	            int v2 = bottom.get((i + 1) % n);
	            graph.addEdge(v1, v2);
	        }

	        for (int i = 0; i < n; i++) {
	            graph.addEdge(top.get(i), bottom.get(i));
	        }

	        if (resultMap != null) {
	            resultMap.put("top0", top.get(0));
	            resultMap.put("bottom0", bottom.get(0));
	        }
	    }
	}
	/**
     * Custom generator for Sunlet graph (cycle with pendant vertices attached).
     */
	private static class SunletGraphGenerator<V extends Integer, E> implements GraphGenerator<Integer, DefaultWeightedEdge, Integer> {

		private final int n;
		
		/**
		 * Creates a sunlet graph generator.
		 * A sunlet graph consists of an n-cycle (central ring) with a pendant 
		 * vertex (leaf) attached to each vertex of the cycle.
		 * 
		 * @param n number of vertices in the central cycle (creates 2×n vertices total)
		 */
		public SunletGraphGenerator(int n) {
		    this.n = n;
		}
		
		@Override
		public void generateGraph(Graph<Integer, DefaultWeightedEdge> graph, Map<String, Integer> resultMap) {
		    if (n < 3) return;
		
		    int counter = 0;
	
		    List<Integer> cycle = new ArrayList<>();
		    List<Integer> leaves = new ArrayList<>();
		    for (int i = 0; i < n; i++) {
		        int v = counter++;
		        int leaf = counter+n;
		        graph.addVertex(v);
		        graph.addVertex(leaf);
		        cycle.add(v);
		        leaves.add(leaf);
		    }
		
		    for (int i = 0; i < n; i++) {
		        int v1 = cycle.get(i);
		        int v2 = cycle.get((i + 1) % n);
		        graph.addEdge(v1, v2);
		    }
		
		    for (int i = 0; i < n; i++) {
		        graph.addEdge(cycle.get(i), leaves.get(i));
		    }
		
		    if (resultMap != null) {
		        resultMap.put("firstCycleVertex", cycle.get(0));
		    }
		}
	}

	/**
     * Custom generator for weighted graphs from adjacency matrices.
     * Creates a weighted graph from a list of vertices and a symmetric weight matrix.
     */
	private static class WeightedGraphMatrixGenerator implements GraphGenerator<Integer, DefaultWeightedEdge, Integer> {
	
		private final List<Integer> vertices;
		private final double[][] weights;
		
		/**
		 * Creates a weighted graph generator from an adjacency matrix.
		 * 
		 * @param vertices list of vertex identifiers for the graph
		 * @param weights symmetric weight matrix where weights[i][j] is the weight between vertex i and j
		 * @throws IllegalArgumentException if weight matrix is not square or doesn't match vertices count
		 */
		public WeightedGraphMatrixGenerator(List<Integer> vertices, double[][] weights) {
		    if (vertices.size() != weights.length || weights.length != weights[0].length) {
		        throw new IllegalArgumentException("Weight matrix must be square and match number of vertices");
		    }
		    this.vertices = vertices;
		    this.weights = weights;
		}
		
		@Override
		public void generateGraph(Graph<Integer, DefaultWeightedEdge> graph,Map<String, Integer> resultMap) {
		
		    for (Integer v : vertices) {
		        graph.addVertex(v);
		    }
		
		    for (int i = 0; i < vertices.size(); i++) {
		        for (int j = i + 1; j < vertices.size(); j++) {
		            double w = weights[i][j];
		
		            if (w != 0.0) {
		                DefaultWeightedEdge e = graph.addEdge(vertices.get(i), vertices.get(j));
		
		                if (e != null && graph instanceof SimpleWeightedGraph) {
		                    ((SimpleWeightedGraph<Integer, DefaultWeightedEdge>) graph)
		                            .setEdgeWeight(e, w);
		                }
		            }
		        }
		    }
		
		    if (resultMap != null && !vertices.isEmpty()) {
		        resultMap.put("firstVertex", vertices.get(0));
		    }
		}
	}

}
