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

func increasing(x int, y int) bool {
	if y > x {
		return true
	} else {
		return false
	}
}

func arrayCompare(arr []int, pred func(int, int) bool) int {
	counts := 0
	for i := 0; i < len(arr)-1; i++ {
		if pred(arr[i], arr[i+1]) {
			counts++
		}
	}
	return counts
}

func main() {
	data := help.ReadFile("../../puzzle_inputs/aoc-1-1.txt")
	depths := lo.Map(data, toInt)

	// Part 1
	fmt.Println("*************************")
	fmt.Println("Part 1:")

	increases := arrayCompare(depths, increasing)
	fmt.Println(increases)

	// Part 2
	fmt.Println("*************************")
	fmt.Println("Part 2:")

	windows := slidingWindow(depths, lo.Sum[int], 3)
	increases = arrayCompare(windows, increasing)
	fmt.Println(increases)

	return
}
