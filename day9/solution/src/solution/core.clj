(ns solution.core
  (:require [clojure.math.combinatorics :refer [permutations]]
            [clojure.string :as str])
  (:gen-class))

(defn parse [line distances] 
  (let [[s _ d _ num] (str/split line #" ")
        dis (Integer. num)] 
    (assoc-in (assoc-in distances [s d] dis) [d s] dis)))

(defn get-distances [path] 
  (with-open [rdr (clojure.java.io/reader path)] 
    (reduce #(parse %2 %1) {} (line-seq rdr))))

(defn sum-distance [path distances]  
  (reduce + (map #(get-in distances %) (partition 2 1 path))))

(defn solve [path] 
  (let [distances (get-distances path)
        towns (keys distances)] 
    (->> 
      (permutations towns)
      (map #(sum-distance % distances))
      ((juxt #(apply max %) #(apply min %))))))

(defn -main
  [& args]
  (time (println (solve "resources/input.txt"))))
