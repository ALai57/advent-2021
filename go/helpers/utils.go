package helpers

import (
	"os"
	"strings"
)

func ReadFile(fname string) []string {

	data, err := os.ReadFile(fname)

	if err != nil {
		panic("Bad input file")
	}

	rawString := strings.TrimSpace(string(data))

	return strings.Split(rawString, "\n")
}
