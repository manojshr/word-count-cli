package io.codlibs;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNullElse;

@Command(
        version = "1.0",
        name = "jwc",
        description = "Gives you the word count",
        mixinStandardHelpOptions = true,
        helpCommand = true
)
public class WordCountCommand implements Callable<String> {

    @Parameters(index = "0", description = "Provide Absolute file path", defaultValue = "test.txt")
    private File file;

    @Option(names = {"-c", "--bytes"}, description = "Print the byte counts")
    private boolean printTotalBytesInFile;

    @Option(names = {"-l", "--lines"}, description = "Print the newline counts")
    private boolean printNumberOfLinesInFile;

    @Option(names = {"-w", "--words"}, description = "Print the word counts")
    private boolean printNumberOfWordsInFile;

    @Option(names = {"-m", "--chars"}, description = "Print the character counts")
    private boolean printNumberOfCharactersInFile;

    public static void main(String[] args) {
        CommandLine wordCountCli = new CommandLine(new WordCountCommand());
        int exitCode = wordCountCli.execute(args);
        String executionResult = requireNonNullElse(wordCountCli.getExecutionResult(), "");
        System.out.println(executionResult);
        System.exit(exitCode);
    }

    @Override
    public String call() {
        if (file == null || file.isDirectory() || !file.isFile()) {
            String fileNameOrEmpty = file != null ? file.getAbsolutePath() + " " : "";
            return fileNameOrEmpty + "open: No such file";
        }
        String fileContent;
        String filePath = file.getPath();
        try {
            fileContent = Files.readString(file.toPath());
        } catch (Exception ex) {
            return String.format("%s: read: Error reading in file", file.getName());
        }

        List<String> result = new ArrayList<>();
        if (printTotalBytesInFile) {
            result.add(printTotalBytesInFile(fileContent));
        }
        if (printNumberOfLinesInFile) {
            result.add(printTotalLinesInFile(fileContent));
        }
        if (printNumberOfWordsInFile) {
            result.add(printNumberOfWordsInFile(fileContent));
        }
        if (printNumberOfCharactersInFile) {
            result.add(printNumberOfCharactersInFile(fileContent));
        }
        result.add("Filename: " + filePath);
        return String.join(System.lineSeparator(), result);
    }

    private String printTotalBytesInFile(String fileLines) {
        return "Total Bytes in File: " + fileLines.getBytes(UTF_8).length;
    }

    private String printTotalLinesInFile(String fileLines) {
        return "Number of Lines in File: " + fileLines.split(System.lineSeparator()).length;
    }

    private String printNumberOfWordsInFile(String fileLines) {
        return "Number of Words in File: " + fileLines.split("\\s+").length;
    }

    private String printNumberOfCharactersInFile(String fileLines) {
        return "Number of Characters in File: " + fileLines.chars().count();
    }
}
