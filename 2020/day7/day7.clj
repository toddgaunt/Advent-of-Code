(require '[clojure.set :as cljset])
(require '[clojure.string :as s])

(def input "input")

(defn stripperiod [s]
  (if (= \. (last s))
    (subs s 0 (- (count s) 1))
    s))

(defn singularize [s]
  (if (= \s (last s))
    (subs s 0 (- (count s) 1))
    s))

(defn read-input [filepath]
  (defn make-bagrule [line]
    (let [split (s/split line #" contain ")
          left (first split)
          right (map #(stripperiod %) (s/split (second split) #","))]
      [
       (keyword (singularize (s/replace left #" " "-"))),
       (into {} (for [subbag right]
                   (if (= "no other bags." (second split))
                     '()
                     (let [trimmed (s/trim subbag)
                           split (s/split trimmed #" ")]
                       [ 
                        (keyword (singularize (s/join "-" (rest split)))),
                        (Integer. (first split)),
                       ]))))
      ]))
  (with-open [r (clojure.java.io/reader filepath)]
    (->> (line-seq r)
         (map make-bagrule)
         (into {}))))

(defn contains-gold-bag? [data k]
  (let [bags (data k)]
    (cond
      (= 0 (count bags)) false
      (contains? bags :shiny-gold-bag) true
      :else (not-every? false? (for [[k _] (seq bags)]
              (contains-gold-bag? data k))))))

(defn part1 [data]
  (->> (seq data)
    (map (fn [[k _]] (contains-gold-bag? data k)))
    (filter true?)
    (count)))

(defn count-bags [data k]
  (let [bags (k data)]
      (reduce + 1 (for [[k v] (seq bags)]
        (* v (count-bags data k))))))

(defn part2 [data]
    (dec (count-bags data :shiny-gold-bag)))

(def data (read-input input))

(println (part1 data))

(println (part2 data))
