# HMFF - A Hierarchical Mapping File Format

This library provides a recursive key-value interpretation for your configuration files, with comment support. 
The goal of HMFF is to provide a configuration file with the end user in mind.
End users need comments and may not be familiar with concepts like encapsulation, escaping, data types or complex data structures.
HMFF keeps it simple while staying flexible. Any text based input will have a valid HMFF interpretation.

## Include with Maven
[![](https://jitci.com/gh/justisr/HMFF/svg)](https://jitci.com/gh/justisr/HMFF) 
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.justisr</groupId>
        <artifactId>HMFF</artifactId>
        <version>master-SNAPSHOT</version>
    </dependency>
</dependencies>
```
Using `master-SNAPSHOT` in place of a version will always provide you the latest build for the master branch. Alternatively, use the short form of your target commit hash.

You can then shade HMFF into your project using a shade configuration like so:
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.2.1</version>
    <configuration>
        <artifactSet>
            <includes>
                <include>com.github.justisr:HMFF</include>
            </includes>
        </artifactSet>
        <relocations>
            <relocation>
                <pattern>com.gmail.justisroot.hmff</pattern>
                <shadedPattern>yourpackage.hmff</shadedPattern>
            </relocation>
        </relocations>
    </configuration>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
Ensure that you are relocating the shaded HMFF library within your own project's packages to avoid potential version conflicts with other projects.

### JavaDocs 
Here you can find the latest version of HMFF's public [JavaDocs](http://jitpack.io/com/github/justisr/HMFF/latest/javadoc/).

## Implementations
HMFF serves as a file wrapper, so creating a HMFF configuration is as simple as passing a file into the HMFF constructor.

```java
HMFF hmff = new HMFF(file);
```

If the file already contains your configuration contents, you can imediately read from it.
If you're not sure if the file contains your configuration at this point, and you'd like to populate the missing values, you have a couple options.

Firstly, you can use an inputstream, perhaps obtained from an internal resource within your jar, to write out the defaults of the configuration if it is empty.
Passing `false` as the overwrite param ensures that if the file has existing contents, an overwrite will not occur.

```java
hmff.save(inputStream, false);
```

Secondly, if you have an existing HMFF instance and would like to copy the contents over to another (including comments), you may do the following:

```java
hmff.copyTo(targetHMFF);
```

Alternatively, or in addition, you can create individual default vaules if the specified path doesn't yet exist:

```java
hmff.getOrSetString("default", "path", "to", "value");
hmff.getOrSetBoolean(false, "path", "to", "boolean");
hmff.getOrSetDouble(3.14, "path", "to", "pi");
hmff.getOrSetStringArray(new String[]{"array", "contents"}, "path", "to", "pi");
```

The return values of each of these lines obviously being either the default value you passed, or the existing value at that location.
If the path did not exist, it will be created with the default value you provided, thus allowing you to obtain all of the configuration values while also generating your defaults in the same stroke.

For situations where obtaining the default value is intensive, the default can also be provided within a cCllable, which will only run if the value does not exist and the default is needed:

```java
hmff.getOrSetStringArray(() -> calculateArrayContents(), "path", "to", "pi");
```
Arrays will be written to file in the format `[array, contents, etc]` but if you attempt to retrieve an array from a value that is not in this format, it will be interpretted using spaces as a delimiter.

```
key: array contents etc
```

Given the above contents for instance, `hmff.getStringArray("key")` would return the array [array, contents, etc].


## Contributing
Public classes and methods should ensure JavaDoc validity and maintain backwards compatibility at all times. For major changes, please create an issue to propose your idea.

Pull requests should always be made to the develop branch and should contain unit tests for any new code that is not already covered by existing tests.


## License
Copyright (C) 2020 Justis Root justis.root@gmail.com
([MIT License](https://choosealicense.com/licenses/mit/))

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.