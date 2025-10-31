import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class QuizApp {

    static class Example {
        final String chapter;
        final String fileName;
        Example(String chapter, String fileName) { this.chapter = chapter; this.fileName = fileName; }
    }

    static class Question {
        final String prompt;
        final List<String> options;
        final int correctIndex;
        Question(String prompt, List<String> options, int correctIndex) {
            this.prompt = prompt; this.options = options; this.correctIndex = correctIndex;
        }
    }

    public static void main(String[] args) throws IOException {
        Path root = args.length > 0 ? Paths.get(args[0]) : Paths.get("").toAbsolutePath();
        List<Example> examples = scanExamples(root);

        if (examples.isEmpty()) {
            System.out.println("No code examples found under: " + root);
            System.out.println("Falling back to chapter-name quiz.");
            List<String> chapters = scanChapters(root);
            runChapterNameQuiz(chapters);
            return;
        }

        runExampleToChapterQuiz(examples);
    }

    static List<Example> scanExamples(Path root) throws IOException {
        List<Example> list = new ArrayList<>();
        try (DirectoryStream<Path> chapters = Files.newDirectoryStream(root, Files::isDirectory)) {
            for (Path chapterDir : chapters) {
                Path codeDir = chapterDir.resolve("CodeExamples");
                if (!Files.isDirectory(codeDir)) continue;

                String chapterName = friendlyChapterName(chapterDir.getFileName().toString());
                try (DirectoryStream<Path> files = Files.newDirectoryStream(codeDir, "*.java")) {
                    for (Path f : files) {
                        list.add(new Example(chapterName, f.getFileName().toString()));
                    }
                } catch (IOException ignored) {
                }
            }
        }
        return list;
    }

    static String friendlyChapterName(String folder) {
        // "1-Building Blocks" -> "Building Blocks"
        return folder.replaceFirst("^[0-9]+[- ]*", "");
    }

    static List<String> scanChapters(Path root) throws IOException {
        List<String> chapters = new ArrayList<>();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(root, Files::isDirectory)) {
            for (Path p : ds) {
                String name = p.getFileName().toString();
                if (name.startsWith(".")) continue;
                chapters.add(friendlyChapterName(name));
            }
        }
        return chapters.stream().distinct().sorted().collect(Collectors.toList());
    }

    static void runExampleToChapterQuiz(List<Example> examples) {
        List<String> chapters = examples.stream().map(e -> e.chapter).distinct().sorted().collect(Collectors.toList());
        if (chapters.size() < 2) {
            System.out.println("Not enough distinct chapters to create multiple-choice questions.");
            return;
        }

        Collections.shuffle(examples, new Random());
        int total = Math.min(10, examples.size());
        List<Question> questions = new ArrayList<>();
        Random rnd = new Random();

        for (int i = 0; i < total; i++) {
            Example ex = examples.get(i);
            List<String> options = new ArrayList<>();
            options.add(ex.chapter);

            List<String> distractors = new ArrayList<>(chapters);
            distractors.remove(ex.chapter);
            Collections.shuffle(distractors, rnd);
            int needed = Math.min(3, distractors.size());
            options.addAll(distractors.subList(0, needed));

            while (options.size() < 4 && options.size() < chapters.size()) {
                String pick = chapters.get(rnd.nextInt(chapters.size()));
                if (!options.contains(pick)) options.add(pick);
            }

            Collections.shuffle(options, rnd);
            int correctIndex = options.indexOf(ex.chapter);
            String prompt = String.format("Which chapter does the example '%s' belong to?", ex.fileName);
            questions.add(new Question(prompt, options, correctIndex));
        }

        runQuiz(questions);
    }

    static void runChapterNameQuiz(List<String> chapters) {
        if (chapters.size() < 2) {
            System.out.println("Not enough chapters to build a quiz.");
            return;
        }
        Collections.shuffle(chapters, new Random());
        int total = Math.min(10, chapters.size());
        List<Question> questions = new ArrayList<>();
        Random rnd = new Random();

        for (int i = 0; i < total; i++) {
            String answer = chapters.get(i);
            List<String> options = new ArrayList<>();
            options.add(answer);

            List<String> distractors = new ArrayList<>(chapters);
            Collections.shuffle(distractors, rnd);
            for (String d : distractors) {
                if (!d.equals(answer)) options.add(d);
                if (options.size() == 4) break;
            }

            Collections.shuffle(options, rnd);
            int correctIndex = options.indexOf(answer);
            String prompt = "Select the valid chapter name:";
            questions.add(new Question(prompt, options, correctIndex));
        }

        runQuiz(questions);
    }

    static void runQuiz(List<Question> questions) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Java SE 8 Study Guide Quiz");
        System.out.println("--------------------------");
        int score = 0;
        int qnum = 1;

        for (Question q : questions) {
            System.out.println();
            System.out.println("Q" + qnum + ". " + q.prompt);
            for (int i = 0; i < q.options.size(); i++) {
                System.out.printf("  %c) %s%n", (char)('A' + i), q.options.get(i));
            }
            int choice = readChoice(sc, q.options.size());
            if (choice == q.correctIndex) {
                System.out.println("\u2713 Correct");
                score++;
            } else {
                System.out.println("\u2717 Incorrect");
                System.out.println("   Answer: " + q.options.get(q.correctIndex));
            }
            qnum++;
        }

        System.out.println();
        System.out.printf("Score: %d/%d%n", score, questions.size());
    }

    static int readChoice(Scanner sc, int optionCount) {
        while (true) {
            System.out.print("Your choice (A-" + (char)('A' + optionCount - 1) + " or 1-" + optionCount + "): ");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            char ch = Character.toUpperCase(line.charAt(0));
            int idx = ch - 'A';
            if (idx >= 0 && idx < optionCount) return idx;

            try {
                int num = Integer.parseInt(line);
                if (num >= 1 && num <= optionCount) return num - 1;
            } catch (NumberFormatException ignored) {}

            System.out.println("Please enter a letter A-" + (char)('A' + optionCount - 1) + " or a number 1-" + optionCount + ".");
        }
    }
}
