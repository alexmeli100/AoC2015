(ns solution.core
  (:require [clojure.set :as set])
  (:gen-class))

(defn get-direction [[x y] direction] 
  (case direction 
    \^ [x (inc y)]
    \v [x (dec y1)]
    \> [(inc x) y]
    \< [(dec x) y]))

(defn visited [input] 
    (set 
      (reductions #(get-direction %1 %2) [0 0] input)))

(defn part1 [input] 
  (count (visited input)))

(defn part2 [input] 
  (let [{evens true odds false} (group-by even? (range (count input))) 
        santa (map #(get input %) evens)
        robo-santa (map #(get input %) odds)] 
    (count 
      (set/union (visited santa) (visited robo-santa)))))

(defn -main
  [& args]
  (println (part2 (slurp "resources/input.txt"))))
