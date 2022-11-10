require 'test/unit'

ex = File.readlines('../puzzle_inputs/aoc-3-ex.txt', chomp: true)
       .map { |x| x.split("")}

def frequencies(arr)
  arr.group_by { |el| el }
    .map{ |k, v| [k, v.count] }
    .sort_by { |_k, v| v }
    .reverse!
end

frequencies([1, 2, 3, 1, 1, 1, 2, 1, 4]) ## => [[1, 5], [2, 2], [4, 1], [3, 1]]

def most_common(arr)
  frequencies(arr)[0][0]
end

def least_common(arr)
  frequencies(arr).reverse![0][0]
end

def gamma(xs)
  xs.transpose.map { |arr| most_common(arr) }
    .join.to_i(2)
end

def epsilon(xs)
  xs.transpose.map { |arr| least_common(arr) }
    .join.to_i(2)
end

most_common([1, 2, 3, 1, 1, 1, 2, 1, 4]) ## => 1

gamma(ex) * epsilon(ex)


raw = File.readlines('../puzzle_inputs/aoc-3.txt', chomp: true)
        .map { |x| x.split("")}

gamma(raw) * epsilon(raw)

def rating(arr)
  x = arr.clone
  bits = []
  (0..x[0].size-1).each do |i|
    puts "i #{i}. bits #{bits}"
    bit = yield x.transpose[i]
    x.select! { |arr| arr[i] == bit }
    bits[i] = bit
  end

  bits.join.to_i(2)
end

rating(ex) { |bit_array| most_common(bit_array)}
rating(ex) { |bit_array| least_common(bit_array)}
