(defn read-input [filepath]
  (defn parse-line [line]
    (mapv (fn [c]
            (cond
              (= c \.)
                :open
              (= c \#)
                :tree))
          line))
  (with-open [r (clojure.java.io/reader filepath)]
    (->> (line-seq r)
         (mapv parse-line))))

(def data (read-input "input"))

(defn hits-tree? [data y x]
  (let [y-bound (min y (dec (count data)))
        x-bound (mod x (count (nth data y-bound)))]
    (= ((data y-bound)  x-bound), :tree)))

(defn trees-hit [dy dx]
  (loop [n 0, y 0, x 0]
    (let [n2 (if (hits-tree? data y x) (inc n) n)
          y2 (+ y dy)
          x2 (+ x dx)]
    (cond
      (>= y (count data))
        n
      :else
        (recur n2 y2 x2)))))


;; Part 1
(println (trees-hit 1 3))

;; Part 2
(println (* (trees-hit 1 1)
            (trees-hit 1 3)
            (trees-hit 1 5)
            (trees-hit 1 7)
            (trees-hit 2 1)))
