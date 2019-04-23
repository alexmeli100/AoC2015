(ns solution.core
  (:gen-class))

(defn solve [input] 
  (->> 
    input
    (partition-by identity)
    (map #(str (count %) (first %)))
    (apply str)))

(defn -main
  [& args]
  (println (count (nth (iterate solve "1113122113") 50))))
