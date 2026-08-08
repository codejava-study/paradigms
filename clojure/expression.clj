; @author Doschennikov Nikita (doschennicovn@gmail.com)

; === OPERATIONS ===
(defn operation [op]
  (fn [& parts]
    (fn [& vars]
      (apply op (mapv (fn [part] (apply part vars)) parts)))))

; Base
(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(def divide (operation (fn [& args]
                         (if (== 1 (count args))
                           (/ 1.0 (double (first args)))
                           (reduce (fn [a b] (/ (double a) (double b))) args)))))

(def negate (operation (fn [x] (- x))))

; 3233
(defn normalize [x]
  (- x (* 2 (Math/floor (/ (inc x) 2.0)))))

(def arcSin (operation (fn [x]
                         (Math/asin (normalize x)))))
(def arcCos (operation (fn [x]
                         (Math/acos (normalize x)))))
(def arcTan12 (operation (fn ([a] (Math/atan a))
                           ([a b] (Math/atan2 a b)))))

; 3435 + 3839
(def arithMean (operation (fn [& args]
                            (/ (double (apply + args)) (double (count args))))))
(def geomMean (operation (fn [& args]
                           (Math/pow (Math/abs (apply * args)) (/ 1.0 (count args))))))
(def harmMean (operation (fn [& args]
                           (/ (double (count args)) (apply + (mapv (fn [arg] (/ 1.0 (double arg))) args))))))

; 3637
(def sumExp (operation (fn [& args]
                         (apply + (mapv (fn [arg] (Math/exp arg)) args)))))
(def softMax (operation (fn [& args]
                          (/ (Math/exp (first args)) (apply + (mapv (fn [arg] (Math/exp arg)) args))))))

; === CONSTANTS ===
(defn constant [c]
  (fn [vars] c))

; === VARIABLES ===
(defn variable [varName]
  (fn [vars] (get vars varName)))

; === PARSING ===
(defn parseFunction [s]
  (letfn [(parse [x]
            (cond
              (number? x) (constant x)
              (symbol? x) (variable (str x))
              (list? x) (let [op (first x)
                              parsedArgs (map parse (rest x))]
                          (case op
                            + (apply add parsedArgs)
                            * (apply multiply parsedArgs)
                            / (apply divide parsedArgs)
                            - (if (== 1 (count parsedArgs))
                                (negate (first parsedArgs))
                                (apply subtract parsedArgs))
                            negate (negate (first parsedArgs))
                            asin (apply arcSin parsedArgs)
                            acos (apply arcCos parsedArgs)
                            atan12 (apply arcTan12 parsedArgs)
                            arithMean (apply arithMean parsedArgs)
                            geomMean (apply geomMean parsedArgs)
                            harmMean (apply harmMean parsedArgs)
                            sumExp (apply sumExp parsedArgs)
                            softMax (apply softMax parsedArgs)))))]
    (parse (read-string s))))

