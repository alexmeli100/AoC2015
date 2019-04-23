(ns solution.core
  (:require [clojure.java.io :as io] 
            [clojure.set :as set])
  (:gen-class))

(def moves [[1 0] [-1 0] [0 1] [0 -1] [1 1] [-1 -1] [1 -1] [-1 1]])

(defn check-line [y line] 
  (keep-indexed #(when (= %2 \#) [%1 y]) line))

(defn get-state [path] 
  (with-open [rdr (io/reader path)] 
    (->> 
      (line-seq rdr)
      (mapcat #(check-line %1 %2) (range 100))
      (into #{}))))

(defn valid-coord [coord] 
  (every? #(<= 0 %1 99) coord))

(defn neighbors [coord state] 
  (->> 
    (map #(map + % coord) moves)
    (filter valid-coord)
    (into #{})
    (set/intersection state)
    count))

(defn next-cell [state coord] 
  (let [neighbors-on (neighbors coord state)] 
    (cond 
      (and (state coord) (<= 2 neighbors-on 3)) coord
      (and (not (state coord)) (= neighbors-on 3)) coord
      :else nil)))

(defn coords [] 
  (for [x (range 100) y (range 100)] [x y]))

(defn next-state [check-cell state] 
  (->> 
    (coords)
    (keep (partial check-cell state))
    (into #{})))

(def always-on #{[0 0] [99 0] [0 99] [99 99]})

(defn next-cell-part2 [state coord] 
  (if (always-on coord) 
    coord 
    (next-cell state coord)))

(defn solve [initial-state] 
  (->
    (iterate (partial next-state next-cell-part2) initial-state)
    (nth 100)
    count))

(defn -main
  [& args]
  (let [state (set/union (get-state "resources/input.txt") always-on)] 
    (time (println (solve state)))))
