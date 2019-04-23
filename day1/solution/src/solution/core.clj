(ns solution.core
  (:gen-class))

(defn convert-input [file-path] 
  (->> 
    (slurp file-path) 
    (map #(if (= % \( 1 -1 )))))
  
(defn first-pos [item coll] 
  (loop [pos 1 [x & xs] coll] 
    (cond 
      (empty? coll) nil
      (= x item) pos
      :else (recur (inc pos) xs))))
  
(defn part1 [file-path] 
  (->> 
    (convert-input file-path)
    (reduce +)))

(defn part2 [file-path] 
  (let [input (convert-input file-path)] 
    (first-pos -1 (reductions + input))))

(defn -main
  [& args]
  (println (part2 "resources/input.txt")))
