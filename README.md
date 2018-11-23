# gesp – get speeches from text

gesp is a utility that allows the user get the speeches (from monologues and dialogs) that a given text contains. by a speech, one should understand the third meaning of this [definition](https://www.dictionary.com/browse/speech).

## copyright note

note that this "gesp" (this project) has **currently no license**, as explained here: https://choosealicense.com/no-permission/.

for your convenience I am including below a quote from that site:
> When you make a creative work (which includes code), the work is under exclusive copyright by default. Unless you include a license that specifies otherwise, nobody else can use, copy, distribute, or modify your work without being at risk of take-downs, shake-downs, or litigation. Once the work has other contributors (each a copyright holder), “nobody” starts including you.

also note that I can **add a lincese** in the near future if it would be relevant to the needs of this project.

## how does this utility work?

firstly, this utility get the content of a specified file. then, it finds a list of speeches and text surrounding them from that content. finally, it creates a new file containing the found list.

## how to run this utility?

this utility is intended to be run in the console. most simply, you may run it in the following way:
```
java gesp FILE_1 FILE_2
```
where:
- `FILE_1` is the input file path; it should be the path to a readable file containing text to get the speeches from.
- `FILE_2` is the output file path; it should be the path to an output file that will be created.

**tip:** if only names of the files will be specified, it will be assumed that the path for each of them is the path to the current directory.

## process details

this utility works in the following way:

1. after it is run, it gets its arguments. if there is less or more than two arguments, it writes a message to [stdout](https://en.wikipedia.org/wiki/Standard_streams#Standard_output_(stdout)) and exits.

2. if there are exactly two arguments, it checks whether the first is the path to a readable file. if it is, then it reads the content of this file. if it is not, it writes a message to stdout and exits.

3. it analyses the content looking for speeches. currently, in the context of this utility, a speech is defined as a pieces of text within a specified pair of characters called _syntactic speech markers_. examples of syntactic speech markers are `""` or `«»` (each character of each pair is one _marker_). a pair of markers contains one opening and one closing marker. the utility also gets small pieces of text before and after the speech, and the whole result piece of text is called _extended speech_.

4. for each found extended speech, it checks whether it contains any word from a specific list of set of words. each set is called one _semantic speech marker_. the set may contain one or many elements. examples of a semantic speech marker are "says" (one marker, and one word) or "have said" (one marker, but two words). semantics speech markers may come not only from the English language; see the "current supported semantic speech markers" section below.

5. all the extended speeches that contain any semantic speech marker are written to the output.

## current supported syntactic speech markers

currently, there is supported only one pair of syntactic speech markers.

|nr|opening marker character|opening marker unicode code|closing marker character|closing marker unicode code|comments|
|-|-|-|-|-|-|
|1|`"`|U+0022|`"`|U+0022|note that some texts may contain very similar, but different quotation marks: U+201C (`“`) and U+201D (`”`).|

## current supported semantic speech markers

what words a semantic speech marker may contain, depends highly on its language. currently, there are supported only Polish semantic speech markers.

a Polish semantic speech marker may be a _normal semantic speech marker_ or a _base semantic speech marker_. each base semantic speech marker constitutes a particular group\* of normal semantic speech markers. a base marker also belongs to the group that its constitutes.

note that in this utility a "[word](https://en.wikipedia.org/wiki/Word)" is not only considered a set of letters; it also has a **meaning**. therefore, if markers (normal or base) in different groups include the same set of characters (i.e. they seem to be the same set of words), they are treated as separate markers. unfortunately, currently I do not know any example of such a situation.

currently, all the Polish markers are verbs. therefore, a group of normal markers is created performing [_conjugation_](https://en.wikipedia.org/wiki/Grammatical_conjugation)\*\* on the appropriate words of the base marker that constitutes that group. the conjugation used in this utility includes for each base marker:
- all [persons](https://en.wikipedia.org/wiki/Grammatical_person) (e.g. "mówię", "mówisz");
- all [numbers](https://en.wikipedia.org/wiki/Grammatical_number) (e.g. "mówię", "mówimy");
- all [genders](https://en.wikipedia.org/wiki/Grammatical_gender) (e.g. "mówił", "mówiła");
- present and past [tenses](https://en.wikipedia.org/wiki/Grammatical_tense) – where appropriate (e.g. "mówię", "mówiłem");
- [aspects](https://en.wikipedia.org/wiki/Grammatical_aspect) – where appropriate; note that usually, in case of this utility if a verb has multiple forms that differ only on aspect, either some of them are not included (because they can be found using another form – e.g. "wykrzyczeć", "krzyczeć"), or they are treated as separate markers;
- [moods](https://en.wikipedia.org/wiki/Grammatical_mood) – where appropriate (e.g. "mówię", "mówiłbym");
- [voices](https://en.wikipedia.org/wiki/Voice_(grammar)) – where appropriate (e.g. "X said to Y", "Y was said").

|group nr|base marker language|base marker\*\*\*|
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

\* I do not know the exact term that would describe in linguistics such a group. the closest term seems to be "[lexeme](https://en.wikipedia.org/wiki/Lexeme)" – understood as a **set** of forms of a word. therefore, a particular group of normal markers would be created based on a subset of a particular lexeme, created from a verb within its base semantic marker.

\*\* for details, one may also see the more general term "[inflection](https://en.wikipedia.org/wiki/Inflection)".

\*\*\* I do not know the exact representation of the "base marker" term in linguistics, but the closest seems to be "[lemma](https://en.wikipedia.org/wiki/Lemma_(morphology))".

## problems with determining speeches

unfortunately, not all the possible locations of speeches in a text are handled. the number of speeches that can be found depends on the form that they are written in. the more common (general) the form, the closer the number and bounds of speeches will be to the expected state. it is possible that the number of speeches will be greater than expected, as well as that it will be smaller.

### I

for example, let us take a situation like this (this is an excerpt from the "[Pan Tadeusz](https://en.wikipedia.org/wiki/Pan_Tadeusz)" poem):

```
"(...)
Psy za nim fajt na lewo, on w las, a mój Kusy
Cap !!" - tak krzycząc pan Rejent, na stół pochylony,
Z palcami swemi zabiegł aż do drugiej strony
I "cap!" - Tadeuszowi wrzasnął tuż nad uchem.
```

the speech is here denoted by the double quotation marks (`""`). note that there are not any capital letters both after the first closing `"` and after the second opening `"`. therefore, the correct way of bounding the extended speech will be to treat text within both the first and the second pair of quotation marks as one speech. in that case, the speech will contain all the characters within the above example (from the first `"` to the period). but, with the current version of this utility, there will be two separate speeches found: the first from the first opening `"` to the first closing `"`, and the second from the text `Z palcami` to the period.

in order to make this utility work for this text, there are only three ways that come to my mind. the utility should presume that:
- either there cannot be lowercase letters after a speech, or
- the extended speech may contain several pieces of text within syntactic speech markers (that is, the speech may be split into multiple parts), or
- the set of rules that this utility uses should be most probably aligned somehow to this poem's style.\*\*\*

the first condition is impossible to fulfill, since the text may contain also proper names. I am not about to try to fulfil the second condition (at least now) because it will probably be too much effort for such a small program. the third condition seems to be too specific.

\*\*\* this may bring to mind [neural networks](https://en.wikipedia.org/wiki/Neural_network) as a solution, but writing this utility, it was not my purpose to implement them.

### II

another situation that does not let handle all the possible locations of speeches is that the utility presumes that semantic speech markers occurs always in one of the following three locations:
1. in the sentence before the sentence that the speech is starting in;
2. in the same sentence that the speech is starting or ending in;
3. in the sentence after the sentence that the speech is ending in.

according to that, if a semantic speech marker related to a speech is used e.g. two sentences before the sentence that the speech is starting in, it **will not** be found. therefore, this speech **will not** be present in the results.

### III

for example, let us take such a situation (this is also an excerpt from the "[Pan Tadeusz](https://en.wikipedia.org/wiki/Pan_Tadeusz)" poem):

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

here, one may be unsure whether the sentence that the speech is ending in is being continued after it or not – that is, whether the text `Tu Ryków przerwał` starts a new sentence or not. this utility assumes that these words **does not** start a new sentence. therefore, if these words are intended to start a new sentence, the extended speech containing this speech will be one sentence longer than expected. since generally in such cases more words are better than few, this usually should not be a problem (apart from some situations, I believe, not described in this README; if one will encounter them, please let me know in the issues).

## tools and technologies used

1. currently, this utility is written 100% in Java 11.
2. to check regular expression correctness, I have used the [regex101](https://regex101.com/) online tool.

## sources that i was using

among many other, the following sources was useful for me when writing this README. maybe they could also be useful for someone else:
- https://en.wikipedia.org/wiki/List_of_Unicode_characters
- https://english.stackexchange.com/questions/2288/how-should-i-use-quotation-marks-in-sections-of-multiline-dialogue
- https://english.stackexchange.com/questions/96608/why-does-the-multi-paragraph-quotation-rule-exist
- http://baxtercommunications.nl/quotation-marks-multi-paragraph-rule/
- https://english.stackexchange.com/questions/347859/where-do-i-put-the-quotation-marks-when-im-quoting-a-character-over-multiple-st
- https://stackoverflow.com/questions/32943179/most-elegant-way-to-join-a-map-to-a-string-in-java-8
- https://stackoverflow.com/a/12853618
- https://stackoverflow.com/a/14602089

## I have experienced a bug / a problem, or I have an idea of improvement – what can I do?

in case you have experienced any bug, any problem or just have an idea how to improve this utility, do not be afraid of report it within this project using the github's functionality (issues).
