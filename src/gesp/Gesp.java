package gesp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author silvuss
 */
public class Gesp {

    public static void main(String[] args) {
        try {
            // input processing
            if (args.length < 2) {
                System.out.println("There are not enough arguments provided\nExiting");
                return;
            } else if (args.length > 2) {
                System.out.println("There are too many arguments provided\nExiting");
            }

            String inputFilePath = args[0];
            String outputFilePath = args[1];

            if (new File(inputFilePath).exists() == false) {
                System.out.println("The provided input file path does not exists: '" + inputFilePath + "'\nExiting");
                return;
            }

            // the readAllLines method is using UTF-8 by default
            String inputFileContent = new String(
                    Files.readAllBytes(Paths.get(inputFilePath))
            );

            String semanticMarkersDictionaryPath = "./src/gesp/semantic-speech-markers-dictionary";
            if (new File(semanticMarkersDictionaryPath).exists() == false) {
                System.out.println("The semantic speech markers dictionary does not exist under the expected path: '" + semanticMarkersDictionaryPath + "'\nExiting");
                return;
            }
            List<String> semanticMarkersGroups = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(semanticMarkersDictionaryPath))) {
                while (br.ready()) {
                    semanticMarkersGroups.add(br.readLine());
                }
            }
            List<SemanticMarker> semanticMarkers = new ArrayList();
            for (String x : semanticMarkersGroups) {
                // split method syntax caveat: https://stackoverflow.com/a/14602089
                semanticMarkers.addAll(
                        Arrays.asList(x.split(",", -1))
                                .stream()
                                .map(y -> new SemanticMarker(y))
                                .collect(Collectors.toList())
                );
            }

            // main processing
            String semanticMarkersAlternative = semanticMarkers
                    .stream()
                    .map(x -> "(" + x.getMarker() + ")")
                    .collect(Collectors.joining("|"));
            Pattern semanticMarkerPattern = Pattern.compile(
                    "(?is).*" + semanticMarkersAlternative + ".*"
            );

            List<String> outputStrings = new ArrayList<>();

            // match sentences with quotation marks containing at least one of
            // semanticMarkersRegexes
            // not escaped regex:
            // (?is)(?:(?<=^)|(?<=(?:\.|\?|!)\s)|(?<=(?:\.|\?|!)"\n)|(?<=(?:\.|\?|!)"\n\n))(?:[^".!?]+(?:\.|\?|!){1,3})?[^".!?]*"[^"]+(?:(?<=\n\n)"?[^"]+)*(?:(?:(?<=\.|\?|!)"(?:[^".!?]*(?:\.|\?|!){1,3})?)|(?:"[^".!?]*(?:\.|\?|!){1,3}))(?:[^".!?]+(?:\.|\?|!){1,3})?(?:(?=\s)|(?=$)|(?="))
            String sentencesAroundQuotesRegex = "(?is)(?:(?<=^)|(?<=(?:\\.|\\?|!)\\s)|(?<=(?:\\.|\\?|!)\"\\n)|(?<=(?:\\.|\\?|!)\"\\n\\n))(?:[^\".!?]+(?:\\.|\\?|!){1,3})?[^\".!?]*\"[^\"]+(?:(?<=\\n\\n)\"?[^\"]+)*(?:(?:(?<=\\.|\\?|!)\"(?:[^\".!?]*(?:\\.|\\?|!){1,3})?)|(?:\"[^\".!?]*(?:\\.|\\?|!){1,3}))(?:[^\".!?]+(?:\\.|\\?|!){1,3})?(?:(?=\\s)|(?=$)|(?=\"))";
            Pattern sentencesAroundQuotesPattern = Pattern.compile(sentencesAroundQuotesRegex);
            Matcher sentencesAroundQuotesMatcher = sentencesAroundQuotesPattern.matcher(inputFileContent);
            while (sentencesAroundQuotesMatcher.find()) {
                String match = sentencesAroundQuotesMatcher.group(0);
                System.out.println(match);
                Matcher semanticMarkersMatcher = semanticMarkerPattern.matcher(match);
                if (semanticMarkersMatcher.find()) {
                    outputStrings.add(match);
                }
            }

            // below are two hyphens because GNU grep also uses two hyphen
            String outputString = String.join("\n--\n", outputStrings);

            // output processing
            // the write method is using UTF-8 by default
            // the CREATE_NEW option causes the write method to fail if the specified file
            // exists
            if (Files.exists(Paths.get(outputFilePath))) {
                System.out.println(
                        "File with the provided output file name already exists: '" + outputFilePath + "'\nExiting");
                return;
            }

            Files.write(Paths.get(outputFilePath), outputString.getBytes(), StandardOpenOption.CREATE_NEW);

            System.out.println("Done\nExiting");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
