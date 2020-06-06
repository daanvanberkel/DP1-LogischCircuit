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

    public HashMap<String, String> readLines(String[] lines, boolean isNodeLine){
        HashMap<String, String> output = new HashMap<>();
        boolean parsing = false;

        for (String line : lines) {
            if (line.length() < 1) {
                if(isNodeLine){
                    parsing = true;
                    continue;
                }
                else{
                    break;
                }
            }
            else if (!parsing && isNodeLine) {
                continue;
            }

            if (line.charAt(0) == '#') {
                continue;
            }

            HashMap<String, String> nodeDetails = getCharacters(line.toCharArray());

            output.put(nodeDetails.get("nodeName"), nodeDetails.get("nodeInfo"));
        }

        return output;
    }

    private HashMap<String, String> getCharacters(char[] characters){
        String nodeName = "";
        String nodeInfo = "";
        boolean parsingNodeName = true;

        for (char c : characters) {
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
                nodeInfo += c;
            }
        }

        nodeName = nodeName.trim();
        nodeInfo = nodeInfo.trim();

        HashMap<String, String> nodeDetails = new HashMap<>();

        nodeDetails.put("nodeName", nodeName);
        nodeDetails.put("nodeInfo", nodeInfo);

        return nodeDetails;
    }

    public HashMap<String, String> getNodes(String path) {
        String[] lines = readFile(path);

        HashMap<String, String> output = readLines(lines, false);

        return output;
    }

    public HashMap<String, String> getNodeConnections(String path) {
        String[] lines = readFile(path);

        HashMap<String, String> output = readLines(lines, true);

        return output;
    }
}
