package gesp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

			if (Files.exists(Paths.get(inputFilePath)) == false) {
				System.out.println("The provided input file does not exists: '" + inputFilePath + "'\nExiting");
				return;
			}

			// the readAllLines method is using UTF-8 by default
			String inputString = new String(Files.readAllBytes(Paths.get(inputFilePath)));

			// main processing

			List<SemanticMarker> semanticMarkersRegexesParts = new ArrayList<>(
					Arrays.asList(new SemanticMarker("powiedział(?:a|o)?"), new SemanticMarker("rzekł(?:a|o)?"),
							new SemanticMarker("mówił(?:a|o)?")));
			String semanticMarkersRegexesPartsAlternative = semanticMarkersRegexesParts.stream()
					.map(x -> "(" + x.getMarker() + ")").collect(Collectors.joining("|"));
			String semanticMarkersRegex = "(?is).*" + semanticMarkersRegexesPartsAlternative + ".*";
			Pattern semanticMarkersPattern = Pattern.compile(semanticMarkersRegex);

			List<String> outputStrings = new ArrayList<>();

			// match sentences with quotation marks containing at least one of
			// semanticMarkersRegexes
			// not escaped regex:
			// (?is)(?:(?<=^)|(?<=(?:\.|\?|!)\s)|(?<=(?:\.|\?|!)"\n)|(?<=(?:\.|\?|!)"\n\n))(?:[^".!?]+(?:\.|\?|!))?[^".!?]*"[^"]+(?:(?<=\n\n)"?[^"]+)*(?:(?:"[^".!?]*(?:\.|\?|!))|(?:(?:\.|\?|!)"))(?:[^".!?]+(?:\.|\?|!))?(?:(?=\s)|(?=$)|(?="))
			String sentencesAroundQuotesRegex = "(?is)(?:(?<=^)|(?<=(?:\\.|\\?|!)\\s)|(?<=(?:\\.|\\?|!)\"\\n)|(?<=(?:\\.|\\?|!)\"\\n\\n))(?:[^\".!?]+(?:\\.|\\?|!))?[^\".!?]*\"[^\"]+(?:(?<=\\n\\n)\"?[^\"]+)*(?:(?:\"[^\".!?]*(?:\\.|\\?|!))|(?:(?:\\.|\\?|!)\"))(?:[^\".!?]+(?:\\.|\\?|!))?(?:(?=\\s)|(?=$)|(?=\"))";
			Pattern sentencesAroundQuotesPattern = Pattern.compile(sentencesAroundQuotesRegex);
			Matcher sentencesAroundQuotesMatcher = sentencesAroundQuotesPattern.matcher(inputString);
			while (sentencesAroundQuotesMatcher.find()) {
				String match = sentencesAroundQuotesMatcher.group(0);
				Matcher semanticMarkersMatcher = semanticMarkersPattern.matcher(match);
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
