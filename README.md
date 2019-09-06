[![Build Status](https://travis-ci.org/silvuss/silvuss-gesp.svg?branch=master)](https://travis-ci.org/silvuss/silvuss-gesp)

# gesp – get speeches from text

**gesp** (pronounce like "jesp"; GEt SPeeches) is a utility that allows the user to get the speeches (from monologues and dialogs) that a given text contains. By a speech, one should understand anything that is spoken: a monologue (or part of it), part of a dialogue etc. (see also the third meaning of the [speech definition by dictionary.com](https://www.dictionary.com/browse/speech)).

**Read before use:** This application **is not** intended to be used according to the purpose described above. You may use it **only** to test whether the code is written the way it is expected to be (i.e. it produces expected results) and **only** when you know what the code will really do. For details, see the section "[Disclaimers](#disclaimers)" below.

## Table of contents

1. [Copyright note](#copyright-note)
2. [Disclaimers](#disclaimers)
3. [How does this application work?](#how-does-this-application-work)
4. [Building](#building)
5. [Installation](#installation)
6. [Usage](#usage)
7. [Environments, tools and technologies used](#environments-tools-and-technologies-used)
8. [Sources](#sources)

## Copyright note

Note that this project "gesp" (this repository) has currently **no license**, as explained in [this GitHub guide on licensing projects](https://choosealicense.com/no-permission/).

For your convenience, I am including below a quote from that site:

> When you make a creative work (which includes code), the work is under exclusive copyright by default. Unless you include a license that specifies otherwise, nobody else can use, copy, distribute, or modify your work without being at risk of take-downs, shake-downs, or litigation. Once the work has other contributors (each a copyright holder), “nobody” starts including you.

Also note that I can add a license in the future if it would be relevant to the needs of this project.

## Disclaimers

**The application that this project contains is an example application that is not intended to be run.** Its purpose is only to show code that is known to work. While probably nothing dangerous would happen, you may run it only under your own responsibility.

Although I have made efforts to make it work as intended and described, it is not a "professional" application. Specifically, it was not tested in terms of separate unit tests or similar. It was tested to build and work on only one platform. For details on the platform, see the section "[Environment, tools and technologies used](#environment-tools-and-technologies-used)" below.

## How does this application work?

Firstly, this application gets the content of the file specified on the input. Then, in that content, it search for a list of speeches and text surrounding each of them. Finally, it creates a new file containing a list of the speeches found.

## Building

Before using, this application has to be compiled.

The prerequisite to be able to compile this application is that the operating system must provide the [javac compiler](https://docs.oracle.com/en/java/javase/11/tools/javac.html#GUID-AEEC9F07-CB49-4E96-8BC7-BCC2C7F725C9).

There are many tools to compile a Java program having its source files and using the `javac` compiler. The simplest way is using no additional tool. Instead, go in the terminal into the main directory of the application and execute the following command:

```
javac src/gesp/*.java
```

The output of this command shall be a set of .class files – one .class file for each of the .java files, with the same name.

## Installation

This application neither can be installed (onto any platform), nor require it. However, to run, it requires the following software to be installed:
- Java Runtime Environment (JRE)

## Usage

**_Note:_** _Before using, this application has to be compiled. For details, see the section "[Building](#building)" above._

**_Note:_** _To run, this application requires specific software to be installed. For details, see the section "[Installation](#installation)" above._

The only interface to use this application is a CLI provided within this project.

This application is intended to be run using the Java runtime environment. One can run it by executing the following command from within its main directory:

```
java src/gesp/Gesp FILE
```

where `FILE` is the path to a file containing a text to get the speeches from.

### Input

The input data of the application has to meet the following requirements (in this order):
1. it has to be the path to a file (i.e. it may not be empty); this file is assumed to contain a text to get the speeches from;
2. the file has to be readable by the user that runs the Java runtime environment;
3. the file has to be printable;
4. the file's content has to be encoded using the [UTF-8 encoding](https://en.Wikipedia.org/wiki/UTF-8).

If any of the first three requirements are not met, the application writes a message to stdout and exits. If only the 4. requirement is not met, the application will not exit, but **the results may be wrong or not**. It depends on the differences between the current encoding and the UTF-8 encoding.

### Output

As its output, this application creates a file in the same directory that the file given on the input is in. The output file will contain the list of speeches. The name of the output file is always `output`. If there is already a file named `output`, the application writes a message to stdout and exists.

**Warning: possibility of overwriting files.** The file will be created only if there is no file with the name `output` in the directory with the input file, but only in the time of checking this directory. This means that it may happen that there exists already a file named `output`; in such a case, **this file will be overwritten.** This is because the application does not append, but always creates a new file. That may be the case, for example, if files (or directories) in that directory were created/renamed very fast in the time that this application was running. One should always make sure that names of the files in the directory that the input file is in are unique, and are not being changed while this application is running.

**Tip:** If any errors occur during running, it probably means that the platform that this application is running on does not support some functions of this application, or some functions are supported in a way that this application cannot handle. For details on which platform this application was tested (i.e. builds and/or runs successfully), see the section "[Environment, tools and technologies used](#environment-tools-and-technologies-used)" below.

## Environments, tools and technologies used

### Environments and tools

- This application has been compiled using the following two environments:
    - **Operating system:** Linux; distribution: Fedora Workstation 29; kernel's version: `4.19.6-300.fc29.x86_64`
    - **Processor's architecture:** `x86-64`
- This application has been tested to work in the same environment as for compilation.

### Technologies and tools

- Programming languages:
    - [Java](https://en.wikipedia.org/wiki/Java_(programming_language)), version: 11.
- Other tools:
    - To check the correctness of regular expressions, I have used the [regex101](https://regex101.com/) online tool (accessed Nov 2018).

## Sources

Below are listed only sources that are not mentioned anywhere else in the documentation. Given in no particular order.

- **Wikipedia:**
    - https://en.wikipedia.org/wiki/List_of_Unicode_characters

- **The StackExchange network:**
    - https://english.stackexchange.com/questions/2288/how-should-i-use-quotation-marks-in-sections-of-multiline-dialogue
    - https://english.stackexchange.com/questions/96608/why-does-the-multi-paragraph-quotation-rule-exist
    - https://english.stackexchange.com/questions/347859/where-do-i-put-the-quotation-marks-when-im-quoting-a-character-over-multiple-st
    - https://stackoverflow.com/questions/32943179/most-elegant-way-to-join-a-map-to-a-string-in-java-8
    - https://stackoverflow.com/questions/12853595/how-to-add-elements-of-a-string-array-to-a-string-array-list/
    - https://stackoverflow.com/questions/14602062/java-string-split-removed-empty-values/

- **The website of Baxter Communications:**
    - http://baxtercommunications.nl/quotation-marks-multi-paragraph-rule/