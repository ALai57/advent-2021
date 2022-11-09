# frozen_string_literal: true

# Some top level documentation

class Problem1
  attr_reader :raw
  attr_reader :soln

  def initialize(data=[])
    @raw = data
  end

  def import(path)
    @raw = File.readlines(path, chomp: true).map(&:to_i)
  end

  def solve
    @soln = @raw.each_cons(2).count { |x, y| x < y }
  end
end

class Problem1B
  attr_reader :raw
  attr_reader :soln

  def initialize(data=[])
    @raw = data
  end

  def import(path)
    @raw = File.readlines(path, chomp: true).map(&:to_i)
  end

  def solve
    @soln = @raw.each_cons(3)
              .map { |x, y, z| x + y + z }
              .each_cons(2)
              .count { |x, y| x < y }

  end
end
