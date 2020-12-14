(require '[clojure.string :as str])

(def input "input")

(defn read-input [filepath]
  (defn convert [ch]
    (cond
      (or (= \L ch) (= \F ch)) :LO
      (or (= \R ch) (= \B ch)) :HI
      :else :UNKNOWN))
  (with-open [r (clojure.java.io/reader filepath)]
    (->> (line-seq r)
         (map vec)
         (map #(map convert %))
         (vec))))

(def data (read-input input))

(defn binary-find [instr, lo, hi]
  (case (first instr)
  :LO (recur (rest instr) lo (- hi (quot (- hi lo -1) 2)))
  :HI (recur (rest instr) (+ lo (quot (- hi lo -1) 2)) hi)
  lo))

(defn find-seat-id [instr]
  (let [row (binary-find (take 7 instr) 0 127)
        seat (binary-find (drop 7 instr) 0 7)]
    (+ (* row 8) seat)))

(defn part1 [data]
  (->> data
       (map find-seat-id)
       (reduce max)))

(defn part2 [data]
  (let [data (sort (map find-seat-id data))]
    (loop [s data]
      (if (= 2 (- (second s) (first s)))
        (inc (first s))
        (recur (rest s))))))

;; Part 1
(println (part1 data))

;; Part 2
(println (part2 data))
