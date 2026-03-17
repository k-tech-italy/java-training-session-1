---
title: "Java and Spring Boot: starting a project"
sub_title: "A quick intro to Java and the JVM ecosystem, and how to scaffold a Spring Boot project"
event: Spring Boot training sessions
author: Diego Paolicelli
theme:
    name: ktbox-dark
options:
    incremental_lists: true
---

<!-- jump_to_middle -->

Java vs Python
===

<!-- end_slide -->

Programming paradigm
===

Python is multi-paradigm, dynamically typed and compiled to bytecode at execution time.

```python
import functools

# OOP
class Maxim:
    def __init__(self, quote, author):
        self.quote = quote
        self.author = author

    def print(self):
        print(f"{self.quote}")
        print(f"-- {self.author}")

# procedural
def main():
    words = ["We", "are", "all", "consenting", "adults."]
    # (somewhat) functional
    sentence = functools.reduce(
        lambda prefix, suffix: " ".join([prefix, suffix]),
        words
    )
    Maxim(sentence, "Guido van Rossum").print()

if __name__ == "__main__":
    main()
```

<!-- end_slide -->

Programming paradigm
===

Java is mainly OOP, statically typed and compiled to bytecode ahead-of-time.

```java
import java.util.stream.Stream;

// everything but imports must be in classes
// forget about free functions, only think about methods
public class Maxims {
    // forget about dynamic types
	public static void main(String[] args) {
        // Java has become more functional lately, but this style is not really performant...
		var quote = Stream
				.of("We", "are", "all", "Oracle", "slaves")
				.reduce((prefix, suffix) -> prefix + " " + suffix)  // hello lambdas!
				.get();

		String maxim = buildMaxim(quote, "Anonymous middle manager");
		System.out.println(maxim);
	}

    // we're not consenting adults anymore, you kids must hide as much as you can
    // from the world!
    // There are also strict rules about `static` methods...
	private static String buildMaxim(String quote, String author) {
		return quote + "\n-- " + author;
	}
}
```

<!-- end_slide -->

Handling collections
===

# Python

```python
comprehended = [x * x for x in range(100) if x % 2 == 0]
```

<!-- pause -->

# Java

```java
// pretend it's in some method :^)
// The "diamond operator" is a shorthand for "use the same type variable as the declaration"
// Also, since Java 6, boxed types and primitive types are interchangeable.
List<Integer> result = new ArrayList<>();

// good ol' C-style `for` loop
for (int i = 0; i < 100; i++) {
    if (i % 2 == 0) {
        result.add(i * i);
    }
}
```

<!-- end_slide -->

Handling collections
===

# Python

```python
comprehended = [x * x for x in range(100) if x % 2 == 0]
```

# Java

```java
// pretend it's in some method :^)
// Java doesn't have a `range` iterator, so we have to create an actual collection...
List<Integer> range = ArrayList.of(0, 1, 2, /* ... */ 99);
List<Integer> result = new ArrayList<>();

// this is the pre-Java 6 way to lazily iterate over a collection
Iterator<Integer> it = range.iterator();
while (it.hasNext()) {
    int x = it.next();
    if (x % 2 == 0) {
        result.add(x * x);
    }
}
```

<!-- end_slide -->

Handling collections
===

# Python

```python
comprehended = [x * x for x in range(100) if x % 2 == 0]
```

# Java

```java
// pretend it's in some method :^)
// Java doesn't have a `range` iterator, so we have to create an actual collection...
List<Integer> range = ArrayList.of(0, 1, 2, /* ... */ 99);
List<Integer> result = new ArrayList<>();

// Java 6 introduced the enhanced `for` loop - the JVM will create the iterator for you.
for (int x : range) {
    if (x % 2 == 0) {
        result.add(x * x);
    }
}
```

<!-- end_slide -->

Handling collections
===

# Python

```python
comprehended = [x * x for x in range(100) if x % 2 == 0]
```

# Java

