# frozen_string_literal: true

require_relative './problem1'

describe Problem1 do
  it 'example data' do
    p = Problem1.new([199, 200, 208, 210, 200, 207, 240, 269, 260, 263])
    expect(p.solve).to eq(7)
  end

  it 'can import data' do
    p = Problem1.new
    expect(p.import('../puzzle_inputs/aoc-1-1.txt').all? Integer).to be true
    expect(p.raw.all? Integer).to be true
    expect(p.solve).to eq(1390)
  end

end

describe Problem1B do
  it 'example data' do
    p = Problem1B.new([199, 200, 208, 210, 200, 207, 240, 269, 260, 263])
    expect(p.solve).to eq(5)
  end

  it 'real data' do
    p = Problem1B.new
    expect(p.import('../puzzle_inputs/aoc-1-1.txt').all? Integer).to be true
    expect(p.solve).to eq(1457)
  end

end
