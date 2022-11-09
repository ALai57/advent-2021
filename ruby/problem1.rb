# frozen_string_literal: true

# Some top level documentation

class Problem1
  attr_reader :raw
  attr_reader :soln

  def initialize(data=[])
    @raw = data
  end

  def import(path)
    @raw = File.readlines(path, chomp: true).map { |x| x.to_i }
  end

  def solve
    @soln = @raw.each_cons(2)
              .map { |x, y| x < y ? 1 : 0}
              .reduce { |acc, x| acc + x}
  end
end
