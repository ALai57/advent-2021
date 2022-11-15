require 'test/unit'

ex = File.readlines('../puzzle_inputs/aoc-3-ex.txt', chomp: true)
       .map { |x| x.split("").map(&:to_i) }

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
  frequencies(arr).reverse[0][0]
end

def gamma(xs)
  xs.transpose.map { |arr| most_common(arr) }
    .join.to_i(2)
end

def epsilon(xs)
  xs.transpose.map { |arr| least_common(arr) }
    .join.to_i(2)
end

# most_common([1, 2, 3, 1, 1, 1, 2, 1, 4]) ## => 1

gamma(ex) * epsilon(ex)


raw = File.readlines('../puzzle_inputs/aoc-3.txt', chomp: true)
        .map { |x| x.split("").map(&:to_i) }

gamma(raw) * epsilon(raw)

def rating(arr)
  n_bits = arr[0].size-1
  (0..n_bits).reduce(arr.clone) do |bit_array, bit_index|
    #puts "Bit Index #{bit_index}. Bit Array #{bit_array}"
    if bit_array.size == 1
      return bit_array
    else
      bit = yield bit_array.transpose[bit_index]
      bit_array.select { |xs| xs[bit_index] == bit }
    end
  end
end

def mean(arr)
  arr.sum(0.0) / arr.size
end

def o2(bit_array)
  rating(bit_array) { |arr| mean(arr) >= 0.5 ? 1 : 0 }
    .join.to_i(2)
end

def co2(bit_array)
  rating(bit_array) { |arr| mean(arr) >= 0.5 ? 0 : 1 }
    .join.to_i(2)
end

o2(ex) * co2(ex)
o2(raw) * co2(raw)
