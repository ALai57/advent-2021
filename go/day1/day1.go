package main

import (
	help "alai57/aoc-2021/helpers"
	"fmt"
	lo "github.com/samber/lo"
	"strconv"
)

func toInt(s string, index int) int {
	v, err := strconv.Atoi(s)
	if err != nil {
		panic("Invalid conversion")
	}
	return v
}

func slidingWindow(vals []int, f func([]int) int, windowSize int) []int {
	windows := []int{}
	for i := 0; i < len(vals)-windowSize+1; i++ {
		windows = append(windows, f(vals[i:i+windowSize]))
	}
	return windows
}

func main() {
	data := help.ReadFile("../../puzzle_inputs/aoc-1-1.txt")
	depths := lo.Map(data, toInt)

	// Part 1
	fmt.Println("*************************")
	fmt.Println("Part 1:")

	increases := 0
	for i := 0; i < len(depths)-1; i++ {
		if depths[i+1] > depths[i] {
			increases++
		}
	}
	fmt.Println(increases)

	// Part 2
	fmt.Println("*************************")
	fmt.Println("Part 2:")

	windows := slidingWindow(depths, lo.Sum[int], 3)

	increases = 0
	for i := 0; i < len(windows)-1; i++ {
		if windows[i+1] > windows[i] {
			increases++
		}
	}
	fmt.Println(increases)

	return
}
