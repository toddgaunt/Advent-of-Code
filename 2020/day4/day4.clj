(require '[clojure.string :as str])

(defn read-input [filepath]
  (defn parse-line [line]
    (->> (str/split line #" ")
         (map #(str/split % #":"))
         (into {})))
  (with-open [r (clojure.java.io/reader filepath)]
    (->> (line-seq r)
         (partition-by #(= "" %))
         (map #(str/join " " %))
         (filter #(not= "" %))
         (map parse-line)
         (into []))))

(defn in? [coll elm] (some #(= elm %) coll))

(defn valid-byr [byr] (and (= 4 (count byr)) (<= 1920 (Integer. byr) 2002)))
(defn valid-iyr [iyr] (and (= 4 (count iyr)) (<= 2010 (Integer. iyr) 2020)))
(defn valid-eyr [eyr] (and (= 4 (count eyr)) (<= 2020 (Integer. eyr) 2030)))
(defn valid-hgt [hgt]
  (cond
    (str/ends-with? hgt "cm")
      (<= 150 (Integer. (str/replace hgt #"cm" "")) 193)
    (str/ends-with? hgt "in")
      (<= 59 (Integer. (str/replace hgt #"in" "")) 76)
    :else
      false))
(defn valid-hcl [hcl] (not (nil? (re-matches #"#[a-fA-F0-9]{6}" hcl))))
(defn valid-ecl [ecl] (in? ["amb" "blu" "brn" "gry" "grn" "hzl" "oth"] ecl))
(defn valid-pid [pid] (not (nil? (re-matches #"[0-9]{9}" pid))))

(defn has-all-fields? [passport]
  (def fields ["byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"])
  (every? #(contains? passport %) fields))

(defn has-valid-fields? [passport]
  (def validations 
    [(valid-byr (passport "byr"))
     (valid-iyr (passport "iyr"))
     (valid-eyr (passport "eyr"))
     (valid-hgt (passport "hgt"))
     (valid-hcl (passport "hcl"))
     (valid-ecl (passport "ecl"))
     (valid-pid (passport "pid"))])
  (every? true? validations))

(defn part1 [passports]
  (count (filter has-all-fields? passports)))

(defn part2 [passports]
  (count (->> passports
              (filter has-all-fields?)
              (filter has-valid-fields?))))

(def passports (read-input "input"))

(println (part1 passports))
(println (part2 passports))
