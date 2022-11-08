(ns advent-2021.problem8
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.string :as string]))


(def example-input
  "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce")

(def raw-input
  (string/trim (slurp (io/resource "aoc-8.txt"))))

;; Helpers
(defn split-on-whitespace
  [s]
  (string/split s #" "))

(defn parse-tokens
  [v]
  (let [scrambled-output (rest (drop-while (partial not= "|") v))
        scrambled-inputs (take-while (partial not= "|") v)]
    {:signal-pattern (map set scrambled-inputs)
     :digital-output (map set scrambled-output)}))

(defn ingest
  [input-string]
  (->> (string/split input-string #"\n")
       (map (comp parse-tokens split-on-whitespace))))

(defn classify
  [s]
  (cond
    (= 2 (count s)) 1
    (= 4 (count s)) 4
    (= 3 (count s)) 7
    (= 7 (count s)) 8))

(defn count-numbers
  [input-string]
  (->> input-string
       ingest
       (mapcat (comp (partial map classify) :digital-output))
       (filter identity)
       count))

(count-numbers example-input);; => 26
(count-numbers raw-input);; => 278

;; Part 2
;;
(defn zero?'
  [state s]
  (let [[one nine] (map (partial get state) [1 9])]
    (and one nine
         (= 6 (count s))
         (= 2 (count (set/intersection one s))))))

(defn one?'
  [state s]
  (= 2 (count s)))

(defn two?
  [state s]
  (let [[five three] (map (partial get state) [5 3])]
    (and five three (= 5 (count s)))))

(defn three?
  [state s]
  (let [[one nine] (map (partial get state) [1 9])]
    (and nine
         (= 5
            (count s)
            (count (set/intersection nine s)))
         (= 2 (count (set/intersection one s))))))

(defn four?
  [state s]
  (= 4 (count s)))

(defn five?
  [state s]
  (let [six (get state 6)]
    (and six
         (= 5
            (count s)
            (count (set/intersection six s))))))

(defn six?
  [state s]
  (let [zero (get state 0)]
    (and zero (= 6 (count s)))))

(defn seven?
  [state s]
  (= 3 (count s)))

(defn eight?
  [state s]
  (= 7 (count s)))

(defn nine?
  [state s]
  (let [[four seven] (map (partial get state) [4 7])]
    (and four seven
         (= 6 (count s))
         (= 5 (count (set/intersection (set/union four seven) s))))))


(defn classify-2
  [state s]
  (cond
    (zero?' state s) 0
    (one?'  state s) 1
    (two?   state s) 2
    (three? state s) 3
    (four?  state s) 4
    (five?  state s) 5
    (six?   state s) 6
    (seven? state s) 7
    (eight? state s) 8
    (nine?  state s) 9))

(defn derive-numbers
  [segment-seq]
  (loop [[current & more] segment-seq
         state            {}]
    ;;(println current more state)
    ;;(Thread/sleep 100)
    (let [n (classify-2 state current)]
      (cond
        (nil? current) state
        (nil? n)       (recur (concat more [current]) state)
        :else          (recur more (assoc state n current))))
    ))

(defn solve-single
  [{:keys [signal-pattern digital-output] :as x}]
  (let [mapping (set/map-invert (derive-numbers signal-pattern))
        decoded (map (partial get mapping) digital-output)]
    (assoc x
           :decoded decoded
           :value   (Integer/parseInt (apply str decoded)))))

(defn solve
  [input-string]
  (->> input-string
       ingest
       (map solve-single)))

(reduce +
        0
        (map :value (solve example-input)));; => 61229


(reduce +
        0
        (map :value (solve raw-input)));; => 986179
