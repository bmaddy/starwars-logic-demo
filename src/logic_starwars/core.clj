(ns logic-starwars.core
  (:refer-clojure :exclude [==])
  (:use clojure.core.logic))

(defrel jedi x)

(fact jedi 'Luke)
(fact jedi 'Yoda)
(fact jedi 'Vader)

(run 1 [q] (jedi q))
(run* [q] (jedi q))

(defrel father x y)
(defrel mother x y)

(fact father 'Vader 'Luke)
(fact mother 'Amidala 'Luke)
(fact father 'Vader 'Leia)
(fact mother 'Amidala 'Leia)

; Luke's father
(run* [q] (father q 'Luke))
; Vader's children
(run* [q] (father 'Vader q))

; Vader's jedi children
(run* [q] (father 'Vader q) (jedi q))
; Luke's parents
(run* [q]
      (conde
        ((mother q 'Luke))
        ((father q 'Luke))))

(defn parent [x y]
  (conde
    ((father x y))
    ((mother x y))))

;Luke's parents (2)
(run* [q] (parent q 'Luke))

;(fact parent 'Shmi 'Vader) ;fail!
(fact mother 'Shmi 'Vader)

(run* [q] (parent q 'Vader))

;(defn grandparent [x z]
;  (parent x (parent ? z))); wait a minute...

(defn grandparent [x z]
  (fresh [y]
         (parent x y)
         (parent y z)))

(run* [q] (grandparent 'Shmi q))
(run* [q] (grandparent 'Shmi q) (jedi q))
(run* [q]
      (conde
        ((grandparent 'Shmi q))
        ((parent 'Shmi q)))
      (jedi q))

; other stuff
; > (run* [q] (appendo [1 2] [3 4] q))
; ([1 2 3 4])
; > (run* [q] (appendo [1 2] q [1 2 3 4]))
; ([3 4])


; links
; https://github.com/swannodette/logic-tutorial
; https://github.com/frenchy64/Logic-Starter/wiki
; http://clojurelx.blogspot.com/2012/02/lx-in-corelogic-3-finite-state.html

