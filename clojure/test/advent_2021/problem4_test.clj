(ns advent-2021.problem4-test
  (:require [advent-2021.problem4 :as sut]
            [clojure.java.io :as io]
            [clojure.test :refer :all]))

(def parsed-example
  (-> "aoc-4-example.txt"
    io/resource
    slurp
    sut/parse-inputs))

(def example-bingo-numbers (:bingo-numbers parsed-example))
(def example-bingo-boards  (:bingo-boards parsed-example))

(deftest row->ints-test
  (is (= [58 60 65 4 91] (sut/row->ints "  58 60 65  4 91"))))

(deftest win-predicate-test
  (testing "Emitted predicates behave as expected"
    (are [expected required-numbers called-numbers]
      (let [pred (sut/->win-condition required-numbers)]
        (= expected (pred called-numbers)))

      true  #{1 2 3} #{1 2 3}
      false #{1 2 3} #{1 2})))

(deftest winning-predicates-test
  (testing "Getting all numbers in a row or column should be considered a win"
    (are [expected called-numbers]
      (= expected (sut/board-wins? called-numbers
                    {:win? (sut/all-ways-to-win [[1 2 3]
                                                 [4 5 6]
                                                 [7 8 9]])}))
      true  #{1 2 3} ;; First row should win
      true  #{1 4 7} ;; First column should win
      true  #{3 6 9} ;; Last column should win
      nil   #{1 5 9} ;; Diagonal should lose
      ))
  (testing "Against real data"
    (are [expected called-numbers]
      (= expected (count (filter (partial sut/board-wins? called-numbers)
                           sut/bingo-boards)))
      1 #{58 60 65 4 91}  ;; First row of the first board should win
      1 #{58 73 98 76 55} ;; First col of the first board should win
      0 #{58 73 98 76 54} ;; Doesn't quite match the first column - should not win
      )))

(deftest example-winner-test
  (is (= 4512 (:score (first (sut/score-all-boards 1 example-bingo-numbers example-bingo-boards)))))
  (is (= 1924 (:score (last (sut/score-all-boards 1 example-bingo-numbers example-bingo-boards))))))
