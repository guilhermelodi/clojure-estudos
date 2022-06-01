(ns curso3.colecoes
  (:use [clojure pprint]))

(defn testa-vetor []
  (let [espera [111 222]]
    (println espera)
    (println (conj espera 333))                             ; adicionou no final
    (println (conj espera 444))                             ; adicionou no final
    (println (pop espera))))                                ; removeu do final

(testa-vetor)

(defn testa-linked-list []
  (let [espera '(111 222)]
    (println espera)
    (println (conj espera 333))                             ; adicionou no começo
    (println (conj espera 444))                             ; adicionou no começo
    (println (pop espera))))                                ; removeu do começo

(testa-linked-list)

; conjunto = SET
(defn testa-conjunto []
  (let [espera #{111 222}]
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    ;(println (pop espera))    ; set não suporta pop
    ))

(testa-conjunto)

; fila - queue
(defn testa-fila []
  (let [espera (conj clojure.lang.PersistentQueue/EMPTY 111 222)]
    (println (seq espera))
    (println (seq (conj espera 333)))
    (println (seq (pop espera)))
    (println (peek espera))
    (pprint espera)))

(testa-fila)