(ns solution.core
  (:require [clojure.java.io :as io])
  (:gen-class))

(defn parse-line [line]
  (->> 
    (re-seq #"\d+" line)
    (map #(Integer. %))))

(defn read-file [path] 
  (with-open [rdr (io/reader path)] 
    (doall (map parse-line (line-seq rdr)))))

(defn dis-travelled [time [s g r]] 
  (+ 
    (* s g (quot time (+ g r)))
    (* s (min g (rem time (+ g r))))))

(defn distances [time reindeers] 
  (map-indexed 
    (fn [i x ] [i (dis-travelled time x)]) reindeers))

;; part 2
(defn update-points [points time reindeers] 
  (let [dis (distances time reindeers)
        max-dis (second (apply max-key second dis))] 
    (->> 
      (filter #(= (second %) max-dis) dis)
      (map first)
      (reduce #(update %1 %2 inc) points))))

(defn points [time reindeers] 
  (reduce 
    #(update-points %1 %2 reindeers) 
    (vec (repeat (count reindeers) 0))
    (range 1 (inc time))))

(defn -main
  [& args]
  (->>
    (read-file "resources/input.txt") 
    (distances 2503)
    (apply max-key second)
    println))
