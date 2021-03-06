![Build](https://img.shields.io/github/workflow/status/sjanarth/dsutils/Maven%20Package)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/sjanarth/dsutils)
![GitHub release (latest by date including pre-releases)](https://img.shields.io/github/v/release/sjanarth/dsutils?include_prereleases)
![License](https://img.shields.io/github/license/sjanarth/dsutils)

# Special Purpose Data Structures
A collection of special purpose utility data structures classes.

* [BasicSuffixArray](https://htmlpreview.github.io/?http://raw.githubusercontent.com/sjanarth/dsutils/master/docs/com/sjanarth/dsutils/BasicSuffixArray.html)
* [BasicSuffixTree](https://htmlpreview.github.io/?http://raw.githubusercontent.com/sjanarth/dsutils/master/docs/com/sjanarth/dsutils/BasicSuffixTree.html)
* [BasicTrie](https://htmlpreview.github.io/?http://raw.githubusercontent.com/sjanarth/dsutils/master/docs/com/sjanarth/dsutils/BasicTrie.html)
* [CollatingIterator](https://htmlpreview.github.io/?http://raw.githubusercontent.com/sjanarth/dsutils/master/docs/com/sjanarth/dsutils/CollatingIterator.html)
* [LRUCache](https://htmlpreview.github.io/?http://raw.githubusercontent.com/sjanarth/dsutils/master/docs/com/sjanarth/dsutils/LRUCache.html)

# Documentation and Usage
**General Documentation**

[JavaDocs](http://htmlpreview.github.io/?http://raw.githubusercontent.com/sjanarth/dsutils/master/docs/overview-summary.html)

**Example Usage** 

Sample programs that illustrate common usage patterns.

* [AutoComplete](https://htmlpreview.github.io/?http://raw.githubusercontent.com/sjanarth/dsutils/master/docs/com/sjanarth/dsutils/samples/AutoComplete.html) : A complete type ahead search system that provides fast prefix and topN queries.
* [AutoCompleteTrie](https://htmlpreview.github.io/?http://raw.githubusercontent.com/sjanarth/dsutils/master/docs/com/sjanarth/dsutils/samples/AutoCompleteTrie.html) : An extension of the BasicTrie data structure specifically for typeahead systems.
* [BasicSuffixArray](https://htmlpreview.github.io/?http://raw.githubusercontent.com/sjanarth/dsutils/master/docs/com/sjanarth/dsutils/samples/BasicSuffixArray.html) : Sample illustrating LongestCommonSubstring & LongestRepeatedSubstring usecases of BasicSuffixArray.
* [BasicSuffixTree](https://htmlpreview.github.io/?http://raw.githubusercontent.com/sjanarth/dsutils/master/docs/com/sjanarth/dsutils/samples/BasicSuffixTree.html)
* [CollatingIterator](https://htmlpreview.github.io/?http://raw.githubusercontent.com/sjanarth/dsutils/master/docs/com/sjanarth/dsutils/samples/CollatingIterator.html)

**Download for Maven Projects**

dsutils is in Maven Central, and can be added to a Maven project as follows:
```
<dependency>
    <groupId>io.github.sjanarth</groupId>
    <artifactId>sjanarth-dsutils</artifactId>
    <version>x.x.x</version>
</dependency>
```

**Download for Gradle Projects**

dsutils is in Maven Central, and can be added to a Gradle project as follows:
```
implementation group: 'io.github.sjanarth', name: 'sjanarth-dsutils', version: 'x.x.x'
```