```java
import java.util.stream.IntStream;

// pretend it's in some method :^)
// Java 7 introduced streams and functional interfaces
// Java 8 introduced lambdas
var streamed = IntStream
        .range(0, 100)
        .filter(x -> x % 2 == 0)
        .map(x -> x * x)
        .boxed()    // from IntStream to Stream<Integer>
        .toList();
```

<!-- end_slide -->

Default parameters
===

# Python

```python
class Animal:
    def __init__(self, name, species="Cryptid", is_extinct=False):
        self.name = name
        self.species = species
        self.is_extinct = is_extinct
```

<!-- pause -->

# Java

No support for default parameters, use overloads and method call chains instead.

```java
public class Animal {
    private String name = name;
    private String species = species;
    private boolean isExtinct = isExtinct;

    public Animal(String name) {
        this(name, "Cryptid");
    }

    // public Animal(String species) {
    //     this("is a compile-time error!", species);
    // }

    public Animal(String name, String species) {
        this(name, species, false);
    }

    public Animal(String name, String species, boolean isExtinct) {
        this.name = name;
        this.species = species;
        this.isExtinct = isExtinct;
    }
}
```

<!-- end_slide -->

Default parameters
===

# Java: Builder pattern

```java
public class Animal {
    private final String name = name;
    private final String species = species;
    private final boolean isExtinct = isExtinct;

    private Animal(AnimalBuilder builder) {
        this.name = builder.name;
        this.species = builder.species;
        this.isExtinct = builder.isExtinct;
    }

    public static class AnimalBuilder {
        private final String name;
        private String species = "Cryptid";
        private boolean isExtinct = false;

        public AnimalBuilder(String name) {
            this.name = name;
        }

        public AnimalBuilder ofSpecies(String species) {
            this.species = species;
            return this;
        }

        public AnimalBuilder extinct() {
            this.extinct = true;
            return this;
        }

        public Animal build() {
            return Animal(this);
        }
    }

    public static AnimalBuilder builder(String name) {
        return new AnimalBuilder(name);
    }
}
```

<!-- end_slide -->

Parameter validation in subclass constructors
===

# Python

```python
class Rectangle:
    def __init__(self, length, width):
        self.length = length
        self.width = width

class ValidRectangle(Rectangle):
    def __init__(self, length, width):
        if length < 0 or width < 0:
            raise ValueError("Neither size can be negative")
        super().__init__(length, width)
```

<!-- end_slide -->

Parameter validation in subclass constructors
===

# Java

```java
// Rectangle.java
public class Rectangle {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
}

// ValidRectangle.java
public class ValidRectangle extends Rectangle {
    public ValidRectangle(double length, double width) {
        if (length < 0 || width < 0) {
            throw new IllegalArgumentException("Neither size can be negative");
        }
        super(length, width);
    }
}
```

<!-- end_slide -->

Parameter validation in subclass constructors
===

# Java

```java {15-18}
// Rectangle.java
public class Rectangle {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
}

// ValidRectangle.java
public class ValidRectangle extends Rectangle {
    public ValidRectangle(double length, double width) {
        if (length < 0 || width < 0) {
            throw new IllegalArgumentException("Neither size can be negative");
        }
        super(length, width);
    }
}
```
<!-- pause -->

> [!CAUTION]
> 
> This is only allowed in Java 25 and later!

In prior versions you get this compile-time error: `error: call to super must be first statement in constructor`

<!-- end_slide -->

Parameter validation in subclass constructors
===

# Java

```java {15-18}
// Rectangle.java
public class Rectangle {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
}

// ValidRectangle.java
public class ValidRectangle extends Rectangle {
    public ValidRectangle(double length, double width) {
        super(length, width);
        if (length < 0 || width < 0) {
            throw new IllegalArgumentException("Neither size can be negative");
        }
    }
}
```

Call the constructor first, validate later.

<!-- end_slide -->

Auto-closing resources
===

# Python: context managers

