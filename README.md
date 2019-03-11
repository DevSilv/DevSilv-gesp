# gesp – Get speeches from text

gesp (GEt SPeeches) is a utility that allows the user get the speeches (from monologues and dialogs) that a given text contains. By a speech, one should understand anything that is spoken: a monologue (or part of it), part of a dialogue etc. (see also the third meaning of the [speech definition by dictionary.com](https://www.dictionary.com/browse/speech)).

**Read before use:** This application **is not** intended to be used according to the purpose described above. You may use it **only** to test whether the code is written the way it is expected (i.e. it produces expected results) and **only** when you know what the code will really do. For details, see the section "[Disclaimers](#disclaimers)" of this README.

## Table of contents

1. [Copyright note](#copyright-note)
2. [Disclaimers](#disclaimers)
3. [How does this utility work (in general)?](#how-does-this-utility-work-in-general)
4. [How to run this utility?](#how-to-run-this-utility)
5. [Process details](#process-details)
6. [Supported syntactic speech markers](#supported-syntactic-speech-markers)
7. [Supported semantic speech markers](#supported-semantic-speech-markers)
8. [Current major problems](#current-major-problems)
9. [Environment, tools and technologies used](#environment-tools-and-technologies-used)
10. [Sources](#sources)

## Copyright note

Note that this "gesp" project (this repository) has currently **no license**, as explained in [this GitHub guide on licensing projects](https://choosealicense.com/no-permission/).

For your convenience, I am including below a quote from that site:

> When you make a creative work (which includes code), the work is under exclusive copyright by default. Unless you include a license that specifies otherwise, nobody else can use, copy, distribute, or modify your work without being at risk of take-downs, shake-downs, or litigation. Once the work has other contributors (each a copyright holder), “nobody” starts including you.

Also note that I can add a lincese in the future if it would be relevant to the needs of this project.

## Disclaimers

**This application is an example application that is not intended to be run.** Its purpose is only to show code that is known to work. While probably nothing dangerous would happen, you may run it only under your own responsibility.

Although I have made efforts to make it work as intended and described, it is not a "proffessional" application. Specifically, it was not tested in terms of separate unit tests or similar. It was tested to work on only one platform, and to build on two platforms. For details on the platforms, see the section ["Environment, tools and technologies used"](#environment-tools-and-technologies-used) of this README.

## How does this utility work (in general)?

Firstly, this utility gets the content of the file specified as its input. Then, in that content it finds a list of speeches and text surrounding each of them. Finally, it creates a new file containing the found list.

## How to run this utility?

This utility is intended to be run in the terminal. Before running, it has to be compiled.

### Compiling

There are one formal prerequisite to be able to successfully compile this utility: the operating system must provide the [javac compiler](https://docs.oracle.com/en/java/javase/11/tools/javac.html#GUID-AEEC9F07-CB49-4E96-8BC7-BCC2C7F725C9).

There are many tools to compile a Java program having its source files and using the `javac` compiler. The simplest way is using no additional tool. Instead, go in the terminal into the utility main directory and execute the following command:

```
javac src/gesp/*.java
```

The output of this command should be a set of .class files – one .class file for each of the .java files, with the same name.

### Running

There is only one formal prerequisite to be able to successfully run this utility: the platform that it is intended to run on must provide [Java Virtual Machine](https://en.wikipedia.org/wiki/Java_virtual_machine) (JVM).

In the case of this utility, the simplest way of running it is going in the terminal into the utility's main directory, and executing the following command (`$` is the user prompt):

```
java src/gesp/Gesp FILE
```

where `FILE` is the input file path; it should be the path to a readable file containing text to get the speeches from.

**Tip:** if only name of the file will be specified, it will be assumed that its path is the path to the user's current directory.

If any errors occur during running, it probably means that the platform that this utility is running on does not support some functions of this utility, or some functions are supported in a way that this utility cannot handle. For details on which platform this utility was tested (i.e. builds and/or runs successfully), see the section "[Environment, tools and technologies used](#environment-tools-and-technologies-used)" of this README.

## Process details

This utility works in the following way:

1. After it is run, it gets its arguments. If there is less or more than one argument, it writes a message to [stdout](https://en.wikipedia.org/wiki/Standard_streams#Standard_output_(stdout)) and exits.

2. If there is exactly one arguments, it checks whether it is the path to a readable file. If it is, then it reads the content of this file. If it is not (either the file does not exist or is not readable), it writes a message to stdout and exits.

3. It analyses the content looking for speeches. Currently, in the context of this utility, a speech is defined as a piece of text within a specified pair of characters called _syntactic speech markers_ (this name was invented for the purpose of this utility). Examples of syntactic speech markers are quotation marks (`""` or `«»`; each character of each pair is one _marker_). One marker of a pair is called "opening marker", and the other "closing marker". The utility also gets small pieces of text before and after the speech; the whole result piece of text is called an _extended speech_.

4. For each found extended speech, it checks whether the extended speech contains any word from a specific list of sets of words. Each set in the list is called a _semantic speech marker set_. The set may contain one or many elements. Examples of a semantic speech marker are "says" (one marker, and one word) or "have said" (one marker, but two words). But note that semantic speech markers may come not only from the English language, as the input file content may not only be written in English; for details, see the "[Supported semantic speech markers](#supported-semantic-speech-markers)" section of this README.

5. The utility finds all the extended speeches that contain any semantic speech marker, and writes them to the output file.

## Supported syntactic speech markers

There is supported only one pair of syntactic speech markers.

|No.|Opening marker character|Opening marker Unicode code|Closing marker character|Closing marker Unicode code|Remarks|
|-|-|-|-|-|-|
|1|`"`|U+0022|`"`|U+0022|Note that some texts may contain very similar, but different quotation marks: U+201C (`“`) and U+201D (`”`).|

## Supported semantic speech markers

What words a semantic speech marker may contain, depends highly on its language.

In some languages, a semantic speech marker may be of two types: a _base semantic speech marker_ or a _normal semantic speech marker_. Each base semantic speech marker constitutes a particular set\* of normal semantic speech markers. A base semantic speech marker also belongs to the group that it constitutes. For example, in the Polish language, a base semantic speech marker is "mówić", and it constitutes the set of markers "mówię", "mówisz", and so on, according to the inflection of the Polish language.

**Note:** in this utility, a "[word](https://en.wikipedia.org/wiki/Word)" is not only considered a set of letters; it also has a **meaning**. Therefore, in a hypothetic situation, if markers (normal or base) in different groups would seem to be the same set of words, they would be treated as separate markers.

Currently, there are supported only Polish semantic speech markers. In Polish, semantic speech markers may be of the two aforementioned types.

---

\* I do not know the exact term that would describe in linguistics such a group. The closest term seems to be "[lexeme](https://en.wikipedia.org/wiki/Lexeme)" – understood as a **set** of forms of a word. Therefore, a particular group of normal markers would be created based on a subset of a particular lexeme, created from a verb within its base semantic marker ("within", because remember that each marker may contains more than one word).

---

### Base semantic speech markers

Currently supported Polish base markers are as follows:
|No.|Base marker\*\*|Remarks|
|-|-|-|-|
|1|rzec|Old use. Only in perfective aspect; an imperfective equivalent in the English language would be mostly "to say".|
|2|mówić|Eng. "to say", "to tell", "to speak", "to talk", and similar.|
|3|powiedzieć|Today only in perfective aspect. An imperfective equivalent in the English language would be mostly "to say".|
|4|powiadać|Old use. A [frequentative form](https://en.wikipedia.org/wiki/Frequentative#Polish) (and also the imperfective aspect) of the verb "powiedzieć" (see). Eng. "to say" as in "She always says that...".|
|5|wołać|Eng. "to call", "to cry", and similar.|
|6|gadać|Eng. "to talk", "to chat", and similar.|
|7|twierdzić|Eng. "to claim" and similar.|
|8|stwierdzać|A form of the verb "twierdzić" (see) with a slightly different meaning in certain situations.|
|9|jęczeć|Eng. "groan", "moan", or similar.|
|10|jęknąć|The perfective aspect of the verb "jęczeć" (see).|
|11|śmiać|Eng. "to laugh". In normal language, at the end there has to occur an additional word "się", but it is omitted for the purpose of this utility.|
|12|mawiać|The frequentative form of the verb "mówić" (see).|
|13|wrzeszczeć|Eng. "to scream", "to yell", and similar.|
|14|wrzasnąć|The perfective aspect of the verb "wrzeszczeć" (see).|
|15|krzyczeć|Eng. "to shout", "to scream", and similar (usually, it denotes a slightly calmer and less agressive sound than the verb "wrzeszczeć").|
|16|krzyknąć|The perfective aspect of the verb "krzyczeć" (see).|
|17|słyszeć|Eng. "to hear".|
|18|słuchać|Eng. "to listen".|

---

\*\* I do not know the exact representation of the term "base marker" in linguistics, but the closest seems to be "[lemma](https://en.wikipedia.org/wiki/Lemma_(morphology))".

---

### Normal semantic speech markers

Currently, all the Polish markers are verbs. Therefore, a group of normal markers is created performing [_conjugation_](https://en.wikipedia.org/wiki/Grammatical_conjugation) (a type of [inflection](https://en.wikipedia.org/wiki/Inflection)) on the base marker that constitutes that group. The conjugation used in this utility includes for each base marker (for an example base marker "mówić"):
- all [persons](https://en.wikipedia.org/wiki/Grammatical_person) (e.g. "mówię", "mówisz");
- all [numbers](https://en.wikipedia.org/wiki/Grammatical_number) (e.g. "mówię", "mówimy");
- all [genders](https://en.wikipedia.org/wiki/Grammatical_gender) (e.g. "mówił", "mówiła");
- present and past [tenses](https://en.wikipedia.org/wiki/Grammatical_tense) – where appropriate (e.g. "mówię", "mówiłem");
- [aspects](https://en.wikipedia.org/wiki/Grammatical_aspect) – where appropriate; note that usually, in case of this utility, if a verb has multiple forms that differ only on aspect, then either some of them are not included (because they can be found using another form – e.g. "wykrzyczeć", "krzyczeć"), or they are treated as separate markers;
- [moods](https://en.wikipedia.org/wiki/Grammatical_mood) – where appropriate (e.g. "mówię", "mówiłbym");
- [voices](https://en.wikipedia.org/wiki/Voice_(grammar)) – where appropriate (e.g. "X said to Y", "Y was said").

## Current major problems

### Determining speeches

Unfortunately, not all the possible locations of speeches in a text are handled. The number of speeches that can be found depends on the form that they are written in. The more common (general) the form, the closer the number and bounds of speeches will be to the expected state. It is possible that the number of speeches will be greater than expected, as well as that it will be smaller.

#### I

For example, let us take a situation like this (this is an excerpt from a Polish poem "[Pan Tadeusz](https://en.wikipedia.org/wiki/Pan_Tadeusz)" (Eng. "Sir Thaddeus"):
```
"Psy za nim fajt na lewo, on w las, a mój Kusy
Cap !!" - tak krzycząc pan Rejent, na stół pochylony,
Z palcami swemi zabiegł aż do drugiej strony
I "cap!" - Tadeuszowi wrzasnął tuż nad uchem.
```

The speech is here denoted by the double quotation marks (`""`). Note that there are no capital letters both after the first closing `"` and after the second opening `"`. That means that the whole text denotes only one sentence. Therefore, the correct way of bounding the extended speech would be to treat text within both the first and the second pair of quotation marks as one speech. And if so, the extended speech would contain all the characters within the above text (from the first `"` to the period).

But with the current version of this utility, there will be found **two separate** speeches: the first from the first opening `"` to the first closing `"`, and the second from the text `Z palcami` to the period. This is because this utility uses capital letters to mark a sentence (alongside with the period). In order to make this utility work for this text, there are only two ways that come to my mind:
- The utility should presume that there cannot be lowercase letters after a speech;
- or, the set of rules that this utility uses should be aligned somehow to this poem's style.\*\*\*

The first condition cannot be fulfilled, since the text may contain also proper names. The second condition seems to be too specific – other poems might not work, regardless.

---

\*\*\* This may bring to mind [neural networks](https://en.wikipedia.org/wiki/Neural_network) as a solution, but writing this utility, it was not my purpose to implement them.

---

#### II

Another situation that does not let handle all the possible locations of speeches is that the utility assumes that semantic speech markers occurs always in one of the following three locations:
1. in the sentence before the sentence that the speech is starting in;
2. in the same sentence that the speech is starting or ending in;
3. in the sentence after the sentence that the speech is ending in.

According to that, if a semantic speech marker related to a speech is used e.g. two sentences before the sentence that the speech is starting in, it **will not** be found. Therefore, this speech **will not** be present in the results.

An example of such a case may be the following text (in Polish):
```
Piotr leżał i rozmyślał na głos. Za oknem ciemniało. "A może jednak dam radę?"
```
Here, the semantic speech marker might be either "rozmyślał" (was thinking) or "na głos" (aloud). It would not be found, since it is located two sentences before the speech (`"A może jednak dam radę?"`).

#### III

For example, let us take such a situation (this is also an excerpt from the aforementioned poem "Pan Tadeusz"):

```
Rykow jadł smaczno, mało wdawał się w rozmowę
(...)
Obaczcież, co się stało w końcu z Bonapartą..."

Tu Ryków przerwał i jadł; wtem z potrawą czwartą
Wszedł służący, i raptem boczne drzwi otwarto.

Weszła nowa osoba, przystojna i młoda;
Jej zjawienie się nagłe, jej wzrost i uroda,
Jej ubiór zwrócił oczy; wszyscy ją witali;
Prócz Tadeusza, widać, że ją wszyscy znali.
```

Here, one may be unsure whether the sentence that the speech is ending in is being continued after it or not – that is, whether the text `Tu Ryków przerwał` starts a new sentence or not. This utility assumes that these words **does not** start a new sentence. Therefore, if these words are intended to start a new sentence, the extended speech containing this speech will be one sentence longer than expected. Since generally in such cases more words are better than few, this usually should not be a problem.

## Environment, tools and technologies used

1. This utility is written 100% in the [Java programming language](https://en.wikipedia.org/wiki/Java_(programming_language)), version 11.
2. To check regular expression correctness, I have used the [regex101](https://regex101.com/) online tool (accessed Nov 2018).
3. The operating system used to compile and test it is Linux; distribution: [Fedora](https://getfedora.org/) Workstation 29; [kernel](https://www.kernel.org/) version: 4.19.6-300.fc29.x86_64.
4. The architecture of the processor used to run the operating system is x86-64.

## Sources

Below are listed only sources that are not mentioned anywhere else in this README.

#### Wikipedia

- https://en.wikipedia.org/wiki/List_of_Unicode_characters

#### StackExchange.com

- https://english.stackexchange.com/questions/2288/how-should-i-use-quotation-marks-in-sections-of-multiline-dialogue
- https://english.stackexchange.com/questions/96608/why-does-the-multi-paragraph-quotation-rule-exist
- https://english.stackexchange.com/questions/347859/where-do-i-put-the-quotation-marks-when-im-quoting-a-character-over-multiple-st

#### Stack Overflow

- https://stackoverflow.com/questions/32943179/most-elegant-way-to-join-a-map-to-a-string-in-java-8
- https://stackoverflow.com/questions/12853595/how-to-add-elements-of-a-string-array-to-a-string-array-list/
- https://stackoverflow.com/questions/14602062/java-string-split-removed-empty-values/

#### Other

- http://baxtercommunications.nl/quotation-marks-multi-paragraph-rule/