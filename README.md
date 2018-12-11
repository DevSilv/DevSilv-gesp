# gesp – get speeches from text

gesp (GEt SPeeches) is a utility that allows the user get the speeches (from monologues and dialogs) that a given text contains. By a speech, one should understand the third meaning of [this speech definition](https://www.dictionary.com/browse/speech).

**Read before use:** This application **is not** intended to be used according to the purpose described above. You may use it **only** to test whether the code is written the way it is expected (i.e. it produces expected results) and **only** when you know what the code will really do. For details, see the section ["Disclaimers"](#disclaimers) of this readme.

## Table of contents

1. [Copyright note](#copyright-note)
2. [Disclaimers](#disclaimers)
3. [How does this utility work?](#how-does-this-utility-work)
4. [How to run this utility?](#how-to-run-this-utility)
5. [Process details](#process-details)
6. [Supported syntactic speech markers](#supported-syntactic-speech-markers)
7. [Supported semantic speech markers](#supported-semantic-speech-markers)
8. [Current major problems](#current-major-problems)
9. [Tools and technologies used](#tools-and-technologies-used)
10. [I have experienced a bug / a problem, or I have an idea of improvement – what can I do?](#i-have-experienced-a-bug-/-a-problem-or-i-have-an-idea-of-improvement-–-what-can-i-do)
11. [Sources that I was using](#sources-that-i-was-using)
12. [Will this project be updated?](#will-this-project-be-updated)

## Copyright note

Note that this "silvuss-thoughts" project (this repository) has currently **no license**, as explained in [this GitHub guide on licensing projects](https://choosealicense.com/no-permission/).

For your convenience, I am including below a quote from that site:

> When you make a creative work (which includes code), the work is under exclusive copyright by default. Unless you include a license that specifies otherwise, nobody else can use, copy, distribute, or modify your work without being at risk of take-downs, shake-downs, or litigation. Once the work has other contributors (each a copyright holder), “nobody” starts including you.

Also note that I can add a lincese in the future if it would be relevant to the needs of this project.

## Disclaimers

**This application is an example application that is intended to show my skills in programming in the Java language.** Its purpose is only to show code that is know to work. While probably nothing dangerous would happen, you may run it only under your own responsibility.

Although I have made efforts to make it work as intended and described, it is not a "proffesional" application. Specifically, it was not tested in terms of separate unit tests or similar. It was tested to work only on one platform. Specifically, it deals with memory, and that never may be safe at all. For details on the platform, see the section ["Tools and technologies used"](#tools-and-technologies-used) of this readme.

## How does this utility work?

Firstly, this utility gets the content of a specified file. Then, it finds a list of speeches and text surrounding them from that content. Finally, it creates a new file containing the found list.

## How to run this utility?

This utility is intended to be run in the console. There is one formal prerequisite to be able to successfully run it: the platform that this utility is intended to run on has to have available an implementation of [Java Virtual Machine](https://en.wikipedia.org/wiki/Java_virtual_machine).

Most simply, you may run this utility in the following way:
```
java gesp FILE_1 FILE_2
```
where:
- `FILE_1` is the input file path; it should be the path to a readable file containing text to get the speeches from.
- `FILE_2` is the output file path; it should be the path to an output file that will be created.

**Tip:** if only names of the files will be specified, it will be assumed that the path for each of them is the path to the current directory.

If any errors will occur during running, it probably means that the platform that it is running on does not support some functions of this utility, or some functions are supported in a way that this utility cannot handle. For details on which platform this utility was tested (i.e. runs successfully), see the section ["Tools and technologies used"](#tools-and-technologies-used) of this readme.

## Process details

This utility works in the following way:

1. after it is run, it gets its arguments. if there is less or more than two arguments, it writes a message to [stdout](https://en.wikipedia.org/wiki/Standard_streams#Standard_output_(stdout)) and exits.

2. if there are exactly two arguments, it checks whether the first is the path to a readable file. if it is, then it reads the content of this file. if it is not, it writes a message to stdout and exits.

3. it analyses the content looking for speeches. currently, in the context of this utility, a speech is defined as a pieces of text within a specified pair of characters called _syntactic speech markers_. examples of syntactic speech markers are `""` or `«»` (each character of each pair is one _marker_). a pair of markers contains one opening and one closing marker. the utility also gets small pieces of text before and after the speech, and the whole result piece of text is called _extended speech_.

4. for each found extended speech, it checks whether it contains any word from a specific list of set of words. each set is called one _semantic speech marker_. the set may contain one or many elements. examples of a semantic speech marker are "says" (one marker, and one word) or "have said" (one marker, but two words). semantics speech markers may come not only from the English language; see the "current supported semantic speech markers" section below.

5. all the extended speeches that contain any semantic speech marker are written to the output.

## Supported syntactic speech markers

There is supported only one pair of syntactic speech markers.

|No.|Opening marker character|Opening marker Unicode code|Closing marker character|Closing marker Unicode code|Remarks|
|-|-|-|-|-|-|
|1|`"`|U+0022|`"`|U+0022|Note that some texts may contain very similar, but different quotation marks: U+201C (`“`) and U+201D (`”`).|

## Supported semantic speech markers

What words a semantic speech marker may contain, depends highly on its language. Currently, there are supported only Polish semantic speech markers.

A Polish semantic speech marker may be a _normal semantic speech marker_ or a _base semantic speech marker_. each base semantic speech marker constitutes a particular group\* of normal semantic speech markers. A base marker also belongs to the group that its constitutes.

Note that in this utility a "[word](https://en.wikipedia.org/wiki/Word)" is not only considered a set of letters; it also has a **meaning**. Therefore, if markers (normal or base) in different groups include the same set of characters (i.e. they seem to be the same set of words), they are treated as separate markers. Unfortunately, currently I do not know any example of such a situation.

Currently, all the Polish markers are verbs. Therefore, a group of normal markers is created performing [_conjugation_](https://en.wikipedia.org/wiki/Grammatical_conjugation)\*\* on the appropriate words of the base marker that constitutes that group. The conjugation used in this utility includes for each base marker:
- all [persons](https://en.wikipedia.org/wiki/Grammatical_person) (e.g. "mówię", "mówisz");
- all [numbers](https://en.wikipedia.org/wiki/Grammatical_number) (e.g. "mówię", "mówimy");
- all [genders](https://en.wikipedia.org/wiki/Grammatical_gender) (e.g. "mówił", "mówiła");
- present and past [tenses](https://en.wikipedia.org/wiki/Grammatical_tense) – where appropriate (e.g. "mówię", "mówiłem");
- [aspects](https://en.wikipedia.org/wiki/Grammatical_aspect) – where appropriate; note that usually, in case of this utility if a verb has multiple forms that differ only on aspect, either some of them are not included (because they can be found using another form – e.g. "wykrzyczeć", "krzyczeć"), or they are treated as separate markers;
- [moods](https://en.wikipedia.org/wiki/Grammatical_mood) – where appropriate (e.g. "mówię", "mówiłbym");
- [voices](https://en.wikipedia.org/wiki/Voice_(grammar)) – where appropriate (e.g. "X said to Y", "Y was said").

|Group no.|Base marker language|Base marker\*\*\*|
|-|-|-|
|1|Polish|rzec|
|2|Polish|mówić|
|3|Polish|powiedzieć|
|4|Polish|powiadać|
|5|Polish|wołać|
|6|Polish|gadać|
|7|Polish|twierdzić|
|8|Polish|stwierdzać|
|9|Polish|jęczeć|
|10|Polish|jęknąć|
|11|Polish|śmiać (the word "się" at the end is omitted)|
|12|Polish|mawiać|
|13|Polish|wrzeszczeć|
|14|Polish|wrzasnąć|
|15|Polish|krzyczeć|
|16|Polish|krzyknąć|
|17|Polish|słyszeć|
|18|Polish|słuchać|

\* I do not know the exact term that would describe in linguistics such a group. the closest term seems to be "[lexeme](https://en.wikipedia.org/wiki/Lexeme)" – understood as a **set** of forms of a word. Therefore, a particular group of normal markers would be created based on a subset of a particular lexeme, created from a verb within its base semantic marker.

\*\* For details, one may also see the more general term "[inflection](https://en.wikipedia.org/wiki/Inflection)".

\*\*\* I do not know the exact representation of the "base marker" term in linguistics, but the closest seems to be "[lemma](https://en.wikipedia.org/wiki/Lemma_(morphology))".

## Current major problems

### Determining speeches

Unfortunately, not all the possible locations of speeches in a text are handled. The number of speeches that can be found depends on the form that they are written in. The more common (general) the form, the closer the number and bounds of speeches will be to the expected state. It is possible that the number of speeches will be greater than expected, as well as that it will be smaller.

#### I

FOr example, let us take a situation like this (this is an excerpt from the "[Pan Tadeusz](https://en.wikipedia.org/wiki/Pan_Tadeusz)" poem):

```
"(...)
Psy za nim fajt na lewo, on w las, a mój Kusy
Cap !!" - tak krzycząc pan Rejent, na stół pochylony,
Z palcami swemi zabiegł aż do drugiej strony
I "cap!" - Tadeuszowi wrzasnął tuż nad uchem.
```

The speech is here denoted by the double quotation marks (`""`). note that there are not any capital letters both after the first closing `"` and after the second opening `"`. Therefore, the correct way of bounding the extended speech will be to treat text within both the first and the second pair of quotation marks as one speech. In that case, the speech will contain all the characters within the above example (from the first `"` to the period). But, with the current version of this utility, there will be two separate speeches found: the first from the first opening `"` to the first closing `"`, and the second from the text `Z palcami` to the period.

In order to make this utility work for this text, there are only three ways that come to my mind. the utility should presume that:
- either there cannot be lowercase letters after a speech, or
- the extended speech may contain several pieces of text within syntactic speech markers (that is, the speech may be split into multiple parts), or
- the set of rules that this utility uses should be most probably aligned somehow to this poem's style.\*\*\*

The first condition is impossible to fulfill, since the text may contain also proper names. I am not about to try to fulfil the second condition (at least now) because it will probably be too much effort for such a small program. The third condition seems to be too specific.

\*\*\* This may bring to mind [neural networks](https://en.wikipedia.org/wiki/Neural_network) as a solution, but writing this utility, it was not my purpose to implement them.

#### II

Another situation that does not let handle all the possible locations of speeches is that the utility presumes that semantic speech markers occurs always in one of the following three locations:
1. in the sentence before the sentence that the speech is starting in;
2. in the same sentence that the speech is starting or ending in;
3. in the sentence after the sentence that the speech is ending in.

According to that, if a semantic speech marker related to a speech is used e.g. two sentences before the sentence that the speech is starting in, it **will not** be found. Therefore, this speech **will not** be present in the results.

#### III

For example, let us take such a situation (this is also an excerpt from the "[Pan Tadeusz](https://en.wikipedia.org/wiki/Pan_Tadeusz)" poem):

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

Here, one may be unsure whether the sentence that the speech is ending in is being continued after it or not – that is, whether the text `Tu Ryków przerwał` starts a new sentence or not. this utility assumes that these words **does not** start a new sentence. Therefore, if these words are intended to start a new sentence, the extended speech containing this speech will be one sentence longer than expected. Since generally in such cases more words are better than few, this usually should not be a problem (apart from some situations, I believe, not described in this README; if one will encounter them, please let me know in the issues).

## Tools and technologies used

1. This utility is written 100% in the [Java programming language](https://en.wikipedia.org/wiki/Java_(programming_language)), version 11.
2. To check regular expression correctness, I have used the [regex101](https://regex101.com/) online tool (accessed Nov 2018).

## Sources that I was using

Among many other, the following sources was useful for me when writing this README. Maybe they could also be useful for someone else:
- https://en.wikipedia.org/wiki/List_of_Unicode_characters
- https://english.stackexchange.com/questions/2288/how-should-i-use-quotation-marks-in-sections-of-multiline-dialogue
- https://english.stackexchange.com/questions/96608/why-does-the-multi-paragraph-quotation-rule-exist
- http://baxtercommunications.nl/quotation-marks-multi-paragraph-rule/
- https://english.stackexchange.com/questions/347859/where-do-i-put-the-quotation-marks-when-im-quoting-a-character-over-multiple-st
- https://stackoverflow.com/questions/32943179/most-elegant-way-to-join-a-map-to-a-string-in-java-8
- https://stackoverflow.com/a/12853618
- https://stackoverflow.com/a/14602089

## I have experienced a bug / a problem, or I have an idea of improvement – what can I do?

In case you have experienced any bug, any problem or just have an idea how to improve this utility, you may report it within this project using the GitHub Issues.

## Will this project be updated?

If you have found any bug or have an idea, it may be incorporated to let the project live. But, generally, this project is going to show what I have learned in Java by the time that it was published – so, it is not supposed to be updated greatly.