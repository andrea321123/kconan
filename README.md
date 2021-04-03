# kconan
Conan is a statically-typed language mostly influenced by C language.  
Language documentation is still in progress.

## Installation

If you want ot skip the build process, go to the <a href="https://github.com/andrea321123/kconan/releases">release</a> page.
Here are the steps to build the emulator from sources:

1. Clone the repo
```sh
git clone https://github.com/andrea321123/kconan
```
2. Compile with Maven
```sh
mvn clean compile
mvn package
```

Jar file should be found in target directory. (kconan-x.x-jar-with-dependencies.jar)

## Usage

If you want to execute a Conan program, run:

```sh
java -jar kconan-x.x-jar-with-dependencies.jar filename.cn
```

If you need more information on kconan usage, run: 

```sh
java -jar kconan-x.x-jar-with-dependencies.jar --help
```