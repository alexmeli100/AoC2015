(ns solution.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn to-int [s] 
  (map #(Integer/parseInt %) s))

(defn process-input [file-path] 
  (->> 
    (slurp file-path)
    (str/split-lines)
    (map #(str/split % #"x")) 
    (map to-int )))

(defn total [dimensions] 
  (let [[l w h] dimensions 
        [s1 s2 _] (sort dimensions)] 
    (+ (* 2 l w)
       (* 2 w h)
       (* 2 l h)
       (* s1 s2))))

(defn total2 [dimensions] 
  (let [[s1 s2 _] (sort dimensions)] 
    (+ (* 2 s1) (* 2 s2) (reduce * dimensions))))

(defn part1 [parsed-input] 
  (reduce 
    + 
    (map total parsed-input)))

(defn part2 [parsed-input] 
  (reduce 
    +
    (map total2 parsed-input)))

(defn -main
  [& args]
  (println (part2 (process-input "resources/input.txt"))))
