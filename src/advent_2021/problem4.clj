(ns advent-2021.problem4
  (:require [clojure.string :as string]
            [clojure.set :as set]
            [clojure.java.io :as io]
            [clojure.test :refer :all]))

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

(defn ->win-predicate
  "Create a predicate function that takes a set of all called numbers and
  determines if the board won or not by checking if all the required numbers
  were called."
  [required-numbers]
  (fn [called-numbers]
    (= (count required-numbers)
      (count (clojure.set/intersection required-numbers called-numbers)))))

(defn T
  "Matrix transpose"
  [matrix]
  (apply map vector matrix))

(defn all-rows-and-columns
  [board]
  (concat board (T board)))

(defn generate-winning-predicates
  "Generate a collection of functions that represent all the ways a board can win.
  For a given board, getting all entries in a row or column will win"
  [board]
  (->> board
    (all-rows-and-columns)
    (map (comp ->win-predicate set))
    (vec)))

(defn win?
  "Check if a board wins by checking if any of the possible winning conditions
  (predicates) are fulfilled"
  [called-numbers {:keys [winning-conditions] :as board}]
  (some identity ((apply juxt winning-conditions) called-numbers)))


;; Scoring
(defn uncalled-bingo-squares
  [all-bingo-squares called-numbers]
  (apply disj (set all-bingo-squares) called-numbers))

(defn append-score
  [called-numbers {:keys [raw-numbers] :as board}]
  (assoc board :score (* (apply + (uncalled-bingo-squares (set raw-numbers) called-numbers))
                        (last called-numbers))))

(defn score-boards
  ([n all-called-numbers boards]
   (score-boards n all-called-numbers boards []))
  ([n all-called-numbers boards already-won]
   (if (zero? (count boards))
     already-won
     (let [called-numbers (take n all-called-numbers)
           winners        (->> boards
                            (filter (partial win? (set called-numbers)))
                            (map (partial append-score called-numbers)))
           losers         (remove (partial win? (set called-numbers)) boards)]
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
                      {:winning-conditions (generate-winning-predicates board)
                       :raw-numbers        (sort (flatten board))
                       :board              board})}))

(def parsed-inputs
  (-> "aoc-4.txt"
    io/resource
    slurp
    parse-inputs))

(def bingo-numbers (:bingo-numbers parsed-inputs))
(def bingo-boards  (:bingo-boards parsed-inputs))

(:score (first (score-boards 1 bingo-numbers bingo-boards)));; => 11536
(:score (last (score-boards 1 bingo-numbers bingo-boards)));; => 1284
