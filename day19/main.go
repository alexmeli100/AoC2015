package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"regexp"
	"strings"
)

type replacements map[string]bool

func solve(molecule string, rules map[string]replacements) map[string]bool {
	res := make(map[string]bool)

	for k, rep := range rules {
		reg := regexp.MustCompile(k)

		idxs := reg.FindAllStringIndex(molecule, -1)

		if idxs == nil {
			continue
		}

		for _, loc := range idxs {
			for rule := range rep {
				m := molecule[0:loc[0]] + rule + molecule[loc[1]:]
				res[m] = true
			}
		}
	}

	return res
}

func main() {
	fname := os.Args[1]
	bytes, err := ioutil.ReadFile(fname)

	if err != nil {
		log.Fatalln(err)
	}

	input := string(bytes)
	s := strings.Split(input, "\n\n")
	rules := make(map[string]replacements)
	r := s[0]
	molecule := s[1]

	splitRules := strings.Split(r, "\n")

	rgx := regexp.MustCompile("([a-zA-z]+) => ([a-zA-z]+)")

	for _, rule := range splitRules {
		match := rgx.FindStringSubmatch(rule)

		found, ok := rules[match[1]]

		if !ok {
			found = make(replacements)
			rules[match[1]] = found
		}

		rules[match[1]][match[2]] = true
	}

	res := solve(molecule, rules)

	fmt.Printf("Part1: %d", len(res))
}
