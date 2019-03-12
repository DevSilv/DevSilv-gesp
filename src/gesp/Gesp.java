package gesp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author silvuss
 */
public class Gesp {

    private static File getInputFile(String[] args) {
        if (args.length < 1) {
            throw new Error("There are not enough arguments provided");
        } else if (args.length > 1) {
            throw new Error("There are too many arguments provided");
        }

        String inputFilePath = args[0];
        File inputFile = new File(inputFilePath);

        if (inputFile.exists() == false) {
            throw new Error("The provided input file path does not exists: \"" + inputFilePath + "\"");
        } else if (inputFile.isFile() == false) {
            throw new Error("The provided input file path is not a \"normal\" file (according to Java documentation): '"
                    + inputFilePath);
        }

        return inputFile;
    }

    private static File getSemanticMarkersDictionaryFile(String path) {
        File semanticMarkersDictionaryFile = new File(path);
        if (semanticMarkersDictionaryFile.exists() == false) {
            throw new Error("The semantic speech markers dictionary does not exist under the expected path: \""
                    + semanticMarkersDictionaryFile + "\"");
        }
        return semanticMarkersDictionaryFile;
    }

    private static String getText(File inputFile) throws IOException {
        Path path = inputFile.toPath();
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes);
    }

    private static List<SemanticMarker> getSemanticMarkers(File semanticMarkersDictionaryFile) throws IOException {
        List<String> semanticMarkersGroups = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(semanticMarkersDictionaryFile))) {
            while (br.ready()) {
                semanticMarkersGroups.add(br.readLine());
            }
        }
        List<SemanticMarker> semanticMarkers = new ArrayList<>();
        for (String x : semanticMarkersGroups) {
            // See the following Stack Overflow answer for a caveat in the syntax
            // of the "split" method: https://stackoverflow.com/a/14602089
            semanticMarkers.addAll(Arrays.asList(x.split(",", -1)).stream().map(y -> new SemanticMarker(y))
                    .collect(Collectors.toList()));
        }
        return semanticMarkers;
    }

    private static String processText(String text, List<SemanticMarker> semanticMarkers) {
        String semanticMarkersAlternative = semanticMarkers.stream().map(x -> "(" + x.getMarker() + ")")
                .collect(Collectors.joining("|"));
        Pattern semanticMarkerPattern = Pattern.compile("(?is).*" + semanticMarkersAlternative + ".*");

        List<String> outputStrings = new ArrayList<>();

        // Match sentences with quotation marks containing
        // at least one of semanticMarkersRegexes.
        int i = 0;
        String sentencesAroundQuotesRegex = "(?is)(?:(?<=^)|(?<=(?:\\.|\\?|!)\\s)|(?<=(?:\\.|\\?|!)\"\\n)|(?<=(?:\\.|\\?|!)\"\\n\\n))(?:[^\".!?]+(?:\\.|\\?|!){1,3})?[^\".!?]*\"[^\"]+(?:(?<=\\n\\n)\"?[^\"]+)*(?:(?:(?<=\\.|\\?|!)\"(?:[^\".!?]*(?:\\.|\\?|!){1,3})?)|(?:\"[^\".!?]*(?:\\.|\\?|!){1,3}))(?:[^\".!?]+(?:\\.|\\?|!){1,3})?(?:(?=\\s)|(?=$)|(?=\"))";
        Pattern sentencesAroundQuotesPattern = Pattern.compile(sentencesAroundQuotesRegex);
        Matcher sentencesAroundQuotesMatcher = sentencesAroundQuotesPattern.matcher(text);
        while (sentencesAroundQuotesMatcher.find()) {
            String match = sentencesAroundQuotesMatcher.group(0);
            System.out.println(String.format("Found %d match(es)", ++i));
            Matcher semanticMarkersMatcher = semanticMarkerPattern.matcher(match);
            if (semanticMarkersMatcher.find()) {
                outputStrings.add(match);
            }
        }

        // The following outputString contains
        // two hyphens as the results separator,
        // because GNU grep uses this separator.
        String outputString = String.join("\n--\n", outputStrings);
        return outputString;
    }

    private static void createOutputFile(String outputFilePath, String outputString) throws IOException {
        // The "write" method is using UTF-8 by default.
        File outputFile = new File(outputFilePath);
        if (outputFile.exists()) {
            throw new Error(
                    "File or directory with the provided output file name already exists: \"" + outputFile + "\"");
        }
        // The "CREATE_NEW" option used below causes the write method
        // to fail if the specified file exists.
        Files.write(outputFile.toPath(), outputString.getBytes(), StandardOpenOption.CREATE_NEW);
    }

    public static void main(String[] args) {
        try {
            // General application parameteres
            
            String outputFilePath = "src/gesp/tests/output.txt";
            String semanticMarkersDictionaryFilePath = "src/gesp/semantic-speech-markers-dictionary";

            // Input processing.

            System.out.println("(1/3) Processing input... ");
            File inputFile = getInputFile(args);
            File semanticMarkersDictionaryFile = getSemanticMarkersDictionaryFile(semanticMarkersDictionaryFilePath);

            String text = getText(inputFile);
            List<SemanticMarker> semanticMarkers = getSemanticMarkers(semanticMarkersDictionaryFile);
            System.out.println("(1/3) Done processing input.");

            // Main processing.

            System.out.println("(2/3) Processing text... ");
            String resultText = processText(text, semanticMarkers);
            System.out.println("(2/3) Done processing text.");

            // Output processing.

            System.out.println("(3/3) Creating output file... ");
            createOutputFile(outputFilePath, resultText);
            System.out.println("(3/3) Done. Exiting.");

        } catch (Throwable t) {
            t.printStackTrace();
            System.err.println(t.getMessage());
            System.err.println("Exiting.");
        }
    }

}
