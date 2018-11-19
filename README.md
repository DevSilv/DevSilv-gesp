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

4. for each found extended speech, it checks whether it contains any word from a specific list of set of words. each set is called one _semantic speech marker_. the set may contain one or many elements. examples of a semantic speech marker are "says" (one marker, one word) or "have said" (one marker, two words). semantics speech markers may come not only from the English language; see the "current supported semantic speech markers" section below.

5. all the extended speeches that contain any semantic speech marker are written to the output.

## current supported syntactic speech markers

currently, there is supported only one pair of syntactic speech markers.

|nr|opening marker character|opening marker unicode code|closing marker character|closing marker unicode code|comments|
|-|-|-|-|-|-|
|1|`"`|U+0022|`"`|U+0022|note that some texts may contain very similar, but different quotation marks: U+201C (`“`) and U+201D (`”`).|

## current supported semantic speech markers

currently, there are supported only Polish semantic speech markers.

in the table below, the words in one marker are divided by spaces, and the markers are divided by commas. the term _base word_ is used to specify an informal group of markers – only in order to improve readability of the whole list of markers.

|group nr|language|base word\*|speech markers\*\*|
|-|-|-|-|
|1|Polish|rzec|rzec, rzekę, rzeczesz, rzecze, rzeczemy, rzeczecie, rzeką, rzekł, rzekła, rzekło, rzekliśmy, rzekłyśmy, rzekliście, rzekłyście, rzekli, rzekły|
|2|Polish|mówić|mówić, mówię, mówisz, mówi, mówimy, mówicie, mówią, mówiłem, mówiłeś, mówił, mówiła, mówiło, mówiliśmy, mówiłyśmy, mówiliście, mówiłyście, mówili, mówiły|
|3|Polish|powiedzieć|powiedzieć, powiedziałem, powiedziałeś, powiedział, powiedziała, powiedziało, powiedzieliśmy, powiedziałyśmy, powiedzieliście, powiedziałyście, powiedzieli, powiedziały|
|4|Polish|wołać|wołać, wołam, wołasz, woła, wołamy, wołacie, wołają, wołałem, wołałeś, wołał, wołała, wołało, wołaliśmy, wołałyśmy, wołaliście, wołałyście, wołali, wołały|
|5|Polish|gadać|gadać, gadam, gadasz, gada, gadamy, gadacie, gadają, gadałem, gadałeś, gadał, gadała, gadało, gadaliśmy, gadałyśmy, gadaliście, gadałyście, gadali, gadały|
|6|Polish|twierdzić|twierdzić, twierdzę, twierdzisz, twierdzi, twierdzimy, twierdzicie, twierdzą, twierdziłem, twierdziłeś, twierdził, twierdziła, twierdziło, twierdziliśmy, twierdziłyśmy, twierdziliście, twierdziłyście, twierdzili, twierdziły|
|7|Polish|stwierdzać|stwierdzać, stwierdzam, stwierdzasz, stwierdza, stwierdzamy, stwierdzacie, stwierdzają, stwierdzić, stwierdziłem, stwierdziłeś, stwierdził, stwierdziła, stwierdziło, stwierdziliśmy, stwierdziłyśmy, stwierdziliście, stwierdziłyśmy, stwierdzili, stwierdziły|
|8|Polish|jęczeć|jęczeć, jęczę, jęczysz, jęczy, jęczymy, jęczycie, jęczą|
|9|Polish|jęknąć|jęknąć, jęknąłem, jęknąłeś, jęknął, jęknęła, jęknęło, jęknęliśmy, jęknęłyśmy, jęknęliście, jęknęłyście, jęknęli, jęknęły|
|10|Polish|śmiać (the word "się" at the end is omitted)|śmiać, śmiałem, śmiałeś, śmiał, śmiała, śmiało, śmialiśmy, śmiałyśmy, śmialiście, śmiałyście, śmiali, śmiały|

\* I do not know the exact representation of a "base word" term in linguistics, but the closest seems to be "[lemma](https://en.wikipedia.org/wiki/Lemma_(morphology))".

\*\* I do not know the exact term that would describe in linguistics the set of the speech markers that I list here. the closest term seems to be "[lexeme](https://en.wikipedia.org/wiki/Lexeme)" – understood as a **set** of forms of a word. therefore, a particular set of words that I list here would be a subset of a particular lexeme.

## current problems with determining speeches

unfortunately, currently not all the possible locations of speeches in a text are handled. the number of speeches that can be found depends on the form that they are written in. the more common (general) the form, the closer the number and bounds of speeches will be to the expected state. it is possible that the number of speeches will be greater than expected, as well as that it will be smaller.

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

the first condition is impossible to fulfil, since the text may contain also proper names. I am not about to try to fulfil the second condition (at least now) because it will probably be too much effort for such a small program. the third condition seems to be too specific.

\*\*\* this may bring to mind [neural networks](https://en.wikipedia.org/wiki/Neural_network) as a solution, but writing this utility, it was not my purpose to implement them.

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

## I have experienced a bug / a problem, or I have an idea of improvement – what can I do?

in case you have experienced any bug, any problem or just have an idea how to improve this utility, do not be afraid of report it within this project using the github's functinality (issues).