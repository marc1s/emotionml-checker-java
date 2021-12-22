emotionml-checker-java
======================

[![Build Status](https://buildhive.cloudbees.com/job/marc1s/job/emotionml-checker-java/badge/icon)](https://buildhive.cloudbees.com/job/marc1s/job/emotionml-checker-java/)

A generic implementation of EmotionML checks, in Java.

Building
--------

Run

    ./gradlew build

Command line usage
------------------

The EmotionML checker can be used to verify the validity of a set of EmotionML files as follows:

    java -jar emotionml-checker-java.jar file.emotionml [more emotionml files]

where `file.emotionml` is an XML file containing the EmotionML document to be validated.

The tool will print for each file either an "ok" or a validation error message.

API usage
---------

Key APIs to use from Java code are the following.

To verify that a given XML document is valid EmotionML:

      new Checker().parse(InputStream)
      new Checker().validate(Document)
      new Checker().validateFragment(DocumentFragment)
    
To obtain a certain Emotion Vocabulary:

    EmotionVocabulary.get(String vocabularyUriWithId)

To inquire about properties of an Emotion Vocabulary:

    vocabulary.getType()
    vocabulary.getItems()

License
-------

This software is placed in the public domain as defined by the CC0 license, see https://creativecommons.org/publicdomain/zero/1.0/.
