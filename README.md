![](images/bobalogotransparent.png)
# Overview
Boba is an interpreted, strongly & statically typed programming language that I'm working on because I'm interested in programming languages and also very bored. **The mission statement of Boba is to develop an educational language that provides the simplicity of Python, but enforces the development of important programming skills that come with learning a more complex language like Java**. After reading quite a bit of online material about what the best starting programming language to learn is, I found a lot of clashing ideas. Programmers who argue that Python is the best start argue that it has incredibly simple syntax, making it easier and less daunting to learn--whereas programmers who advocate a start in something like Java argue that Python is too dynamic, and that it can lead to bad habits. Through my development of Boba, I aim to marry the benefits of Java and Python in both these regards.

### To write and run a Boba program:
1. write syntax in a .boba file
2. run CommandShell.java
3. use command `boba [filename]` to run

## Interpreted, Strongly & Statically Typed?
Yep, it's a bit unique in this way. For those unfamiliar, Java is statically typed, whereas Python is dynamically typed. In Java, I must tell the program the types of my variables, and I can't convert them to other types beyond that declaration. In Python, type inference is implicit, and I can change a variable's type anytime I want.

In Java:

    String hello = "hello";
    hello = 5;
    ^ NOT ALLOWED
    
In Python:

    hello = "hello"
    hello = 5
    ^ ALLOWED
    
Python, being a dynamically typed language, may sway a beginner into developing some bad habits as a programmer (which is certainly debatable)--Boba enforces explicit type declaration and stronger type conversions, but is still very easy to pick up and understand like Python.

Boba may not technically count as statically typed since it is strictly interpreted at this point in time, but for all intents and purposes it is.

## Basics
### Data Types & "Objects"
* `string`
* `number` (integers and floats packaged into "number")
* `character`
* `boolean`
* `list`
* `function` (functions are treated as objects)
### Comparative Operators
* `is`
* `isn't`
* `<, >, <=, >=`
### Logical Operators
* `and`
* `or`
* `not`
* `xor`

*Arithmetic operators are as expected* (+, -, *, /, %)

## Syntax
Everything listed below is *currently implemented*.
### Declaring Variables
**make [type] [name] [value]**

    make number x 10
    make string hello "hello"
    make character letter_a 'a'
    make list number_list number[1 2 3 4 5]
    make list string_list string["one" "two" "three" "four" "five"]
    
### Changing Variables
**change [variable] to [value/variable]**

    make number x 10
    make number y 20
    
    change x to 15
    change x to 20
    
### Print To Console
**print [value/variable]**

    make number x 10
    
    print x
    print "hello"
---
*> see ideas.txt for more syntax details*
