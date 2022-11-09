
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

raw = File.readlines('../puzzle_inputs/aoc-2-ex.txt', chomp: true)
        .map(&:split)
        .map { |dir, n| [dir, n.to_i]}

raw.reduce({"horizontal" => 0, "depth" => 0}) { |acc, command| step(acc, command)}
