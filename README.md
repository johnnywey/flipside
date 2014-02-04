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
Flipside is a very simple Groovy / Java options library. It includes three distinct kinds of options and a Matcher. That's it. Easy peasy.

I built it because I especially missed these idioms when transitioning from Scala back to Java / Groovy.

# Fail Enum
Two of the three options take the `Fail` enum as part of their constructor params. This is to indicate what went wrong and to try and map the failure back to an HTTP response code. For now, these are hard-coded. In the future, they will be an interface that will allow you to drop in your own failure types.

# Option Types
First of all, if you want to know more about Options, start [here](http://en.wikipedia.org/wiki/Option_type).

There are three different types of Options in Flipside:

* A `Box` (either `Some` or `None`)
* A `Marker` (either `Worked` or `DidNotWork`)
* A `Failable` (either `Success` or `Failed`)

They each have distinct uses and similar interfaces.

# Box
A `Box` is either empty or full. It's designed for operations that wouldn't necessarily "fail" if a null value were returned but get around passing null or a value:

```groovy
import static com.johnnywey.flipside.Boxes

def full = Some("A string!") // Creates a box containing a string value
def empty = None() // Creates an empty box

assert empty.isEmpty()
assert !full.isEmpty()

assert "A string!" == full.get()
empty.get() // will throw an UnsupportedOperationException```

# Marker
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
worked.getDetail()```

# Failable
A `Failable` is similar to the `Marker` and is designed for operations that succeed with a value or fail with an error condition. When the operation succeeds, you can call `.get()` on the resulting object to get at the value.

```groovy
import static com.johnnywey.flipside.Failable
import com.johnnywey.flipside.failable.Fail

def failed = Failed(Fail.NOT_FOUND, "The thing was not found") // Creates a new one indicating the operation failed and why
def success = Success("It worked!") // Creates one indicating the operation succeeded

assert !failed.isSuccess()
assert success.isSuccess()

assert "The thing was not found" == failed.getDetail()
assert Fail.NOT_FOUND == failed.getReason()
failed.get() // will throw an UnsupportedOperationException

assert success.get() == "It worked!"

# Matcher
**Coming soon!**






