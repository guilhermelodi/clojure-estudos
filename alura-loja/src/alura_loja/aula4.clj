(ns alura-loja.aula4
  (:require [alura-loja.db :as l.db]
            [alura-loja.logic :as l.logic]))

(println (l.db/todos-os-pedidos))

(println (l.logic/resumo-por-usuario (l.db/todos-os-pedidos)))

(let [pedidos (l.db/todos-os-pedidos)
      resumo (l.logic/resumo-por-usuario pedidos)]
  (println "Resumo" resumo)
  (println "Ordenacao por preco" (sort-by :preco-total resumo))
  (println "Ordenacao ao contrario" (reverse (sort-by :preco-total resumo)))

  (println (get-in pedidos [0 :itens :mochila :quantidade])))

(defn resumo-por-usuario-ordenado
  [pedidos]
  (->> pedidos
       l.logic/resumo-por-usuario
       (sort-by :preco-total)
       reverse))

(println (resumo-por-usuario-ordenado (l.db/todos-os-pedidos)))

(let [pedidos (l.db/todos-os-pedidos)
      resumo (l.logic/resumo-por-usuario pedidos)]
  (println "Resumo ordenado" resumo)
  (println "Primeiro" (first resumo))
  (println "Segundo" (second resumo))
  (println "Resto" (rest resumo))
  (println "Qtd" (count resumo))
  (println "Apenas usuário 15" (filter #(= (:id-usuario %) 15) resumo))
  (println "nth 1" (nth resumo 1))
  (println "take" (take 2 resumo)))

(defn top-2
  [resumo]
  (take 2 resumo))

(let [pedidos (l.db/todos-os-pedidos)
      resumo (l.logic/resumo-por-usuario pedidos)]
  (println "top-2" (top-2 resumo)))

; ex: filtro de quem gastou mais de 400
(let [pedidos (l.db/todos-os-pedidos)
      resumo (l.logic/resumo-por-usuario pedidos)]
  (println "filtro gastou > 400:" (filter #(> (:preco-total %) 400) resumo))
  (println "filtro gastou > 400 retorna lista preenchida:" (not (empty? (filter #(> (:preco-total %) 400) resumo))))
  (println "alguem gastou > 400:" (some #(> (:preco-total %) 400) resumo)))