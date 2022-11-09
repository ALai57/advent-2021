#!/usr/bin/env ruby

raw = File.readlines('../puzzle_inputs/aoc-1-1.txt', chomp: true).map(&:to_i)

puts raw.each_cons(2).count { | x, y | x < y }
