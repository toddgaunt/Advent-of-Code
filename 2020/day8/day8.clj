(require '[clojure.set :as cljset])
(require '[clojure.string :as s])

(def input "input")

(defn read-input [filepath]
  (defn make-instr [line]
    (let [[instr value] (s/split line #" ")]
      [(keyword instr) (Integer. value)]))
  (with-open [r (clojure.java.io/reader filepath)]
    (->> (line-seq r)
         (map make-instr)
         (into []))))

(defn exec-instr [data [pc acc]]
  (let [[instr value] (nth data pc)]
    (case instr
      :acc [(+ pc 1) (+ acc value)]
      :jmp [(+ pc value) acc]
      ;; nop
      [(+ pc 1) acc])))

(defn run-data [data]
  (loop [visited #{}, pc 0, acc 0]
    (if (contains? visited pc)
      [acc :noterm]
      (let [[pc2 acc2] (exec-instr data [pc acc])]
        (if (>= pc2 (count data))
          [acc2 :term]
          (recur (conj visited pc) pc2 acc2))))))

(defn part1 [data]
  (first (run-data data)))

(defn part2 [data]
  (loop [fix 0]
    (let [[instr value] (data fix)
          fixed-data (case instr
            :nop (assoc data fix [:jmp value])
            :jmp (assoc data fix [:nop value])
            data)
          [acc term] (run-data fixed-data)]
      (if (= term :noterm)
        (recur (inc fix))
        acc))))

(def data (read-input input))

(println (part1 data))

(println (part2 data))
