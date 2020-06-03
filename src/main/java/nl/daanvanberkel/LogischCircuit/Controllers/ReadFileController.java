package nl.daanvanberkel.LogischCircuit.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class ReadFileController {

    private String[] readFile(String path) {
        ArrayList<String> lines = new ArrayList<>();

        try {
            Stream<String> stream = Files.lines(Paths.get(path));

            stream.forEach(lines::add);
        } catch (IOException e) {
            e.printStackTrace(); // TODO: Handle exception
        }

        return lines.toArray(new String[0]);
    }

    public HashMap<String, String> getNodes(String path) {
        String[] lines = readFile(path);
        HashMap<String, String> output = new HashMap<>();

        for (String line : lines) {
            // readline(line) ?
            if (line.length() < 1) {
                break;
            }

            if (line.charAt(0) == '#') {
                continue;
            }

            String nodeName = "";
            String nodeType = "";
            boolean parsingNodeName = true;

            for (char c : line.toCharArray()) {
                // readCharacter() ?
                if (c == ':') {
                    // Found the end of the name
                    parsingNodeName = false;
                    continue;
                }

                if (c == ';') {
                    // Found end of the statement
                    break;
                }

                if (c == ' ') {
                    // Skip is character is an empty space
                    continue;
                }

                if (parsingNodeName) {
                    nodeName += c;
                } else {
                    nodeType += c;
                }
            }

            nodeName = nodeName.trim();
            nodeType = nodeType.trim();

            output.put(nodeName, nodeType);
        }

        return output;
    }

    public HashMap<String, String> getNodeConnections(String path) {
        String[] lines = readFile(path);
        HashMap<String, String> output = new HashMap<>();

        for (String line : lines) {
            if (line.length() < 1) {
                continue;
            }

            if (line.charAt(0) == '#') {
                continue;
            }

            String nodeName = "";
            String connectingNodeNames = "";
            boolean parsingNodeName = true;

            for(char c: line.toCharArray()) {
                if (c == ':') {
                    // Found the end of the name
                    parsingNodeName = false;
                    continue;
                }

                if (c == ';') {
                    // Found end of the statement
                    break;
                }

                if (c == ' ') {
                    // Skip is character is an empty space
                    continue;
                }

                if (parsingNodeName) {
                    nodeName += c;
                } else {
                    connectingNodeNames += c;
                }
            }

            nodeName = nodeName.trim();
            connectingNodeNames = connectingNodeNames.trim();

            output.put(nodeName, connectingNodeNames);
        }

        return output;
    }
}
