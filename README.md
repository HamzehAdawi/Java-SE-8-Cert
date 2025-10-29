# Java SE 8 Programmer Study Guide ☕

Concise, practical notes and runnable examples to prepare for the Oracle Java SE 8 Programmer certification. This guide distills concepts into bite‑sized Markdown overviews with focused Java snippets you can build and run.

### What is this? 

- A study guide built from personal notes for the Java SE 8 Programmer exam (OCA 1Z0-808 style topics)
- Organized by topic with a short explanation and hands-on code examples
- Kept simple, direct, and easy to skim when revising

 

- Learners preparing for the Java SE 8 certification
- Developers refreshing Java fundamentals with small, runnable demos

### Getting started: 

1. Open a topic folder and read the corresponding Markdown (overview and key rules).
2. Explore `CodeExamples/` in that topic and run the demos locally.
	- Compile: `javac MyDemo.java`
	- Run: `java MyDemo`
3. Come back to the notes before/after practice questions to reinforce edge cases.


Each topic directory pairs a quick-reference Markdown file with a `CodeExamples/` folder to reinforce concepts with minimal, focused programs.

## Contributing 🤝

Contributions are very welcome—whether it’s fixing a typo, adding a tiny demo, or clarifying a tricky edge case.

Ways to contribute:

- Improve explanations in the Markdown notes
- Add or simplify a demo in `CodeExamples/`
- Report ambiguities, edge cases, or exam‑relevant gotchas

Guidelines (keep it simple):

- Stick to Java 8 features and syntax for examples
- Keep demos short, runnable, and focused on a single idea
- Use clear filenames like `FeatureConceptDemo.java`
- Prefer comments that highlight rules and edge cases over verbose prose

Submitting changes:

1. Fork the repo and create a branch: `feature/short-description`
2. Make your changes with small, focused commits
3. Open a Pull Request describing what changed and why (link to topic if relevant)
4. If you’re adding code, note how to run it and the expected output

Thanks for helping make these notes clearer and more helpful for everyone preparing for the exam!


## Repository structure 🗂️

```text
.
├─ README.md
├─ 1-Building Blocks/
	├─ Building Blocks.md
	└─ CodeExamples/
		├─ HelloWorld.java
		├─ InstanceInitializerDemo.java
		├─ PrimitivesVsReferencesDemo.java
		├─ PublicClassAndFileDemo.java
		└─ StaticAndThisDemo.java
├─ 2-Operatprs & Statements/
	├─ Operators & Statements.md
	└─ CodeExamples/
		├─ CompoundAssignmentDemo.java
		├─ EqualityDemo.java
		├─ IncrementDecrementDemo.java
		├─ LogicalOperatorsDemo.java
		├─ LoopsAndLabelsDemo.java
		├─ NumericPromotionDemo.java
		├─ OperatorPrecedenceDemo.java
		├─ SwitchDemo.java
		└─ TernaryDemo.java
├─ 3-Core Java/
	├─ Core Java.md
	└─ CodeExamples/
		├─ ArrayListAndAutoboxingDemo.java
		├─ ArraysAndSearchDemo.java
		├─ DateTimeAndFormattingDemo.java
		├─ StringsAndStringBuilderDemo.java
		└─ WrapperParsingAndNumberDemo.java
├─ 4-Encapsulation & Methods/
	├─ Encapsulation & Methods.md
	└─ CodeExamples/
		├─ ConstructorsChainingAndVisibilityDemo.java
		├─ EncapsulationImmutableDefensiveCopyDemo.java
		├─ LambdaAndPredicateDemo.java
		├─ OverloadingAndVarargsResolutionDemo.java
		└─ StaticAndInitializationOrderDemo.java
├─ 5-Classes/
	├─ Class Design.md
	└─ CodeExamples/
		├─ AbstractAndInterfaceDemo.java
		├─ ConstructorsAndSuperDemo.java
		├─ FieldHidingAndFinalDemo.java
		├─ OverridingVsHidingDemo.java
		└─ PolymorphismAndCastingDemo.java
├─ 6-Exceptions/
	├─ Exceptions.md
	└─ CodeExamples/
		├─ CatchAndFinallyFlowDemo.java
		├─ CheckedAndThrowsDemo.java
		├─ FinallyOverrideExceptionDemo.java
		├─ MultiCatchDemo.java
		├─ RuntimeExceptionsDemo.java
		└─ TryWithResourcesSuppressedDemo.java
└─ misc/
	└─ Gotcha Rules.md
```