```python
class DatabaseManager:
    def __init__(self, connection_string): ...
    def connect(self): ...
    def query(self, sql): ...
    def close(self): ...

class DatabaseContext:
    def __init__(self, connection_string):
        self.manager = DatabaseManager(connection_string)

    def __enter__(self):
        self.manager.connect()
        return self.manager

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.manager.close()

with DatabaseContext("localhost:5432") as db:
    results = db.query("SELECT * FROM users")
    print(results)
```
<!-- end_slide -->

Auto-closing resources
===

# Java: try-with-resources

```java
import java.lang.AutoCloseable;
// other imports omitted

public class DatabaseManager implements AutoCloseable {
    private String connectionString;
    private Connection connection;

    public DatabaseManager(String connectionString) {
        this.connectionString = connectionString;
    }

    public DatabaseManager connect() throws SQLException {/* ... */}
    public <T extends DbRecord> T query(String sql) throws SQLException {/* ... */}

    @Override public void close() {
        // do all necessary cleanup, then...
        if (connection != null) {
            connection.close();
        }
    }
}

// pretend this is in some method :^)
// anyway - resources in the `try` clause **must** implement `AutoCloseable`
try (DatabaseManager db = new DatabaseManager("localhost:5432")) {
    db.connect();
    return db.query("SELECT * FROM users");
} catch (SQLException e) {
    e.printStackTrace();
}
```

<!-- end_slide -->

@Annotations
===

# Python: decorators

```python
import warnings

def deprecated(reason="This function is deprecated"):
    def _decorated(fn):
        def _wrapped(*args, **kwargs):
            warnings.warn(f"{fn.__name__}: {reason}", DeprecationWarning)
            return fn(*args, **kwargs)
        return _wrapped
    return _decorated

@deprecated("use the + operator directly")
def add(x, y):
    return x + y

add(1, 2)
# --> <python-input-1>:4: DeprecationWarning: add: use the + operator directly
# --> 3
```

<!-- pause -->

# Java: ... nothing?

```java
// annotations are just metadata added to methods and classes
// for tools and frameworks to use, either at runtime or at
// compile time
@Deprecated(since = "3.0.0")
public double add(double x, double y) {
    return x + y;
}
```

<!-- end_slide -->

<!-- jump_to_middle -->

The rest is for you to discover on your own.
===

