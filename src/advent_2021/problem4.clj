(ns advent-2021.problem4
  (:require [clojure.string :as string]
            [clojure.set :as set]
            [clojure.java.io :as io]
            [clojure.test :refer :all]
            [advent-2021.core :as aoc]))

;; Basic parsing
(defn row->ints
  [s]
  (vec (map #(Integer/parseInt %)
         (string/split (string/trim s) #"\s+"))))


;; Determining if a board won or not is a function of the Bingo numbers called
;; so far and the spaces on the particular board.
;;
;; Once we have the board, we can create a set of predicate functions such that,
;; if any of them evaluate to true, indicate that a given board wins

(defn ->win-condition
  "Create a predicate function that takes a set of all called numbers and
  determines if the board won or not by checking if all the required numbers
  were called."
  [required-numbers]
  (fn [called-numbers]
    (= (count required-numbers)
      (count (clojure.set/intersection required-numbers called-numbers)))))

(defn row-win-conditions [board]
  (map (comp ->win-condition set) board))

(defn column-win-conditions [board]
  (map (comp ->win-condition set) (aoc/T board)))

(defn all-ways-to-win
  "Generate a function that represent all the ways a board can win.
  For a given board, getting all entries in a row or column will win"
  [board]
  (->> [row-win-conditions
        column-win-conditions]
    (mapcat (fn [win-condition-generator]
              (win-condition-generator board)))
    (apply some-fn)))

(defn board-wins?
  [called-numbers {:keys [win?] :as board}]
  (win? (set called-numbers)))

;; Scoring
(defn uncalled-squares
  [{:keys [raw-numbers] :as board} called-numbers]
  (apply disj (set raw-numbers) called-numbers))

(defn append-score
  [called-numbers board]
  (assoc board :score (* (apply + (uncalled-squares board called-numbers))
                        (last called-numbers))))

(defn score-all-boards
  ([all-called-numbers boards]
   (score-all-boards 1 all-called-numbers boards []))
  ([n all-called-numbers boards already-won]
   (if (zero? (count boards))
     already-won
     (let [called-numbers (take n all-called-numbers)
           winners        (->> boards
                            (filter (partial board-wins? called-numbers))
                            (map (partial append-score called-numbers)))
           losers         (remove (partial board-wins? called-numbers) boards)]
       (recur (inc n) all-called-numbers losers (concat already-won winners))))))


;; Input parsing
(defn parse-inputs
  [inputs]
  (let [[header & more] (string/split-lines inputs)]
    {:bingo-numbers (map #(Integer/parseInt %) (string/split header #","))
     :bingo-boards  (for [board (->> more
                                  (remove string/blank?)
                                  (map row->ints)
                                  (partition 5))]
                      {:win?        (all-ways-to-win board)
                       :raw-numbers (sort (flatten board))
                       :board       board})}))

(def parsed-inputs
  (-> "aoc-4.txt"
    io/resource
    slurp
    parse-inputs))

(def bingo-numbers (:bingo-numbers parsed-inputs))
(def bingo-boards  (:bingo-boards parsed-inputs))

(:score (first (score-all-boards bingo-numbers bingo-boards)));; => 11536
(:score (last (score-all-boards bingo-numbers bingo-boards)));; => 1284
