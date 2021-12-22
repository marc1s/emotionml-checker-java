emotionml-checker-java
======================

[![CI](https://github.com/marytts/emotionml-checker-java/actions/workflows/main.yml/badge.svg)](https://github.com/marytts/emotionml-checker-java/actions/workflows/main.yml)

A generic implementation of EmotionML checks, in Java.

Dependency resolution
---------------------

Ensure you have a valid GitHub personal access token with `read:packages` scope.

### Gradle

Configure your GitHub usename and token as Gradle properties `GitHubPackagesUsername` and `GitHubPackagesPassword`, respectively.

Then, add this to your `build.gradle`:

```gradle
repositories {
    exclusiveContent {
        forRepository {
            maven {
                name 'GitHubPackages'
                url 'https://maven.pkg.github.com/marytts/emotionml-checker-java'
                credentials PasswordCredentials
            }
        }
        filter {
            includeModule 'de.dfki.mary', 'emotionml-checker-java'
        }
    }
}

dependencies {
    implementation group: 'de.dfki.mary', name: 'emotionml-checker-java', version: '1.2'
}
```

### Maven

Configure your Maven `settings.xml` to [authenticate to GitHub Packages](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry).

Then, add this to your `pom.xml`:

```xml
<dependency>
    <groupId>de.dfki.mary</groupId>
    <artifactId>emotionml-checker-java</artifactId>
    <version>1.2</version>
</dependency>
```

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

```java
new Checker().parse(InputStream);
new Checker().validate(Document);
new Checker().validateFragment(DocumentFragment);
```

To obtain a certain Emotion Vocabulary:

```java
EmotionVocabulary.get(String vocabularyUriWithId);
```

To inquire about properties of an Emotion Vocabulary:

```java
vocabulary.getType()
vocabulary.getItems()
```

License
-------

This software is placed in the public domain as defined by the CC0 license, see https://creativecommons.org/publicdomain/zero/1.0/.
