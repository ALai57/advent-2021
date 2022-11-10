
def step(acc, command)
  dir, n = command
  update = case dir
           when 'forward'
             {'horizontal' => n}
           when 'down'
             {'depth' => n}
           when 'up'
             {'depth' => -n}
           end

  acc.merge(update) { |k, old, new| old + new}
end

ex = File.readlines('../puzzle_inputs/aoc-2-ex.txt', chomp: true)
        .map(&:split)
        .map { |dir, n| [dir, n.to_i]}

ex.reduce({"horizontal" => 0, "depth" => 0}) { |acc, command| step(acc, command)}


raw = File.readlines('../puzzle_inputs/aoc-2.txt', chomp: true)
        .map(&:split)
        .map { |dir, n| [dir, n.to_i]}

result = raw.reduce({"horizontal" => 0, "depth" => 0}) { |acc, command| step(acc, command)}
result["horizontal"] * result['depth']



def step2(acc, command)
  dir, n = command
  update = case dir
           when 'forward'
             {'horizontal' => n, 'depth' => acc['aim'] * n}
           when 'down'
             {'aim' => n}
           when 'up'
             {'aim' => -n}
           end

  acc.merge(update) { |k, old, new| old + new}
end

result2 = raw.reduce({"horizontal" => 0, "depth" => 0, "aim" => 0}) { |acc, command| step2(acc, command)}
result2["horizontal"] * result2['depth']
