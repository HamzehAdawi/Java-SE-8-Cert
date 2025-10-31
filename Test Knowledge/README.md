# Test Knowledge - CLI Quiz

This is a tiny Java command-line quiz that scans the chapter folders and asks multiple-choice questions about which chapter a code example belongs to.

What it does
- Looks for `CodeExamples/*.java` inside each chapter folder (e.g., `1-Building Blocks/CodeExamples/HelloWorld.java`).
- Asks up to 10 randomized questions.
- Accepts A–D or 1–4 as answers and shows your score.
- If no examples are found, it falls back to a simple "chapter name" quiz.

## How to run (Windows PowerShell)

From the repository root:

```powershell
# Compile
javac ".\Test Knowledge\QuizApp.java"

# Run (scans the repository root automatically)
java -cp ".\Test Knowledge" QuizApp

# Or run pointing to a specific path (quotes handle spaces)
java -cp ".\Test Knowledge" QuizApp "f:\Java SE Programmer 8 Study Guide"
```

Notes
- The quiz removes numeric prefixes from chapter folder names for nicer display (e.g., `"1-Building Blocks"` -> `"Building Blocks"`).
- You can change the number of questions by editing `total = Math.min(10, examples.size())` in `QuizApp.java`.
- If you move the file elsewhere, adjust the `-cp` (classpath) accordingly.
