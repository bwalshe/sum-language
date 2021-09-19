# Sum Language
This is a plugin for a simple language based on an example grammar taken from chapter 2 of
<em>Compilers - Principals, Techniques and Tools</em> by Aho, Sethi & Ullman. 

The language allows you to specify simple equations and assign them to variables, which can then
be used in other equations. For example:

```
a = (1 + 2) / 2
b = a + 1
```
## Plugin Features:
The plugin currently supports the following opperations:
 * Jump to a variable's definition
 * Show the variable's usage
 * Highlight undefined variables.
 * Annotate variables with their value
 
The plugin implementation borrows heavily from the <a href="https://plugins.jetbrains.com/docs/intellij/custom-language-support-tutorial.html">
Custom Language Creation</a> tutorial provided by Jetbrains and was created to help clarify
some of the details around implementing reference finding.