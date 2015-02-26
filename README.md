<pre>

              ______ _ _       _____ _     _                    
              |  ___| (_)     /  ___(_)   | |                   
 ______ ______| |_  | |_ _ __ \ `--. _  __| | ___ ______ ______ 
|______|______|  _| | | | '_ \ `--. \ |/ _` |/ _ \______|______|
              | |   | | | |_) /\__/ / | (_| |  __/              
              \_|   |_|_| .__/\____/|_|\__,_|\___|              
                        | |                                     
                        |_|                                     

</pre>
Flipside
============
[![Build Status](https://secure.travis-ci.org/johnnywey/flipside.png)](http://travis-ci.org/johnnywey/flipside)

Flipside is a very simple Groovy / Java options library. It includes three distinct kinds of options and a Matcher. That's it. Easy peasy.

I built it because I especially missed these idioms when transitioning from Scala back to Java / Groovy.

## Install
You can install Flipside from the OSS Sonatype Maven repo `https://oss.sonatype.org/content/repositories/snapshots/`

You will probably need to manually exclude the Groovy dependency.

### Maven:
```xml
<dependency>
    <groupId>com.johnnywey</groupId>
    <artifactId>flipside</artifactId>
    <version>0.1.17-SNAPSHOT</version>
	<exclusions>
     <exclusion>
       <groupId>org.codehaus.groovy</groupId>
       <artifactId>groovy-all</artifactId>
     </exclusion>
   </exclusions>    
</dependency>
```

### Gradle:
```groovy
compile('com.johnnywey:flipside:0.1.17-SNAPSHOT') {
  exclude module: 'groovy-all'
}
```

### Grails:
```groovy
compile('com.johnnywey:flipside:0.1.17-SNAPSHOT') { excludes 'groovy-all' }
```

All files are built for Java versions >= 1.6. I will pull together a full release shortly.

## Fail Enum
Two of the three options take the `Fail` enum as part of their constructor params. This is to indicate what went wrong and to try and map the failure back to an HTTP response code. For now, these are hard-coded. In the future, they will be an interface that will allow you to drop in your own failure types.

## Option Types
First of all, if you want to know more about Options, start [here](http://en.wikipedia.org/wiki/Option_type).

There are three different types of Options in Flipside:

* A `Box` (either `Some` or `None`)
* A `Marker` (either `Worked` or `DidNotWork`)
* A `Failable` (either `Success` or `Failed`)

They each have distinct uses and similar interfaces.

## Box
A `Box` is either empty or full. It's designed for operations that wouldn't necessarily "fail" if a null value were returned but get around passing null or a value:

```groovy
import static com.johnnywey.flipside.Boxes.*

def full = Some("A string!") // Creates a box containing a string value
def empty = None() // Creates an empty box

assert empty.isEmpty()
assert !full.isEmpty()

assert "A string!" == full.get()
empty.get() // will throw an UnsupportedOperationException
```

## Marker
A `Marker` is designed for operations that result in [side effects](http://en.wikipedia.org/wiki/Side_effect_(computer_science)) such as writing some tracking information in a database or uploading a photo to a back-end image processing tier. You wouldn't need a return type if things go well but, if things fail, you'd probably want some indication as to *why*.

```groovy
import static com.johnnywey.flipside.Markers
import com.johnnywey.flipside.failable.Fail

def failed = DidNotWork(Fail.NOT_FOUND, "The thing was not found") // Creates a new one indicating the operation failed and why
def worked = Worked() // Creates one indicating the operation succeeded

assert !failed.isSuccess()
assert worked.isSuccess()

assert "The thing was not found" == failed.getDetail()
assert Fail.NOT_FOUND == failed.getReason()

// these will throw an UnsupportedOperationException
worked.getReason()
worked.getDetail()
```

## Failable
A `Failable` is similar to the `Marker` and is designed for operations that succeed with a value or fail with an error condition. When the operation succeeds, you can call `.get()` on the resulting object to get at the value.

```groovy
import static com.johnnywey.flipside.Failable.*
import com.johnnywey.flipside.failable.Fail

def failed = Failed(Fail.NOT_FOUND, "The thing was not found") // Creates a new one indicating the operation failed and why
def success = Succeeded("It worked!") // Creates one indicating the operation succeeded

assert !failed.isSuccess()
assert success.isSuccess()

assert "The thing was not found" == failed.getDetail()
assert Fail.NOT_FOUND == failed.getReason()
failed.get() // will throw an FailableException with embedded details of the failure

assert success.get() == "It worked!"
```

## Matcher
The Matcher is my attempt to replicate some of the Scala matching functionality using a Groovy DSL. While in many cases the Matcher works similarly to a Groovy `switch` statement, it becomes a lot more useful when combined with the existing Option classes.

It supports a bunch of different types including literal values, data types and Options. When used with Options, the values are automatically unwrapped and injected into the respective handler functions.

A simple type example:

```groovy
import static com.johnnywey.flipside.Matcher.match

def foundString = false
match "test" on {
	matches String, { foundString = true }
	matches Integer, { foundString = false }
}

assert foundString == true
```

A literal value example:

```groovy
import static com.johnnywey.flipside.Matcher.match

def foundString = false
def string = "test"

// Should match the first Closure even though both would apply.
match string on {
	matches "test", { foundString = true }  
	matches String, { foundString = false }
}

assert foundString == true
```
To match boxes, use the match key words `some` and `none`. In a `some` condition, the value is automatically unboxed and injected into the handler function.

```groovy
import static com.johnnywey.flipside.Matcher.match
import static com.johnnywey.flipside.Boxes.Some

def full = Some("test")

def result = match full on {
	some { it } // In this case, the matcher will return the value of the box and set it to the implicit var 'it'
	none { "fail" } // If the box were empty, we would get this value
}

assert result == "test"
```

To match Markers, use the match key words `success` and `failure`. In a `success` condition, the handler function is executed with no implicit value (as Markers do not have one). In a `failure` condition, `it` is set to the `DidNotWork` object for easy evaluation.

```groovy
import static com.johnnywey.flipside.Matcher.match
import static com.johnnywey.flipside.Markers.Worked

def option_worked = Worked()
def result_worked = null

match option_worked on {
	failure { result_worked = null }
	success { result_worked = "worked!" } // no values are injected here as a Marker contains none
}

assert result_worked == "worked!"
```

To match Failables, use the match key words `success` and `failure`. In a `success` condition, the handler function is executed with the value of `it` set to whatever is returned when `.get()` is called on the `Success` object. In a `failure` condition, `it` is set to the `Failed` object for easy evaluation.

```groovy
import static com.johnnywey.flipside.Matcher.match
import static com.johnnywey.flipside.Markers.Failables.Succeeded

def option_worked = Succeeded("worked!")
def result_worked = null

match option_worked on {
	failure { result_worked = null }
	success { result_worked = it } // 'it' is automatically set to the the value of 'option_worked.get()'
}

assert result_worked == "worked!"
```

To see the whole range of functionality, check out the [MatcherSpec](https://github.com/johnnywey/flipside/blob/master/src/test/groovy/com/johnnywey/flipside/MatcherSpec.groovy).

# License
[MIT License](LICENSE.txt)






