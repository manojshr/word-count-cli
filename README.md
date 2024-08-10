### Build Jar First
```shell
> ./gradlew clean build
```
The Jar will be created in build/libs/word-count-<version>.jar path

### Add Shell alias
```shell
> alias jwc='java -jar <absolute-path>/build/libs/word-count-initial.jar'
```

### Let's Play
```shell
> jwc -h
Usage: jwc [-chlmVw] <file>
Gives you the word count
      <file>      Provide absolute file path
  -c, --bytes     Print the byte counts
  -h, --help      Show this help message and exit.
  -l, --lines     Print the newline counts
  -m, --chars     Print the character counts
  -V, --version   Print version information and exit.
  -w, --words     Print the word counts
```