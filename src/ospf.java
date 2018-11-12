// ospf
// Jerred Shepherd

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
        solutions.forEach(solution -> printSolution(solution, printWriter));
    }

    static Solution solveInput(Input input) {
        return null;
    }

    static List<Solution> solveInputs(List<Input> inputs) {
        List<Solution> solutions = new ArrayList<>();
        inputs.forEach(input -> solutions.add(solveInput(input)));
        return solutions;
    }

    static class PathDistancePair {
        int destination;
        int distance;

        public PathDistancePair(int destination, int distance) {
            this.destination = destination;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "PathDistancePair{" +
                    "destination=" + destination +
                    ", distance=" + distance +
                    '}';
        }
    }

    static class ShortestPathsFromSource {
        int source;
        List<PathDistancePair> pathDistancePairs;

        public ShortestPathsFromSource(int source, List<PathDistancePair> pathDistancePairs) {
            this.source = source;
            this.pathDistancePairs = pathDistancePairs;
        }

        @Override
        public String toString() {
            return "ShortestPaths{" +
                    "source=" + source +
                    ", pathDistancePairs=" + pathDistancePairs +
                    '}';
        }
    }

    static class Solution {
        List<ShortestPathsFromSource> paths;

        @Override
        public String toString() {
            return "Solution{" +
                    "paths=" + paths +
                    '}';
        }

        public Solution(List<ShortestPathsFromSource> paths) {
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

            Long[][] matrix = new Long[numberOfNodes][numberOfNodes];
            for (int i = 0; i < numberOfNodes; i++) {
                for (int j = 0; j < numberOfNodes; j++) {
                    long k = scanner.nextLong();
                    matrix[i][j] = k;
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
        Long[][] matrix;
        List<Integer> sources;

        public Input(int numberOfNodes, Long[][] matrix, List<Integer> sources) {
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
