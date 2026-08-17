; @author Doschennikov Nikita (doschennicovn@gmail.com)

; === UTIL ===

(defn equalLengthed? [& elements]
  (apply == (mapv count elements)))

; === VECTORS ===

(defn v? [v]
  (and (coll? v) (every? number? v)))

(defn vOp [op & vs]
  {:pre [(every? v? vs) (apply equalLengthed? vs)]}
  (apply mapv op vs))

(def v+ (partial vOp +))
(def v- (partial vOp -))
(def v* (partial vOp *))
(def vd (partial vOp /))

(defn v*s [v & s]
  (mapv (fn [x] (* x (apply * s))) v))

(defn scalar [& vs]
  (apply + (apply v* vs)))

(defn vect2 [v1 v2]
  {:pre [(v? v1)
         (v? v2)
         (== 3 (count v1) (count v2))]}
  (let [[ax ay az] v1
        [bx by bz] v2]
    (vector (- (* ay bz) (* az by))
            (- (* az bx) (* ax bz))
            (- (* ax by) (* ay bx)))))

(defn vect [& vs]
  (reduce vect2 vs))

; === MATRICIES ===

(defn m? [m]
  (and (coll? m) (every? v? m)))

(defn mOp [op & ms]
  {:pre [(every? m? ms) (apply equalLengthed? ms)]}
  (apply mapv (fn [& lines] (apply vOp op lines)) ms))

(def m+ (partial mOp +))
(def m- (partial mOp -))
(def m* (partial mOp *))
(def md (partial mOp /))

(defn m*s [m & s]
  {:pre [(m? m)
         (every? number? s)]}
  (mapv (fn [line] (apply v*s line s)) m))

(defn m*v [m v]
  (mapv (fn [line] (scalar line v)) m))

(defn transpose [m]
  (apply mapv vector m))

(defn m*m2 [m1 m2]
  {:pre [(m? m1) (m? m2)]}
  (let [m2T (transpose m2)]
    (mapv (fn [line] (m*v m2T line)) m1)))

(defn m*m [& ms]
  (reduce m*m2 ms))

; 3233, 3435

(defn sOp [op & args]
  (if (every? number? args)
    (apply op args)
    (apply mapv (partial sOp op) args)))

(def s+ (partial sOp +))
(def s- (partial sOp -))
(def s* (partial sOp *))
(def sd (partial sOp /))

; 3637

(defn xOp2 [op a b]
  (if (and (number? a) (number? b))
    (op a b)
    (let [la (count a)
          lb (count b)
          n (min la lb)
          combined (mapv (fn [i] (xOp2 op (nth a i) (nth b i))) (range n))
          leftover (subvec (if (> la lb) a b) n)]
      (into combined leftover))))

(defn xOp [op & xs]
  (reduce (partial xOp2 op) xs))

(def x+ (partial xOp +))
(def x- (partial xOp -))
(def x* (partial xOp *))
(def xd (partial xOp /))
