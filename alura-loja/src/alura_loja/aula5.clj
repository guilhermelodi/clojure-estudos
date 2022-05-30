(ns alura-loja.aula5
  (:require [alura-loja.db :as l.db]
            [alura-loja.logic :as l.logic]))

; KEEP
; EAGER / LAZY

(defn gastou-bastante?
  [resumo]
  (> (:preco-total resumo) 400))

(let [pedidos (l.db/todos-os-pedidos)
      resumo (l.logic/resumo-por-usuario pedidos)]
  (println "Keep" (keep gastou-bastante? resumo)))
;Keep (true false true)

; take é lazy
(println (range 10))
(println (take 2 (range 10)))
(println (take 2 (range 100000000)))

(defn filtro1
  [x]
  (println "filtro1" x)
  x)

(defn filtro2
  [x]
  (println "filtro2" x)
  x)

; map utiliza chunks
; map é uma mescla de eager com lazy
(println "map é híbrido" (map filtro2 (map filtro1 (range 50))))

; mapv força eager
(println "mapv é eager" (mapv filtro2 (mapv filtro1 (range 50))))