[](https://learnxinyminutes.com/java/)
===

<!-- end_slide -->

<!-- jump_to_middle -->

The state of the Java ecosystem
===

<!-- end_slide -->

<!-- jump_to_middle -->

The sad, sorry state of the Java ecosystem
===

<!-- end_slide -->

Distributions
===

# Python

You can find the official distribution at [](https://python.org/downloads). There are alternative distributions for niche uses (e.g. PyPy, MicroPython)

<!-- pause -->

# Java

You can find the official distribution of the Java SE Development Kit (JDK) on Oracle's website ([](https://www.oracle.com/java/technologies/downloads/archive/))

<!-- pause -->

... but, unless you have a good lawyer, ***stay away from it!***

<!-- pause -->

Oracle changed their licensing policies like this:

* Until Java 10: _Oracle Binary Code License Agreement_ - you can use the JDK for commercial purposes
* Java 11-16: _Oracle Technology Network License Agreement for Oracle Java SE_ - you can use the JDK for commercial purposes **but you have to pay Oracle a fee**.
* Java 17+: _Oracle No-Fee Terms and Conditions_ - you can use the JDK **only for internal business operations** (but what does it mean exactly?)

## OpenJDK

FOSS (GPLv2+CE) version of the JDK, distributed by Oracle ([](https://jdk.java.net)).

<!-- pause -->

Other builds based on OpenJDK (from [](whichjdk.com)):

* **Adoptium Eclipse Temurin** ([](https://adoptium.net/)) - FOSS distribution backed by tech giants like Red Hat, IBM, ~~Microslop~~ Microsoft. **Generally recommended.**
* **AdoptOpenJDK** ([](https://adoptopenjdk.net/)) - Adoptium's predecessor. Site and old releases only kept online for archival purposes. **Legacy.**
* **Azul Zulu** ([](https://www.azul.com/)) - FOSS distribution maintained by Azul. **Generally a good choice, but not future-proof.**
* **Azul Zing** ([](https://www.azul.com/)) - Commercial distribution maintained by Azul. Contains proprietary optimizations aimed at low-latency applications. **Obtain a license first.**
* **BellSoft Liberica JDK** ([](https://bell-sw.com/)) - FOSS distribution maintained by BellSoft. Trusted by the Spring Boot project. **Generally a good choice, but not future-proof.**
* **Amazon Corretto** ([](https://aws.amazon.com/corretto)) - Maintained by Amazon, optimized for their services. **Decent choice. Use if you're vendor-locked to AWS for hosting**.
* **~~Microslop~~ Microsoft Build of OpenJDK** ([](https://microsoft.com/openjdk)) - Maintained by MSFT, optimized for their services. **Use only if you're vendor-locked to Azure for hosting, avoid otherwise**.
* **SapMachine** - Maintained by SAP. **Use only if you're vendor-locked to SAP for hosting, avoid otherwise**.
* **Red Hat OpenJDK** - Maintained by Red Hat. **Use only if you're running directly on RHEL, avoid otherwise**.
* **IBM Semeru Runtime** - avoid.
* **Alibaba Dragonwell** - don't use outside of China.
* **ojdkbuild** - Windows only, discontinued.
* **GraalVM** - Developed by Oracle. Focused on high performance, has a polyglot VM. Always on the bleeding edge, **use in production at your own risk**.

<!-- end_slide -->

Language version
===

# Python

LTS builds of Python 3! The days of the Python 2 vs Python 3 war are finally over.

<!-- pause -->

# Java

LTS builds of the (Open)JDK...

<!-- pause -->

Version history (from [](whichjdk.com)):

| JDK version | Type | Release date | Features | Notes |
|-------------|------|--------------|----------|-------|
| **8** | **LTS** | **03/2014** | Lambdas | **Latest LTS before Oracle changed their licensing policy**. No longer maintained by Oracle |
| 9 | Feature | 09/2017 | Module system | EOL |
| 10 | Feature | 03/2018 | Type inference in local variable declarations (`var`) | EOL |
| **11** | **LTS** | **09/2018** |  Revamped HTTP client | Still maintained by Oracle |
| 12 | Feature | 03/2019 |  | EOL |
| 13 | Feature | 09/2019 |  | EOL |
| 14 | Feature | 03/2020 | `switch` expressions | EOL |
| 15 | Feature | 09/2020 | Text blocks (multi-line strings) | EOL |
| 16 | Feature | 03/2021 | Records (think named tuples in Python) | EOL |
| **17** | **LTS** | **09/2021** | Sealed classes and interfaces | Still maintained by Oracle |
| 18 | Feature | 03/2022 | UTF-8 by default | EOL |
| 19 | Feature | 09/2022 | | EOL |
| 20 | Feature | 03/2023 | | EOL |
| **21** | **LTS** | **09/2023** | Pattern matching, Virtual threads | Still maintained. **Good choice for new projects.** |
| 22 | Feature | 03/2024 | | |
| 23 | Feature | 09/2024 | Markdown doc comments | |
| 24 | Feature | 03/2025 | Ahead-of-time class loading | |
| **25** | **LTS** | **09/2025** | Flexible constructor bodies | Current LTS. **Should be stable enough by now.** |
| 26 | Feature | 03/2026 | Primitive types in patterns, `instanceof` and `switch` expressions | Just released. Stick to the latest LTS. |

<!-- end_slide -->

Build systems
===

# Python

Built-in tools like `setuptools`, `wheel`, `pip` and tools built on top of them (Pipenv, Poetry, `uv`)

<!-- pause -->

# Java

* **Apache Maven**
* **Gradle**
* Bazel (if you work at Google)
* Buck (if you work for Zuck)
* sbt (if you use Scala ~~like you should~~)
* Apache Ant (if you live in the '90s)
* `make` (if you live in a cave)

<!-- end_slide -->

Which distribution? Which version? Which build system?
===

The main problem here is a clear lack of _de facto_ standards.

Companies are quite resistant to change, so expect opposition against changes to long-standing processes and operations.

<!-- pause -->

# Distribution

You should probably use **Eclipse Temurin** for new projects.

<!-- pause -->

... but many companies are not eager to migrate to non-Oracle distributions. Expect **Oracle OpenJDK** at best, **older official JDKs** at worst.

<!-- pause -->

# Version

You should be able to **adopt Java 25** for greenfield projects without much fuss, but if your project requires more conservative choices then **stick to Java 21**.

<!-- pause -->

In the "real world", though, you will end up maintaining existing projects, so you should expect to be stuck with Java 11 (17 if you're lucky).

<!-- pause -->

Some companies are still using pre-licensing change Oracle JDKs/JREs in production, so don't be surprised if you're asked to maintain an old Java 1.5 monolith or start the development of a new service in Java 8.

<!-- pause -->

# Build system

Expect either Maven or Gradle, but some dinosaurs could still be sticking with Ant.

<!-- end_slide -->

<!-- jump_to_middle -->

Enough with the gloomy atmosphere, let's get our hands dirty!
===

<!-- end_slide -->

<!-- jump_to_middle -->

Environment setup
===

<!-- end_slide -->

Checklist
===

* **Version manager** - We need to manage multiple versions of the JDK, so we can work on both Java 21 microservices and Java 6 monoliths on the same machine :^)
* **The JDK** - We will install it through the version manager.
* **A build system** - If possible, we will use the version manager to install it.
* **An IDE** - ~~I use Neovim btw~~

<!-- end_slide -->

Version Manager
===

# Python

[PyEnv](https://pyenv.net/) - an invasive but useful choice for Python devs

[uv](https://astral.sh/uv/) - less invasive, way faster

<!-- pause -->

# Java

[SDKMAN!](https://sdkman.io) - an invasive but useful choice for Java devs

<!-- pause -->

# Language independent

[asdf](https://asdf-vm.com)

[mise](https://mise.jdx.dev)

<!-- pause -->

We are going to use SDKMAN! for these sessions.
===

<!-- end_slide -->

SDKMAN!
===

# Installation

```sh
curl -s https://get.sdkman.io | sh

source ~/.sdkman/bin/sdkman-init.sh

# confirm SDKMAN is working
sdk version
```

<!-- pause -->

# Installing an SDK

```sh
# If only we could use Scala... Kotlin is fine too.
# Don't run this, though - unless you're really interested in Scala.
sdk install scala
```

<!-- end_slide -->

JDK
===

Too many choices...

**Too many widely used LTS versions**: 8, 11, 17, 21, and now 25

**Too many distributions**: Oracle OpenJDK, Temurin, Corretto, Zulu...

<!-- pause -->

We are going to adopt the latest Oracle OpenJDK.
===

```sh
sdk install java 25.0.2-open
```

<!-- pause -->

> [!IMPORTANT]
>
> You can omit the version, but SDKMAN will install the latest LTS Eclipse Temurin by default.
>
> Be sure to specify the version and the distribution as shown above.

<!-- end_slide -->

Build system
===

* **Maven** is the safe choice
* **Gradle** is compatible with Maven and generally more ergonomic
* **Ant** exists and companies still use it in production - that's all you have to know.

Gradle is our pick for these sessions.
===

```sh
sdk install gradle
```

<!-- end_slide -->

IDE
===

* **Eclipse**
* JetBrains **IntelliJ IDEA**
* **Visual Studio C\*de**
* **Cursor** - may the Slop be with you
* **Neovim** btw
* **Zed**
* **Emacs** - decent OS with a terrible editor
* Pretty much anything with LSP support - if it supports DAP, too, then it's even better.

Pick your favorite
===

If you want a feature-rich and user-friendly IDE, then JetBrains products are never a bad choice.

Otherwise, pick your poison. I myself am experimenting with Neovim (btw) - Java tooling (LSP, DAP) has come a long way, and now it looks like you're no longer stuck with an IDE if you prefer lightweight editors. The `jdtls` LSP in particular looks fantastic.

<!-- end_slide -->

<!-- jump_to_middle -->

Starting a project
===

<!-- end_slide -->

One-time SDK setup
===

```sh
sdk config
```

then set `sdkman_auto_env` to `true` if you want SDK auto-switching (like PyEnv or `uv`)

<!-- end_slide -->

Starting a standalone project
===

```sh
sdk env init
```

<!-- pause -->

```sh
gradle init
```

`gradle init` is an interactive CLI command which helps you set up a Java (or C++, Kotlin, Groovy...) project in a matter of seconds.

<!-- pause -->

`gradle init` will never ask you for the base package name. You need to provide it with the `--package` option, otherwise it will use `org.example`.

```sh
gradle init --package com.me.myproject
```

<!-- pause -->

`gradle init --help` does not tell you about the `--package` option.

<!-- pause -->

You're welcome.

<!-- end_slide -->

Starting a Spring Boot project
===

There are two ways to go:

* **[Spring Initializr](https://start.spring.io/index.html)**
* The **Spring Boot CLI** - Initializr in CLI form

You can install the Spring Boot CLI with SDKMAN:

```sh
sdk install springboot
```

<!-- pause -->

Initialize a new Spring Boot project by running

```sh
spring init
```

<!-- pause -->

... and unpacking the zip file it downloads - the same zip file you would download from the Initializr website :^)

<!-- pause -->

```sh
# list all options and flags - they're pretty much the same as the ones on the Initializr site
spring help init

# show all available dependencies
spring init --list
```

See [](https://docs.spring.io/spring-boot/cli/using-the-cli.html) for more information.

<!-- end_slide -->

<!-- jump_to_middle -->

Before delving into Spring Boot...
===

<!-- end_slide -->

<!-- jump_to_middle -->

... let's get acquainted with Java!
===

<!-- end_slide -->

Assignment
===

<!-- pause -->

# Port the `GOMOKO` game from GW-BASIC to Java

The game first appeared in [David H. Ahl's "BASIC Computer Games" book](https://en.wikipedia.org/wiki/BASIC_Computer_Games), published in 1973.

[Source code here](https://raw.githubusercontent.com/coding-horror/basic-computer-games/refs/heads/main/40_Gomoko/gomoko.bas)

[Try it out here](https://coding-horror.github.io/basic-computer-games/40_Gomoko/javascript/gomoko.html) (JavaScript port)

<!-- pause -->

From the readme:

> GOMOKO or GOMOKU is a traditional game of the Orient. It is played by two people on a board of intersecting lines (19 left-to-right lines, 19 top-to-bottom lines, 361 intersections in all). Players take turns. During his turn, a player may cover one intersection with a marker; (one player uses white markers; the other player uses black markers). The object of the game is to get five adjacent markers in a row, horizontally, vertically or along either diagonal.
>
> Unfortunately, this program does not make the computer a very good player. It does not know when you are about to win or even who has won. But some of its moves may surprise you.

<!-- pause -->

## You MUST...

* **Implement all the features of the GW-BASIC program.** The final program must look and work exactly like its BASIC counterpart.
* **Initialize and structure the game properly.** Initialize your project with Gradle, use classes wisely.
* **Document your classes' public API.**

## You MUST NOT...

* **Make a straight, line-by-line port.** You are not programming in BASIC - Java is way more powerful, so use that power well. Give the game the structure and design it deserves :^)
* **Use AI for coding, not even for completions.** You are learning, and learning is done best without AI assistance. ***Turn Copilot off!***
* **CHEAT** by looking at the existing port on GitHub. I **will** notice.

## You CAN...

* **Add features (if you have time to spare).** For instance, you can add the check for winning conditions that the original BASIC program lacks, or restrictions on the first moves - look up _Gomoku_ on Wikipedia.
* **Use AI to understand the BASIC program.** You're learning Java, not BASIC :^)
* **Write tests.** We'll see JUnit later, but you are welcome to try it :^)

## You SHOULD...

* **Ask for help if you're stuck.**
* ***HAVE FUN.***
    * ... if you can.
