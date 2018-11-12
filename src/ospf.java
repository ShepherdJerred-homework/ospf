// ospf
// Jerred Shepherd

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class ospf {
    public static void main(String[] args) throws FileNotFoundException {
        List<Input> inputs = getInputs();
        List<Solution> solutions = solveInputs(inputs);
        printSolutions(solutions);
    }

    static void printSolution(Solution solution, PrintWriter printWriter) {
        System.out.println(solution);
        printWriter.println(solution);
    }

    static void printSolutions(List<Solution> solutions) throws FileNotFoundException {
        File outputFile = new File("ospf.out");
        PrintWriter printWriter = new PrintWriter(outputFile);
        int i = 0;
        for (Solution solution : solutions) {
            printSolution(solution, printWriter);
            if (i < solutions.size() - 1) {
                printWriter.println("*****");
                System.out.println("*****");
            }
            i++;
        }
        printWriter.close();
    }

    static int findMinDistance(Set<Integer> sptSet, double[] distances) {
        int u = -1;
        for (int j = 0; j < distances.length; j++) {
            if (sptSet.contains(j)) {
                continue;
            }
            if ((u == -1 || distances[j] < distances[u]) && distances[j] != -1) {
                u = j;
            }
        }
        return u;
    }

    // https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
    static DistancesFromSourceToDestinations findShortestPath(double[][] matrix, int source, int numberOfNodes) {
        Set<Integer> sptSet = new HashSet<>();
        double[] distances = new double[numberOfNodes];

        for (int i = 0; i < numberOfNodes; i++) {
            if (i == source - 1) {
                distances[i] = 0.0;
            } else {
                distances[i] = Double.MAX_VALUE;
            }
        }

        for (int i = 0; i < numberOfNodes; i++) {
            int u = findMinDistance(sptSet, distances);

            sptSet.add(u);

            for (int v = 0; v < numberOfNodes; v++) {
                if (!sptSet.contains(v) && matrix[u][v] != 0 && distances[u] != Double.MAX_VALUE && distances[u] + matrix[u][v] < distances[v]) {
                    distances[v] = distances[u] + matrix[u][v];
                }
            }
        }

        List<DestinationDistance> paths = new ArrayList<>();
        sptSet.forEach(destination -> {
            if (destination != source - 1) {
                paths.add(new DestinationDistance(destination, distances[destination]));
            }
        });
        return new DistancesFromSourceToDestinations(source, paths);
    }

    static Solution solveInput(Input input) {
        List<DistancesFromSourceToDestinations> paths = new ArrayList<>();
        input.sources.forEach(source -> paths.add(findShortestPath(input.matrix, source, input.numberOfNodes)));
        return new Solution(paths);
    }

    static List<Solution> solveInputs(List<Input> inputs) {
        List<Solution> solutions = new ArrayList<>();
        inputs.forEach(input -> solutions.add(solveInput(input)));
        return solutions;
    }

    static class DestinationDistance {
        int destination;
        double distance;

        public DestinationDistance(int destination, double distance) {
            this.destination = destination;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return String.format("%s(%.2f)", destination + 1, distance);
        }
    }

    static class DistancesFromSourceToDestinations {
        int source;
        List<DestinationDistance> destinationDistances;

        public DistancesFromSourceToDestinations(int source, List<DestinationDistance> destinationDistances) {
            this.source = source;
            this.destinationDistances = destinationDistances;
        }

        @Override
        public String toString() {
            return String.format("%s: %s", source, destinationDistances.toString().replace("[", "").replace("]", "").replace(",", ""));
        }
    }

    static class Solution {
        List<DistancesFromSourceToDestinations> paths;

        @Override
        public String toString() {
            return paths.toString().replace("[", "").replace("]", "").replace(", ", "\n");
        }

        public Solution(List<DistancesFromSourceToDestinations> paths) {
            this.paths = paths;
        }
    }

    public static List<Input> getInputs() throws FileNotFoundException {
        List<Input> inputs = new ArrayList<>();

        File inputFile = new File("ospf.in");
        Scanner scanner = new Scanner(inputFile);

        while (scanner.hasNextLine()) {
            int numberOfNodes = scanner.nextInt();
            if (numberOfNodes == 0) {
                break;
            }

            double[][] matrix = new double[numberOfNodes][numberOfNodes];
            for (int i = 0; i < numberOfNodes; i++) {
                for (int j = 0; j < numberOfNodes; j++) {
                    long k = scanner.nextLong();
                    double d;
                    if (k == 0) {
                        d = 0;
                    } else {
                        d = 10e7 / k;
                    }
                    matrix[i][j] = d;
                }
            }

            List<Integer> sources = new ArrayList<>();
            int nextInt = scanner.nextInt();
            while (nextInt != 0) {
                sources.add(nextInt);
                nextInt = scanner.nextInt();
            }

            inputs.add(new Input(numberOfNodes, matrix, sources));
        }

        return inputs;
    }

    static class Input {
        int numberOfNodes;
        double[][] matrix;
        List<Integer> sources;

        public Input(int numberOfNodes, double[][] matrix, List<Integer> sources) {
            this.numberOfNodes = numberOfNodes;
            this.matrix = matrix;
            this.sources = sources;
        }

        @Override
        public String toString() {
            return "Input{" +
                    "numberOfNodes=" + numberOfNodes +
                    ", matrix=" + Arrays.deepToString(matrix) +
                    ", sources=" + sources +
                    '}';
        }
    }
}
