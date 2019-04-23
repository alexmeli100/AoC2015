(ns solution.core
  (:gen-class))

(defn get-index [[x y]] 
  (+ y (* x 1000)))

(defn get-points [[x1 y1 x2 y2]] 
  (for [x (range x1 (inc x2)) 
        y (range y1 (inc y2))] 
    [y x]))

(defn get-action [action] 
  (let [turn-off 
          (fn [grid index] (assoc grid index false))
        turn-on 
          (fn [grid index] (assoc grid index true))
        toggle 
          (fn [grid index] (assoc grid index (not (get grid index))))]
    (cond 
      (= action "turn off") turn-off
      (= action "turn on") turn-on
      (= action "toggle") toggle)))

(defn execute-action [action grid points] 
  (reduce (get-action action) grid points))

(defn process-line [grid line] 
  (let [[_ action & indices] 
          (re-find #"(toggle|turn on|turn off)\s(\d*),(\d*)\s[a-z]+\s(\d*),(\d*)" line)
        points (map get-index (get-points (map #(Integer. %) indices)))] 
    (execute-action action grid points)))

(defn solve [file] 
  (let [grid (vec (boolean-array 1000000 false))] 
    (with-open [rdr (clojure.java.io/reader file)] 
      (reduce #(process-line %1 %2) grid (line-seq rdr)))))

(defn -main
  [& args]
  (time (println (count (filter identity (solve "resources/input.txt"))))))
