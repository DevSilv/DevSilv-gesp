[![Build Status](https://travis-ci.org/silvuss/silvuss-gesp.svg?branch=master)](https://travis-ci.org/silvuss/silvuss-gesp)

# gesp – Get speeches from text

gesp (GEt SPeeches) is a utility that allows the user get the speeches (from monologues and dialogs) that a given text contains. By a speech, one should understand anything that is spoken: a monologue (or part of it), part of a dialogue etc. (see also the third meaning of the [speech definition by dictionary.com](https://www.dictionary.com/browse/speech)).

**Read before use:** This utility **is not** intended to be used according to the purpose described above. You may use it **only** to test whether the code is written the way it is expected to be (i.e. it produces expected results) and **only** when you know what the code will really do. For details, see the section "[Disclaimers](#disclaimers)" of this README.

## Table of contents

1. [Copyright note](#copyright-note)
2. [Disclaimers](#disclaimers)
3. [How to install and run this utility?](#how-to-install-and-run-this-utility)
4. [How does this utility work (in general)?](#how-does-this-utility-work-in-general)
5. [Environment, tools and technologies used](#environment-tools-and-technologies-used)
6. [Sources](#sources)
7. [Details](#details)

## Copyright note

Note that this "gesp" project (this repository) has currently **no license**, as explained in [this GitHub guide on licensing projects](https://choosealicense.com/no-permission/).

For your convenience, I am including below a quote from that site:

> When you make a creative work (which includes code), the work is under exclusive copyright by default. Unless you include a license that specifies otherwise, nobody else can use, copy, distribute, or modify your work without being at risk of take-downs, shake-downs, or litigation. Once the work has other contributors (each a copyright holder), “nobody” starts including you.

Also note that I can add a lincese in the future if it would be relevant to the needs of this project.

## Disclaimers

**This utility is an example utility that is not intended to be run.** Its purpose is only to show code that is known to work. While probably nothing dangerous would happen, you may run it only under your own responsibility.

Although I have made efforts to make it work as intended and described, it is not a "proffessional" utility. Specifically, it was not tested in terms of separate unit tests or similar. It was tested to build and work on only one platform. For details on the platform, see the section "[Environment, tools and technologies used](#environment-tools-and-technologies-used)" of this README.

## How to install and run this utility?

This utility is intended to be run in the terminal. Before running, it has to be compiled.

### Compiling

The prerequisite to be able to compile this utility is that the operating system must provide the [javac compiler](https://docs.oracle.com/en/java/javase/11/tools/javac.html#GUID-AEEC9F07-CB49-4E96-8BC7-BCC2C7F725C9).

There are many tools to compile a Java program having its source files and using the `javac` compiler. The simplest way is using no additional tool. Instead, go in the terminal into the utility root directory and execute the following command:

```
javac src/gesp/*.java
```

The output of this command should be a set of .class files – one .class file for each of the .java files, with the same name.

### Running

The prerequisite to be able to run this utility is that the platform that it is intended to run on must provide [Java Virtual Machine](https://en.wikipedia.org/wiki/Java_virtual_machine) (JVM).

One of the simplest ways of running it is going in the terminal into the utility's root directory, and executing the following command:

```
java src/gesp/Gesp FILE
```

where `FILE` is the input file path; it should be the path to a readable file containing text to get the speeches from.

**Tip:** if only name of the file will be specified, it will be assumed that its path is the path to the user's current directory.

If any errors occur during running, it probably means that the platform that this utility is running on does not support some functions of this utility, or some functions are supported in a way that this utility cannot handle. For details on which platform this utility was tested (i.e. builds and/or runs successfully), see the section "[Environment, tools and technologies used](#environment-tools-and-technologies-used)" of this README.

## How does this utility work (in general)?

Firstly, this utility gets the content of the file specified as its input. Then, in that content it finds a list of speeches and text surrounding each of them. Finally, it creates a new file containing the found list.

## Input and output – details

### Input

1. This utility accepts one argument: the path to a FILE. If there is no argument, it writes a message to stdout and exits.

    **Hint:** stdout is default, but it may be redirected if one wishes.

2. If FILE does not exist, or the given path is incorrect, this utility writes a message to stdout and exits.

3. FILE has to be "normal" (not directory, among others)and readable by the user that runs Node.js. If it is not either of those, the utility writes a message to stdout and exits.

4. FILE is assumed to have its content written using [UTF-8 encoding](https://en.Wikipedia.org/wiki/UTF-8). If it has content written in different encoding, **the results may be wrong or not**. It depends on the differences between the current encoding and the UTF-8 encoding.

### Output

This utility creates one file in the directory that the input file is in. The name of the output file is always `output`. If there is already a file named `output`, this utility writes a message to stdout and exists.

#### Warning of overwritten files

The output file name will be created only if there is no file with such a name in the input file's directory, only in the time of checking this directory. If, for example, files (or directories) in that directory were created/renamed very fast in the time that this utility was running, the name may not be unique. And since this utility does not append, but always creates a new file, **some file or directory in that directory may be overwritten**. One should always pay attention whether names of the files in the directory that the input file is in are not being changed while this utility is running.

## Environment, tools and technologies used

1. This utility is created 100% in the [Java programming language](https://en.wikipedia.org/wiki/Java_(programming_language)), version 11.
2. To check regular expression correctness, I have used the [regex101](https://regex101.com/) online tool (accessed Nov 2018).
3. The operating system used to compile and test this utility is Linux; distribution: [Fedora](https://getfedora.org/) Workstation 29; [kernel](https://www.kernel.org/) version: 4.19.6-300.fc29.x86_64.
4. The architecture of the processor used to run the operating system is x86-64.

## Sources

Below are listed only sources that are not mentioned anywhere else either in this README, or in the [wiki](https://github.com/silvuss/silvuss-gesp/wiki).

#### Wikipedia

- https://en.wikipedia.org/wiki/List_of_Unicode_characters

#### StackExchange.com

- https://english.stackexchange.com/questions/2288/how-should-i-use-quotation-marks-in-sections-of-multiline-dialogue
- https://english.stackexchange.com/questions/96608/why-does-the-multi-paragraph-quotation-rule-exist
- https://english.stackexchange.com/questions/347859/where-do-i-put-the-quotation-marks-when-im-quoting-a-character-over-multiple-st

#### StackOverflow.com

- https://stackoverflow.com/questions/32943179/most-elegant-way-to-join-a-map-to-a-string-in-java-8
- https://stackoverflow.com/questions/12853595/how-to-add-elements-of-a-string-array-to-a-string-array-list/
- https://stackoverflow.com/questions/14602062/java-string-split-removed-empty-values/

#### Other

- http://baxtercommunications.nl/quotation-marks-multi-paragraph-rule/

## Details

For detailed information about this project, see [this project wiki](https://github.com/silvuss/silvuss-gesp/wiki). Particularly, the wiki includes:
- [the details of text processing that this utility performs](https://github.com/silvuss/silvuss-gesp/wiki/Process-details);
- [what syntactic speech markers are currently supported](https://github.com/silvuss/silvuss-gesp/wiki/Supported-syntactic-speech-markers);
- [what semantic speech markers are currently supported](https://github.com/silvuss/silvuss-gesp/wiki/Supported-semantic-speech-markers);
- [major problems that this utility (currently) cannot handle](https://github.com/silvuss/silvuss-gesp/wiki/Current-major-problems).