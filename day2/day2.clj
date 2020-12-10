(require '[clojure.string :as str])

(defn read-input [filepath]
  (defn parse-line [line]
    (let [left-right (str/split line #": ")
          lohi-letter (str/split (nth left-right 0) #" ")
          password (nth left-right 1)
          letter (.charAt (nth lohi-letter 1) 0)
          lo-hi (str/split (nth lohi-letter 0) #"\-")
          lo (nth lo-hi 0)
          hi (nth lo-hi 1)]
    {:lo (Integer/parseInt lo),
     :hi (Integer/parseInt hi),
     :letter letter,
     :password password}))
  (with-open [r (clojure.java.io/reader filepath)]
    (->> (line-seq r)
         (mapv parse-line))))

(defn count-chars [s c]
  (reduce + (map #(if (= c %) 1 0) s)))

(defn part1 [data]
  (defn meets-requirements [x]
    (let [count (count-chars (x :password) (x :letter))
          lo (x :lo)
          hi (x :hi)]
      (cond
        (<= lo count hi)
          1
        :else 0)))
  (->> data
       (map meets-requirements)
       (reduce +)))

(defn part2 [data]
  (defn meets-requirements [x]
    (let [password (x :password)
          letter (x :letter)
          ;; The indexes for this rule are 1-indexed
          lo (- (x :lo) 1)
          hi (- (x :hi) 1)
          c1 (nth password lo)
          c2 (nth password hi)]
      (cond
        (and (not= c1 c2) (or (= c1 letter) (= c2 letter))) 1
        :else 0)))
  (->> data
       (mapv meets-requirements)
       (reduce +)))

(def data (read-input "input"))
(println (part1 data))
(println (part2 data))
