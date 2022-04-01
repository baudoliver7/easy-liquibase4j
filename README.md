[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/baudoliver7/easy-liquibase4j)](http://www.rultor.com/p/baudoliver7/easy-liquibase4j)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![Javadoc](http://www.javadoc.io/badge/com.baudoliver7/easy-liquibase4j.svg)](http://www.javadoc.io/doc/com.baudoliver7/easy-liquibase4j)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/baudoliver7/easy-liquibase4j/blob/master/LICENSE.txt)
[![codecov](https://codecov.io/gh/baudoliver7/easy-liquibase4j/branch/master/graph/badge.svg)](https://codecov.io/gh/baudoliver7/easy-liquibase4j)
[![Hits-of-Code](https://hitsofcode.com/github/baudoliver7/easy-liquibase4j)](https://hitsofcode.com/view/github/baudoliver7/easy-liquibase4j)
[![Maven Central](https://img.shields.io/maven-central/v/com.baudoliver7/easy-liquibase4j.svg)](https://maven-badges.herokuapp.com/maven-central/com.baudoliver7/easy-liquibase4j)
[![PDD status](http://www.0pdd.com/svg?name=baudoliver7/easy-liquibase4j)](http://www.0pdd.com/p?name=baudoliver7/easy-liquibase4j)

It makes using Liquibase easy.

## How does it work ?
Simply wrap your data source like this :

```java
final DataSource source =  
    new LiquibaseDataSource(
        <!-- put here your data source -->, 
        "liquibase/db.changelog-master-test.xml"
    );
``` 

Or your connection like this :

```java
final Connection connection =  
    new LiquibaseConnection(
        <!-- put here your connection -->, 
        "liquibase/db.changelog-master-test.xml"
    );
``` 

These commands run automatically scripts contained in the change log master file.

## Use it in your project
If you're using Maven, you should add it to your <code>pom.xml</code> dependencies like this:

```xml
<dependency>
    <groupId>com.baudoliver7</groupId>
    <artifactId>easy-liquibase4j</artifactId>
    <version><!-- latest version --></version>
</dependency>
<dependency>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-core</artifactId>
    <version><!-- latest version --></version>
</dependency>
``` 

## How to contribute
Fork repository, make changes, send us a pull request. We will review
your changes and apply them to the `master` branch shortly, provided
they don't violate our quality standards. To avoid frustration, before
sending us your pull request please run full Maven build:

> mvn clean install -Pqulice

Keep in mind that JDK 8 and Maven 3.1.0 are the lowest versions you may use.

## Got questions ?

If you have questions or general suggestions, don't hesitate to submit
a new [Github issue](https://github.com/baudoliver7/easy-liquibase4j/issues/new).