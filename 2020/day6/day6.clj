(require '[clojure.set :as cljset])

(def input "input")

(defn read-input [filepath]
  (with-open [r (clojure.java.io/reader filepath)]
    (->> (line-seq r)
         (partition-by #(= "" %))
         (filter #(not= '("")  %))
         (into []))))


(def data (read-input input))

(defn count-answers [group]
  (->> group
       (map set)
       (apply cljset/union)
       (count)))

(defn count-answers-2 [group]
  (->> group
       (map set)
       (apply cljset/intersection)
       (count)))

(defn part1 [data]
  (->> data
    (map count-answers)
    (reduce +)))

(defn part2 [data]
  (->> data
    (map count-answers-2)
    (reduce +)))

(println (part1 data))
(println (part2 data))
