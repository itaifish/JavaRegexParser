# JavaRegexParser

This is a basic REGEX parser in Java using a state machine to traverse the input and find regex matches. Currently only legal syntax is:

`[xyz]` : this means "x" or "y" or "z"

`a*`: this means any amount of "a"

`a+`: this means one or more of "a"

Example from a unit test:

`[abcdefghij]*q*r*s*t+u*`: any amount of one of: "a"->"i", plus any amount of "q", plus any amount of "r", plus any amount of "s", plus one or more of "t", plus any amount of "u"
